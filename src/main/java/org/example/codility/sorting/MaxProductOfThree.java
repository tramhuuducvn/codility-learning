package org.example.codility.sorting;

// LINK: https://app.codility.com/programmers/lessons/6-sorting/max_product_of_three/
/*
A non-empty array A consisting of N integers is given. The product of triplet (P, Q, R) equates to A[P] * A[Q] * A[R] (0 ≤ P < Q < R < N).

For example, array A such that:

  A[0] = -3
  A[1] = 1
  A[2] = 2
  A[3] = -2
  A[4] = 5
  A[5] = 6
contains the following example triplets:

(0, 1, 2), product is −3 * 1 * 2 = −6
(1, 2, 4), product is 1 * 2 * 5 = 10
(2, 4, 5), product is 2 * 5 * 6 = 60
Your goal is to find the maximal product of any triplet.

Write a function:

class Solution { public int solution(int[] A); }

that, given a non-empty array A, returns the value of the maximal product of any triplet.

For example, given array A such that:

  A[0] = -3
  A[1] = 1
  A[2] = 2
  A[3] = -2
  A[4] = 5
  A[5] = 6
the function should return 60, as the product of triplet (2, 4, 5) is maximal.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [3..100,000];
each element of array A is an integer within the range [−1,000..1,000].
*/

public class MaxProductOfThree {
    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    public static int solution(int[] A) {
        int size = A.length;
        int count = 1;

        while (count < 4) {
            int minIndex = count - 1;
            for (int i = count; i < size; ++i) {
                if (A[minIndex] > A[i]) {
                    minIndex = i;
                }
            }
            swap(A, minIndex, count - 1);

            int maxIndex = size - count;
            for (int i = 0; i < size - count; ++i) {
                if (A[maxIndex] < A[i]) {
                    maxIndex = i;
                }
            }
            swap(A, maxIndex, size - count);
            count++;
        }

        int a = A[size - 1] * A[size - 2] * A[size - 3];
        int b = A[0] * A[1] * A[size - 1];
        return a > b ? a : b;
    }
}
