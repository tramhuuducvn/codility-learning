package org.example.data_structure.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinarySearchTreeTest {
    BinarySearchTree binarySearchTree = new BinarySearchTree();

    @BeforeEach
    void setup() {
        binarySearchTree.addNode(4);
        binarySearchTree.addNode(3);
        binarySearchTree.addNode(7);
        binarySearchTree.addNode(1);
    }

    // @Test
    // void testAddNode() {
    // BinaryTree.preorderTraversal(binarySearchTree.getRoot());
    // }
}
