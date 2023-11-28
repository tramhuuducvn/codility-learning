package org.example.mini_exercise.reverse_array;

public class ReverseArray {
    public static int[] solution(int[] A) {
        int size = A.length;

        for (int i = 0; i < size / 2; ++i) {
            int temp = A[i];
            A[i] = A[size - i - 1];
            A[size - i - 1] = temp;
        }

        return A;
    }
}
