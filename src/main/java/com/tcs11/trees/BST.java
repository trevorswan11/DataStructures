package com.tcs11.trees;

import com.tcs11.queue.*;

/**
 * Binary Search Tree implementation
 * 
 * @author Trevor Swan
 * @version 1.0
 */
public class BST<T> {
    private Node<T> root;

    /** Creates a new Binary Search Tree with no nodes */
    public BST() {
        this.root = null;                               // Tree should start as being empty and need to build up
    }

    /**
     * Creates a new Binary Search Tree from an array of keys
     * 
     * @param <T> The type of the elements; No restriction
     * @param keys The keys to be inserted
     * @apiNote corresponding elements will be set to null
     * @return The created Binary Search Tree
     */
    @SuppressWarnings("unchecked")
    public static <T> BST<T> treeify(int[] keys) {
        T[] elements = (T[]) (new Object[keys.length]); // We might want to make a null tree for sorting reasons
        for (int i = 0; i < keys.length; i++) elements[i] = null;
        return treeify(keys, elements);
    }

    /**
     * Creates a new Binary Search Tree from an array of keys and elements
     * 
     * @param <T> The type of the elements; No restriction
     * @param keys The keys to be inserted
     * @param elements Th elements to be inserted with the corresponding keys 
     * @throws ArrayStoreException if the number of keys does not agree with the number of elements
     * @return
     */
    public static <T> BST<T> treeify(int[] keys, T[] elements) {
        if (keys.length != elements.length)
            throw new ArrayStoreException("Number of keys must agree with number of elements.");
        BST<T> resultTree = new BST<>();
        for (int i = 0; i < keys.length; i++)
            resultTree.insert(keys[i], elements[i]);
        return resultTree;
    }

    /**
     * Returns the number of nodes in the tree
     * 
     * @return The total number of nodes
     */
    public int size() {
        return root == null ? 0 : treeSize(this.root);
    }

    private int treeSize(Node<T> root) {
        if (root == null)
            return 0;

        return 1 + treeSize(root.left) + treeSize(root.right);
    }

    /**
     * Returns the height of the tree
     * 
     * @return The max height of the tree
     */
    public int height() {
        return nodeHeight(this.root);
    }

    private int nodeHeight(Node<T> node) {
        if (node == null)                               // Node's height is -1 if it is null
            return -1;

        return 1 + Math.max(nodeHeight(node.left), nodeHeight(node.right));
    }

    /**
     * Searches the tree for the given key
     * 
     * @param key The key to look for
     * @return The Node containing the key or null if not found
     */
    public Node<T> search(int key) {                    // Recursive implementation as search is done iteratively for the rest of this file
        Node<T> n = searchTree(root, key);
        return n == null ? null : n;
    }

    private Node<T> searchTree(Node<T> root, int key) {
        if (root == null)
            return null;                                // Base case 1: key is not found
        else if (key == root.key)
            return root;                                // Base case 2: key is found at current subtree root
        else if (key < root.key)
            return searchTree(root.left, key);          // Recursive call to appropriate subtree 
        else
            return searchTree(root.right, key);
    }

    /**
     * Inserts a new key in the tree according to BST rules
     * 
     * @param key The key to insert
     * @apiNote Corresponding element will be set to null
     */
    public void insert(int key) {
        insert(key, null);                      // Insertion for testing, key is only important for now
    }

    /**
     * Inserts a new key in the tree according to BST rules
     * 
     * @param key The key to insert
     * @param element The element to insert with the key
     */
    public void insert(int key, T element) {
        Node<T> trav = root;
        Node<T> parent = null;
        while (trav != null) {                          // Find the node according to BST rules
            parent = trav;
            if (key < trav.key)
                trav = trav.left;
            else
                trav = trav.right;
        }
        if (parent == null)                             // Assumes tree starts as null
            this.root = new Node<T>(key, element);
        else if (key < parent.key)
            parent.left = new Node<T>(key, element); 
        else
            parent.right = new Node<T>(key, element);
    }

    /**
     * Removes a Node from the tree
     * 
     * @param key The key of the node to remove
     * @return The removed Node or null if not found
     */
    public Node<T> delete(int key) {
        Node<T> trav = root;
        Node<T> parent = null;
        while (trav != null && trav.key != key) {       // Find the node with the key to delete
            parent = trav;
            if (key < trav.key)
                trav = trav.left;
            else
                trav = trav.right;
        }
        Node<T> deletedNode = trav;                     // Store the element and delete its Node
        deleteNode(trav, parent);
        return deletedNode;
    }

