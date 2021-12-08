package com.rahulografy.dsalgokotlin.datastructures.binarysearchtree

import java.util.LinkedList
import java.util.Queue
import java.util.Stack

/**
 * This file contains an implementation of a Binary Search Tree (BST).
 *
 * Any comparable data is allowed within this tree (numbers, strings, comparable Objects, etc...).
 *
 * Supported operations include adding, removing, height, and containment checks.
 *
 * Furthermore, multiple tree traversal Iterators are provided including:
 * 1) Preorder traversal
 * 2) Inorder traversal
 * 3) Postorder traversal
 * 4) Level order traversal
 *
 * @author Rahul S Deshpande
 */
class BinarySearchTree<T : Comparable<T>?> {

    // Tracks the number of nodes in this BST
    private var nodeCount = 0

    // This BST is a rooted tree so we maintain a handle on the root node
    private var root: Node? = null

    // Internal node containing node references and the actual node data
    private inner class Node(
        var left: Node?,
        var right: Node?,
        var data: T
    )

    // Check if this binary tree is empty
    val isEmpty: Boolean
        get() = size() == 0

    // Get the number of nodes in this binary tree
    fun size(): Int {
        return nodeCount
    }

    // Add an element to this binary tree. Returns true
    // if we successfully perform an insertion
    fun add(elem: T): Boolean {

        // Check if the value already exists in this
        // binary tree, if it does ignore adding it
        return if (contains(elem)) {
            false

            // Otherwise add this element to the binary tree
        } else {
            root = add(root, elem)
            nodeCount++
            true
        }
    }

    // Private method to recursively add a value in the binary tree
    private fun add(node: Node?, elem: T): Node {

        // Base case: found a leaf node
        var node = node
        if (node == null) {
            node = Node(left = null, right = null, data = elem)
        } else {
            // Pick a subtree to insert element
            if (elem!! < node.data) {
                node.left = add(node.left, elem)
            } else {
                node.right = add(node.right, elem)
            }
        }
        return node
    }

    // Remove a value from this binary tree if it exists, O(n)
    fun remove(elem: T): Boolean {

        // Make sure the node we want to remove
        // actually exists before we remove it
        if (contains(elem)) {
            root = remove(root, elem)
            nodeCount--
            return true
        }
        return false
    }

    private fun remove(node: Node?, elem: T): Node? {
        if (node == null) return null
        val cmp = elem!!.compareTo(node.data)

        // Dig into left subtree, the value we're looking
        // for is smaller than the current value
        if (cmp < 0) {
            node.left = remove(node.left, elem)

            // Dig into right subtree, the value we're looking
            // for is greater than the current value
        } else if (cmp > 0) {
            node.right = remove(node.right, elem)

            // Found the node we wish to remove
        } else {

            // This is the case with only a right subtree or
            // no subtree at all. In this situation just
            // swap the node we wish to remove with its right child.
            if (node.left == null) {
                return node.right

                // This is the case with only a left subtree or
                // no subtree at all. In this situation just
                // swap the node we wish to remove with its left child.
            } else if (node.right == null) {
                return node.left

                // When removing a node from a binary tree with two links the
                // successor of the node being removed can either be the largest
                // value in the left subtree or the smallest value in the right
                // subtree. In this implementation I have decided to find the
                // smallest value in the right subtree which can be found by
                // traversing as far left as possible in the right subtree.
            } else {

                // Find the leftmost node in the right subtree
                val tempNode = findMin(node.right)

                // Swap the data
                node.data = tempNode!!.data

                // Go into the right subtree and remove the leftmost node we
                // found and swapped data with. This prevents us from having
                // two nodes in our tree with the same value.
                node.right = remove(node.right, tempNode.data)

                // If instead we wanted to find the largest node in the left
                // subtree as opposed to smallest node in the right subtree
                // here is what we would do:
                // Node tmp = findMax(node.left);
                // node.data = tmp.data;
                // node.left = remove(node.left, tmp.data);
            }
        }
        return node
    }

    // Helper method to find the leftmost node (which has the smallest value)
    private fun findMin(node: Node?): Node? {
        var node = node
        while (node?.left != null) node = node.left
        return node
    }

    // Helper method to find the rightmost node (which has the largest value)
    private fun findMax(node: Node?): Node? {
        var node = node
        while (node?.right != null) node = node.right
        return node
    }

    // returns true is the element exists in the tree
    operator fun contains(elem: T): Boolean {
        return contains(root, elem)
    }

