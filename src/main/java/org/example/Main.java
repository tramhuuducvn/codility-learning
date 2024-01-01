package org.example;

import java.util.function.Function;

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

    }
}