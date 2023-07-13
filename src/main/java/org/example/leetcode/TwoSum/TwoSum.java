package org.example.leetcode.TwoSum;

import java.util.HashMap;

public class TwoSum {
    public static int[] solution(int[] nums, int target) {
        int[] a = new int[2];
        HashMap<Integer, Integer> store = new HashMap<>();

        for (int i = 0; i < nums.length; ++i) {
            if (store.containsKey(target - nums[i])) {
                a[0] = store.get(target - nums[i]);
                a[1] = i;
                break;
            }
            store.put(nums[i], i);
        }

        return a;
    }
}
