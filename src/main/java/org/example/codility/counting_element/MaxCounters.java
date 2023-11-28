package org.example.codility.counting_element;

public class MaxCounters {

    public static int increase(int[] src, int index) {
        if (index > -1 && index < src.length) {
            src[index] += 1;
            return src[index];
        }
        return 0;
    }

    public static void setToValue(int[] src, int value) {
        for (int i = 0; i < src.length; ++i) {
            src[i] = value;
        }
    }

    public static int[] solution(int N, int[] A) {
        int[] result = new int[N];
        int max = 0;
        int lastUpdate = max;

        for (int a : A) {

            if (a == N + 1) {
                if (lastUpdate == max) {
                    continue;
                }
                setToValue(result, max);
                lastUpdate = max;
                continue;
            }

            int temp = increase(result, a - 1);

            if (temp > max) {
                max = temp;
            }
        }

        return result;
    }
}
