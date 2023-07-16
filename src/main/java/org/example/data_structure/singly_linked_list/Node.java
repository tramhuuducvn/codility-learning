package org.example.data_structure.singly_linked_list;

import lombok.Getter;
import lombok.Setter;

/**
 * Define a node in a singly linked list
 */
@Setter
@Getter
public class Node<T> {
    private T val;
    private Node<T> next;

    public Node() {
        this.val = null;
        this.next = null;
    }

    public Node(T val) {
        this.val = val;
        this.next = null;
    }

    public Node(T val, Node<T> next) {
        this.val = val;
        this.next = next;
    }
}
