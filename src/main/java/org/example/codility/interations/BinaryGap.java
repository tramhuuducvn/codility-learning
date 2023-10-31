//Reference: https://app.codility.com/programmers/lessons/1-iterations/binary_gap/
package org.example.codility.interations;

public class BinaryGap {
    public static String convertToBinary(int N) {
        StringBuilder str = new StringBuilder("");
        while (N > 0) {
            int temp = N % 2;
            str.append(temp);
            N /= 2;
        }
        return str.reverse().toString();
    }

    public static int solution(int N) {
        char[] binstr = convertToBinary(N).toCharArray();
        int size = binstr.length;
        int index = 0;
        // ignore first zero cases
        while (index < size && binstr[index] == '0') {
            index++;
        }

        int max = 0;

        for (; index < size; ++index) {
            int count = 0;

            index++;
            while (index < size && binstr[index] == '0') {
                count++;
                index++;
            }

            if (index >= size) {
                count = 0;
            }

            if (count > max) {
                max = count;
            }
            index--;
        }

        return max;
    }
}
