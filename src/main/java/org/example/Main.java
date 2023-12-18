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

    public static void main(String[] args) {
        String result = test("Hello", Main::log);
        String ret = test("Hello", String::toUpperCase);
        System.out.println(result);
    }
}