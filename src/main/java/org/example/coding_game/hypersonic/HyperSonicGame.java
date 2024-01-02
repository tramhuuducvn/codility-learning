package org.example.coding_game.hypersonic;

import java.util.*;

import org.example.util.Logger;

import java.io.*;
import java.math.*;

enum CellType {
    WALL,
    EMPTY,
    BOX,
    BOX_EXTRA_RANGE,
    BOX_EXTRA_BOMB
}

enum EntityType {
    PLAYER,
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
    int x;
    int y;

    public Entity(EntityType entityType, int x, int y) {
        this.entityType = entityType;
        this.x = x;
        this.y = y;
    }
}

class Player extends Entity {
    int id;
    int bomb;
    int range;

    public Player(EntityType entityType, int x, int y, int id, int bomb, int range) {
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
}

class AI {
    private GameConfig config;
    private List<Turn> turns;

    public AI(GameConfig config) {
        this.config = config;
    }
}

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class HyperSonicGame {
    // for scan input
    private Scanner scanner;
    // game configuration
    private GameConfig gameConfig;

    // Entrance Point
    public static void main(String args[]) {
        HyperSonicGame player = new HyperSonicGame();
        // Start the game
        player.play();
    }

    public HyperSonicGame() {
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

    private Turn scanTurn() {
        Turn turn = new Turn();
        turn.board = new ArrayList<>(); // Board game = {width*height} cells

        // Load board game
        for (int i = 0; i < this.gameConfig.height; i++) {
            String row = scanner.next();
            char[] cells = row.toCharArray();

            List<CellType> boardRow = new ArrayList<>(); // {width} cells on each row
            for (char c : cells) {
                boardRow.add(CellType.valueOf(String.valueOf(c)));
            }
            turn.board.add(boardRow);
        }

        int entities = scanner.nextInt();

        for (int i = 0; i < entities; i++) {
            int entityType = scanner.nextInt();
            int owner = scanner.nextInt();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int param1 = scanner.nextInt();
            int param2 = scanner.nextInt();
        }

        return turn;
    }

    private void play() {
        if (this.gameConfig == null) {
            System.out.println("GAME CONFIG IS NULL");
            return;
        }

        AI ai = new AI(this.gameConfig);

        while (true) {
            Turn turn = scanTurn();
            break;
        }
        System.out.println(gameConfig);
    }
}