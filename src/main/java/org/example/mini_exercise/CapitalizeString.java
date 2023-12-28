package org.example.mini_exercise;

import java.util.Arrays;

/*
 * hello world -> Hello World
 * hellO   worlD -> Hello   World
 */

public class CapitalizeString {
    public static String solution(String S) {
        byte[] chars = S.getBytes();
        System.out.println(chars);

        boolean cap = true;
        int size = S.length();
        for (int i = 0; i < size; ++i) {
            if (chars[i] == 32) {
                cap = true;
                continue;
            }
            if (cap == true) {
                chars[i] = (byte) Character.toUpperCase(chars[i]);
                cap = false;
            } else {
                chars[i] = (byte) Character.toLowerCase(chars[i]);
            }
        }
        String result = "";
        for (byte b : chars) {
            result += (char) b;
        }
        return result;
    }
}
