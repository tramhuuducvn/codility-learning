// https://app.codility.com/programmers/lessons/4-counting_elements/missing_integer/

/**
 * 
This is a demo task.

Write a function:

class Solution { public int solution(int[] A); }

that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.

For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.

Given A = [1, 2, 3], the function should return 4.

Given A = [−1, −3], the function should return 1.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [1..100,000];
each element of array A is an integer within the range [−1,000,000..1,000,000].

*/

package org.example.codility.counting_element;

/*
 * Main Idea:
 * Iterate through the boolean array and use that array to mark the presence of positive integers.
 * After marking, find the first unmarked index, which represents the smallest missing positive integer.
 */

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