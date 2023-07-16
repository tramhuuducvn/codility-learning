package org.example.data_structure.singly_linked_list;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SinglyLinkedList<T> {
    private Node<T> head;

    public SinglyLinkedList(T value) {
        head = new Node<T>(value);
    }

    public void add(T value) {
        Node<T> p = this.head;

        while (p.getNext() != null) {
            p = p.getNext();
        }

        p.setNext(new Node<T>(value));
    }

    public void print() {
        Node<T> p = this.head;

        while (p != null) {
            System.out.println(p.getVal());
            p = p.getNext();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((head == null) ? 0 : head.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        SinglyLinkedList other = (SinglyLinkedList) obj;

        if (head == null) {
            if (other.head != null) {
                return false;
            }
        } else {
            Node a = head;
            Node b = other.getHead();

            while (a != null && b != null && a.equals(b)) {
                a = a.getNext();
                b = b.getNext();
            }

            if (a == null && b == null) {
                return true;
            }

            return false;
        }

        return true;
    }

}
