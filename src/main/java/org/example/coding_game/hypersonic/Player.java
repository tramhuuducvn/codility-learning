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
}

enum ActionType {
    MOVE,
    BOMB
}

class Command { // MOVE x y || BOMB x y
    ActionType action;
    Point point;

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

class Pilot {
    private static final int TIME_LIMIT = 100; // ms
    private static final int BOMBER_NUMBER = 4;
    private static final int BOMB_TIME = 8;
    private static final int[] dy = { -1, 1, 0, 0, 0 };
    private static final int[] dx = { 0, 0, 1, -1, 0 };

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

    Output solve(Turn turn) {
        long clock_begin = System.currentTimeMillis();
        Map<Integer, Bomber> bombers = this.selectBomber(turn.entities);
        Map<Point, List<Entity>> entity_at = entityMultimap(turn.entities);

        // to survive
        Set<Command> forbidden;
        {
            List<List<ExplodedTimeInfo>> explodedTime = explodedTime(turn);
            List<Map<Integer, Command>> commandBase = new ArrayList<>();
            Map<Point, Bomb> bombs = selectBomb(turn);

            for (Bomber b : bombers.values()) {
                // @HERE
                if (b.id == this.config.myId) {

                }
            }
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
}