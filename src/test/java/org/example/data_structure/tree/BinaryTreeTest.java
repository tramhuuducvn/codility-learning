package org.example.data_structure.tree;

import org.example.util.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryTreeTest {
    BNode root;

    @BeforeEach
    void setup() {
        root = new BNode(1);

        root.addLeft(2);
        root.getLeft().addLeft(4);
        root.getLeft().addRight(5);
        root.getLeft().getRight().addLeft(9);
        root.getLeft().getRight().getLeft().addRight(10);

        root.addRight(3);
        root.getRight().addLeft(6);
        root.getRight().addRight(7);
        root.getRight().getRight().addRight(8);
    }

    @Test
    void testInOrderTraversal() {
        Logger.log("Inorder Traversal");
        BinaryTree.inorderTraversal(root);
    }

    @Test
    void testPreOrderTraversal() {
        Logger.log("Preorder Traversal");
        BinaryTree.preorderTraversal(root);
    }

    @Test
    void testPostOrderTraversal() {
        Logger.log("Postorder Traversal");
        BinaryTree.postorderTraversal(root);
    }

    @Test
    void testLevelOrderTraversal() {
        Logger.log("Level Order Traversal");
        BinaryTree.levelOrderTraversal(root);
    }

    @Test
    void testLevelHeight() {
        Logger.log("Height: ");
        Logger.print(BinaryTree.height(root));
    }

    @Test
    void testCountNode() {
        Logger.log("Total nodes: ");
        Logger.print(BinaryTree.countNode(root));
    }

    @Test
    void testFindNode() {
        Logger.log("Find node 7: ");
        Logger.print(BinaryTree.findNode(root, 7).getValue());
    }
}
