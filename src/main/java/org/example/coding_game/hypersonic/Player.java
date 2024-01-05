package org.example.coding_game.hypersonic;

import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.math.*;

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Cell [x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

}

enum CellType {
    EMPTY('.'),
    BOX('0'),
    WALL('X'),
    BOX_EXTRA_RANGE('1'),
    BOX_EXTRA_BOMB('2');

    private static final CellType[] cellTypes = values();

    private char value;

    CellType(char value) {
        this.value = value;
    }

    public static CellType valueOf(char c) {
        for (CellType cellType : cellTypes) {
            if (cellType.value == c) {
                return cellType;
            }
        }
        return CellType.WALL;
    }
}

enum EntityType {
    BOMBER,
    BOMB,
    ITEM
}

class GameConfig {
    int height;
    int width;
    int myId;
}

class Entity {
    EntityType entityType;
    Point point;

    public Entity(EntityType entityType, int x, int y) {
        this.entityType = entityType;
        this.point = new Point(x, y);
    }

    @Override
    public String toString() {
        return "Entity [entityType=" + entityType + ", x=" + point.x + ", y=" + point.y + "]";
    }
}

class Bomber extends Entity {
    int id;
    int bomb;
    int range;

    public Bomber(EntityType entityType, int x, int y, int id, int bomb, int range) {
        super(entityType, x, y);
        this.id = id;
        this.bomb = bomb;
        this.range = range;
    }
}

class Bomb extends Entity {
    int ownerId;
    // starts with 8s, keep decrease util time == 0 => this bomb will explode
    int time;
    int range; // explosion range

    public Bomb(EntityType entityType, int x, int y, int ownerId, int time, int range) {
        super(entityType, x, y);
        this.ownerId = ownerId;
        this.time = time;
        this.range = range;
    }
}

enum ItemType {
    EXTRA_RANGE, EXTRA_BOMB
}

class Item extends Entity {
    ItemType itemType;

    public Item(EntityType entityType, int x, int y, ItemType itemType) {
        super(entityType, x, y);
        this.itemType = itemType;
    }
}

class Turn {
    List<List<CellType>> board;
    List<Entity> entities;

    public Turn() {
        board = new ArrayList<>();
        entities = new ArrayList<>();
    }

    public Turn(Turn turn) {
        board = new ArrayList<>(turn.board);
        entities = new ArrayList<>(turn.entities);
    }

    public Turn(List<List<CellType>> board, List<Entity> entities) {
        this.board = board;
        this.entities = entities;
    }

    @Override
    public String toString() {
        return "Turn [board=" + board + ", entities=" + entities + "]";
    }

}

class NextTurnInfo {
    boolean[] killed = new boolean[Game.BOMBER_NUMBER];
    int[] box = new int[Game.BOMBER_NUMBER];
    int[] range = new int[Game.BOMBER_NUMBER];
    int[] bomb = new int[Game.BOMBER_NUMBER];
}

enum ActionType {
    MOVE,
    BOMB
}

class Command { // MOVE x y || BOMB x y
    ActionType action;
    Point point;

    public Command() {
    }

    public Command(ActionType action, Point point) {
        this.action = action;
        this.point = point;
    }

    @Override
    public String toString() {
        return action + " " + point.x + " " + point.y + " ";
    }
}

class Output {
    Command command;
    String message;

    @Override
    public String toString() {
        return command + " " + message;
    }
}

class ExplodedTimeInfo {
    int time;
    boolean[] owner = new boolean[4];

    public ExplodedTimeInfo(int time) {
        this.time = time;
    }
}

class Game {
    public static final int TIME_LIMIT = 100; // ms
    public static final int BOMBER_NUMBER = 4;
    public static final int BOMB_TIME = 8;
    public static final int[] dy = { -1, 1, 0, 0, 0 };
    public static final int[] dx = { 0, 0, 1, -1, 0 };
}

