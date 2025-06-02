# server.py
import socket
import threading
import pygame
from dotenv import load_dotenv
from logs import log, trylog, inspect_object
import os
from utils import send, receive 
import datetime, time
import enums

from message import ( 
    MoveMessage, SetPlayerNameMessage, GetPlayerMessage, 
    AllowCollectItemsMessage, RemoveInProcessMoveMessage)

from game_board import GameBoard

from enums import GameStatus, PlayerStatus
from config import Config
import config_server

class Server:
    def __init__(self,  host=None, port=None, test_mode: bool= False):

        load_dotenv(override=True)

        self.host = host or os.environ.get('SERVER', '0.0.0.0')
        self.port = port or int(os.environ.get('PORT', 4444))
        self.test_mode = test_mode
        log(f'Listern to {self.host}:{self.port}', '[SERVER]')
        self.tick_count:int = 0 
        self.fps = Config.FPS #  frame per second
        self.clients = {}  
        self.lock = threading.Lock()

        self.server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.server_socket.bind((self.host, self.port))
        self.server_socket.listen(200)

        self.game_board = GameBoard()
        self.game_client_dispatcher = None
        
        
    def start(self):
        # Thread to connect to client
        threading.Thread(target=self.accept_clients, daemon=True).start()

        # Start game loop
        self.start_game_loop()
    
    def send(self, sock, obj):
        with self.lock:
            send(sock, obj)

    def accept_clients(self):
        if not self.game_client_dispatcher:
            log("Server started. Waiting for dispatcher...", "[SERVER]")
        else:
            log("Server started. Waiting for clients...", "[SERVER]")
            
        while True:
            if self.game_board.game_status == GameStatus.WAITING_FOR_PLAYERS:
                client_socket, addr = self.server_socket.accept()
                log(f"New connection from addr: {addr}, client_socket: {client_socket}", "[SERVER]")
                if not self.game_client_dispatcher:
                    self.game_client_dispatcher = client_socket
                    threading.Thread(target=self.handle_client, args=(client_socket,), daemon=True).start()
                    time.sleep(.5)
                    self.send(client_socket, "Connected")

                else:
                    player = self.game_board.create_random_player(id=len(self.game_board.players))
                    self.clients[client_socket] = player.id

                    log(f"Send init player {player} to client {addr}", "[SERVER]")
                    #  use thread for each client to receive client message 

                    threading.Thread(target=self.handle_client, args=(client_socket,), daemon=True).start()
                    time.sleep(.5)
                    self.send(client_socket, player)
    
    def move_player(self,player, dir):
        has_move = False
        r, c = player.row, player.col
        if dir == enums.Direction.LEFT.value:
            if self.game_board.map.can_move(player, enums.Direction.LEFT):
                player.col -= 1
                has_move = True
        elif dir == enums.Direction.RIGHT.value:
            if self.game_board.map.can_move(player, enums.Direction.RIGHT):
                player.col += 1
                has_move = True
        elif dir == enums.Direction.UP.value:
            if self.game_board.map.can_move(player, enums.Direction.UP):
                player.row -= 1
                has_move = True
        if dir == enums.Direction.DOWN.value:
            if self.game_board.map.can_move(player, enums.Direction.DOWN):
                player.row += 1
                has_move = True
        if has_move:
            self.game_board.map.set_value(r, c, 'g')
        return has_move

    def collision_result(self, player, other_player):

        # ⚔️ Sword vs ⚔️ Sword & ⚔️ Sword vs 🛡️ Shield: Agent loses tool and continues moving 

        if player.sword and other_player.sword:
            player.sword = 0
            other_player.sword = 0
        elif player.sword and other_player.armor:
            player.sword = 0
            other_player.armor = 0
        elif player.armor and other_player.sword:
            player.armor = 0
            other_player.sword = 0

        #- ⚔️ Sword vs No Sword/Shield: Agent loses sword & continues moving, the remaining agent is returned to Home & cannot 
        elif player.sword and not other_player.sword and not other_player.armor:
            other_player.items_on_hand = []
            self.game_board.map.set_value(other_player.row, other_player.col, 'g')
            other_player.row, other_player.col = other_player.home_row, other_player.home_col
            other_player.status = PlayerStatus.PAUSED
            other_player.paused_time = datetime.datetime.now().timestamp()
            

    def handle_collision_at_position(self, player, row, col):
        # Retrieve the grid element at the given position
        grid_element = player.grid[row][col]

        # Check if the element is numeric (indicating another player)
        if grid_element.isnumeric():
            # Get the other player using the grid value as the player index
            other_player_index = int(grid_element)
            other_player = self.game_board.players.get(other_player_index)

            # Ensure the player exists and is in the playing state
            if other_player and other_player.status == PlayerStatus.PLAYING:
                log(f'Player {player.name} collided with player {other_player.name}', '[SERVER]')
                self.collision_result(player, other_player)
    
    def collision(self, player):

        # check collision with other players in up
        if player.row>0:
            self.handle_collision_at_position(player, player.row-1, player.col)
        # check collision with other players in down
        if player.row<self.game_board.map.n_row-1:
            self.handle_collision_at_position(player, player.row+1, player.col)
        # check collision with other players in left
        if player.col>0:
            self.handle_collision_at_position(player, player.row, player.col-1)
        # check collision with other players in right
        if player.col<self.game_board.map.n_col-1:
            self.handle_collision_at_position(player, player.row, player.col+1)
        
    def update_player(self, player, dir=None):
        player.last_updated = datetime.datetime.now().timestamp()
        if player.status == PlayerStatus.PAUSED:
            log(f'Player {player.name} is dead, cannot move', '[SERVER]')
            if player.paused_time + Config.PAUSED_TIME < datetime.datetime.now().timestamp():
                player.status = PlayerStatus.PLAYING
                player.row, player.col = player.home_row, player.home_col
                self.game_board.map.set_value(player.row, player.col, player.id)
            return  
        
        if dir is not None:
            if player.status == PlayerStatus.PLAYING:
                self.move_player(player, dir)
        elif len(player.in_process_move_messages) > 0:
            message = player.in_process_move_messages.pop(0)
            if isinstance(message, MoveMessage):
                if player.status == PlayerStatus.PLAYING:
                    dir = message.dir
                    self.move_player(player, dir)
                            
        self.game_board.update_nearby_map_area(player)
        self.collision(player)
        self._collect_items(player)
        self._store_items_if_at_home(player)
        
        self.convert_wood_cotton_to_fabric(player)
        self._check_win_condition(player)

        self.game_board.map.set_value(player.row, player.col, player.id)

    def convert_wood_cotton_to_fabric(self, player):
        c = player.store.count('c')
        fa = c // config_server.COTON_TO_FABRIC
        if fa > 0:
            log(f'Convert wood and cotton to fabric for player {player.name}', '[SERVER]')
            player.store = [x for x in player.store if x != 'c']
            player.store += ['fa'] * fa
            player.store += ['c'] * (c % config_server.COTON_TO_FABRIC)
                                
    def _collect_items(self, player):
        with self.lock:
            # Collect items
            self.game_board.map.collect_items(player)

            # update items bring to home
    
    def _store_items_if_at_home(self, player):
        if self.game_board.map.at_home(player):
            capacity = Config.MAX_STORAGE_CAPACITY - len(player.store)
            if capacity >= len(player.items_on_hand):
                player.store += player.items_on_hand
                player.items_on_hand = []
            else:
                # store only part of items
                player.store += player.items_on_hand[:capacity]
                player.items_on_hand = player.items_on_hand[capacity:]
    
    def _check_win_condition(self, player):
        fa = player.store.count('fa')
        w = player.store.count('w')
        if fa >= Config.WIND_N_FABRIC and w >= Config.WIND_N_WOOD:
            player.status = PlayerStatus.WIN
            log(f'Player {player.name} win the game', '[SERVER]')
            open(f'winner_{player.name}.txt', 'a').write(f'{player.name} won the game in {self.game_board.tick / Config.FPS}s\n')

    def depatcher_process(self, client_socket):
        while True:
            client_message = receive(client_socket)
            if client_message is None:
                time.sleep(1/Config.FPS)
                continue

            log(f'Revceive from dispatcher: {client_message}', '[SERVER]')

            log(f'Broadcast message to all players')
            for player_id in self.game_board.players:
                self.game_board.messages.append(client_message)
                self.game_board.players[player_id].message = client_message
            
            # self.broadcast_message_start_game()
 
    def client_process(self, client_socket):
        player_id = self.clients[client_socket]
        player = self.game_board.players[player_id]
        log(f'Handle client with player id is {player_id}', '[SERVER]')
        try:
            while True:

                # only process when time tick
                # Receive message from server
                
                if self.game_board.game_status == GameStatus.FINISHED:
                    continue

                client_message = receive(client_socket)
                if client_message is None:
                    continue

                # Messges need to process imeddiately

                if player.status == PlayerStatus.WIN:
                    continue

                if isinstance(client_message, SetPlayerNameMessage):
                    if self.game_board.game_status == GameStatus.WAITING_FOR_PLAYERS:
                        player.name = client_message.player_name
                        player.last_updated = datetime.datetime.now().timestamp()
                        self.send(client_socket, client_message.player_name)

                    continue

                if isinstance(client_message, AllowCollectItemsMessage):
                    player.last_updated = datetime.datetime.now().timestamp()
                    player.allow_collect_items = client_message.items
                    self.send(client_socket, player.allow_collect_items)
                    continue

                if isinstance(client_message, GetPlayerMessage):
                    self.send(client_socket, player)
                    continue

                # Process messages by time tick
                if self.game_board.game_status == GameStatus.PLAYING:
                    if isinstance(client_message, MoveMessage):
                        player.in_process_move_messages.append(client_message)
                    
                        continue

                if isinstance(client_message, RemoveInProcessMoveMessage):
                    # remove all in process messages
                    player.in_process_move_messages = []
                    continue
                
        except Exception as e:
            log(f"Error with client {player_id}: {e}", "[SERVER]")

        finally:
            with self.lock:
                if player_id in self.game_board.players:
                    del self.game_board.players[player_id]
                if client_socket in self.clients:
                    del self.clients[client_socket]
            client_socket.close()
            if self.game_board.current_player_index >= len(self.game_board.players):
                self.game_board.current_player_index = -1

    # Process client message
    def handle_client(self, client_socket):
        if client_socket == self.game_client_dispatcher:
            self.depatcher_process(client_socket)
        else:
            self.client_process(client_socket)

    def update_status_all_players(self, status):
        for client_socket, player_id in self.clients.items():
            player = self.game_board.players[player_id]
            player.status = status

    def broadcast_message(self):
        
        for client_socket, player_id in self.clients.items():
            player = self.game_board.players[player_id]
            self.send(client_socket, player)

    def test_mode_play(self, event):
        if self.game_board.current_player_index == -1:
            return
        player = self.game_board.players[self.game_board.current_player_index]
        dir = 0
        if event.key == pygame.K_LEFT: dir = 0
        if event.key == pygame.K_RIGHT: dir = 1
        if event.key == pygame.K_UP: dir = 2
        if event.key == pygame.K_DOWN: dir = 3
        
        self.update_player(player, dir)

    def update(self):
        for player_id, player in self.game_board.players.items():
            if player.status != PlayerStatus.WIN:
                self.update_player(player)

    def start_game_loop(self):    
        clock = pygame.time.Clock()
        while True:
            events = pygame.event.get()

            # Process events
            for event in events:
                if event.type == pygame.QUIT:
                    pygame.quit()
                    return
                if event.type == pygame.KEYDOWN:
                    
                    # START GAME
                    if event.key == pygame.K_s:
                        # change to start game
                        self.game_board.game_status = GameStatus.PLAYING
                        self.game_board.tick =0
                        # notify all players
                        self.update_status_all_players(PlayerStatus.PLAYING)
                    elif event.key == pygame.K_EQUALS: #pygame.K_RIGHT:
                        self.game_board.current_player_index +=1
                        if self.game_board.current_player_index >= len(self.game_board.players):
                            self.game_board.current_player_index = -1
                    elif event.key == pygame.K_MINUS: #pygame.K_LEFT:
                        self.game_board.current_player_index -=1
                        if self.game_board.current_player_index <-1:
                            self.game_board.current_player_index = len(self.game_board.players) -1

                    elif event.key == pygame.K_ESCAPE:
                        pygame.quit()
                        return
                    elif self.test_mode:
                        # allow move player in test mode
                        self.test_mode_play(event)
            
            

            if self.game_board.game_status == GameStatus.PLAYING:
                # Update game state
                self.update()
                if self.game_board.tick /Config.FPS >= Config.GAME_DURATION:
                    self.game_board.game_status = GameStatus.FINISHED
                    log(f'Game FINISHED', '[SERVER]')

            # Draw the game board
            self.game_board.draw()

            pygame.display.flip()
            clock.tick(self.fps)
            
if __name__ == "__main__":
    server = Server(test_mode=True)
    server.start()