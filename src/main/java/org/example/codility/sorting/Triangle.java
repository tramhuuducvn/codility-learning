package org.example.codility.sorting;

import java.util.Arrays;

public class Triangle {
    public static int solution(int[] A) {
        Arrays.sort(A);
        int i = A.length - 1;

        for (; i > 1; --i) {
            if (A[i - 2] > A[i] - A[i - 1]) {
                return 1;
            }
        }

        return 0;
    }
}
