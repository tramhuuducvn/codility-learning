package org.example.data_structure.singly_linked_list;

import lombok.Getter;
import lombok.Setter;

/**
 * Define a node in a singly linked list
 */
@Setter
@Getter
public class ListNode<T> {
    private T val;
    private ListNode<T> next;

    public ListNode() {
        this.val = null;
        this.next = null;
    }

    public ListNode(T val) {
        this.val = val;
        this.next = null;
    }

    public ListNode(T val, ListNode<T> next) {
        this.val = val;
        this.next = next;
    }
}
