package org.example.leetcode.MaximumAverageSubArrayOne;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MaximumAverageSubArrayOneTest {
    // nums = [1,12,-5,-6,50,3], k = 4
    @Test
    void testSolution() {
        int[] nums = { 1, 12, -5, -6, 50, 3 };
        int k = 4;
        double result = MaximumAverageSubArrayOne.solution(nums, k);
        assertEquals(12.75f, result);
    }
}
