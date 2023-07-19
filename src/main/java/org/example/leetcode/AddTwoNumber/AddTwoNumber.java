// Reference: https://leetcode.com/problems/add-two-numbers/

package org.example.leetcode.AddTwoNumber;

import org.example.data_structure.singly_linked_list.Node;
import org.example.data_structure.singly_linked_list.SinglyLinkedList;

public class AddTwoNumber {
    /**
     * Add two numbers without recursion
     * 
     * @param l1
     * @param l2
     * @return
     */
    public static SinglyLinkedList<Integer> solutionWithoutRecursion(SinglyLinkedList<Integer> l1,
            SinglyLinkedList<Integer> l2) {
        if (l1 == null && l2 == null) {
            return null;
        }

        SinglyLinkedList<Integer> linkedList = new SinglyLinkedList<Integer>();

        Node<Integer> p1 = l1.getHead();
        Node<Integer> p2 = l2.getHead();

        int remain = 0;

        while (p1 != null && p2 != null) {
            int temp = p1.getVal() + p2.getVal() + remain;
            remain = temp / 10;
            linkedList.add(temp % 10);

            p1 = p1.getNext();
            p2 = p2.getNext();
        }

        while (p1 != null) {
            int temp = p1.getVal() + remain;
            remain = temp / 10;
            linkedList.add(temp % 10);
            p1 = p1.getNext();
        }

        while (p2 != null) {
            int temp = p2.getVal() + remain;
            remain = temp / 10;
            linkedList.add(temp % 10);
            p2 = p2.getNext();
        }

        if (remain > 0) {
            linkedList.add(remain);
        }

        return linkedList;
    }
}
