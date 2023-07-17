package org.example.data_structure.singly_linked_list;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SinglyLinkedList<T> {
    private Node<T> head;
    private int size;

    public SinglyLinkedList() {
        this.size = 0;
        this.head = null;
    }

    public SinglyLinkedList(T[] arr) {
        for (T t : arr) {
            this.add(t);
        }
    }

    public SinglyLinkedList(T value) {
        head = new Node<T>(value);
        this.size = 1;
    }

    public void add(T value) {
        if (this.head == null) {
            this.head = new Node<T>(value);
            this.size++;
            return;
        }

        Node<T> p = this.head;

        while (p.getNext() != null) {
            p = p.getNext();
        }

        p.setNext(new Node<T>(value));
        this.size++;
    }

    public Node<T> get(int index) {
        if (index < 0) {
            System.out.println("Error at get: Index < 0");
            return null;
        }

        Node<T> p = this.head;
        while (p != null && index > 0) {
            p = p.getNext();
            index--;
        }

        return p;
    }

    public void insert(int index, T value) {
        if (index < 0 || value == null) {
            System.out.println("Error at insert: Index < 0 or T is null");
        }

        if (index == 0) {
            this.head = new Node<T>(value, this.head);
            this.size++;
            return;
        }

        Node<T> p = this.head;

        while (p.getNext() != null && index > 1) {
            p = p.getNext();
            index--;
        }

        if (index > 1) {
            System.out.println("Error at insert: Index is longer than the size of the list");
            return;
        }

        if (p.getNext() == null) {
            p.setNext(new Node<T>(value));
        } else {
            Node<T> next = new Node<T>(value, p.getNext());
            p.setNext(next);
        }
        this.size++;
    }

    public void remove(int index) {
        if (index < 0 || index >= this.size) {
            System.out.println("Error at remove: Index is out of index");
            return;
        }

        if (index == 0) {
            this.head = this.head.getNext();
            this.size--;
            return;
        }

        Node<T> p = this.head;
        while (p.getNext() != null && p.getNext().getNext() != null && index > 1) {
            p = p.getNext();
            index--;
        }

        p.setNext(p.getNext().getNext());
        this.size--;
    }

    public void print() {
        Node<T> p = this.head;

        while (p != null) {
            System.out.print(p.getVal() + "\t");
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
