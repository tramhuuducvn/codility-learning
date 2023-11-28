package org.example.mini_exercise.reverse_array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReverseArrayTest {
    @Test
    void test_1() {
        int[] A = { 1, 2, 3, 4, 5 };
        int[] E = { 5, 4, 3, 2, 1 };

        Assertions.assertArrayEquals(E, ReverseArray.solution(A));
    }

    @Test
    void test_2() {
        int[] A = { 7, 5, 4, 3, 2, 1 };
        int[] E = { 1, 2, 3, 4, 5, 7 };

        Assertions.assertArrayEquals(E, ReverseArray.solution(A));
    }

    @Test
    void test_3() {
        int[] A = { -1, -3 };
        int[] E = { -3, -1 };

        Assertions.assertArrayEquals(E, ReverseArray.solution(A));
    }

    @Test
    void test_4() {
        int[] A = {};
        int[] E = {};

        Assertions.assertArrayEquals(E, ReverseArray.solution(A));
    }

    @Test
    void test_5() {
        int[] A = { 1 };
        int[] E = { 1 };

        Assertions.assertArrayEquals(E, ReverseArray.solution(A));
    }
}
