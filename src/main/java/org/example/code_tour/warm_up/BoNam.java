package org.example.code_tour.warm_up;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BoNam {
    public static int getPentaListMax(int w1, int w2, List<Integer> arr) {
        Collections.sort(arr, Collections.reverseOrder());

        if (w1 == 0 && w2 == 0) {
            return arr.get(0);
        }

        int result = Integer.MIN_VALUE;
        int[] weights = { w1, w2, 1, w1, w2 };

        int n = arr.size();
        List<Integer> tempList = new ArrayList<>(arr.subList(0, 5));
        tempList.addAll(arr.subList(n - 5, n));

        if (arr.size() < 10) {
            tempList = new ArrayList<>(arr.subList(0, n));
        }

        List<List<Integer>> combinations = generateCombinations(tempList, 5);

        for (List<Integer> combination : combinations) {
            List<List<Integer>> permutations = generatePermutations(combination);
            for (List<Integer> permutation : permutations) {
                int temp = 0;
                for (int j = 0; j < 5; j++) {
                    temp += permutation.get(j) * weights[j];
                }
                result = Math.max(result, temp);
            }
        }

        return result;
    }

    private static List<List<Integer>> generateCombinations(List<Integer> arr, int r) {
        List<List<Integer>> combinations = new ArrayList<>();
        int n = arr.size();
        int[] combination = new int[r];

        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }

        while (combination[r - 1] < n) {
            List<Integer> currentCombination = new ArrayList<>();
            for (int i : combination) {
                currentCombination.add(arr.get(i));
            }
            combinations.add(currentCombination);

            int t = r - 1;
            while (t != 0 && combination[t] == n - r + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

        return combinations;
    }

    private static List<List<Integer>> generatePermutations(List<Integer> numbers) {
        List<List<Integer>> result = new ArrayList<>();
        permute(numbers, 0, result);
        return result;
    }

    private static void permute(List<Integer> numbers, int start, List<List<Integer>> result) {
        if (start >= numbers.size()) {
            result.add(new ArrayList<>(numbers));
            return;
        }

        for (int i = start; i < numbers.size(); i++) {
            swap(numbers, start, i);
            permute(numbers, start + 1, result);
            swap(numbers, start, i);
        }
    }

    private static void swap(List<Integer> numbers, int i, int j) {
        int temp = numbers.get(i);
        numbers.set(i, numbers.get(j));
        numbers.set(j, temp);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int w1 = scanner.nextInt();
        int w2 = scanner.nextInt();

        List<Integer> arr = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            int a = scanner.nextInt();
            arr.add(a);
        }

        System.out.println(getPentaListMax(w1, w2, arr));
    }
}
