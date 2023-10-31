package org.example.mini_exercise;

public class MyMath {
    public static boolean isSquareNumber(int N) {
        if (N < 0) {
            return false;
        }
        if (N == 0) {
            return true;
        }

        for (int i = 0; i < N; ++i) {
            if (i * i == N) {
                return true;
            }
        }

        return false;
    }

    /**
     * given an array
     * write a function count how many pair of element in that array has a sum
     * equals a given number.
     * ex: arr = {2,3,4,5,6}, givenNumber = 8
     * countPair() = 2, explain: [{2, 6}, {3,5}]
     */

    public static void main(String[] args) {
        int[] array = { 1, 2, 3, 4, 5, 7, 6 };
        int count = 0;
        int target = 8;

        for (int i = 0; i < array.length; ++i) {
            for (int j = i + 1; j < array.length; ++j) {
                if (array[i] + array[j] == target) {
                    count++;
                    break;
                }
            }
        }

        System.out.println(count);
    }
}