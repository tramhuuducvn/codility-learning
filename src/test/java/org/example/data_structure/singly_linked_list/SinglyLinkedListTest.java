package org.example.data_structure.singly_linked_list;

import org.junit.jupiter.api.Test;

public class SinglyLinkedListTest {
    @Test
    void testAdd() {

    }

    @Test
    void testGetHead() {

    }

    @Test
    void testPrint() {

    }

    @Test
    void testSetHead() {

    }

    @Test
    void test() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>(1);
        list.add(2);
        list.add(4);
        list.add(5);
        list.add(7);

        list.print();
    }
}
