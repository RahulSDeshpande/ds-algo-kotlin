package com.rahulografy.dsalgokotlin.datastructures.binarysearchtree

import com.rahulografy.dsalgokotlin.datastructures.utils.TreePrinter
import java.util.Scanner

/**
 * Standard Splay Tree Implementation, supports generic data(must implement Comparable)
 *
 * The Basic Concept of SplayTree is to keep frequently used nodes close to the root of the tree
 * It performs basic operations such as insertion,search,delete,findMin,findMax in O(log n)
 * amortized time Having frequently-used nodes near to the root can be useful in implementing many
 * algorithms. e.g: Implementing caches, garbage collection algorithms etc Primary disadvantage of
 * the splay tree can be the fact that its height can go linear. This causes the worst case running
 * times to go O(n) However, the amortized costs of this worst case situation is logarithmic, O(log
 * n)
 *
 * @author Ashiqur Rahman, https://github.com/ashiqursuperfly
 */
class SplayTree<T : Comparable<T>?> {

    var root: BinaryTree<T>?
        private set

    class BinaryTree<T : Comparable<T>?>(data: T?) : TreePrinter.PrintableNode {

        private var data: T? = null

        override var left: BinaryTree<T>? = null
        override var right: BinaryTree<T>? = null
        override val text: String
            get() = data.toString()

        fun getData(): T? {
            return data
        }

