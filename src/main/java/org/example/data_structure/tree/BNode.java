package org.example.data_structure.tree;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BNode {
    private int value;
    private BNode left;
    private BNode right;

    public BNode(int value) {
        this.value = value;
    }

    public void addLeft(int value) {
        this.left = new BNode(value);
    }

    public void addRight(int value) {
        this.right = new BNode(value);
    }
}
