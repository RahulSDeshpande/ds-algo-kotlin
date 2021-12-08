package com.rahulografy.dsalgokotlin.datastructures.binarysearchtree

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.util.Collections

class BinarySearchTreeTest {

    @Before
    fun setup() {
    }

    @Test
    fun testIsEmpty() {
        val tree = BinarySearchTree<String>()
        Truth.assertThat(tree.isEmpty).isTrue()
        tree.add("Hello World!")
        Truth.assertThat(tree.isEmpty).isFalse()
    }

    @Test
    fun testSize() {
        val tree = BinarySearchTree<String>()
        Truth.assertThat(tree.size()).isEqualTo(0)
        tree.add("Hello World!")
        Truth.assertThat(tree.size()).isEqualTo(1)
    }

    @Test
    fun testHeight() {
        val tree = BinarySearchTree<String>()

        // Tree should look like:
        //        M
        //      J   S
        //    B   N   Z
        //  A

        // No tree
        Truth.assertThat(tree.height()).isEqualTo(0)

        // Layer One
        tree.add("M")
        Truth.assertThat(tree.height()).isEqualTo(1)

        // Layer Two
        tree.add("J")
        Truth.assertThat(tree.height()).isEqualTo(2)
        tree.add("S")
        Truth.assertThat(tree.height()).isEqualTo(2)

        // Layer Three
        tree.add("B")
        Truth.assertThat(tree.height()).isEqualTo(3)
        tree.add("N")
        Truth.assertThat(tree.height()).isEqualTo(3)
        tree.add("Z")
        Truth.assertThat(tree.height()).isEqualTo(3)

        // Layer 4
        tree.add("A")
        Truth.assertThat(tree.height()).isEqualTo(4)
    }

    @Test
    fun testAdd() {

        // Add element which does not yet exist
        val tree = BinarySearchTree<Char>()
        Truth.assertThat(tree.add('A')).isTrue()

        // Add duplicate element
        Truth.assertThat(tree.add('A')).isFalse()

        // Add a second element which is not a duplicate
        Truth.assertThat(tree.add('B')).isTrue()
    }

    @Test
    fun testRemove() {

        // Try removing an element which doesn't exist
        val tree = BinarySearchTree<Char>()
        tree.add('A')
        Truth.assertThat(tree.size()).isEqualTo(1)
        Truth.assertThat(tree.remove('B')).isFalse()
        Truth.assertThat(tree.size()).isEqualTo(1)

        // Try removing an element which does exist
        tree.add('B')
        Truth.assertThat(tree.size()).isEqualTo(2)
        Truth.assertThat(tree.remove('B')).isTrue()
        Truth.assertThat(tree.size()).isEqualTo(1)
        Truth.assertThat(tree.height()).isEqualTo(1)

        // Try removing the root
        Truth.assertThat(tree.remove('A')).isTrue()
        Truth.assertThat(tree.size()).isEqualTo(0)
        Truth.assertThat(tree.height()).isEqualTo(0)
    }

    @Test
    fun testContains() {

        // Setup tree
        val tree = BinarySearchTree<Char>()
        tree.add('B')
        tree.add('A')
        tree.add('C')

        // Try looking for an element which doesn't exist
        Truth.assertThat(tree.contains('D')).isFalse()

        // Try looking for an element which exists in the root
        Truth.assertThat(tree.contains('B')).isTrue()

        // Try looking for an element which exists as the left child of the root
        Truth.assertThat(tree.contains('A')).isTrue()

        // Try looking for an element which exists as the right child of the root
        Truth.assertThat(tree.contains('C')).isTrue()
    }

    @Test(expected = ConcurrentModificationException::class)
    fun concurrentModificationErrorPreOrder() {
        val bst = BinarySearchTree<Int>()
        bst.add(1)
        bst.add(2)
        bst.add(3)
        val iter = bst.traverse(TreeTraversalOrder.PRE_ORDER)
        while (iter!!.hasNext()) {
            bst.add(0)
            iter.next()
        }
    }

    @Test(expected = ConcurrentModificationException::class)
    fun concurrentModificationErrorInOrderOrder() {
        val bst = BinarySearchTree<Int>()
        bst.add(1)
        bst.add(2)
        bst.add(3)
        val iter = bst.traverse(TreeTraversalOrder.IN_ORDER)
        while (iter!!.hasNext()) {
            bst.add(0)
            iter.next()
        }
    }

    @Test(expected = ConcurrentModificationException::class)
    fun concurrentModificationErrorPostOrder() {
        val bst = BinarySearchTree<Int>()
        bst.add(1)
        bst.add(2)
        bst.add(3)
        val iter = bst.traverse(TreeTraversalOrder.POST_ORDER)
        while (iter!!.hasNext()) {
            bst.add(0)
            iter.next()
        }
    }

    @Test(expected = ConcurrentModificationException::class)
    fun concurrentModificationErrorLevelOrder() {
        val bst = BinarySearchTree<Int>()
        bst.add(1)
        bst.add(2)
        bst.add(3)
        val iter = bst.traverse(TreeTraversalOrder.LEVEL_ORDER)
        while (iter!!.hasNext()) {
            bst.add(0)
            iter.next()
        }
    }

