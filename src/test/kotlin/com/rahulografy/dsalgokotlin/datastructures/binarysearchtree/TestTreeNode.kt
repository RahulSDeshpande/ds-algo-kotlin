package com.rahulografy.dsalgokotlin.datastructures.binarysearchtree

import java.util.ArrayDeque
import java.util.Deque

internal class TestTreeNode(
    var data: Int,
    var left: TestTreeNode?,
    var right: TestTreeNode?
) {
    companion object {

        fun add(node: TestTreeNode?, data: Int): TestTreeNode {
            var node = node
            if (node == null) {
                node = TestTreeNode(data, null, null)
            } else {
                // Place lower elem values on left
                if (data < node.data) {
                    node.left = add(node.left, data)
                } else {
                    node.right = add(node.right, data)
                }
            }
            return node
        }

        fun preOrder(list: MutableList<Int>, node: TestTreeNode?) {
            if (node == null) return
            list.add(node.data)
            if (node.left != null) preOrder(list, node.left)
            if (node.right != null) preOrder(list, node.right)
        }

        fun inOrder(list: MutableList<Int>, node: TestTreeNode?) {
            if (node == null) return
            if (node.left != null) inOrder(list, node.left)
            list.add(node.data)
            if (node.right != null) inOrder(list, node.right)
        }

        fun postOrder(list: MutableList<Int>, node: TestTreeNode?) {
            if (node == null) return
            if (node.left != null) postOrder(list, node.left)
            if (node.right != null) postOrder(list, node.right)
            list.add(node.data)
        }

        fun levelOrder(list: MutableList<Int>, node: TestTreeNode?) {
            var node = node
            val q: Deque<TestTreeNode?> = ArrayDeque()
            if (node != null) q.offer(node)
            while (!q.isEmpty()) {
                node = q.poll()
                list.add(node!!.data)
                if (node.left != null) q.offer(node.left)
                if (node.right != null) q.offer(node.right)
            }
        }
    }
}
