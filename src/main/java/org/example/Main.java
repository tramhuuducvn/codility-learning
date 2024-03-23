package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.ListUtils;

public class Main {

    public static void main(String[] args) {
        List<Integer> originalList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        List<List<Integer>> subList = ListUtils.partition(originalList, 10);
        System.out.println("Hello ");
    }
}
