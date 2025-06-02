from pydantic import BaseModel
import pygame
from player import Player
import time, datetime
import random
from map import Map
from logs import log
from utils import display_image, load_image
from pathlib import Path
from utils import draw_text, draw_energy
from config import Config
from enums import PlayerStatus
from enums import GameStatus
import numpy as np
import threading
from collections import Counter


class GameBoard():
    def __init__(self):
        self.start_x = 2
        self.start_y = 60
        self.CELL_SIZE: int = Config.CELL_SIZE
        # so cell mo bang do
        self.OPEN_CELL = Config.OPEN_CELL

        self.n_row = Config.N_ROW
        self.n_col = Config.N_COL
        self.map: Map = Map(self.n_row, self.n_col)
        self.map.random_map()
        self.width = self.n_col * self.CELL_SIZE
        self.height =self.n_row * self.CELL_SIZE
        
        

        self.game_status: GameStatus = GameStatus.WAITING_FOR_PLAYERS
        self.tick = 0

        pygame.display.set_caption("Server Game Dashboard")

        # Dashboard game
        pygame.init()
        self.screen = pygame.display.set_mode((self.width + 2 * self.start_x, self.height + self.start_y + 10), pygame.NOFRAME)

        self.players = {}

        # text fonnt
        self.text_font_size=16
        self.text_font_name="Courier New"
        self.text_font = pygame.font.SysFont(self.text_font_name, self.text_font_size, bold=True)  
        self.text_color = (0, 255, 0)
        self.text_background_color = (0, 0, 0)        

        self.icon_font_size = 16
        self.icon_font_name="Courier New"
        self.icon_color = (255, 0, 0)
        self.icon_font = pygame.font.SysFont(self.icon_font_name, self.icon_font_size, bold=True) 
        self.icon_background_color = (255, 255, 255)    

        # Only display current player info
        # Can use hot key to select player
        self.current_player_index = -1   

        self.images = self.load_images()

        self.lock = threading.Lock()
        self.messages = []
        
        self.draw()

    def load_images(self):

        # Load players images
        player_image_paths = [file.name for file in Path('img/players').glob('*.*')]
        
        images = {}
        images['players'] = {}
        for i, img_path in enumerate(player_image_paths):
            images['players'][i] = load_image(Path('img/players') / img_path, self.CELL_SIZE, self.CELL_SIZE)

        player_image_paths = [file.name for file in Path('img/houses').glob('*.*')]
        images['houses'] = {}
        for i, img_path in enumerate(player_image_paths):
            images['houses'][i] = load_image(Path('img/houses') / img_path, self.CELL_SIZE, self.CELL_SIZE)

        # Load players map and assests
        images['maps'] = {}

        images['maps']['black'] = load_image(Path('img/maps/black.png'), self.CELL_SIZE, self.CELL_SIZE)

        images['maps']['a'] = load_image(Path('img/maps/armor.png'), self.CELL_SIZE, self.CELL_SIZE)
        images['maps']['c'] = load_image(Path('img/maps/cotton.png'), self.CELL_SIZE, self.CELL_SIZE)
        images['maps']['f'] = load_image(Path('img/maps/food.png'), self.CELL_SIZE, self.CELL_SIZE)
        images['maps']['g'] = load_image(Path('img/maps/grass.png'), self.CELL_SIZE, self.CELL_SIZE)
        images['maps']['s'] = load_image(Path('img/maps/sword.png'), self.CELL_SIZE, self.CELL_SIZE)
        images['maps']['w'] = load_image(Path('img/maps/wood.png'), self.CELL_SIZE, self.CELL_SIZE)
        # 
        images['maps']['f-a'] = load_image(Path('img/maps/f-armor.png'), self.CELL_SIZE, self.CELL_SIZE)
        images['maps']['f-c'] = load_image(Path('img/maps/f-cotton.png'), self.CELL_SIZE, self.CELL_SIZE)
        images['maps']['f-f'] = load_image(Path('img/maps/f-food.png'), self.CELL_SIZE, self.CELL_SIZE)
        images['maps']['f-g'] = load_image(Path('img/maps/f-grass.png'), self.CELL_SIZE, self.CELL_SIZE)
        images['maps']['f-s'] = load_image(Path('img/maps/f-sword.png'), self.CELL_SIZE, self.CELL_SIZE)
        images['maps']['f-w'] = load_image(Path('img/maps/f-wood.png'), self.CELL_SIZE, self.CELL_SIZE)
        
        return images
        
    def draw_clock(self):
        # Convert tick (seconds) to HH:MM:SS format
        time_str = str(datetime.timedelta(seconds=int(self.tick/Config.FPS)))

        # Font and colors
        font = pygame.font.SysFont("Courier New", 26, bold=True)
        GREEN = (0, 255, 0)
        BG_COLOR = (10, 10, 10)
        padding = 8
        inner_padding = 8

        # Render time
        text_surface = font.render(time_str, True, GREEN)
        text_rect = text_surface.get_rect()

        # Top-right position
        screen_width = self.screen.get_width()
        box_x = screen_width - text_rect.width - inner_padding * 2 - padding
        box_y = padding
        box_width = text_rect.width + inner_padding * 2
        box_height = text_rect.height + inner_padding * 2

        # Background box
        pygame.draw.rect(self.screen, BG_COLOR, (box_x, box_y, box_width, box_height), border_radius=8)

        # Blit time text
        self.screen.blit(text_surface, (box_x + inner_padding, box_y + inner_padding))

    def create_random_player(self, id:str) -> Player:
        while True:
            row=random.randint(0, self.n_row)
            # row = random.randint(0, 5)
            col=random.randint(0, self.n_col)
            if self.map.get_value(row, col) == 'g':
                break

        grid = np.full((self.n_row , self.n_col), '-1')
        player = Player(
            id=id,
            row=row,
            col=col,
            home_row = row,
            home_col = col,
            color=(random.randint(0, 255), random.randint(0, 255), random.randint(0, 255)),
            last_updated = datetime.datetime.now().timestamp(),
            grid = grid, 
            map_h= self.n_row,
            map_w= self.n_col
        )
        self.players[id] = player
        print(f'add player: {id}, self.players len: {len(self.players)}')
        # update map
        self.map.set_value(row, col, f'{id}')
        # update nearby map area

        log(f'type of map ')

        self.update_nearby_map_area(player)
        
        return player

    def update_nearby_map_area(self, player):
        Map.copy_grid(self.map.grid, player.grid, player.row - self.OPEN_CELL//2, player.row + self.OPEN_CELL//2,
                     player.col - self.OPEN_CELL//2, player.col + self.OPEN_CELL//2)
        

    def draw_players(self):
        visible_all = self.current_player_index == -1   
        
        with self.lock:
            if visible_all:
                for id, player in self.players.items():
                    self.draw_home(player)
                    self.draw_player(player)
            else:
                player = self.players[self.current_player_index]
                self.draw_home(player)
                self.draw_player(player)

    def draw_player(self, player):
        x = self.start_x + player.col * self.CELL_SIZE 
        y = self.start_y + player.row * self.CELL_SIZE
        display_image(self.screen, self.images['players'][int(player.id)], x+1, y+1)

    def draw_home(self, player):
        x = self.start_x + player.home_col * self.CELL_SIZE 
        y = self.start_y + player.home_row * self.CELL_SIZE
        display_image(self.screen, self.images['houses'][int(player.id)], x, y)

    # draw map: can draw all players of specific player
    def draw_game_board(self):

        visible_all = self.current_player_index == -1   
         
        if visible_all:
            map = self.map
        else:
            player = self.players[self.current_player_index]
            map =  Map.from_player(player)
            

        BG_COLOR = (255, 255, 255)
        self.screen.fill(BG_COLOR)
       
        for row in range(self.n_row):
            for col in range(self.n_col):
                value = map.get_value(row, col)
                x = self.start_x + col * self.CELL_SIZE
                y = self.start_y + row * self.CELL_SIZE

                if value == '-1':
                    display_image(self.screen, self.images['maps']['black'], x,y)
                else: #value in ['g','f','w','c','a','s']:
                    if visible_all:
                        display_image(self.screen, self.images['maps']['g'], x,y) # Background
                        if value.isdigit() and int(value) in range(len(self.players)):
                            display_image(self.screen, self.images['players'][int(value)], x+1,y+1)
                        else:
                            display_image(self.screen, self.images['maps'][value], x,y)
                    else:
                        if row in range(player.row - self.OPEN_CELL//2, player.row + self.OPEN_CELL//2 + 1) and \
                            col in range(player.col - self.OPEN_CELL//2, player.col + self.OPEN_CELL//2 + 1):
                            display_image(self.screen, self.images['maps']['g'], x,y) # Background
                            if value.isdigit() and int(value) in range(len(self.players)):
                                display_image(self.screen, self.images['players'][int(value)], x+1,y+1)
                            else:
                                display_image(self.screen, self.images['maps'][value], x,y)
                        else:
                            display_image(self.screen, self.images['maps']['f-g'], x,y) # Background
                            if value.isdigit() and int(value) in range(len(self.players)):
                                display_image(self.screen, self.images['players'][int(value)], x+1,y+1)
                            else:
                                display_image(self.screen, self.images['maps'][f'f-{value}'], x,y)
                # elif value.isdigit():
                #     value = int(value)
                #     if value in range(len(self.players)):
                #         display_image(self.screen, self.images['players'][value], x, y)

                # draw caro 
                # rect = pygame.Rect(
                #     x, y, self.CELL_SIZE, self.CELL_SIZE)
                # pygame.draw.rect(self.screen, (127,127,127), rect, width=1)
                          
    def draw_game_status(self):
        # Font and colors
        font = pygame.font.SysFont("Courier New", 18, bold=True)

        BG_COLOR = (10, 10, 10)
        padding = 10
        inner_padding = 10
        color = (255, 255, 255)
        if self.tick % 2 == 0:
            color = (0, 255, 0)
        else:
            color = (255, 255, 255)

        # Render time
        if self.game_status == GameStatus.WAITING_FOR_PLAYERS:
            text = f'{len(self.players)} WAITING ...'
        elif self.game_status == GameStatus.FINISHED:
            text = f'{len(self.players)} FINISHED'
        else:
            text = f'{len(self.players)} PLAYING'
        
        text_surface = font.render(text, True, color)
        text_rect = text_surface.get_rect()

        # Top-right position
        box_x = padding
        box_y = padding
        box_width = text_rect.width + inner_padding * 2
        box_height = text_rect.height + inner_padding * 2

        # Background box
        box = (box_x, box_y, box_width, box_height)
        pygame.draw.rect(self.screen, BG_COLOR, box, border_radius=8)

        # Blit time text
        self.screen.blit(text_surface, (box_x + inner_padding, box_y + inner_padding))
        return box
    
    def build_items_on_hand_str(self, items_on_hand):
        counts = Counter(items_on_hand)
        s = ", ".join(f"{key}:{count}" for key, count in counts.items())
        return f"Carrying: {s}"
    
    def build_store_str(self, store):
        counts = Counter(store)
        s = ", ".join(f"{key}:{count}" for key, count in counts.items())
        return f"Store: {s}"
    
    def build_equipment_str(self, player):
        s = f"Equipment: armor: {player.armor}, sword: {player.sword}"
        return s
    
    def draw_player_detail_info(self, pre_box):
        if  self.current_player_index == -1:
            return
        
        player = self.players[self.current_player_index]

        x = pre_box[0] + pre_box[2] + 10
        y = 10
        name = f'{player.id}_{player.name}'
        box = draw_text(self.screen, x, y, name.ljust(10)[:10], 
            self.text_color, self.text_background_color,
            self.text_font)
        
        display_image(self.screen, self.images['players'][int(player.id)], x + box.width, y )

        if player.status == PlayerStatus.PAUSED:
            draw_energy(self.screen, f'row: {player.row}, col: {player.col}. Status: {player.status.value}, Remain time: {int(Config.PAUSED_TIME - (datetime.datetime.now().timestamp() - player.paused_time))} s', 0, 0 , x + box.width + 28, y + 6 )
        else:
            draw_energy(self.screen, f'row: {player.row}, col: {player.col}. Status: {player.status.value}', 0, 0 , x + box.width + 28, y + 6)
        
        draw_energy(self.screen, 
                    self.build_store_str(player.store).ljust(50)  +
                    self.build_items_on_hand_str(player.items_on_hand).ljust(50) + 
                    self.build_equipment_str(player),
                    0, 0, x + box.width + 28, y + 28 )
    
    def draw_message(self, pre_box):
        def chunk_message_fixed(message: str, size: int = 90) -> list[str]:
            return [message[i : i + size] for i in range(0, len(message), size)]

        visible_all = self.current_player_index == -1   
        if not visible_all:
            return
        
        font = pygame.font.SysFont("Courier New", 18, bold=True)
        x = pre_box[0] + pre_box[2] + 10
        y = 10

        color = (0, 0, 0)
        if self.tick % 2 == 0:
            color = (100, 100, 100)
        else:
            color = (0, 0, 0)
        if len(self.messages)> 0:
            message = self.messages[-1]
            chunks = chunk_message_fixed(message)[:3]
            for c in chunks:
                text_surface = font.render(c, True, color)
                self.screen.blit(text_surface, (x, y))
                y+=14

    def draw(self):
        if self.game_status == GameStatus.PLAYING or self.game_status == GameStatus.WAITING_FOR_PLAYERS:
            self.tick += 1
        self.draw_game_board()
        self.draw_clock()
        box = self.draw_game_status()
        self.draw_players()
        self.draw_player_detail_info(box)
        self.draw_message(box)