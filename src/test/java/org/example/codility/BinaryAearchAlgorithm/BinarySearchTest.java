package org.example.codility.BinaryAearchAlgorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BinarySearchTest {
    // example
    @Test
    public void example() {
        int[] A = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int value = 7;
        int expected = 6;
        int actual = BinarySearch.solution(A, value);

        assertEquals(expected, actual);
    }
}
