package org.example.data_structure.tree;

import org.junit.jupiter.api.Test;

public class BinaryTreeTest {
    @Test
    void testInOrderTraversal() {
        BNode root = new BNode(1);
        root.addLeft(2);
        root.getLeft().setLeft(new BNode(0));
        root.getLeft().addLeft(4);
    }
}
