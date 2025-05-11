package org.example.codility.interations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BinaryGapTest {

    @ParameterizedTest
    @MethodSource("provideValueForConvertToBinary")
    public void testConvertBinary(int value, String expected) {
        assertEquals(expected, BinaryGap.convertToBinary(value));
    }

    @ParameterizedTest
    @MethodSource("provideValueForSolution")
    public void testSolution(int value, int expected) {
        assertEquals(expected, BinaryGap.solution1(value));
    }

    @ParameterizedTest
    @MethodSource("provideValueForSolution")
    public void testSolution2(int value, int expected) {
        assertEquals(expected, BinaryGap.solution2(value));
    }

    private static Stream<Arguments> provideValueForConvertToBinary() {
        return Stream.of(
                Arguments.of(9, "1001"),
                Arguments.of(529, "1000010001"),
                Arguments.of(20, "10100"),
                Arguments.of(15, "1111"),
                Arguments.of(32, "100000"),
                Arguments.of(1162, "10010001010"),
                Arguments.of(328, "101001000")

        );
    }

    private static Stream<Arguments> provideValueForSolution() {
        return Stream.of(Arguments.of(9, 2),
                Arguments.of(529, 4),
                Arguments.of(20, 1),
                Arguments.of(15, 0),
                Arguments.of(32, 0),
                Arguments.of(1162, 3),
                Arguments.of(328, 2));
    }

}
