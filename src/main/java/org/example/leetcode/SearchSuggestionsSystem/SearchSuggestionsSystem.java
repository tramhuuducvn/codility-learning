package org.example.leetcode.SearchSuggestionsSystem;

import java.util.Arrays;

import org.example.util.Logger;

public class SearchSuggestionsSystem {

    public static void solution() {
        String[] products = { "mobile", "mouse", "moneypot", "monitor", "mousepad" };
        Arrays.sort(products);
        for (String string : products) {
            Logger.log(string);
        }
    }
}
