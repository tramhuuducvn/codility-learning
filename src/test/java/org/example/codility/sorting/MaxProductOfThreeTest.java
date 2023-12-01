package org.example.codility.sorting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MaxProductOfThreeTest {
    // example case
    @Test
    void example() {
        int[] A = { -3, 1, 2, -2, 5, 6 };

        int expected = 60;
        int actual = MaxProductOfThree.solution(A);
        assertEquals(expected, actual);
    }

    // correctness case
    @Test
    void correctness_1() {
        int[] A = { 1, 2, 3 };

        int expected = 6;
        int actual = MaxProductOfThree.solution(A);
        assertEquals(expected, actual);
    }

    @Test
    void correctness_2() {
        int[] A = { 0, -10, -1, 1, 2, 3 };
        int expected = 30;
        int actual = MaxProductOfThree.solution(A);
        assertEquals(expected, actual);
    }

    @Test
    void correctness_3() {
        int[] A = { 0, 10, 1, 1, 2, 3 };
        int expected = 60;
        int actual = MaxProductOfThree.solution(A);
        assertEquals(expected, actual);
    }

    @Test
    void correctness_4() {
        int[] A = { 0, 1, -2, -3 };
        int expected = 6;
        int actual = MaxProductOfThree.solution(A);
        assertEquals(expected, actual);
    }
}
