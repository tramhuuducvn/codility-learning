package org.example.coding_game.hypersonic;

import java.util.*;

import org.example.util.Logger;

import java.io.*;
import java.math.*;

enum CellType {
    WALL, EMPTY, BOX, BOX_EXTRA_RANGE, BOX_EXTRA_BOMB
}

enum EntityType {
    PLAYER, BOMB, ITEM
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

    // public Entity() {
    // }

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
    private List<List<CellType>> board;
    private List<Entity> entities;
}

class AI {
    private GameConfig config;
    private List<Turn> turns;
}

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class HyperSonicGame {
    // for scan input
    private Scanner scanner;

    // Entrance Point
    public static void main(String args[]) {
        HyperSonicGame player = new HyperSonicGame();
        // Start the game
        player.play();
    }

    public HyperSonicGame() {
        scanner = new Scanner(System.in);
    }

    /*
     * Scan the map's dimensions including with and height
     * Scan current playerId
     */
    private GameConfig loadGameConfig() {
        GameConfig gameConfig = new GameConfig();
        gameConfig.width = scanner.nextInt();
        gameConfig.height = scanner.nextInt();
        gameConfig.myId = scanner.nextInt();

        return gameConfig;
    }

    private void play() {
        GameConfig gameConfig = loadGameConfig();

        while (true) {
            break;
        }

        // // game loop
        // while (true) {
        // for (int i = 0; i < height; i++) {
        // String row = in.next();
        // }
        // int entities = in.nextInt();
        // for (int i = 0; i < entities; i++) {
        // int entityType = in.nextInt();
        // int owner = in.nextInt();
        // int x = in.nextInt();
        // int y = in.nextInt();
        // int param1 = in.nextInt();
        // int param2 = in.nextInt();
        // }

        // // Write an action using System.out.println()
        // // To debug: System.err.println("Debug messages...");

        // System.out.println("BOMB 6 5");
        // }
        System.out.println(gameConfig);
    }
}