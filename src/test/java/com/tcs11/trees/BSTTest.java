package com.tcs11.trees;

public class BSTTest {
    public static void main(String[] args) {
        BST<Integer> tree = new BST<>();
        tree.data();
        tree.insert(9);
        tree.insert(10);
        tree.insert(8);
        tree.insert(1);
        tree.insert(11);
        tree.insert(2);
        tree.insert(3);
        tree.insert(13);
        tree.insert(14);
        tree.insert(17);
        tree.insert(18);
        tree.insert(16);
        tree.insert(12);
        tree.data();
        tree.delete(16);
        tree.data();
        tree.insert(11);
        tree.delete(13);
        tree.data();
    }
}