    private void deleteNode(Node<T> toDelete, Node<T> parent) {
        if (toDelete.left == null || toDelete.right == null) {
            Node<T> toDeleteChild = null;                   // toDelete has zero or no children
            if (toDelete.left != null)
                toDeleteChild = toDelete.left;              // check if the left child exists
            else
                toDeleteChild = toDelete.right;             // right child either exists or is null

            if (toDelete == this.root)
                this.root = toDeleteChild;                  // make delete's child the new root
            else if (toDelete.key < parent.key)
                parent.left = toDeleteChild;                // toDeleteChild must be less than parent due to BST rules
            else
                parent.right = toDeleteChild;
        } else {                                            
            Node<T> replacementParent = toDelete;           // toDelete must then have 2 children
            Node<T> replacementNode = toDelete.right;
            while (replacementNode.left != null) {          // Find toDelete's inorder successor     
                replacementParent = replacementNode;
                replacementNode = replacementNode.left;
            }
            toDelete.key = replacementNode.key;             // Copy the data of the replacement node to the one to be deleted
            toDelete.element = replacementNode.element;
            deleteNode(replacementNode, replacementParent); // Delete the copied node - will have either one or zero children
        }
    }

    /** Prints the BST in preorder (key, left, right) */
    public void preorder() {
        System.out.print("BST.preorder(): ");
        myPreOrder(root);
        System.out.println();
    }

    private void myPreOrder(Node<T> root) {
        if (root == null)
            return;
        System.out.print(root.key);                         // "pre" order = print statement FIRST (pre-recursion)
        System.out.print(" ");
        myPreOrder(root.left);
        myPreOrder(root.right);
    }
    
    /** Prints the BST in postorder (left, right, key) */
    public void postorder() {
        System.out.print("BST.postorder(): ");
        myPostOrder(root);
        System.out.println();
    }

    private void myPostOrder(Node<T> root) {
        if (root == null)
            return;
        myPostOrder(root.left);
        myPostOrder(root.right);
        System.out.print(root.key);                         // "post" order = print statement AFTER (post-recursion)
        System.out.print(" ");
        
    }
    
    /** Prints the BST in inorder (left, key, right) */
    public void inorder() {
        System.out.print("BST.inorder(): ");
        myInOrder(root);
        System.out.println();
    }

    private void myInOrder(Node<T> root) {
        if (root == null)
            return;
        myInOrder(root.left);
        System.out.print(root.key);                         // "In" order = print statement INSIDE (in between recursive calls)
        System.out.print(" ");
        myInOrder(root.right);
    }

    /** Prints the BST in level order */
    public void levelorder() {
        System.out.print("BST.levelorder(): ");
        if (this.root == null) {                            // No queue operations can be done if tree is empty
            System.out.println();
            return;
        }
        CircularQueue<Node<T>> queue = new CircularQueue<>(this.size());
        queue.enqueue(this.root);                                
        while (!queue.isEmpty()) {
            Node<T> node = queue.dequeue();                 // Pull the next item
            if (node.left != null)
                queue.enqueue(node.left);                   // Enqueue the nodes from left to right
            if (node.right != null)
                queue.enqueue(node.right);
            System.out.print(node.key);                     // Print the item that was pulled
            System.out.print(" ");
        }
        System.out.println();
    }

    /**
     * Node of a Binary Search Tree
     * 
     * @author Trevor Swan
     * @version 1.0
     */
    private class Node<E> {
        /** Key of the node */
        private int key;

        /** Element stored in the node */
        private E element;

        /** Child of the node */
        private Node<E> left, right;

        /**
         * Creates a new Node
         * 
         * @param key The nodes key
         * @param element The nodes element
         * @apiNote Children always start as null
         */
        public Node(int key, E element) {
            this.element = element;
            this.key = key;
            this.left = null;                               // Children should always start as null
            this.left = null;
        }
    }

    /** Prints the BST traversals and height */
    public void data() {
        this.preorder();
        this.postorder();
        this.inorder();
        this.levelorder();
        System.out.print("BST.height(): ");
        System.out.println(this.height());
    }
}