class Pilot extends Game {

    private GameConfig config;
    private List<Turn> turns;

    public Pilot(GameConfig config) {
        this.config = config;
    }

    private Map<Integer, Bomber> selectBomber(List<Entity> entities) {
        Map<Integer, Bomber> players = new HashMap<>();

        for (Entity entity : entities) {
            if (entity.entityType == EntityType.BOMBER) {
                Bomber player = (Bomber) entity;
                players.put(player.id, player);
            }
        }
        return players;
    }

    private Map<Point, List<Entity>> entityMultimap(List<Entity> entities) {
        Map<Point, List<Entity>> cells = new HashMap<>();

        for (Entity entity : entities) {
            if (cells.containsKey(entity.point)) {
                cells.get(entity.point).add(entity);
            }
        }
        return cells;
    }

    private List<List<ExplodedTimeInfo>> initExplodedTimeInfo() {
        List<List<ExplodedTimeInfo>> ret = new ArrayList<>();
        for (int i = 0; i < this.config.height; ++i) {
            List<ExplodedTimeInfo> row = new ArrayList<>();
            for (int j = 0; j < this.config.width; ++j) {
                row.add(new ExplodedTimeInfo(Integer.MAX_VALUE));
            }
            ret.add(row);
        }

        return ret;
    }

    private void updateExplodeTime(List<List<ExplodedTimeInfo>> result, int x, int y, int time, int bomberId) {
        if (result.get(y).get(x).time < time) {
            return;
        }
        result.get(y).get(x).time = time;
        result.get(y).get(x).owner[bomberId] = true;
    }

    private boolean isOnField(int x, int y, int h, int w) {
        return 0 <= y && y < h && 0 <= x && x < w;
    }

    private boolean isBox(CellType type) {
        return type != CellType.WALL && type != CellType.EMPTY;
    }

    private void explode(
            Map<Point, Entity> obstructions,
            List<List<CellType>> field,
            List<List<ExplodedTimeInfo>> result,
            Bomb bomb,
            int time,
            Set<Point> used) {
        if (used.contains(bomb.point)) {
            return;
        }

        used.add(bomb.point);
        updateExplodeTime(result, bomb.point.x, bomb.point.y, time, bomb.ownerId);

        for (int i = 0; i < BOMBER_NUMBER; ++i) {
            for (int j = 1; j < bomb.range; ++j) {
                int nx = bomb.point.x + j * dx[i];
                int ny = bomb.point.y + j * dy[i];
                // continue if it is a
                if (!isOnField(nx, ny, this.config.height, this.config.width)) {
                    continue;
                }
                // break if it is a wall
                if (field.get(ny).get(nx) == CellType.WALL) {
                    break;
                }

                updateExplodeTime(result, nx, ny, time, bomb.ownerId);
                boolean obstructed = false;

                Point point = new Point(nx, ny);
                if (obstructions.containsKey(point)) {
                    obstructed = true;
                    Entity entity = obstructions.get(point);
                    // > Any bomb caught in an explosion is treated as if it had exploded at the
                    // very same moment.
                    // > Explosions do not go through obstructions such as boxes, items or other
                    // bombs, but are included on the cells the obstruction occupies.
                    // > A single obstruction may block the explosions of several bombs that explode
                    // on the same turn.
                    if (entity.entityType == EntityType.BOMB && ((Bomb) entity).time > time) {
                        explode(obstructions, field, result, (Bomb) entity, time, used);
                    } else if (entity.entityType == EntityType.ITEM) {
                        // @NOTE
                        used.add(point);
                    }
                }

                if (field.get(ny).get(nx) == CellType.EMPTY) {
                    obstructed = true;
                }
                if (isBox(field.get(ny).get(nx))) {
                    used.add(point);
                }
                if (obstructed) {
                    break;
                }
            }
        }
    }

