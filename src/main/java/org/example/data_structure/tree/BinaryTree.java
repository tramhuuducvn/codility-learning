package org.example.data_structure.tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

import org.example.util.Logger;

public class BinaryTree {

    public static void inorderTraversal(BNode node) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.getLeft());
        Logger.print(node.getValue());
        inorderTraversal(node.getRight());
    }

    public static void preorderTraversal(BNode node) {
        if (node == null) {
            return;
        }

        Logger.print(node.getValue());
        preorderTraversal(node.getLeft());
        preorderTraversal(node.getRight());
    }

    public static void postorderTraversal(BNode node) {
        if (node == null) {
            return;
        }

        postorderTraversal(node.getLeft());
        postorderTraversal(node.getRight());
        Logger.print(node.getValue());
    }

    public static void levelOrderTraversal(BNode node) {
        Deque<BNode> queue = new ArrayDeque<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            int size = queue.size();
            // Print all elements of level N th for each loop
            for (int i = 0; i < size; ++i) {
                BNode temp = queue.pop();
                // Print value
                Logger.print(temp.getValue());
                // Add to queue
                Optional.ofNullable(temp.getLeft()).ifPresent(t -> queue.add(t));
                Optional.ofNullable(temp.getRight()).ifPresent(t -> queue.add(t));
            }
            System.out.println();
        }
    }

    public static int height(BNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    public static int countNode(BNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + countNode(node.getLeft()) + countNode(node.getRight());
    }

    public static BNode findNode(BNode node, int value) {
        Deque<BNode> queue = new ArrayDeque<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            BNode temp = queue.pop();
            if (temp.getValue() == value) {
                return temp;
            }

            if (temp.getLeft() != null) {
                queue.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                queue.add(temp.getRight());
            }
        }

        return null;
    }

    // public static

    public static void main(String[] args) {

    }

}
