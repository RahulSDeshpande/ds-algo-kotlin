package com.rahulografy.dsalgokotlin.datastructures.priorityqueue

import java.util.Arrays

/**
 * A generic implementation of a D-ary heap inspired by the work of David Brink.
 *
 * @author David Brink, William Fiset
 */
class MinDHeap<T : Comparable<T>>(degree: Int, maxNodes: Int) {

    private val heap: Array<T?>
    private val d: Int
    private val n: Int
    private var sz = 0
    private val child: IntArray
    private val parent: IntArray

    // Returns the number of elements currently present inside the PQ
    fun size(): Int {
        return sz
    }

    // Returns true/false depending on whether the PQ is empty
    val isEmpty: Boolean
        get() = sz == 0

    // Clears all the elements inside the PQ
    fun clear() {
        Arrays.fill(heap, null)
        sz = 0
    }

    // Returns the element at the top of the PQ or null if the PQ is empty
    fun peek(): T? {
        return if (isEmpty) null else heap[0]
    }

    // Polls an element from the priority queue.
    // Make sure the queue is not empty before calling.
    fun poll(): T? {
        if (isEmpty) return null
        val root = heap[0]
        heap[0] = heap[--sz]
        heap[sz] = null
        sink(0)
        return root
    }

    // Adds a none null element to the priority queue
    fun add(elem: T?) {
        requireNotNull(elem) { "No null elements please :)" }
        heap[sz] = elem
        swim(sz)
        sz++
    }

    private fun sink(i: Int) {
        var i = i
        var j = minChild(i)
        while (j != -1) {
            swap(i, j)
            i = j
            j = minChild(i)
        }
    }

    private fun swim(i: Int) {
        var i = i
        while (less(i, parent[i])) {
            swap(i, parent[i])
            i = parent[i]
        }
    }

    // From the parent node at index i find the minimum child below it
    private fun minChild(i: Int): Int {
        var i = i
        var index = -1
        val from = child[i]
        val to = Math.min(sz, from + d)
        for (j in from until to) if (less(j, i)) {
            i = j
            index = i
        }
        return index
    }

    private fun less(i: Int, j: Int): Boolean {
        return heap[i]!! < heap[j]!!
    }

    private fun swap(i: Int, j: Int) {
        val tmp = heap[i]
        heap[i] = heap[j]
        heap[j] = tmp
    }

    // Initializes a D-ary heap with a maximum capacity of n
    init {
        d = Math.max(2, degree)
        n = Math.max(d, maxNodes)
        heap = arrayOfNulls<Comparable<*>?>(n) as Array<T?>
        child = IntArray(n)
        parent = IntArray(n)
        for (i in 0 until n) {
            parent[i] = (i - 1) / d
            child[i] = i * d + 1
        }
    }
}
