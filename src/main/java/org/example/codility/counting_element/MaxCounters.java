package org.example.codility.counting_element;

/*
LINK: https://app.codility.com/programmers/lessons/4-counting_elements/max_counters/
You are given N counters, initially set to 0, and you have two possible operations on them:

increase(X) − counter X is increased by 1,
max counter − all counters are set to the maximum value of any counter.
A non-empty array A of M integers is given. This array represents consecutive operations:

if A[K] = X, such that 1 ≤ X ≤ N, then operation K is increase(X),
if A[K] = N + 1 then operation K is max counter.
For example, given integer N = 5 and array A such that:

    A[0] = 3
    A[1] = 4
    A[2] = 4
    A[3] = 6
    A[4] = 1
    A[5] = 4
    A[6] = 4
the values of the counters after each consecutive operation will be:

    (0, 0, 1, 0, 0)
    (0, 0, 1, 1, 0)
    (0, 0, 1, 2, 0)
    (2, 2, 2, 2, 2)
    (3, 2, 2, 2, 2)
    (3, 2, 2, 3, 2)
    (3, 2, 2, 4, 2)
The goal is to calculate the value of every counter after all operations.

Write a function:

class Solution { public int[] solution(int N, int[] A); }

that, given an integer N and a non-empty array A consisting of M integers, returns a sequence of integers representing the values of the counters.

Result array should be returned as an array of integers.

For example, given:

    A[0] = 3
    A[1] = 4
    A[2] = 4
    A[3] = 6
    A[4] = 1
    A[5] = 4
    A[6] = 4
the function should return [3, 2, 2, 4, 2], as explained above.

Write an efficient algorithm for the following assumptions:

N and M are integers within the range [1..100,000];
each element of array A is an integer within the range [1..N + 1].
 */

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

    /*
     * The first solution for this problem is traverse the list of elements of array
     * A, if 1 <= current value is <= N then increase the counter by 1,
     * otherwise if current value = N + 1,
     * set value of all counters to the maximum value of all current counters.
     * Advantage: This approach is easy to understand
     * Disadvantage: This approach has low performance
     * Time complexity: O(N*M) (N is length of result, )
     * Space complexity: O(N) (so we need to store N elements of result)
     */
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

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    /*
     * just use two loop:
     * The target of first iterate is traverse all elements of array A to increase
     * the value of counters with `suitable value` and find the maximum value of
     * counters simultaneously. In the case the current counter value less than
     * current max value, we need to set current counter to max + 1, otherwise just
     * increase current value, and that is the `suitable value`.
     * 
     * And the second iterate we update all value less than the max in the counters
     * to max value.
     * Advantage: Better performance
     * Disadvantage: It's difficult to figure out and understand
     * Time complexity: O(N + M)
     * Space complexity: (N)
     */
    public static int[] solution_2(int N, int[] A) {
        int[] counters = new int[N];
        int max = 0;
        int b = 0;

        for (int a : A) {
            if (a <= N) {
                counters[a - 1] = max(b, counters[a - 1]) + 1;
                max = max(max, counters[a - 1]);
            } else {
                b = max;
            }
        }

        for (int i = 0; i < counters.length; ++i) {
            if (counters[i] < b) {
                counters[i] = b;
            }
        }

        return counters;
    }
}
