package org.example.codility.greedy;

import java.util.Arrays;

import org.example.util.Logger;

// https://codility.com/media/train/14-GreedyAlgorithms.pdf
/**
 * 
 */
public class GreedyCoinChanging {

    public static int solution(int value, int[] coins) {
        Arrays.sort(coins);
        int result = 0;
        for (int i = coins.length - 1; i > -1 && value > 0; i--) {
            result += value / coins[i];
            value %= coins[i];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] coins = { 5, 1, 2 };
        Logger.log(solution(13, coins));
    }
}