package org.example;

import java.util.Map;
import java.util.function.Function;

import org.example.util.Logger;

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
        // String result = test("Hello", Main::log);
        // String ret = test("Hello", String::toUpperCase);
        // System.out.println(result);

        Map<String, String> envVariables = System.getenv();

        for (Map.Entry<String, String> entry : envVariables.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Logger.log(key + " = " + value);
        }
    }
}