    private List<List<ExplodedTimeInfo>> explodedTime(Turn turn) {
        Map<Point, Entity> obstructions = new HashMap<>();
        List<List<Point>> explodedAt = new ArrayList<>(BOMB_TIME);
        for (int i = 0; i < BOMB_TIME; ++i) {
            explodedAt.add(new ArrayList<>());
        }
        // store bomb or item to Map<Point, Entity> obstructions
        for (Entity e : turn.entities) {
            if (e.entityType == EntityType.BOMB) {
                Bomb bomb = (Bomb) e;
                obstructions.put(bomb.point, bomb);
                // store location of bomb to explodedAt (which bomb will explode after
                // {index} seconds)
                explodedAt.get(bomb.time - 1).add(bomb.point);
            } else if (e.entityType == EntityType.ITEM) {
                obstructions.put(e.point, e);
            }
        }

        List<List<CellType>> field = Helper.clone(turn.board); // modified
        List<List<ExplodedTimeInfo>> result = initExplodedTimeInfo();

        // define `explode` function above
        for (int i = 0; i < BOMB_TIME; ++i) {
            Set<Point> used = new HashSet<>();
            for (Point point : explodedAt.get(i)) {
                if (obstructions.containsKey(point)) {
                    Entity entity = obstructions.get(point);
                    if (entity.entityType == EntityType.BOMB) {
                        explode(obstructions, field, result, ((Bomb) entity), i + 1, used);
                    }
                }
            }

            for (Point point : used) {
                obstructions.remove(point);
                field.get(point.y).set(point.x, CellType.EMPTY);
            }
        }

        return result;
    }

    private Map<Point, Bomb> selectBomb(Turn turn) {
        Map<Point, Bomb> bombs = new HashMap<>();
        for (Entity entity : turn.entities) {
            if (entity.entityType == EntityType.BOMB) {
                bombs.put(entity.point, (Bomb) entity);
            }
        }
        return bombs;
    }

    private Bomber findBomber(List<Entity> entities, int id) {
        Map<Integer, Bomber> bombers = this.selectBomber(entities);

        return bombers.get(id);
    }

    private Command createCommand(Bomber bomber, int dx, int dy, ActionType actionType) {
        return new Command(actionType, new Point(dx + bomber.point.x, dy + bomber.point.y));
    }

