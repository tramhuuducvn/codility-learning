// https://leetcode.com/problems/reverse-linked-list/

package org.example.leetcode.ReverseLinkedList;

public class ReverseLinkedList {
    public static ListNode solution(ListNode node) {
        if (node == null || node.next == null) {
            return node;
        }

        ListNode second = node.next;
        node.next = null;
        ListNode rest = solution(second);
        second.next = node;

        return rest;
    }
}
