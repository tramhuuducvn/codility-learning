package org.example.codility.BinaryAearchAlgorithm;

public class BinarySearch {
    public static int solution(int[] A, int value) {
        int size = A.length;
        int beg = 0;
        int end = size - 1;

        int result = -1;

        while (beg <= end) {
            int mid = (beg + end) / 2;

            if (A[mid] <= value) {
                beg = mid + 1;
                result = mid;
            } else {
                end = mid - 1;
            }
        }

        return result;
    }
}
