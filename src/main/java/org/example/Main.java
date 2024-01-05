package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.example.util.Logger;

import com.netflix.spectator.atlas.impl.Query.In;

public class Main {
    public static String test(String input, Function<String, String> transformer) {
        return transformer.apply(input);
    }

    public static String log(String src) {
        System.out.println("@D_LOG: " + src);
        return "DUKE";
    }

    /**
     * Value
     */
    public static class Value {
        public int a;
        public int b;
    }

    public static void change(final Value value) {
        value.a = 10;
        value.b = 20;
    }

    public static void main(String[] args) {
        Map<Integer, Point> pMap = new HashMap<>();

        // Point point = new Point(1, 3);
        pMap.put(10, new Point(1, 3));
        pMap.put(5, new Point(1, 2));

        for (Point p : pMap.values()) {
            p.x += 2;
        }

        Logger.log(pMap.get(10));
    }
}

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
