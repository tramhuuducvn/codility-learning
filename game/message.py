from pydantic import BaseModel
from enums import GameStatus


class Message(BaseModel):
    pass

class SetPlayerNameMessage(Message):
    player_name: str

class GetPlayerMessage(Message):
    pass

class MoveMessage(Message):
    dir: int = 0 # 0= left, 1= right, 2= up, 3= down
class StatusMessage(Message):
    game_status: GameStatus

class AllowCollectItemsMessage(Message):
    items: list[str] = []

class RemoveInProcessMoveMessage(Message):
    pass
    
