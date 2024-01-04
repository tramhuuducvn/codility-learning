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
        Map<Point, String> map = new HashMap<>();
        map.put(new Point(1, 2), "Hello");
        map.put(new Point(3, 2), "Hello");

        map.put(new Point(1, 2), "Hello Bro!");
        Logger.log("#DUKE: " + map.get(new Point(1, 2)));
    }
}

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