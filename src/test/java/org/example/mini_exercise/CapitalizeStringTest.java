package org.example.mini_exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CapitalizeStringTest {
    @Test
    void testSolution() {
        String actual = CapitalizeString.solution("hEllo   wOrld");
        String expected = "Hello   World";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_2() {
        String actual = CapitalizeString.solution("  hEllo   wOrld");
        String expected = "  Hello   World";
        Assertions.assertEquals(expected, actual);
    }
}
