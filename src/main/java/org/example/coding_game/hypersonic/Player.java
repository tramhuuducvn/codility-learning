package org.example.coding_game.hypersonic;

// package org.example.coding_game.hypersonic;

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

class Entity implements Comparable {
    EntityType entityType;
    Point point;
    int owner;
    int param1;
    int param2;

    public Entity(EntityType entityType, int x, int y) {
        this.entityType = entityType;
        this.point = new Point(x, y);
        owner = 0;
        param1 = 0;
        param2 = 0;
    }

    @Override
    public String toString() {
        return "Entity [entityType=" + entityType + ", x=" + point.x + ", y=" + point.y + "]";
    }

    @Override
    public int compareTo(Object o) {
        Entity other = (Entity) o;
        return (this.entityType.ordinal()) - (other.entityType.ordinal());
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
        this.owner = id;
        this.param1 = bomb;
        this.param2 = range;
    }

    @Override
    public String toString() {
        return "Bomber [id=" + id + ", bomb=" + bomb + ", range=" + range + " point = " + this.point + "]";
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
        this.owner = ownerId;
        this.param1 = time;
        this.param2 = range;
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
        this.param1 = itemType.ordinal();
        this.owner = 0;
        this.param2 = 0;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((action == null) ? 0 : action.hashCode());
        result = prime * result + ((point == null) ? 0 : point.hashCode());
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
        Command other = (Command) obj;
        if (action != other.action)
            return false;
        if (point == null) {
            if (other.point != null)
                return false;
        } else if (!point.equals(other.point))
            return false;
        return true;
    }

}

class Output {
    Command command;
    String message;

    public Output() {

    }

    public Output(Command command, String message) {
        this.command = command;
        this.message = message;
    }

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
    public static final int TIME_LIMIT_MARGIN = 5; // ms

    public static final int BOMBER_NUMBER = 4;
    public static final int BOMB_TIME = 8;
    public static final int[] dy = { -1, 1, 0, 0, 0 };
    public static final int[] dx = { 0, 0, 1, -1, 0 };
    // time_limit - time_limit_margin; // magic, randomness
}

class Photon implements Comparable {
    Turn turn;
    Command initCommand;
    int age;
    int box, range, bomb; // difference
    List<List<ExplodedTimeInfo>> expTime;
    int boxAcc;
    double bonus;
    double score;
    BigInteger signature;

    public Photon() {
    }

    public Photon(Photon photon) {
        this.turn = photon.turn;
        this.initCommand = photon.initCommand;
        this.age = photon.age;
        this.box = photon.box;
        this.range = photon.range;
        this.bomb = photon.bomb;
        this.expTime = photon.expTime;
        this.boxAcc = photon.boxAcc;
        this.bonus = photon.bonus;
        this.score = photon.score;
        this.signature = photon.signature;
    }

    public Photon(Turn turn, Command initCommand, int age, int box, int range, int bomb,
            List<List<ExplodedTimeInfo>> expTime, int boxAcc, double bonus, double score, BigInteger signature) {
        this.turn = turn;
        this.initCommand = initCommand;
        this.age = age;
        this.box = box;
        this.range = range;
        this.bomb = bomb;
        this.expTime = expTime;
        this.boxAcc = boxAcc;
        this.bonus = bonus;
        this.score = score;
        this.signature = signature;
    }

    @Override
    public int compareTo(Object o) {
        Photon other = (Photon) o;
        return (int) (this.score - other.score);
    }
}

class Pilot extends Game {

    private GameConfig config;
    private List<Turn> turns;
    private List<Output> outputs;

    public Pilot(GameConfig config) {
        this.turns = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.config = config;
    }

