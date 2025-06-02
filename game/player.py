from typing import Tuple
from pydantic import BaseModel, Field, ConfigDict, field_serializer
from enums import PlayerStatus
import numpy as np
from message import MoveMessage 

class Player(BaseModel):
    model_config = ConfigDict(arbitrary_types_allowed=True)
    id: int
    name: str=''
    status:PlayerStatus = PlayerStatus.WAITING_FOR_PLAYERS

    # Position
    home_row: int = 0
    home_col: int = 0
    row: int = 0
    col: int = 0

    # allow collect items
    allow_collect_items:list[str] = []

    # items
    # HP: int =10
    store: list[str] = []
    food: int = 0 # Store
    wood: int = 0 # Store
    cotton: int = 0 # Store

    items_on_hand: list[str] = []

    armor: int = 0 
    sword: int = 0 # damage

    # calculate
    fabric: int = 0

    last_updated: float = 0
    paused_time: float = 0
    map_w: int = 0
    map_h: int = 0
    grid: np.ndarray
    message: str = ''
    in_process_move_messages: list[MoveMessage]= []

    @field_serializer('grid', when_used='json')
    def serialize_data(self, v: np.ndarray, _info):
        # Convert to nested list before JSON dumping
        return v.tolist()