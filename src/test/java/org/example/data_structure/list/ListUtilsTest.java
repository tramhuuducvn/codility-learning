package org.example.data_structure.list;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ListUtilsTest {

    @Test
    @DisplayName("Given a list has 100 elements, when partitioning with offset 0 and limit 50, then return 50 elements")
    void givenOffset0Limit50_whenPartitioning_thenReturn50() { 
        // Given
        List<Integer> list = IntStream.range(0, 90).boxed().collect(Collectors.toList());
        int partitionSize = 50;

        // When
        List<List<Integer>> result = ListUtils.partition(list, partitionSize);

        // Then
        assertEquals(2, result.size());
    }
}
