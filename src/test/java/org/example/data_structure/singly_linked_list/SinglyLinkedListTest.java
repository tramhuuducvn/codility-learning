package org.example.data_structure.singly_linked_list;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SinglyLinkedListTest {
    private SinglyLinkedList<Integer> integerList;

    @BeforeEach
    void setup() {
        integerList = new SinglyLinkedList<Integer>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(4);
        integerList.add(5);
        integerList.add(7);
        integerList.insert(3, 10);
        // integerList.remove(5);
        integerList.print();
    }

    @Test
    void testGetSize() {
        Assertions.assertEquals(6, integerList.getSize());
    }

    @Test
    void testGet() {
        Assertions.assertEquals(4, integerList.get(2).getVal());
        Assertions.assertEquals(1, integerList.get(0).getVal());
        Assertions.assertEquals(5, integerList.get(4).getVal());
        Assertions.assertEquals(null, integerList.get(-1));
        Assertions.assertEquals(null, integerList.get(6));
        Assertions.assertEquals(null, integerList.get(7));
    }
}