        fun setData(data: T?) {
            if (data == null) {
                try {
                    throw Exception("Null data not allowed into tree")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else this.data = data
        }

        override fun toString(): String {
            return TreePrinter.getTreeDisplay(this)
        }

        init {
            if (data == null) {
                try {
                    throw Exception("Null data not allowed into tree")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else this.data = data
        }
    }

    /** Public Methods *  */
    constructor() {
        this.root = null
    }

    constructor(root: BinaryTree<T>?) {
        this.root = root
    }

    /** Searches a node and splays it on top,returns the new root *  */
    fun search(node: T): BinaryTree<T>? {
        if (root == null) return null
        this.root = splayUtil(root, node)
        return if (this.root!!.getData()!!.compareTo(node) == 0) this.root else null
    }

    /** Inserts a node into the tree and splays it on top, returns the new root*  */
    fun insert(node: T): BinaryTree<T>? {
        if (root == null) {
            root = BinaryTree(node)
            return root
        }
        splay(node)
        val l_r = split(node)
        val left = l_r[0]
        val right = l_r[1]
        root = BinaryTree(node)
        root?.left = left
        root?.right = right
        return root
    }

    /** Deletes a node,returns the new root *  */
    fun delete(node: T): BinaryTree<T>? {
        if (root == null) return null
        val searchResult = splay(node)
        if (searchResult!!.getData()!!.compareTo(node) != 0) return null
        val leftSubtree = root?.left
        val rightSubtree = root?.right

        // Set the 'to be deleted' key ready for garbage collection
        root?.left = null
        root?.right = null
        root = join(leftSubtree, rightSubtree!!)
        return root
    }

    /** To FindMax Of Entire Tree *  */
    fun findMax(): T? {
        var temp = root
        while (temp?.right != null) temp = temp.right
        return temp?.getData()
    }

    /** To FindMin Of Entire Tree *  */
    fun findMin(): T? {
        var temp = root
        while (temp?.left != null) temp = temp.left
        return temp?.getData()
    }

    /** * To FindMax Of Tree with specified root *  */
    fun findMax(root: BinaryTree<T>?): T? {
        var temp = root
        while (temp?.right != null) temp = temp.right
        return temp?.getData()
    }

    /** * To FindMin Of Tree with specified root *  */
    fun findMin(root: BinaryTree<T>?): T? {
        var temp = root
        while (temp?.left != null) temp = temp.left
        return temp?.getData()
    }

    override fun toString(): String {
        println("Elements:" + inorder(root, ArrayList()))
        return if (root != null) root.toString() else ""
    }

    /** Private Methods *  */
    private fun rightRotate(node: BinaryTree<T>?): BinaryTree<T>? {
        val tree = node?.left
        node?.left = tree?.right
        tree?.right = node
        return tree
    }

    private fun leftRotate(node: BinaryTree<T>?): BinaryTree<T>? {
        val tree = node?.right
        node?.right = tree?.left
        tree?.left = node
        return tree
    }

    private fun splayUtil(root: BinaryTree<T>?, key: T): BinaryTree<T>? {
        var root = root
        if (root == null || root.getData() === key) return root
        return if (root.getData()!! > key) {
            if (root.left == null) return root
            if (root.left?.getData()!! > key) {
                root.left?.left = splayUtil(root.left?.left, key)
                root = rightRotate(root)
            } else if (root.left?.getData()!! < key) {
                root.left?.right = splayUtil(root = root.left?.right, key = key)
                if (root.left?.right != null) root.left = leftRotate(root.left)
            }
            if (root!!.left == null) root else rightRotate(root)
        } else {
            if (root.right == null) return root
            if (root.right?.getData()!! > key) {
                root.right?.left = splayUtil(root.right?.left, key)
                if (root.right?.left != null) root.right = rightRotate(root.right)
            } else if (root.right?.getData()!!.compareTo(key) < 0) // Zag-Zag (Right Right)
                {
                    root.right?.right = splayUtil(root.right?.right, key)
                    root = leftRotate(root)
                }
            if (root!!.right == null) root else leftRotate(root)
        }
    }

    private fun splay(node: T): BinaryTree<T>? {
        if (root == null) return null
        this.root = splayUtil(root, node)
        return this.root
    }

    private fun split(node: T): ArrayList<BinaryTree<T>?> {
        val right: BinaryTree<T>?
        val left: BinaryTree<T>?
        if (node!! > root?.getData()!!) {
            right = root?.right
            left = root
            left!!.right = null
        } else {
            left = root?.left
            right = root
            right!!.left = null
        }
        val l_r = ArrayList<BinaryTree<T>?>()
        l_r.add(left)
        l_r.add(right)
        return l_r
    }

    private fun join(L: BinaryTree<T>?, R: BinaryTree<T>): BinaryTree<T>? {
        if (L == null) {
            root = R
            return R
        }
        root = splayUtil(root = L, key = findMax(L)!!)
        root?.right = R
        return root
    }

    private fun inorder(root: BinaryTree<T>?, sorted: ArrayList<T>): ArrayList<T> {
        if (root == null) {
            return sorted
        }
        inorder(root.left, sorted)
        sorted.add(root.getData()!!)
        inorder(root.right, sorted)
        return sorted
    }
}

internal object SplayTreeRun {

    @JvmStatic
    fun main(args: Array<String>) {
        val splayTree = SplayTree<Int>()
        val scanner = Scanner(System.`in`)
        val data = intArrayOf(2, 29, 26, -1, 10, 0, 2, 11)
        var count = 0

        for (i in data) {
            splayTree.insert(i)
        }

        while (count != 7) {
            println("1. Insert\n2. Delete\n3. Search\n4. FindMin\n5. FindMax\n6. PrintTree\n7. Exit")

            count = scanner.nextInt()

            when (count) {
                1 -> {
                    println("Enter Data :")
                    splayTree.insert(scanner.nextInt())
                }
                2 -> {
                    println("Enter Element to be Deleted:")
                    splayTree.delete(scanner.nextInt())
                }
                3 -> {
                    println("Enter Element to be Searched and Splayed:")
                    splayTree.search(scanner.nextInt())
                }
                4 -> println("Min: " + splayTree.findMin())
                5 -> println("Max: " + splayTree.findMax())
                6 -> println(splayTree)
                7 -> scanner.close()
            }
        }
    }
}
