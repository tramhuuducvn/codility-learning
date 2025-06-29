import socket
from dotenv import load_dotenv
from logs import log
import os
from utils import send, receive  
from message import (
    Message, MoveMessage, RemoveInProcessMoveMessage, SetPlayerNameMessage, 
    GetPlayerMessage, AllowCollectItemsMessage
)
from config import Config

load_dotenv()


class Client:
    def __init__(self, host=None, port=None):

        self.host = host or os.environ.get('SERVER', '0.0.0.0')
        self.port = port or int(os.environ.get('PORT', 4444))

        log(f'Listern to {self.host}:{self.port}', '[CLIENT]')

        self.client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.client_socket.connect((self.host, self.port))
        self.player = self.receive_message()
        log(f"Receive client from server: {self.player}", '[CLIENT]')
    
    def set_player_name(self, name):
        log(f'Send message to server to set name: {name}', '[CLIENT]')
        self.send_message(SetPlayerNameMessage(player_name=name))
        name = self.receive_message()
       
        return name

    def get_map_value(self, row, col):
        return self.player.map[row * Config.N_COL + col]
    
    def set_map_value(self, row, col, val):
        self.player.map[row * Config.N_COL + col] = val

    def send_message(self, msg: Message):
        send(self.client_socket, msg)
    
    def clear_in_process_messages(self):
        self.send_message(RemoveInProcessMoveMessage())

    def get_task(self):
        self.send_message(RemoveInProcessMoveMessage())

    def receive_message(self):
        return receive(self.client_socket)

    def move(self, direction):
        log(f'Move to dir {direction}', '[CLIENT]')
        self.send_message(MoveMessage(dir=direction))
    
    def move_left(self):
        self.send_message(MoveMessage(dir=0))
    
    def move_right(self):
        self.send_message(MoveMessage(dir=1))

    def move_up(self):
        self.send_message(MoveMessage(dir=2))

    def move_down(self):
        self.send_message(MoveMessage(dir=3))

    def get_surrounding_tiles(self):
        r, c = self.player.row, self.player.col
        r1 = max(0, r - Config.OPEN_CELL//2)
        r2 = min(Config.N_ROW, r + Config.OPEN_CELL//2 +1)
        c1 = max(0, c - Config.OPEN_CELL//2)
        c2 = min(Config.N_COL, c + Config.OPEN_CELL//2 +1)
        return self.player.grid[r1:r2, c1:c2]

    def get_player(self):
        self.send_message(GetPlayerMessage())
        self.player = self.receive_message()
        return self.player
    
    def allow_collect_items(self, items=['w','f','c']):
        self.send_message(AllowCollectItemsMessage(items=items))
        allowed_items = self.receive_message()
        return allowed_items
        
    def close(self):    
        self.client_socket.close()
        log('Client closed', '[CLIENT]')