    @Test(expected = ConcurrentModificationException::class)
    fun concurrentModificationErrorRemovingPreOrder() {
        val bst = BinarySearchTree<Int>()
        bst.add(1)
        bst.add(2)
        bst.add(3)
        val iter = bst.traverse(TreeTraversalOrder.PRE_ORDER)
        while (iter!!.hasNext()) {
            bst.remove(2)
            iter.next()
        }
    }

    @Test(expected = ConcurrentModificationException::class)
    fun concurrentModificationErrorRemovingInOrderOrder() {
        val bst = BinarySearchTree<Int>()
        bst.add(1)
        bst.add(2)
        bst.add(3)
        val iter = bst.traverse(TreeTraversalOrder.IN_ORDER)
        while (iter!!.hasNext()) {
            bst.remove(2)
            iter.next()
        }
    }

    @Test(expected = ConcurrentModificationException::class)
    fun concurrentModificationErrorRemovingPostOrder() {
        val bst = BinarySearchTree<Int>()
        bst.add(1)
        bst.add(2)
        bst.add(3)
        val iter = bst.traverse(TreeTraversalOrder.POST_ORDER)
        while (iter!!.hasNext()) {
            bst.remove(2)
            iter.next()
        }
    }

    @Test(expected = ConcurrentModificationException::class)
    fun concurrentModificationErrorRemovingLevelOrder() {
        val bst = BinarySearchTree<Int>()
        bst.add(1)
        bst.add(2)
        bst.add(3)
        val iter = bst.traverse(TreeTraversalOrder.LEVEL_ORDER)
        while (iter!!.hasNext()) {
            bst.remove(2)
            iter.next()
        }
    }

    @Test
    fun randomRemoveTests() {
        for (i in 0 until LOOP_COUNT) {
            val tree = BinarySearchTree<Int?>()
            val list = genRandList(i)
            for (value in list) tree.add(value)
            Collections.shuffle(list)
            // Remove all the elements we just placed in the tree
            for (j in 0 until i) {
                val value = list[j]
                Truth.assertThat(tree.remove(value)).isTrue()
                Truth.assertThat(tree.contains(value)).isFalse()
                Truth.assertThat(tree.size()).isEqualTo(i - j - 1)
            }
            Truth.assertThat(tree.isEmpty).isTrue()
        }
    }

    fun validateTreeTraversal(trav_order: TreeTraversalOrder?, input: List<Int>): Boolean {
        val out: MutableList<Int> = ArrayList()
        val expected: MutableList<Int> = ArrayList()
        var testTree: TestTreeNode? = null
        val tree = BinarySearchTree<Int>()

        // Construct Binary Tree and test tree
        for (value in input) {
            testTree = TestTreeNode.add(testTree, value)
            tree.add(value)
        }
        when (trav_order) {
            TreeTraversalOrder.PRE_ORDER -> TestTreeNode.preOrder(expected, testTree)
            TreeTraversalOrder.IN_ORDER -> TestTreeNode.inOrder(expected, testTree)
            TreeTraversalOrder.POST_ORDER -> TestTreeNode.postOrder(expected, testTree)
            TreeTraversalOrder.LEVEL_ORDER -> TestTreeNode.levelOrder(expected, testTree)
        }

        // Get traversal output
        val iter = tree.traverse(trav_order)
        while (iter!!.hasNext()) out.add(iter.next())

        // The output and the expected size better be the same size
        if (out.size != expected.size) return false

        // Compare output to expected
        for (i in out.indices) if (expected[i] != out[i]) return false
        return true
    }

    @Test
    fun testPreOrderTraversal() {
        for (i in 0 until LOOP_COUNT) {
            val input = genRandList(i)
            Truth.assertThat(validateTreeTraversal(TreeTraversalOrder.PRE_ORDER, input)).isTrue()
        }
    }

    @Test
    fun testInOrderTraversal() {
        for (i in 0 until LOOP_COUNT) {
            val input = genRandList(i)
            Truth.assertThat(validateTreeTraversal(TreeTraversalOrder.IN_ORDER, input)).isTrue()
        }
    }

    @Test
    fun testPostOrderTraversal() {
        for (i in 0 until LOOP_COUNT) {
            val input = genRandList(i)
            Truth.assertThat(validateTreeTraversal(TreeTraversalOrder.POST_ORDER, input)).isTrue()
        }
    }

    @Test
    fun testLevelOrderTraversal() {
        for (i in 0 until LOOP_COUNT) {
            val input = genRandList(i)
            Truth.assertThat(validateTreeTraversal(trav_order = TreeTraversalOrder.LEVEL_ORDER, input = input)).isTrue()
        }
    }

    companion object {
        const val LOOP_COUNT = 100
        fun genRandList(sz: Int): List<Int> {
            val list: MutableList<Int> = ArrayList(sz)
            for (i in 0 until sz) list.add(i)
            list.shuffle()
            return list
        }
    }
}