    private Map<Integer, Bomber> selectBomber(List<Entity> entities) {
        Map<Integer, Bomber> bombers = new HashMap<>();

        for (Entity entity : entities) {
            if (entity.entityType == EntityType.BOMBER) {
                Bomber bomber = (Bomber) entity;
                bombers.put(bomber.id, bomber);
            }
        }
        for (Entity e : entities) {
            System.err.println("E PIT " + e);
        }
        return bombers;
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
        for (Entity e : entities) {
            System.err.println("E PIT " + e);
        }
        System.err.println(">>> POINT???? " + bombers.get(id).point);

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
                        System.err.println("Command is not isOnField");
                        return false;
                    }
                    if (Math.abs(command.point.y - bomber.point.y) + Math.abs(command.point.x - bomber.point.x) >= 2) {
                        System.err.println("Command is >= 2");
                        return false;
                    }
                    if (turn.board.get(command.point.y).get(command.point.x) != CellType.EMPTY) {
                        System.err.println("Command point != EMPTY");
                        return false;
                    }
                    if (bombs.contains(command.point)) {
                        System.err.println("Bombs not contain command's point");
                        return false;
                    }
                }

                if (command.action == ActionType.BOMB) {
                    if (bomber.bomb == 0) {
                        System.err.println("Number of bombs is zero");
                        return false;
                    }
                    if (bombs.contains(bomber.point)) {
                        System.err.println("Number of bombs is zero");
                        return false;
                    }
                }
            }
        }
        return true;
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
            System.err.println("Command is INVALID");
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
        System.err.println("BOMBER:  " + bomber);

        try {
            if (expTime.get(bomber.point.y).get(bomber.point.x).time - 1 == 0) {
                return false;
            }
        } catch (Exception e) {
            System.err.println("BOUND OF INDEX: " + bomber.point.y + " " + bomber.point.y);
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
        System.err.println("isSurvivableWithCommands: " + this.config.myId);

        return isSurvivable(this.config.myId, turn, expTime);
    }

    private Set<Command> forbiddenCommands(Turn turn, Map<Integer, Command> commandBase) {
        List<List<ExplodedTimeInfo>> expTime = explodedTime(turn);
        Bomber self = this.findBomber(turn.entities, this.config.myId);
        Set<Command> forbidden = new HashSet<>();
        final ActionType[] actionTypes = { ActionType.BOMB, ActionType.MOVE };

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 2; ++j) {
                Map<Integer, Command> commands = new HashMap<>(commandBase);
                commands.put(self.id, createCommand(self, dx[i], dy[i], actionTypes[j]));
                if (isSurvivableWithCommands(self.id, commands, turn, expTime)) {
                    break;
                } else {
                    forbidden.add(commands.get(self.id));
                }
            }
        }

        return forbidden;
    }

    private int totalBomb(int id, List<Entity> entities) {
        int placed = 0;
        int reserved = 0;

        for (Entity ent : entities) {
            if (ent.entityType == EntityType.BOMBER) {
                if (((Bomber) ent).id == id) {
                    reserved += ((Bomber) ent).bomb;
                }
            } else if (ent.entityType == EntityType.BOMB) {
                if (((Bomb) ent).ownerId == id) {
                    placed += 1;
                }
            }
        }
        return placed + reserved;
    }

    private double evaluatePhoton(Photon pho) { // fucking magic here
        int height = this.config.height;
        int width = this.config.width;

        Map<Integer, Bomber> bombers = this.selectBomber(pho.turn.entities);
        Bomber self = bombers.get(this.config.myId);
        double score = 0;
        double box_base = 10;
        double box_delta = 1;
        score += box_base * pho.box;
        score += box_delta * pho.boxAcc;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                if (this.isBox(pho.turn.board.get(y).get(x)) && pho.expTime.get(y).get(x).time != Integer.MAX_VALUE
                        && pho.expTime.get(y).get(x).owner[self.id]) {
                    score += box_base - box_delta * pho.expTime.get(y).get(x).time;
                }
            }
        }
        score += 0.8 * Math.min(5, pho.range) + 0.4 * pho.range;
        score += 3.0 * Math.min(2, pho.bomb) + 1.1 * Math.min(4, pho.bomb) + 0.5 * pho.bomb;
        score -= 0.2 * (pho.bomb - self.bomb);
        score -= 0.05 * Math.abs(self.point.y - height / 2.);
        score -= 0.05 * Math.abs(self.point.x - width / 2.);
        // score -= 2 * players.size(); // TODO: 実際には相手は回避するので、回避不能性を見なければ
        score += pho.bonus;
        return score;
    }

    private BigInteger signaturePhoton(Photon photon) {
        int height = this.config.height;
        int width = this.config.width;

        BigInteger p = BigInteger.valueOf(1000000009);
        BigInteger acc = BigInteger.valueOf(0);

        acc.multiply(p).add(BigInteger.valueOf(photon.age));
        acc.multiply(p).add(BigInteger.valueOf(photon.box));
        acc.multiply(p).add(BigInteger.valueOf(photon.range));
        acc.multiply(p).add(BigInteger.valueOf(photon.bomb));
        acc.multiply(p).add(BigInteger.valueOf(photon.boxAcc));

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int min = Math.min(Game.BOMB_TIME + 1, photon.expTime.get(y).get(x).time);
                acc.multiply(p).add(BigInteger.valueOf(min));
            }
        }

        for (Entity entity : photon.turn.entities) {
            acc.multiply(p).add(BigInteger.valueOf(entity.entityType.ordinal()));
            acc.multiply(p).add(BigInteger.valueOf(entity.owner));
            acc.multiply(p).add(BigInteger.valueOf(entity.point.x));
            acc.multiply(p).add(BigInteger.valueOf(entity.point.y));
            acc.multiply(p).add(BigInteger.valueOf(entity.param1));
            acc.multiply(p).add(BigInteger.valueOf(entity.param2));
        }
        return acc;
    }

    private Photon initPhoton(Turn turn) {
        Photon photon = new Photon();
        photon.turn = new Turn(turn);
        Collections.sort(photon.turn.entities);
        photon.range = findBomber(turn.entities, this.config.myId).range;
        photon.bomb = totalBomb(this.config.myId, turn.entities);
        photon.expTime = explodedTime(turn);
        photon.score = evaluatePhoton(photon);
        photon.signature = signaturePhoton(photon);
        return photon;
    }

    Output solve(Turn turn) {
        long clock_begin = System.currentTimeMillis();
        Map<Integer, Bomber> bombers = this.selectBomber(turn.entities);
        Map<Point, List<Entity>> entity_at = entityMultimap(turn.entities);
        Bomber self = bombers.get(this.config.myId);

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
            if (forbidden.size() == 10) {
                commandBase.clear();
                forbidden = forbiddenCommands(turn, commandBase);
            }
            // TODO: Ignore it because it has no choice. I should have noticed this one time
            // ago.
            if (forbidden.size() == 10) {
                forbidden.clear();
            }
        }

        // Beam Search
        String message = "";
        Command command = new Command(ActionType.MOVE, self.point);
        {
            List<Photon> beam = new ArrayList<>();
            beam.add(initPhoton(turn));
            int beam_width = 100;
            int point_beam_width = 6;
            int simulation_time = 8;
            int time_limit_margin = 5;
            long clock_end = System.currentTimeMillis();
            long clock_count = clock_end - clock_begin;

            for (int age = 0; age < simulation_time; ++age) {
                System.err.println("beam size: " + beam.size());
                Set<BigInteger> used = new HashSet<>();
                List<List<List<Photon>>> nbeam = setupNBeam(this.config.height, this.config.width, beam);
                for (Photon photon : beam) {
                    for (int i = 0; i < 5; ++i) {
                        for (int j = 0; j < 2; ++j) {
                            Map<Integer, Command> commands = new HashMap<>();
                            {
                                Bomber curself = this.findBomber(photon.turn.entities, this.config.myId);
                                ActionType actionType = j == 0 ? ActionType.MOVE : ActionType.BOMB;
                                Command comd = new Command(actionType, new Point(dx[i], dy[i]));
                                if (age == 0 && forbidden.contains(comd)) {
                                    continue;
                                }
                                commands.put(curself.id, comd);
                            }
                            Photon npho = updatePhoton(photon, commands);
                            if (npho == null) {
                                System.err.println("Photon is null: " + beam.size());
                                continue;
                            }
                            used.add(npho.signature);
                            if (!this.isSurvivable(this.config.myId, npho.turn, npho.expTime)) {
                                continue;
                            }
                            Point p = this.findBomber(npho.turn.entities, this.config.myId).point;
                            nbeam.get(p.y).get(p.x).add(npho);
                            beam.add(npho);
                        }
                    }
                    if (clock_count >= Game.TIME_LIMIT - Game.TIME_LIMIT_MARGIN) {
                        System.err.println("@ERROR TIMEOUT");
                        break;
                    }
                }
                if (clock_count >= Game.TIME_LIMIT - Game.TIME_LIMIT_MARGIN) {
                    System.err.println("@ERROR TIMEOUT");
                    break;
                }
                beam.clear();

                //
                for (int y = 0; y < this.config.height; ++y) {
                    for (int x = 0; x < this.config.width; ++x) {
                        Collections.sort(nbeam.get(y).get(x));
                        Helper.resize(nbeam.get(y).get(x), point_beam_width);
                        for (Photon photon : beam) {
                            nbeam.get(y).get(x).add(photon);
                        }
                    }
                }
                Collections.sort(beam);
                Helper.resize(beam, beam_width);

                if (!beam.isEmpty()) {
                    command = beam.get(0).initCommand;
                }

                if (message.isEmpty() && beam.isEmpty()) {
                    if (age == 0) {
                        message = "Pitbull So Cute!";
                    } else {
                        message = "Bully So Cute";
                    }
                }
            }
        }

        // log
        long clock_end_2 = System.currentTimeMillis();
        {
            long clock_count_2 = clock_end_2 - clock_begin;
            if (message.isEmpty()) {
                message = clock_count_2 + "ms";
            } else {
                message = message + " (" + clock_count_2 + "ms)";
            }
        }
        turns.add(turn);
        Output output = new Output(command, message);
        outputs.add(output);
        return output;
    }

    private Photon updatePhoton(Photon photon, Map<Integer, Command> commands) {
        Photon nPhoton = new Photon(photon);
        NextTurnInfo info = new NextTurnInfo();
        Turn nextTurn = this.nextTurn(photon.turn, photon.expTime, commands, info);
        if (nextTurn == null) {
            System.err.println("this.nextTurn is Null");
            return null;
        }
        nPhoton.turn = new Turn(nextTurn);
        if (photon.age == 0) {
            nPhoton.initCommand = commands.get(this.config.myId);
        }

        Collections.sort(nPhoton.turn.entities);
        nPhoton.age += 1;
        nPhoton.box += info.box[this.config.myId];
        nPhoton.range += info.range[this.config.myId];
        nPhoton.bomb += info.bomb[this.config.myId];
        nPhoton.expTime = this.explodedTime(nPhoton.turn);
        nPhoton.boxAcc += photon.box;
        nPhoton.score = evaluatePhoton(nPhoton);
        nPhoton.signature = signaturePhoton(nPhoton);

        return nPhoton;
    }

    private List<List<List<Photon>>> setupNBeam(int height, int width, List<Photon> beam) {
        List<List<List<Photon>>> nbeam = new ArrayList<>();
        for (int i = 0; i < height; ++i) {
            List<List<Photon>> d1 = new ArrayList<>();
            for (int j = 0; j < width; ++j) {
                d1.add(beam);
            }
            nbeam.add(d1);
        }
        return nbeam;
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
            System.err.println(">>> POINT: " + entity.point);
            turn.entities.add(entity);
        }

        return turn;
    }

    private void play() {
        if (this.gameConfig == null) {
            System.err.println("GAME CONFIG IS NULL");
            return;
        }

        Pilot ai = new Pilot(this.gameConfig);

        while (true) {
            Turn turn = scanTurn();
            Output output = ai.solve(turn);
            //
            Random random = new Random();

            int pill = random.nextInt(2);
            if (pill == 1) {
                int x = random.nextInt(random.nextInt(this.gameConfig.width));
                int y = random.nextInt(random.nextInt(this.gameConfig.height));

                System.out.println("MOVE " + x + " " + y);
            } else {

            }
            System.out.println(output);
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

    public static <T> List<T> resize(List<T> list, int size) {
        while (list.size() > size) {
            list.remove(list.size() - 1);
        }
        return list;
    }
}