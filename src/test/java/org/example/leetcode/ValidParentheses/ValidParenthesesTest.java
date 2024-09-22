package org.example.leetcode.ValidParentheses;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidParenthesesTest {

    @ParameterizedTest
    @ValueSource(strings = { "{}[]", "{[()[{}](({[[]]}))]}{}", "{}{}[]()" })
    void testIsValid_ReturnTrue_WhenValidCase(String value) {
        assertTrue(ValidParentheses.isValid(value));
    }

    @ParameterizedTest
    @ValueSource(strings = { "{[])", "{[(][{}](({[[]]}))]}{}", "{}[]}[]()", "(])" })
    void testIsValid_ReturnFalse_WhenInValidCase(String value) {
        assertFalse(ValidParentheses.isValid(value));
    }

    @Test
    void testRun() {
        assertTrue(ValidParentheses.isValid("{}"));
    }
}
