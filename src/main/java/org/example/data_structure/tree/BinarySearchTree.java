package org.example.data_structure.tree;

import lombok.Getter;

@Getter
public class BinarySearchTree {
    private BNode root;

    public BNode addNode(int value) {

        if (root == null) {
            root = new BNode(value);
            return root;
        }

        if (value > root.getValue()) {
            root.setRight(addNode(value));
            return root.getRight();
        }

        if (value <= root.getValue()) {
            root.setLeft(addNode(value));
            return root.getLeft();
        }

        return null;
    }
}
