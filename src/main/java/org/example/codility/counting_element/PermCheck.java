package org.example.codility.counting_element;

import java.util.HashSet;
import java.util.Set;

// PROBLEM
// LINK: https://app.codility.com/programmers/lessons/4-counting_elements/perm_check/
/**
 * A non-empty array A consisting of N integers is given.
 * 
 * A permutation is a sequence containing each element from 1 to N once, and
 * only once.
 * 
 * For example, array A such that:
 * 
 * A[0] = 4
 * A[1] = 1
 * A[2] = 3
 * A[3] = 2
 * is a permutation, but array A such that:
 * 
 * A[0] = 4
 * A[1] = 1
 * A[2] = 3
 * is not a permutation, because value 2 is missing.
 * 
 * The goal is to check whether array A is a permutation.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that, given an array A, returns 1 if array A is a permutation and 0 if it is
 * not.
 * 
 * For example, given array A such that:
 * 
 * A[0] = 4
 * A[1] = 1
 * A[2] = 3
 * A[3] = 2
 * the function should return 1.
 * 
 * Given array A such that:
 * 
 * A[0] = 4
 * A[1] = 1
 * A[2] = 3
 * the function should return 0.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * N is an integer within the range [1..100,000];
 * each element of array A is an integer within the range [1..1,000,000,000].
 */

// SOLUTION
/*
 * The main idea is check all conditions of a permutation:
 * 1: All elements from 1 to `N` are present in the `Set`
 * 2: There are no duplicates
 */

public class PermCheck {

    public static int solution(int[] A) {
        // Implement your solution here
        int size = A.length;
        Set<Integer> storage = new HashSet<>();

        for (int a : A) {
            // ensure the value of `a` in [1, N] and if the storage contains `a` that means
            // there are duplicates number so we have to return 0
            if (a > size || a < 1 || storage.contains(a)) {
                return 0;
            }
            storage.add((a));
        }
        // check the size of storage again
        return storage.size() == size ? 1 : 0;
    }

    public static void main(String[] args) {

    }
}
