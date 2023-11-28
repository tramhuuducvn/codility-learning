package org.example.codility.counting_element;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MaxCountersTest {
    // example test
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
}
