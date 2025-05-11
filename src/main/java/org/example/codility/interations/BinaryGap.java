//Reference: https://app.codility.com/programmers/lessons/1-iterations/binary_gap/
package org.example.codility.interations;

import java.util.ArrayList;
import java.util.List;

/**
 * A binary gap within a positive integer N is any maximal sequence of
 * consecutive zeros that is surrounded by ones at both ends in the binary
 * representation of N.
 * 
 * For example, number 9 has binary representation 1001 and contains a binary
 * gap of length 2. The number 529 has binary representation 1000010001 and
 * contains two binary gaps: one of length 4 and one of length 3. The number 20
 * has binary representation 10100 and contains one binary gap of length 1. The
 * number 15 has binary representation 1111 and has no binary gaps. The number
 * 32 has binary representation 100000 and has no binary gaps.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int N); }
 * 
 * that, given a positive integer N, returns the length of its longest binary
 * gap. The function should return 0 if N doesn't contain a binary gap.
 * 
 * For example, given N = 1041 the function should return 5, because N has
 * binary representation 10000010001 and so its longest binary gap is of length
 * 5. Given N = 32 the function should return 0, because N has binary
 * representation '100000' and thus no binary gaps.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * N is an integer within the range [1..2,147,483,647].
 */

public class BinaryGap {
    /**
     * Convert number from decimal base to binary base.
     * 
     * @param N the number in decimal base
     * @return a number in binary base
     */
    public static String convertToBinary(int N) {
        StringBuilder str = new StringBuilder("");
        while (N > 0) {
            int temp = N % 2;
            str.append(temp);
            N /= 2;
        }
        return str.reverse().toString();
    }

    /**
     * Solution 1.
     * 
     * @param N
     * @return
     */
    public static int solution1(int N) {
        char[] binstr = convertToBinary(N).toCharArray();
        int size = binstr.length;
        int index = 0;
        // ignore first zero cases
        while (index < size && binstr[index] == '0') {
            index++;
        }

        // init max value
        int max = 0;

        for (; index < size; ++index) {
            int count = 0;

            index++;
            while (index < size && binstr[index] == '0') {
                count++;
                index++;
            }

            // in the case index >= size that mean the end of array is not "1" => the
            // current
            // zeros sequence is not surrounded by ones => count = 0
            if (index >= size) {
                count = 0;
            }
            // find the max
            if (count > max) {
                max = count;
            }
            // This line is very important because it make sure that we don't miss any
            // element in the complex nested loop
            index--;
        }

        return max;
    }

    public static int solution2(int N) {
        Integer.toBinaryString(N);
        List<Integer> positions = new ArrayList<>();
        int currentValue = N;
        int r = 0;
        int p = 0;
        while (currentValue > 0) {
            r = currentValue % 2;
            currentValue = currentValue / 2;
            if (r != 0) {
                positions.add(p);
            }
            p++;
        }

        int maxInterval = 0;
        int index = positions.size() - 1;
        while (index > 0) {
            int interval = positions.get(index) - positions.get(index - 1) - 1;
            if (interval > maxInterval) {
                maxInterval = interval;
            }
            index--;
        }

        return maxInterval;
    }
}
