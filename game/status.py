from enum import Enum

class GameStatus(Enum):
    """
    Represents the possible states of a game.
    """
    WAITING_FOR_PLAYERS = 0
    PLAYING = 1
    PAUSED = 2
    FINISHED = 3


class PlayerStatus(Enum):
    WAITING_FOR_PLAYERS = 'waiting for players'
    PLAYING = 'playing'
    WIN = 'win'
    LOSS = 'loss'
    PAUSED = 'paused'
    DEAD = 'dead'


    

