# Autonomous Agent Gameplay in a 2D Game Environment

## Overal

The player takes the role of a game character in a 2D map.  
The task is to program an agent that can automatically play the game based on the rules defined below.

Each turn is a maximum of 5 agents, within 15 minutes.
The Agent must complete tasks to collect enough items to escape the island.
The storage for each agent is a maximum of 50 items.
The main items include: Food 🍖 - Wood 🪵 - Cotton - 🧶 Fabric (Automated convert from cotton. For example: 5 cotton convert to 1 Fabric)
The main task is to build a boat to escape the island, with each boat ⛵ being made from X Wood + Y Fabric

    - After joining the game, the server will send a text message detailing the quantities X (wood) and Y (cotton), as well as the formula for converting cotton into fabric.

⚔️ Combat
- In addition, agents will be able to collect additional items including: ⚔️ Sword (Attack) + 🛡️ Shield (Defend)
With the rules when 2 agents clash:
    - No ⚔️ Sword or 🛡️ Shield: Continue moving
    - 🛡️ Shield vs 🛡️ Shield: Agent loses Shield and continues moving
    - ⚔️ Sword vs ⚔️ Sword: Agent loses Sword and is returned to Home
    - ⚔️ Sword vs 🛡️ Shield: Agent loses item and continues moving
    - ⚔️ Sword vs No Sword/Shield: Agent steals the item and the other agent is returned to Home.

- Unexpected events will appear on the island at any time during the match - in text form for Agents to analyze - understand and execute commands. Example:
    - 🎁 Reward Box: Agent goes to the announced location to receive the reward
    - ⛈️ Rain/Storm: Agent must run home, otherwise, the item being carried will be lost and returned to Home

- 🏆 Victory Conditions
    How to calculate points:

    - Finish a boat to leave the island: 250 points + Bonus points in order of completion
    - Collect items: Food (1p) - Wood (3p) - Cotton (3p) - Fabric (20p)
    --> Ranking based on the total points earned by the Agent.

## Details

### 🗺️ Map

- The map dimensions: see in the config.py file

### Vision System

- The player can see multiple adjacent tiles around their current position.
- Previously explored areas remain visible but appear dimmed.
- Currently visible areas are brighter and display moving objects in real-time.

### Cell Types

Each cell may contain one of the following values:

- `'g'`: Ground — walkable tile for players
- `'f'`, `'w'`, `'c'`: Resources — food, wood, and cotton
- `'s'`, `'a'`: Weapons — sword and armor
- `-1`: Cell that hasn’t been explored yet.
- `number`: The ID of another player

### 👤 Player

#### Player Information

Defined in the class and sent from the server to the player every game loop (once per second).

```python
from typing import Tuple
from pydantic import BaseModel, Field, ConfigDict, field_serializer
from enums import PlayerStatus
import numpy as np

class Player(BaseModel):
   model_config = ConfigDict(arbitrary_types_allowed=True)
   id: int
   name: str=''
   status:PlayerStatus = PlayerStatus.WAITING_FOR_PLAYERS
   color: Tuple[int, int, int] = (0, 0, 0)

   # Position
   home_row: int = 0
   home_col: int = 0
   row: int = 0
   col: int = 0

   # allow collect items
   allow_collect_items:list[str] = []

   # items
   HP: int =10
   store: list[str] = []

   items_on_hand: list[str] = []

   armor: int = 0
   sword: int = 0 # damage

   # calculate
   fabric: int = 0

   last_updated: float = 0
   map_w: int = 0
   map_h: int = 0
   grid: np.ndarray
   message: str = ''

   @field_serializer('grid', when_used='json')
   def serialize_data(self, v: np.ndarray, _info):
       # Convert to nested list before JSON dumping
       return v.tolist()
```

#### 🎮 Actions

- **Move**:  
  The player can move in four directions: left, right, up, and down.  
  Movement is only allowed to cells containing:

  - `'g'` (ground)
  - `'a'` (armor)
  - `'s'` (sword)
  - `-1`  (hasn’t been explored yet)

