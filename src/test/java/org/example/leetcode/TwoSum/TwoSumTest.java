package org.example.leetcode.TwoSum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TwoSumTest {

    @Test
    void test_1() {
        int[] nums = { 2, 7, 11, 15 };
        int target = 9;

        int[] expected = { 0, 1 };
        Assertions.assertArrayEquals(expected, TwoSum.solution(nums, target));
    }

    @Test
    void test_2() {
        int[] nums = { 3, 2, 4 };
        int target = 6;

        int[] expected = { 1, 2 };
        Assertions.assertArrayEquals(expected, TwoSum.solution(nums, target));
    }

    @Test
    void test_3() {
        int[] nums = { 3, 3 };
        int target = 6;

        int[] expected = { 0, 1 };
        Assertions.assertArrayEquals(expected, TwoSum.solution(nums, target));
    }

    @Test
    void test_4() {
        int[] nums = { 8, 2, 11, 15, 1, 7 };
        int target = 9;

        int[] expected = { 0, 4 };
        Assertions.assertArrayEquals(expected, TwoSum.solution(nums, target));
    }
}
