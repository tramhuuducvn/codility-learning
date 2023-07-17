package org.example.leetcode.AddTwoNumber;

import org.example.data_structure.singly_linked_list.SinglyLinkedList;
import org.junit.jupiter.api.Test;

public class AddTwoNumberTest {
    @Test
    void test_1() {
        SinglyLinkedList<Integer> list1 = new SinglyLinkedList<Integer>(2);
        list1.add(4);
        list1.add(3);

        SinglyLinkedList<Integer> list2 = new SinglyLinkedList<Integer>(5);
        list2.add(6);
        list2.add(4);

        AddTwoNumber.solution(list1, list2).print();
    }

    @Test
    void test_2() {
        SinglyLinkedList<Integer> list1 = new SinglyLinkedList<Integer>(9);
        SinglyLinkedList<Integer> list2 = new SinglyLinkedList<Integer>(0);

        AddTwoNumber.solution(list1, list2).print();
    }

    @Test
    void test_3() {
        Integer[] arr1 = { 9, 9, 9, 9, 9, 9, 9 };
        SinglyLinkedList<Integer> list1 = new SinglyLinkedList<>(arr1);
        Integer[] arr2 = { 9, 9, 9, 9 };
        SinglyLinkedList<Integer> list2 = new SinglyLinkedList<>(arr2);

        AddTwoNumber.solution(list1, list2).print();
    }
}