- **Collect**:  
  The player can collect resources with one action per unit/item:

  - `'w'`, `'f'`, `'x'`, `'c'`: collect 1 unit per action (wood, food, unknown resource, cotton)
  - `'a'`, `'s'`: collect 1 item per action (armor, sword)

- **Craft**:  
  The player can craft fabric using cotton:
  - K units of cotton (`'c'`) → 1 unit of fabric

### 🧰 Store System

- The player has an inventory (store) that can hold multiple types of items simultaneously.

### 🏠 Home Base Mechanics (Respawn)

- When the player dies, they are returned to their home base and must wait a fixed cooldown period to respawn.

### 📡 Message System (Dispatcher)

- Can broadcast events to all clients as events

  - 🎁 Reward Box: Agent goes to the announced location to receive the reward
  - ⛈️ Rain/Storm: Agent must run home, otherwise, the item being carried will be lost and returned to Home


## How to run game

You can use below command to update you python environment

```bash
uv sync
```


### Run server

Server using pygame and run with GUI interface

- There is the config for runing in debug mode if you using VS Code editor. 

- You can run in command live as below:

  ```bash
    python server.py
  ```

Some shortcuts:

- View all player or specific player by press

  `'+'`: next player
  `'-'`: previous player

- press `'s'` to start game. After start, server will not accept new game client and game client now can:
  - get their information by call `get_player` function

- To debugger game client
    - press `'+'`, `'-'` to select game client
    - press `'arrow'` button to move game client

### Run Message Dispatcher

code is in `client_communicator.ipynb file`

```python

class ClientCommunicator():
    def __init__(self, host=None, port=None):

        self.list_messages = [
            """To complete this game, you need to collect 2 units of wood and 2 units of fabric. Every 3 units of cotton can be converted into 1 unit of fabric.""",
            """Go home immediately, or you’ll die.""",
            """Go to the cell at (10, 18) to obtain one unit of fabric."""
        ]

        self.host = host or os.environ.get('SERVER', '0.0.0.0')
        self.port = port or int(os.environ.get('PORT', 4444))

        log(f'Listern to {self.host}:{self.port}', '[CLIENT]')
        
        self.other_players = {}

        self.client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.client_socket.connect((self.host, self.port))
        self.message = self.receive_message()
        log(f"Receive client from server: {self.message}", '[Dispatcher]')

    def send_message(self, msg: Message):
        send(self.client_socket, msg)

    def receive_message(self):
        return receive(self.client_socket)
```

#### How to send message to all game client
- Create instance of `ClientCommunicator` class
- Call `send_message` method to send message

For example:

```python
client_communicator = ClientCommunicator()
client_communicator.send('Your task is to collect 10 units of cotton! Just kidding.')

```

### Implement GameClient

- You can refer to `test-agent.ipynb` for a demo of building the game client.

- To write a game client simply extend the `Client` object by adding helpful functions or integrating agents into it.

- Game client auto connect to game server

- For naming your player use:
    `set_player_name` method

- To move your player:
    Move left (0), right (1), up (2), or down (3) by calling the corresponding functions: `move_left()`, `move_right()`, `move_up()`, `move_down()`, or by using `move(dir_code)` with dir_code set to `0, 1, 2, or 3`.

- To allow yor player collect resource use:
    `allow_collect_items` method with params as items (list of resource)

    For example you would like you player  collect only wood:
    ```python
    my_game_client.allow_collect_items(items = ['w'])
    ```

- **Importance**
  - The server updates your player information once per second (a “game tick”). If you send move commands too quickly, they’ll be added to a queue and processed one per tick. You can call the `clear_in_process_messages()` method to clear this queue.
  - Call the `get_player()` method to fetch your player’s current state. The data returned reflects the most recent game‐tick update from the server.


`Client` class

```python
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
from threading import Lock

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
        with self.lock:
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

```
