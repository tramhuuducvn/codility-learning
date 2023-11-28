package org.example.codility.counting_element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RogRiverOneTest {
    @Test
    void caseFrogCanJumpToTheOtherSide() {
        int[] A = { 1, 3, 1, 4, 2, 3, 5, 4 };
        int x = 5;
        assertEquals(6, RogRiverOne.solution(x, A));
    }

    @Test
    void caseFrogIsNeverAbleJumpToTheOtherSide() {
        int[] A = { 1, 3, 1, 4, 2, 3, 4, 4 };
        int x = 5;
        assertEquals(-1, RogRiverOne.solution(x, A));
    }
}
