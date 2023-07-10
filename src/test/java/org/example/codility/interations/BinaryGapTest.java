package org.example.codility.interations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BinaryGapTest {
    @Test
    void testConvertToBinary_1() {
        String result = BinaryGap.convertToBinary(9);
        assertEquals("1001", result);
    }

    @Test
    void testConvertToBinary_2() {
        String result = BinaryGap.convertToBinary(529);
        assertEquals("1000010001", result);
    }

    @Test
    void testConvertToBinary_3() {
        String result = BinaryGap.convertToBinary(20);
        assertEquals("10100", result);
    }

    @Test
    void testConvertToBinary_4() {
        String result = BinaryGap.convertToBinary(15);
        assertEquals("1111", result);
    }

    @Test
    void testConvertToBinary_5() {
        String result = BinaryGap.convertToBinary(32);
        assertEquals("100000", result);
    }

    @Test
    void testConvertToBinary_6() {
        String result = BinaryGap.convertToBinary(1162);
        assertEquals("10010001010", result);
    }

    @Test
    void testConvertToBinary_7() {
        String result = BinaryGap.convertToBinary(328);
        assertEquals("101001000", result);
    }

    @Test
    void testSolution_1() {
        int k = BinaryGap.solution(9);
        assertEquals(2, k);
    }

    @Test
    void testSolution_2() {
        int k = BinaryGap.solution(529);
        assertEquals(4, k);
    }

    @Test
    void testSolution_3() {
        int k = BinaryGap.solution(20);
        assertEquals(1, k);
    }

    @Test
    void testSolution_4() {
        int k = BinaryGap.solution(15);
        assertEquals(0, k);
    }

    @Test
    void testSolution_5() {
        int k = BinaryGap.solution(32);
        assertEquals(0, k);
    }

    @Test
    void testSolution_6() {
        int k = BinaryGap.solution(1162);
        assertEquals(3, k);
    }

    @Test
    void testSolution_7() {
        int k = BinaryGap.solution(328);
        assertEquals(2, k);
    }
}
