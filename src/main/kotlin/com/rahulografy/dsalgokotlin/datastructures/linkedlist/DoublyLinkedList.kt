/**
 * A doubly linked list implementation.
 *
 * @author Rahul S Deshpande
 */
package com.rahulografy.dsalgokotlin.datastructures.linkedlist

class DoublyLinkedList<T> : Iterable<T> {

    private var size = 0
    private var head: Node<T>? = null
    private var tail: Node<T>? = null

    /**
     * Internal node class to represent data
     */
    private class Node<T>(
        var data: T?,
        var prev: Node<T>?,
        var next: Node<T>?
    ) {
        override fun toString() = data.toString()
    }

    /**
     * Empty this linked list: O(n)
     */
    fun clear() {
        var trav = head
        while (trav != null) {
            val next = trav.next
            trav.next = null
            trav.prev = trav.next
            trav.data = null
            trav = next
        }
        trav = null
        tail = trav
        head = tail
        size = 0
    }

    /**
     * Return the size of this linked list
     */
    fun size(): Int {
        return size
    }

    /**
     * Check whether this linked list empty
     */
    val isEmpty: Boolean
        get() = size() == 0

    /**
     * Add an element to the tail of the linked list, O(1)
     */
    fun add(elem: T) {
        addLast(elem)
    }

    /**
     * Add a node to the tail of the linked list, O(1)
     */
    fun addLast(elem: T) {
        if (isEmpty) {
            tail = Node(elem, null, null)
            head = tail
        } else {
            tail!!.next = Node(elem, tail, null)
            tail = tail!!.next
        }
        size++
    }

    /**
     * Add an element to the beginning of this linked list, O(1)
     */
    fun addFirst(elem: T) {
        if (isEmpty) {
            tail = Node(elem, null, null)
            head = tail
        } else {
            head!!.prev = Node(elem, null, head)
            head = head!!.prev
        }
        size++
    }

    /**
     * Add an element at a specified index
     */
    @Throws(Exception::class)
    fun addAt(index: Int, data: T) {
        if (index < 0) {
            throw Exception("Illegal Index")
        }
        if (index == 0) {
            addFirst(data)
            return
        }
        if (index == size) {
            addLast(data)
            return
        }
        var temp = head
        for (i in 0 until index - 1) {
            temp = temp!!.next
        }
        val newNode = Node(data, temp, temp!!.next)
        temp.next!!.prev = newNode
        temp.next = newNode
        size++
    }

    /**
     * Check the value of the first node if it exists, O(1)
     */
    fun peekFirst(): T? {
        if (isEmpty) throw RuntimeException("Empty list")
        return head?.data
    }

    /**
     * Check the value of the last node if it exists, O(1)
     */
    fun peekLast(): T? {
        if (isEmpty) throw RuntimeException("Empty list")
        return tail?.data
    }

    /**
     * Remove the first value at the head of the linked list, O(1)
     */
    fun removeFirst(): T? {
        // Can't remove data from an empty list
        if (isEmpty) throw RuntimeException("Empty list")

        // Extract the data at the head and move
        // the head pointer forwards one node
        val data = head?.data
        head = head?.next
        --size

        // If the list is empty set the tail to null
        if (isEmpty) tail = null else head!!.prev = null

        // Return the data that was at the first node we just removed
        return data
    }

    /**
     * Remove the last value at the tail of the linked list, O(1)
     */
    fun removeLast(): T? {
        // Can't remove data from an empty list
        if (isEmpty) throw RuntimeException("Empty list")

        // Extract the data at the tail and move
        // the tail pointer backwards one node
        val data = tail!!.data
        tail = tail!!.prev
        --size

        // If the list is now empty set the head to null
        if (isEmpty) head = null else tail!!.next = null

        // Return the data that was in the last node we just removed
        return data
    }

    /**
     * Remove an arbitrary node from the linked list, O(1)
     */
    private fun remove(node: Node<T>?): T? {
        // If the node to remove is somewhere either at the
        // head or the tail handle those independently
        var node = node
        if (node?.prev == null) return removeFirst()
        if (node.next == null) return removeLast()

        // Make the pointers of adjacent nodes skip over 'node'
        node.next?.prev = node.prev
        node.prev?.next = node.next

        // Temporarily store the data we want to return
        val data = node.data

        // Memory cleanup
        node.data = null
        node.next = null
        node.prev = node.next
        node = node.prev
        --size

        // Return the data in the node we just removed
        return data
    }

    /**
     * Remove a node at a particular index, O(n)
     */
    fun removeAt(index: Int): T? {
        // Make sure the index provided is valid
        require(!(index < 0 || index >= size))
        var i: Int
        var trav: Node<T>?

        // Search from the front of the list
        if (index < size / 2) {
            i = 0
            trav = head
            while (i != index) {
                trav = trav!!.next
                i++
            }
            // Search from the back of the list
        } else {
            i = size - 1
            trav = tail
            while (i != index) {
                trav = trav!!.prev
                i--
            }
        }
        return remove(trav)
    }

    /**
     * Remove a particular value in the linked list, O(n)
     */
    fun remove(obj: Any?): Boolean {
        var trav = head

        // Support searching for null
        if (obj == null) {
            trav = head
            while (trav != null) {
                if (trav.data == null) {
                    remove(trav)
                    return true
                }
                trav = trav.next
            }
            // Search for non null object
        } else {
            trav = head
            while (trav != null) {
                if (obj == trav.data) {
                    remove(trav)
                    return true
                }
                trav = trav.next
            }
        }
        return false
    }

    /**
     * Find the index of a particular value in the linked list, O(n)
     */
    fun indexOf(obj: Any?): Int {
        var index = 0
        var trav = head

        // Support searching for null
        if (obj == null) {
            while (trav != null) {
                if (trav.data == null) {
                    return index
                }
                trav = trav.next
                index++
            }
            // Search for non null object
        } else while (trav != null) {
            if (obj == trav.data) {
                return index
            }
            trav = trav.next
            index++
        }
        return -1
    }

    /**
     * Check is a value is contained within the linked list
     */
    operator fun contains(obj: Any?): Boolean {
        return indexOf(obj) != -1
    }

    override fun iterator(): MutableIterator<T> {
        return object : MutableIterator<T> {
            private var trav = head
            override fun hasNext(): Boolean {
                return trav != null
            }

            override fun next(): T {
                val data = trav?.data
                trav = trav?.next
                return data ?: null as T
            }

            override fun remove() {
                throw UnsupportedOperationException()
            }
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("[ ")
        var trav = head
        while (trav != null) {
            sb.append(trav.data)
            if (trav.next != null) {
                sb.append(", ")
            }
            trav = trav.next
        }
        sb.append(" ]")
        return sb.toString()
    }
}
