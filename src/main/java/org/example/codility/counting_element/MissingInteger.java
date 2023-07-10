package org.example.codility.counting_element;

public class MissingInteger {
    public static int solution(int[] A) {
        int size = A.length;
        boolean[] marked = new boolean[size];

        for (int k : A) {
            if (k > 0 && k <= size) {
                marked[k - 1] = true;
            }
        }

        for (int i = 0; i < size; ++i) {
            if (marked[i] == false) {
                return i + 1;
            }
        }

        return size + 1;
    }
}