package org.example.leetcode.ValidParentheses;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.example.util.Logger;

public class ValidParentheses {

    private static final Set<Character> openingBrackets = new HashSet<>(Arrays.asList('(', '[', '{'));
    private static final Map<Character, Character> closingBrackets = new HashMap<>();
    static {
        closingBrackets.put(')', '(');
        closingBrackets.put(']', '[');
        closingBrackets.put('}', '{');
    }

    public static boolean isValid(String s) {
        // Define a stack to store our opening brackets
        Stack<Character> brackets = new Stack<>();

        for (int item : s.chars().toArray()) {
            char current = (char) item;
            if (openingBrackets.contains(current)) {
                brackets.add(Character.valueOf(current));
            } else if (closingBrackets.containsKey(current)) {
                if (brackets.empty()) {
                    return false;
                }

                char top = brackets.peek();
                if (top == closingBrackets.get(current)) {
                    brackets.pop();
                } else {
                    return false;
                }
            }
        }

        // The given parentheses will be true if no brackets remain in our store
        return brackets.isEmpty();
    }
}
