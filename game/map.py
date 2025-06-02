import random
import numpy as np
from config import Config
from player import Player
from logs import log
import enums

map_static_items = {
    'g': 'Ground', # players can stand on it
    'f': 'Food', # health (HP)'
    'w': 'Wood', # Use to build boats,
    'c': 'Cotton', # Use to craft fabric',
}

map_dynamic_items = {
    'a': 'Armor', # reduces damage taken during combat',
    's': 'Sword', # increases damage dealt to opponents during combat',
}

class Map():
    def __init__(self, n_row, n_col):
        self.n_row = n_row
        self.n_col = n_col
        self.grid = np.array(['-1'] * self.n_row * self.n_col).reshape(self.n_row, self.n_col)
    
    def from_player(player):
        map = Map(player.map_h, player.map_w)
        map.grid = player.grid
        return map

    def random_map(self):
        self.grid = np.array(['g'] * self.n_row * self.n_col).reshape(self.n_row, self.n_col)
        self.ramdom_static_items()
        self.random__dynamic_items()

    
    def correct_grid_range(grid: np.array, row: int, col: int) -> tuple[int, int]:
        correct_row, correct_col = row, col
        max_row, max_col = grid.shape

        if correct_row < 0: correct_row = 0
        if correct_row >= max_row -1: correct_row = max_row -1 
        if correct_col < 0: correct_col = 0
        if correct_col >= max_col-1: correct_col = max_col -1 

        return correct_row, correct_col
    

    def copy_grid(grid1, grid2, r1:int, r2:int, c1:int, c2:int):
        if grid1.shape != grid2.shape:
            raise ValueError('grid1 and grid2 must be the same size')

        r1, c1 = Map.correct_grid_range(grid1, r1, c1)
        r2, c2 = Map.correct_grid_range(grid2, r2, c2)

        grid2[r1:r2+1,c1:c2+1] = grid1[r1:r2+1,c1:c2+1]

    def get_player_position(self, player_id: int, r1:int=None, r2:int=None, c1:int=None, c2:int=None):
        if r1 is None:
            r1 = 0
        if r2 is None:
            r2 = self.n_row-1
        if c1 is None:
            c1 = 0
        if c2 is None:
            c2 = self.n_col-1
        positions = np.where(self.grid[r1:r2, c1:c2] == f'{player_id}')
        return positions
    
    def find_players(self, players, r1:int, r2: int, c1: int, c2: int) -> list[int]:
        list_positions = []
        for player_id in players:
            positions = self.get_player_position(player_id,r1,r2,c1,c2)
            list_positions.append(positions)
        return list_positions
    
    def clear_outside_players(self, player_ids, r1:int, r2: int, c1: int, c2: int, clear_value='g'):
        for r in range(self.n_row):
            for c in range(self.n_col):
                if self.grid[r, c] in player_ids:
                    self.grid[r, c] = clear_value
                    
    def clear_player(self, player_id, value='g'):
        print(f'Clear player: {player_id}')
        position = self.get_player_position(player_id)
        self.grid[position] = value
    
    def clear_all_player(self, players):
        for player_id in players:
            self.clear_player(player_id)

    def set_value(self, row, col, val):
        self.grid[row, col] = val

    def get_value(self, row, col):
        if row<0 or row >= self.n_row or col <0 or col >= self.n_col:
            return -1
        item = self.grid[row, col]
        return item
    
    def get_neighbor_values(self, row, col, allow_items=[]):
        items = []
        while 'g' in allow_items:
            allow_items.remove('g')

        if len(allow_items) == 0:
            return items
        # left
        item = self.get_value(row, col-1)
        if item in allow_items:
            items.append(item)
        
        item = self.get_value(row, col+1)
        if item in allow_items:
            items.append(item)
        
        item = self.get_value(row-1, col)
        if item in allow_items:
            items.append(item)
        
        item = self.get_value(row+1, col)
        if item in allow_items:
            items.append(item)
        return items
    
    def can_move(self, player: Player, dir: enums.Direction):
        can_move_cross_items = ['g'] + list(map_dynamic_items.keys())
        if dir == enums.Direction.LEFT:
            item = self.get_value(player.row, player.col - 1)
            if item in can_move_cross_items:
                return True
        elif dir == enums.Direction.RIGHT:
            item = self.get_value(player.row, player.col + 1)
            if item in can_move_cross_items:
                return True
        elif dir == enums.Direction.UP:
            item = self.get_value(player.row -1, player.col)
            if item in can_move_cross_items:
                return True
        elif dir == enums.Direction.DOWN:
            item = self.get_value(player.row +1, player.col)
            if item in can_move_cross_items:
                return True
        else:
            return False

    def at_home(self, player):
        return player.home_row == player.row and player.home_col == player.col

    def ramdom_static_items(self):
        # random food
        for i in range(Config.MAP_NUMBER_FOOT):
            w = random.randint(1, 2)
            h = random.randint(1, 2)
            c = random.randint(w , self.n_col - w)
            r = random.randint(h , self.n_row - h )
            self.grid[r-h:r+h, c-w:c+w] = 'f'

        # random wood
        for i in range(Config.MAP_NUMBER_WOOD):
            w = random.randint(1, 2)
            h = random.randint(1, 2)
            c = random.randint(w , self.n_col - w)
            r = random.randint(h , self.n_row - h)
            self.grid[r-h:r+h, c-w:c+w] = 'w'

        # random cotton
        for i in range(Config.MAP_NUMBER_COTTON):
            w = random.randint(1, 2)
            h = random.randint(1, 2)
            c = random.randint(w , self.n_col - w)
            r = random.randint(h , self.n_row - h)
            self.grid[r-h:r+h, c-w:c+w] = 'c'

    def random_item(self, item, number):
        count = 0
        
        while count < number:
            w = 1
            h = 1
            c = random.randint(w , self.n_col - w)
            r = random.randint(h , self.n_row - h)
            if self.grid[r, c] == 'g':
                self.grid[r, c] = item
                count += 1

    def random__dynamic_items(self):
        self.random_item('a', Config.MAP_NUMBER_AMOR)
        self.random_item('s', Config.MAP_NUMBER_SWORD)

    def collision(self, player: Player):
        items = self.get_neighbor_values(player.row, player.col, [i for i in range])
        return items

    def collect_items(self, player):
        items = {} 
        row, col = player.row, player.col

        # with dynamic items, player should on cell
        item = self.get_value(row, col)
        if item in map_dynamic_items:
            if item == 'a':
                if player.armor == 0:
                    player.armor +=1
                    self.set_value(row, col, 'g')

            elif item == 's':
                if player.sword == 0:
                    player.sword +=1
                    self.set_value(row, col, 'g')

        # with staict items, check around player
        items = self.get_neighbor_values(row, col, player.allow_collect_items)
        items = list(set(items + player.items_on_hand))
        if len(items) > 0:
            player.items_on_hand = items
      