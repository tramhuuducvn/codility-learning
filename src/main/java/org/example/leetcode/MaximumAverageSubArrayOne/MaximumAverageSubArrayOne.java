package org.example.leetcode.MaximumAverageSubArrayOne;

// Maximum Average Sub Array I
/*
 * You are given an integer array `nums` consisting of `n` elements, and an integer `k`
 * Find a contiguous sub-array whose length is equal to `k` that has the maximum average value and return this value
 * 
 * Link: https://leetcode.com/problems/maximum-average-subarray-i/
 */
public class MaximumAverageSubArrayOne {
    // [1,2,3,4,5,6,7,8]
    public static double solution(int[] nums, int k) {
        int size = nums.length;
        int subArraySum = 0;
        for (int i = 0; i < k; ++i) {
            subArraySum += nums[i];
        }
        double maxAvg = subArraySum / (double) k;

        for (int i = 0; i < size - k; ++i) {
            subArraySum = subArraySum - nums[i] + nums[k + i];
            double currentMaxAvg = subArraySum / (double) k;

            if (currentMaxAvg > maxAvg) {
                maxAvg = currentMaxAvg;
            }
        }

        return maxAvg;
    }
}
