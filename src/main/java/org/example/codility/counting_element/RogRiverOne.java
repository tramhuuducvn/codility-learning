package org.example.codility.counting_element;

// PROBLEM
/*

LINK: https://app.codility.com/programmers/lessons/4-counting_elements/frog_river_one/

A small frog wants to get to the other side of a river. The frog is initially located on one bank of the river (position 0) and wants to get to the opposite bank (position X+1). Leaves fall from a tree onto the surface of the river.

You are given an array A consisting of N integers representing the falling leaves. A[K] represents the position where one leaf falls at time K, measured in seconds.

The goal is to find the earliest time when the frog can jump to the other side of the river. The frog can cross only when leaves appear at every position across the river from 1 to X (that is, we want to find the earliest moment when all the positions from 1 to X are covered by leaves). You may assume that the speed of the current in the river is negligibly small, i.e. the leaves do not change their positions once they fall in the river.

For example, you are given integer X = 5 and array A such that:

  A[0] = 1
  A[1] = 3
  A[2] = 1
  A[3] = 4
  A[4] = 2
  A[5] = 3
  A[6] = 5
  A[7] = 4
In second 6, a leaf falls into position 5. This is the earliest time when leaves appear in every position across the river.

Write a function:

class Solution { public int solution(int X, int[] A); }

that, given a non-empty array A consisting of N integers and integer X, returns the earliest time when the frog can jump to the other side of the river.

If the frog is never able to jump to the other side of the river, the function should return −1.

For example, given X = 5 and array A such that:

  A[0] = 1
  A[1] = 3
  A[2] = 1
  A[3] = 4
  A[4] = 2
  A[5] = 3
  A[6] = 5
  A[7] = 4
the function should return 6, as explained above.

Write an efficient algorithm for the following assumptions:

N and X are integers within the range [1..100,000];
each element of array A is an integer within the range [1..X].

 */

/*
 * SOLUTION: 
 * Using a Set is to iterate through the array `A` and add each leaf's position to the set. 
 * We check if the set contains all the solutions from 1 to `X`.
 * If it's does, we return the current time.
 * This solution has a time complexity is O(N) but uses additional memory to store the set.
 */

import java.util.HashSet;
import java.util.Set;

public class RogRiverOne {

    public static int solution(int X, int[] A) {
        int size = A.length;
        // Initialize an empty set
        Set<Integer> positions = new HashSet<>();

        // iterate through the array `A`
        for (int i = 0; i < size; ++i) {
            int key = A[i];

            if (!positions.contains(key)) {
                // add each leaf's position to the set
                positions.add(key);
            }

            if (positions.size() == X) {
                // return the earliest time if all leaves is covered
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] A = { 1, 2, 3, 4, 5, 6 };
        for (int a : A) {
            System.out.print(a);
        }
    }
}
