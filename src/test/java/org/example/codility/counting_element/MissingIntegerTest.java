package org.example.codility.counting_element;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MissingIntegerTest {

    @Test
    @DisplayName("Test case 1")
    public void test_1() {
        int[] A = { 1, 3, 6, 4, 1, 2 };
        assertEquals(5, MissingInteger.solution(A));
    }

    @Test
    @DisplayName("Test case 2")
    public void test_2() {
        int[] A = { 1, 3, 2 };
        assertEquals(4, MissingInteger.solution(A));
    }

    @Test
    @DisplayName("Test case 3")
    public void test_3() {
        int[] A = { -1, -3 };
        assertEquals(1, MissingInteger.solution(A));
    }

}