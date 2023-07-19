package org.example.leetcode.ReverseLinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReverseLinkedListTest {
    ListNode node;

    @BeforeEach
    void setup() {
        node = new ListNode(1);
        ListNode p = node;
        int arr[] = { 2, 3, 4, 5, 6 };

        for (int i : arr) {
            p.next = new ListNode(i);
            p = p.next;
        }
        node.println();
    }

    @Test
    void test_1() {
        // node.println();
    }
}
