package org.example.codility.counting_element;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

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
        int[] actual = MaxCounters.solution_2(N, A);
        int[] expected = { 3, 2, 2, 4, 2 };

        assertArrayEquals(expected, actual);
    }

    // correctness tests
    /*
     * (0) - (0) - (1) - (2) - (2) - (3)
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

    /**
     * (0, 0, 0, 0)
     * (1, 0, 0, 0)
     * (1, 0, 1, 0)
     * (1, 1, 1, 1)
     * (1, 1, 1, 1)
     * (1, 1, 1, 2)
     * (2, 2, 2, 2)
     * (2, 3, 2, 2)
     * (2, 4, 2, 2)
     * (4, 4, 4, 4)
     */
    @Test
    void correctness_3() {
        // Given info
        int N = 4;
        int[] A = { 1, 3, 5, 5, 4, 5, 2, 2, 5 };
        // Check
        int[] actual = MaxCounters.solution(N, A);
        int[] expected = { 4, 4, 4, 4 };

        assertArrayEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
    void palindromes(String candidate) {
        assertTrue(StringUtils.isNotBlank(candidate));
    }
}
