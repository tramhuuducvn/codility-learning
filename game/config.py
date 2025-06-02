from dataclasses import dataclass

@dataclass
class Config:
    # GAME DPEED
    FPS:float=2.0
    GAME_DURATION:int=60*60 # in minutes
    
    # PLAYER
    # PLAYER_HP_INIT:int=5
    PLAYER_HP_MAX:int=10
    PLAYER_FOOT_MAX:int=10
    PLAYER_ARMOR_MAX:int=5
    
    # DEAD_TIME
    PAUSED_TIME:int=30 # in seconds
    
    # STORE
    MAX_STORAGE_CAPACITY = 50
    
    # Wind condition
    WIND_N_WOOD = 1
    WIND_N_FABRIC = 1
    
    # MAP
    N_ROW:int = 18
    N_COL:int = 32
    CELL_SIZE: int = 42
    OPEN_CELL: int = 4
    
    MAP_NUMBER_FOOT:int = 3
    MAP_NUMBER_WOOD:int = 3
    MAP_NUMBER_COTTON:int = 2
    MAP_NUMBER_AMOR:int = 5
    MAP_NUMBER_SWORD:int = 5

    LLM_MODEL:str='gpt-4o-mini'




