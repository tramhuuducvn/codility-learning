package org.example.leetcode.AddTwoNumber;

import org.example.data_structure.singly_linked_list.Node;
import org.example.data_structure.singly_linked_list.SinglyLinkedList;

public class AddTwoNumber {
    public static SinglyLinkedList<Integer> solution(SinglyLinkedList<Integer> l1, SinglyLinkedList<Integer> l2) {
        if (l1 == null && l2 == null) {
            return null;
        }

        SinglyLinkedList<Integer> linkedList = new SinglyLinkedList<Integer>(null);

        Node<Integer> head1 = l1.getHead();
        Node<Integer> head2 = l2.getHead();

        while (head1 != null && head2 != null) {

        }

        return null;
    }
}
