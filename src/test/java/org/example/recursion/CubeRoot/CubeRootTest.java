package org.example.recursion.CubeRoot;

import java.util.stream.Stream;

import org.example.util.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("CubeRootTest")
public class CubeRootTest {

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void testSolution(int value, int expected) {
        Logger.log("Testing with value: " + value + " -  " + expected);
    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(Arguments.of(27, 3),
                Arguments.of(64, 4),
                Arguments.of(125, 5),
                Arguments.of(216, 6));
    }

}