    private boolean isValidCommands(Turn turn, Map<Integer, Command> commands) {
        List<Bomber> bombers = new ArrayList<>();
        Set<Point> bombs = new HashSet<>();

        for (Entity entity : turn.entities) {
            switch (entity.entityType) {
                case BOMBER:
                    bombers.add((Bomber) entity);
                    break;
                case BOMB:
                    bombs.add(entity.point);
                    break;
                default:
                    break;
            }
        }

        for (Bomber bomber : bombers) {
            if (commands.containsKey(bomber.id)) {
                Command command = commands.get(bomber.id);
                if (command.point.equals(bomber.point)) {
                    if (!isOnField(command.point.x, command.point.y, this.config.height, this.config.width)) {
                        return false;
                    }
                    if (Math.abs(command.point.y - bomber.point.y) + Math.abs(command.point.x - bomber.point.x) >= 2) {
                        return false;
                    }
                    if (turn.board.get(command.point.y).get(command.point.x) != CellType.EMPTY) {
                        return false;
                    }
                    if (bombs.contains(command.point)) {
                        return false;
                    }
                }

                if (command.action == ActionType.BOMB) {
                    if (bomber.bomb == 0) {
                        return false;
                    }
                    if (bombs.contains(bomber.point)) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    private ItemType openItemBox(CellType cellType) {
        switch (cellType) {
            case BOX_EXTRA_BOMB:
                return ItemType.EXTRA_BOMB;
            case BOX_EXTRA_RANGE:
                return ItemType.EXTRA_RANGE;
            default:
                return ItemType.EXTRA_BOMB;
        }
    }

    private Bomb placeBomb(Bomber bomber) {
        return new Bomb(EntityType.BOMB, bomber.point.x, bomber.point.y, bomber.id, BOMB_TIME, bomber.range);
    }

    private Turn nextTurn(Turn turn,
            List<List<ExplodedTimeInfo>> expTime,
            Map<Integer, Command> commands,
            NextTurnInfo info) {
        if (!isValidCommands(turn, commands)) {
            return null;
        }
        Turn next = new Turn();
        next.board = Helper.clone(turn.board);
        // bomb
        // > At the start of the round, all bombs have their countdown decreased by 1.
        // > Any bomb countdown that reaches 0 will cause the bomb to explode
        // immediately, before players move.
        Map<Point, Item> items = new HashMap<>();

        for (int y = 0; y < this.config.height; ++y) {
            for (int x = 0; x < this.config.width; ++x) {
                if (expTime.get(y).get(x).time - 1 == 0) {
                    // > Once the explosions have been computed, any box hit is then removed. This
                    // means that the destruction of 1 box can count for 2 different players.
                    if (this.isBox(turn.board.get(x).get(x))) {
                        next.board.get(y).set(x, CellType.EMPTY);
                        // drop item
                        if (turn.board.get(y).get(x) != CellType.BOX) {
                            ItemType itemType = openItemBox(turn.board.get(y).get(x));
                            items.put(new Point(x, y), new Item(EntityType.ITEM, x, y, itemType));
                        }
                        for (int i = 0; i < Game.BOMBER_NUMBER; ++i) {
                            if (expTime.get(y).get(x).owner[i]) {
                                info.box[i] += 1;
                            }
                        }
                    }
                }
            }
        }
        // split entities
        Map<Integer, Bomber> bombers = new HashMap<>(); // after explosion
        Map<Point, Bomb> bombs = new HashMap<>(); // after explosion, before placing
        int[] exploded_bombs = new int[Game.BOMBER_NUMBER];

        for (Entity e : turn.entities) {
            if (expTime.get(e.point.y).get(e.point.x).time - 1 == 0) {
                switch (e.entityType) {
                    case BOMBER:
                        info.killed[((Bomber) e).id] = true;
                        if (((Bomber) e).id == this.config.myId) {
                            return null;
                        }
                        break;
                    case BOMB:
                        exploded_bombs[((Bomb) e).ownerId] += 1;
                        break;
                    default:
                        break;
                }
            } else {
                switch (e.entityType) {
                    case BOMBER:
                        bombers.put(((Bomber) e).id, (Bomber) e);
                        break;
                    case BOMB: // @NOTE
                        Bomb bomb = (Bomb) e;
                        bomb.time -= 1;
                        bombs.put(bomb.point, bomb);
                        next.entities.add(e);
                        break;
                    case ITEM:
                        items.put(e.point, (Item) e);
                        break;
                    default:
                        break;
                }
            }
        }
        // player
        // > Players then perform their actions simultaneously.
        // > Any bombs placed by a player appear at the end of the round.
        Set<Point> playerExists = new HashSet<>(); // moved
        for (Bomber bomber : bombers.values()) {
            if (commands.containsKey(bomber.id)) {
                Command command = commands.get(bomber.id);
                // place bomb
                if (command.action == ActionType.BOMB) {
                    bomber.bomb -= 1;
                    next.entities.add(this.placeBomb(bomber)); // don't add to map<point_t,player_t> bombs
                }
                // move
                if (!command.point.equals(bomber.point)) {
                    bomber.point.x = command.point.x;
                    bomber.point.y = command.point.y;
                    // get item
                    if (items.containsKey(bomber.point)) {
                        Item item = items.get(bomber.point);
                        switch (item.itemType) {
                            case EXTRA_BOMB:
                                bomber.bomb += 1;
                                info.range[bomber.id] += 1;
                                break;
                            case EXTRA_RANGE:
                                bomber.range += 1;
                                info.range[bomber.id] += 1;
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            bomber.bomb += exploded_bombs[bomber.id];
            playerExists.add(bomber.point);
            next.entities.add(bomber);
        }
        // item
        for (Item item : items.values()) {
            if (playerExists.contains(item.point)) {
                continue;
            }
            next.entities.add(item);
        }
        return next;
    }

    private boolean isSurvivable(int id, Turn turn, List<List<ExplodedTimeInfo>> expTime) {
        Bomber bomber = this.findBomber(turn.entities, id);
        if (bomber == null) {
            return false;
        }

        if (expTime.get(bomber.point.y).get(bomber.point.x).time - 1 == 0) {
            return false;
        }

        int height = this.config.height;
        int width = this.config.width;

        Set<Point> bombs = new HashSet<>();
        for (Entity ent : turn.entities) {
            if (ent.entityType == EntityType.BOMB) {
                bombs.add(ent.point);
            }
        }

        List<List<Boolean>> cur = Helper.matrixBoolean(height, width);
        cur.get(bomber.point.y).set(bomber.point.x, true);

        for (int t = 0; t < Game.BOMB_TIME; ++t) {
            boolean exists = false;
            List<List<Boolean>> prv = Helper.clone(cur);
            cur = Helper.matrixBoolean(height, width);

            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    if (!prv.get(y).get(x)) {
                        continue;
                    }
                    if (expTime.get(y).get(x).time - 1 == t) {
                        continue;
                    }
                    for (int i = 0; i < 5; ++i) {
                        int ny = y + dy[i];
                        int nx = x + dx[i];
                        if (!isOnField(nx, ny, height, width)) {
                            continue;
                        }
                        if (turn.board.get(ny).get(nx) == CellType.WALL) {
                            continue;
                        }
                        if (isBox(turn.board.get(ny).get(nx)) && expTime.get(y).get(x).time - 1 >= t) {
                            continue;
                        }
                        if (ny != y && nx != x && bombs.contains(new Point(nx, ny))
                                && expTime.get(y).get(x).time - 1 >= t) {
                            continue;
                        }
                        cur.get(ny).set(nx, true);
                        exists = true;
                    }
                }
            }
            if (!exists) {
                return false;
            }
        }
        return true;
    }

    private boolean isSurvivableWithCommands(
            int bomberId,
            Map<Integer, Command> commands,
            Turn aTurn,
            List<List<ExplodedTimeInfo>> aExpTime) {
        // Something
        Turn turn = new Turn(aTurn);
        List<List<ExplodedTimeInfo>> expTime = Helper.clone(aExpTime);
        Bomber bomber = findBomber(turn.entities, bomberId);
        if (bomber == null) {
            return false;
        }
        NextTurnInfo nextTurnInfo = new NextTurnInfo();
        Turn nextTurn = nextTurn(turn, expTime, commands, nextTurnInfo);
        if (nextTurn == null) {
            return false;
        }
        expTime = explodedTime(nextTurn);
        if (findBomber(turn.entities, this.config.myId) == null) {
            return false;
        }
        return isSurvivable(this.config.myId, turn, expTime);
    }

    private Set<Command> forbiddenCommands(Turn turn, Map<Integer, Command> commandBase) {
        List<List<ExplodedTimeInfo>> expTime = explodedTime(turn);
        Bomber self = this.findBomber(turn.entities, this.config.myId);

        final ActionType[] actionTypes = { ActionType.BOMB, ActionType.MOVE };

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 2; ++j) {
                Map<Integer, Command> commands = new HashMap<>(commandBase);
                commands.put(self.id, createCommand(self, dx[i], dy[i], actionTypes[j]));
                // if()
            }
        }

        return null;
    }

    Output solve(Turn turn) {
        long clock_begin = System.currentTimeMillis();
        Map<Integer, Bomber> bombers = this.selectBomber(turn.entities);
        Map<Point, List<Entity>> entity_at = entityMultimap(turn.entities);

        // to survive
        Set<Command> forbidden;
        {
            List<List<ExplodedTimeInfo>> explodedTime = explodedTime(turn);
            Map<Integer, Command> commandBase = new HashMap<>();
            Map<Point, Bomb> bombs = selectBomb(turn);

            for (Bomber bomber : bombers.values()) {
                if (bomber.id != this.config.myId) {
                    if (bomber.bomb == 0) {
                        continue;
                    }
                    if (bombs.containsKey(bomber.point)) {
                        continue;
                    }
                    commandBase.put(bomber.id, createCommand(bomber, 0, 0, ActionType.BOMB));
                }
            }
            forbidden = forbiddenCommands(turn, commandBase);
        }
        return null;
    }
}

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    // for scan input
    private Scanner scanner;
    // game configuration
    private GameConfig gameConfig;

    // Entrance Point
    public static void main(String args[]) {
        Player player = new Player();
        // Start the game
        player.play();
    }

    public Player() {
        scanner = new Scanner(System.in);
        this.gameConfig = scanGameConfig();
    }

    /**
     * Scan the map's dimensions including with and height
     * Scan current playerId
     * 
     * @return Game config
     */
    private GameConfig scanGameConfig() {
        GameConfig gameConfig = new GameConfig();
        gameConfig.width = scanner.nextInt();
        gameConfig.height = scanner.nextInt();
        gameConfig.myId = scanner.nextInt();

        return gameConfig;
    }

    private Entity scanEntity() {
        int entityType = scanner.nextInt();
        int owner = scanner.nextInt();
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int param1 = scanner.nextInt();
        int param2 = scanner.nextInt();

        switch (entityType) {
            case 0:
                return new Bomber(EntityType.BOMBER, x, y, owner, param1, param2);
            case 1:
                return new Bomb(EntityType.BOMB, x, y, owner, param1, param2);
            case 2:
                return new Item(EntityType.ITEM, x, y, param1 == 1 ? ItemType.EXTRA_BOMB : ItemType.EXTRA_RANGE);
            default:
                return null;
        }
    }

    private Turn scanTurn() {
        Turn turn = new Turn();
        turn.board = new ArrayList<>(this.gameConfig.height); // Board game = {width*height} cells

        // Load board game
        for (int i = 0; i < this.gameConfig.height; i++) {
            String row = scanner.next();
            char[] cells = row.toCharArray();

            List<CellType> boardRow = new ArrayList<>(this.gameConfig.width); // {width} cells on each row
            for (char c : cells) {
                boardRow.add(CellType.valueOf(c));
            }
            turn.board.add(boardRow);
        }

        int entities = scanner.nextInt();

        for (int i = 0; i < entities; i++) {
            Entity entity = scanEntity();
            turn.entities.add(entity);
        }

        System.out.println("MOVE 1 2");
        System.err.println(turn);
        return turn;
    }

    private void play() {
        if (this.gameConfig == null) {
            System.out.println("GAME CONFIG IS NULL");
            return;
        }

        Pilot ai = new Pilot(this.gameConfig);

        while (true) {
            Turn turn = scanTurn();
            Output output = ai.solve(turn);
            // System.out.println(output);
        }
    }
}

class Helper {
    public static <T> List<List<T>> clone(List<List<T>> src) {
        return new ArrayList<>(src.stream().map(e -> new ArrayList<>(e)).collect(Collectors.toList()));
    }

    public static List<List<Boolean>> matrixBoolean(int a, int b) {
        List<List<Boolean>> matrix = new ArrayList<>(a);
        for (int i = 0; i < a; ++i) {
            List<Boolean> row = new ArrayList<>();
            for (int j = 0; j < b; ++j) {
                row.add(false);
            }
            matrix.add(row);
        }
        return matrix;
    }
}