package org.example.data_structure.tree;

import org.example.util.Logger;

public class BinaryTree {
    private BNode root;

    public BinaryTree(int value) {
        root = new BNode(value);
    }

    public void inorderTraversal(BNode node) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.getLeft());
        Logger.log(node.getValue());
        inorderTraversal(node.getRight());
    }

    public void preorderTraversal(BNode node) {
        if (node == null) {
            return;
        }

        Logger.log(node.getValue());
        preorderTraversal(node);
        preorderTraversal(node);
    }

    public void postorderTraversal(BNode node) {
        if (node == null) {
            return;
        }

        postorderTraversal(node);
        postorderTraversal(node);
        Logger.log(node.getValue());
    }

}
