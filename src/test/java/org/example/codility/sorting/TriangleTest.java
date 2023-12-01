package org.example.codility.sorting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TriangleTest {
    // example case
    @Test
    void example() {
        int[] A = { 10, 2, 5, 1, 8, 20 };
        int expected = 1;
        int actual = Triangle.solution(A);
        assertEquals(expected, actual);
    }

    // correctness case
    @Test
    void correctness_1() {
        int[] A = { 50, 2, 3, 1 };
        int expected = 0;
        int actual = Triangle.solution(A);
        assertEquals(expected, actual);
    }

    @Test
    void correctness_2() {
        int[] A = { 10, 2, 5, 1, 8, 20, -3, -4, -5 };
        int expected = 1;
        int actual = Triangle.solution(A);
        assertEquals(expected, actual);
    }
}
