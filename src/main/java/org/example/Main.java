package org.example;

import java.nio.charset.Charset;
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

    }
}