    // private recursive method to find an element in the tree
    private fun contains(node: Node?, elem: T): Boolean {

        // Base case: reached bottom, value not found
        if (node == null) return false
        val cmp = elem!!.compareTo(node.data)

        // Dig into the left subtree because the value we're
        // looking for is smaller than the current value
        return if (cmp < 0) contains(node.left, elem) else if (cmp > 0) contains(node.right, elem) else true
    }

    // Computes the height of the tree, O(n)
    fun height(): Int {
        return height(root)
    }

    // Recursive helper method to compute the height of the tree
    private fun height(node: Node?): Int {
        return if (node == null) 0 else Math.max(height(node.left), height(node.right)) + 1
    }

    // This method returns an iterator for a given TreeTraversalOrder.
    // The ways in which you can traverse the tree are in four different ways:
    // preorder, inorder, postorder and levelorder.
    fun traverse(order: TreeTraversalOrder?): Iterator<T>? {
        return when (order) {
            TreeTraversalOrder.PRE_ORDER -> preOrderTraversal()
            TreeTraversalOrder.IN_ORDER -> inOrderTraversal()
            TreeTraversalOrder.POST_ORDER -> postOrderTraversal()
            TreeTraversalOrder.LEVEL_ORDER -> levelOrderTraversal()
            else -> null
        }
    }

    // Returns as iterator to traverse the tree in pre order
    private fun preOrderTraversal(): Iterator<T> {

        val expectedNodeCount = nodeCount
        val stack: Stack<Node?> = Stack<Node?>()

        stack.push(root)

        return object : MutableIterator<T> {
            override fun hasNext(): Boolean {
                if (expectedNodeCount != nodeCount) throw ConcurrentModificationException()
                return root != null && !stack.isEmpty()
            }

            override fun next(): T {
                if (expectedNodeCount != nodeCount) throw ConcurrentModificationException()
                val node = stack.pop()
                if (node?.right != null) stack.push(node.right)
                if (node?.left != null) stack.push(node.left)
                return node!!.data
            }

            override fun remove() {
                throw UnsupportedOperationException()
            }
        }
    }

    // Returns as iterator to traverse the tree in order
    private fun inOrderTraversal(): Iterator<T> {

        val expectedNodeCount = nodeCount
        val stack: Stack<Node?> = Stack<Node?>()

        stack.push(root)

        return object : MutableIterator<T> {
            var trav = root
            override fun hasNext(): Boolean {
                if (expectedNodeCount != nodeCount) throw ConcurrentModificationException()
                return root != null && !stack.isEmpty()
            }

            override fun next(): T {
                if (expectedNodeCount != nodeCount) throw ConcurrentModificationException()

                // Dig left
                while (trav != null && trav?.left != null) {
                    stack.push(trav?.left)
                    trav = trav?.left
                }
                val node = stack.pop()

                // Try moving down right once
                if (node?.right != null) {
                    stack.push(node.right)
                    trav = node.right
                }
                return node!!.data
            }

            override fun remove() {
                throw UnsupportedOperationException()
            }
        }
    }

    // Returns as iterator to traverse the tree in post order
    private fun postOrderTraversal(): Iterator<T> {

        val expectedNodeCount = nodeCount
        val stack1: Stack<Node?> = Stack<Node?>()
        val stack2: Stack<Node> = Stack<Node>()

        stack1.push(root)

        while (!stack1.isEmpty()) {
            val node: Node? = stack1.pop()
            if (node != null) {
                stack2.push(node)
                if (node.left != null) stack1.push(node.left)
                if (node.right != null) stack1.push(node.right)
            }
        }

        return object : MutableIterator<T> {

            override fun hasNext(): Boolean {
                if (expectedNodeCount != nodeCount) throw ConcurrentModificationException()
                return root != null && !stack2.isEmpty()
            }

            override fun next(): T {
                if (expectedNodeCount != nodeCount) throw ConcurrentModificationException()
                return stack2.pop().data
            }

            override fun remove() {
                throw UnsupportedOperationException()
            }
        }
    }

    // Returns as iterator to traverse the tree in level order
    private fun levelOrderTraversal(): Iterator<T> {

        val expectedNodeCount = nodeCount
        val queue: Queue<Node?> = LinkedList<Node?>()

        queue.offer(root)

        return object : MutableIterator<T> {

            override fun hasNext(): Boolean {
                if (expectedNodeCount != nodeCount) throw ConcurrentModificationException()
                return root != null && !queue.isEmpty()
            }

            override fun next(): T {
                if (expectedNodeCount != nodeCount) throw ConcurrentModificationException()
                val node = queue.poll()
                if (node?.left != null) queue.offer(node.left)
                if (node?.right != null) queue.offer(node.right)
                return node!!.data
            }

            override fun remove() {
                throw UnsupportedOperationException()
            }
        }
    }
}
