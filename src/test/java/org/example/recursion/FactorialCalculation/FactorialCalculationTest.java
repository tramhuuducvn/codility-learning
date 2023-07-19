package org.example.recursion.FactorialCalculation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FactorialCalculationTest {
    @Test // zero case
    public void test_1() {
        Assertions.assertEquals(1, FactorialCalculation.solution(0));
    }

    @Test // one case
    public void test_2() {
        Assertions.assertEquals(1, FactorialCalculation.solution(1));
    }

    @Test // normal case
    public void test_3() {
        Assertions.assertEquals(120, FactorialCalculation.solution(5));
    }

    @Test // negative case
    public void test_4() {
        Assertions.assertEquals(-1, FactorialCalculation.solution(-10));
    }
}
