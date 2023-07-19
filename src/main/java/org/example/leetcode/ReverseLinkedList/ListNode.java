package org.example.leetcode.ReverseLinkedList;

public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public void println() {
        ListNode p = this;
        while (p != null) {
            System.out.println(p.val + "\t");
            p = p.next;
        }
    }
}
