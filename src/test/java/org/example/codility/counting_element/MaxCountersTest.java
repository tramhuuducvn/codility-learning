package org.example.codility.counting_element;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class MaxCountersTest {
    // example test
    /*
     * (0, 0, 1, 0, 0)
     * (0, 0, 1, 1, 0)
     * (0, 0, 1, 2, 0)
     * (2, 2, 2, 2, 2)
     * (3, 2, 2, 2, 2)
     * (3, 2, 2, 3, 2)
     * (3, 2, 2, 4, 2)
     */
    @Test
    void example() {
        // Given info
        int N = 5;
        int[] A = { 3, 4, 4, 6, 1, 4, 4 };
        // Check
        int[] actual = MaxCounters.solution(N, A);
        int[] expected = { 3, 2, 2, 4, 2 };

        assertArrayEquals(expected, actual);
    }

    // correctness tests
    /*
     * (0) - (0) - (1) - (2) - (2) - (3)
     * 
     */
    @Test
    void correctness_1() {
        // Given info
        int N = 1;
        int[] A = { 2, 1, 1, 2, 1 };
        // Check
        int[] actual = MaxCounters.solution(N, A);
        int[] expected = { 3 };

        assertArrayEquals(expected, actual);
    }

    /**
     * (0, 0, 0, 0)
     * (1, 0, 0, 0)
     * (1, 0, 1, 0)
     * (1, 1, 1, 1)
     * (1, 1, 1, 1)
     * (1, 1, 1, 2)
     * (2, 2, 2, 2)
     * (2, 2, 2, 2)
     */
    @Test
    void correctness_2() {
        // Given info
        int N = 4;
        int[] A = { 1, 3, 5, 5, 4, 5, 5 };
        // Check
        int[] actual = MaxCounters.solution(N, A);
        int[] expected = { 2, 2, 2, 2 };

        assertArrayEquals(expected, actual);
    }

}
