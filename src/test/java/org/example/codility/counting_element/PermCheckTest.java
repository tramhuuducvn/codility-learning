package org.example.codility.counting_element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PermCheckTest {
    @Test
    void test_1() {
        int[] A = { 4, 3, 1, 2 };
        assertEquals(1, PermCheck.solution(A));
    }

    @Test
    void test_2() {
        int[] A = { 4, 3, 2 };
        assertEquals(0, PermCheck.solution(A));
    }

    @Test
    void test_3() {
        int[] A = { 4, 3, 2, 0 };
        assertEquals(0, PermCheck.solution(A));
    }

    @Test
    void test_4() {
        int[] A = { 4, 3, 2, 5 };
        assertEquals(0, PermCheck.solution(A));
    }

    @Test
    void test_5() {
        int[] A = { 4, 3, 2, 5, 1 };
        assertEquals(1, PermCheck.solution(A));
    }
}
