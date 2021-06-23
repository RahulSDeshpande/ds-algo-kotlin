package com.rahulografy.dsalgokotlin.datastructures.priorityqueue

/**
 * A min priority queue implementation using a binary heap.
 *
 * @author Rahul S Deshpande
 */
class BinaryHeap<T : Comparable<T>?> {

    // A dynamic list to track the elements inside the heap
    private var heap: MutableList<T>? = null

    // Construct a priority queue with an initial capacity
    // Construct and initially empty priority queue
    @JvmOverloads
    constructor(sz: Int = 1) {
        heap = ArrayList(sz)
    }

    // Construct a priority queue using heapify in O(n) time, a great explanation can be found at:
    // http://www.cs.umd.edu/~meesh/351/mount/lectures/lect14-heapsort-analysis-part.pdf
    constructor(elems: Array<T>) {
        val heapSize = elems.size
        heap = ArrayList(heapSize)

        // Place all element in heap
        for (i in 0 until heapSize) heap?.add(elems[i])

        // Heapify process, O(n)
        for (i in Math.max(0, heapSize / 2 - 1) downTo 0) sink(i)
    }

    // Priority queue construction, O(n)
    constructor(elems: Collection<T>) {
        val heapSize = elems.size
        heap = ArrayList(heapSize)

        // Add all elements of the given collection to the heap
        heap?.addAll(elems)

        // Heapify process, O(n)
        for (i in Math.max(0, heapSize / 2 - 1) downTo 0) sink(i)
    }

    // Returns true/false depending on if the priority queue is empty
    val isEmpty: Boolean
        get() = size() == 0

    // Clears everything inside the heap, O(n)
    fun clear() {
        heap!!.clear()
    }

    // Return the size of the heap
    fun size(): Int {
        return heap!!.size
    }

    // Returns the value of the element with the lowest
    // priority in this priority queue. If the priority
    // queue is empty null is returned.
    fun peek(): T? {
        return if (isEmpty) null else heap!![0]
    }

    // Removes the root of the heap, O(log(n))
    fun poll(): T? {
        return removeAt(0)
    }

    // Test if an element is in heap, O(n)
    operator fun contains(elem: T): Boolean {
        // Linear scan to check containment
        for (i in 0 until size()) if (heap!![i] == elem) return true
        return false
    }

    // Adds an element to the priority queue, the
    // element must not be null, O(log(n))
    fun add(elem: T?) {
        requireNotNull(elem)
        heap!!.add(elem)
        val indexOfLastElem = size() - 1
        swim(indexOfLastElem)
    }

    // Tests if the value of node i <= node j
    // This method assumes i & j are valid indices, O(1)
    private fun less(i: Int, j: Int): Boolean {
        val node1 = heap!![i]
        val node2 = heap!![j]
        return node1!!.compareTo(node2) <= 0
    }

    // Perform bottom up node swim, O(log(n))
    private fun swim(k: Int) {

        // Grab the index of the next parent node WRT to k
        var k = k
        var parent = (k - 1) / 2

        // Keep swimming while we have not reached the
        // root and while we're less than our parent.
        while (k > 0 && less(k, parent)) {
            // Exchange k with the parent
            swap(parent, k)
            k = parent

            // Grab the index of the next parent node WRT to k
            parent = (k - 1) / 2
        }
    }

    // Top down node sink, O(log(n))
    private fun sink(k: Int) {
        var k = k
        val heapSize = size()
        while (true) {
            val left = 2 * k + 1 // Left  node
            val right = 2 * k + 2 // Right node
            var smallest = left // Assume left is the smallest node of the two children

            // Find which is smaller left or right
            // If right is smaller set smallest to be right
            if (right < heapSize && less(right, left)) smallest = right

            // Stop if we're outside the bounds of the tree
            // or stop early if we cannot sink k anymore
            if (left >= heapSize || less(k, smallest)) break

            // Move down the tree following the smallest node
            swap(smallest, k)
            k = smallest
        }
    }

    // Swap two nodes. Assumes i & j are valid, O(1)
    private fun swap(i: Int, j: Int) {
        val elem_i = heap!![i]
        val elem_j = heap!![j]
        heap!![i] = elem_j
        heap!![j] = elem_i
    }

    // Removes a particular element in the heap, O(n)
    fun remove(element: T?): Boolean {
        if (element == null) return false
        // Linear removal via search, O(n)
        for (i in 0 until size()) {
            if (element == heap!![i]) {
                removeAt(i)
                return true
            }
        }
        return false
    }

    // Removes a node at particular index, O(log(n))
    private fun removeAt(i: Int): T? {
        if (isEmpty) return null
        val indexOfLastElem = size() - 1
        val removed_data = heap!![i]
        swap(i, indexOfLastElem)

        // Obliterate the value
        heap!!.removeAt(indexOfLastElem)

        // Check if the last element was removed
        if (i == indexOfLastElem) return removed_data
        val elem = heap!![i]

        // Try sinking element
        sink(i)

        // If sinking did not work try swimming
        if (heap!![i] == elem) swim(i)
        return removed_data
    }

    // Recursively checks if this heap is a min heap
    // This method is just for testing purposes to make
    // sure the heap invariant is still being maintained
    // Called this method with k=0 to start at the root
    fun isMinHeap(k: Int): Boolean {
        // If we are outside the bounds of the heap return true
        val heapSize = size()
        if (k >= heapSize) return true
        val left = 2 * k + 1
        val right = 2 * k + 2

        // Make sure that the current node k is less than
        // both of its children left, and right if they exist
        // return false otherwise to indicate an invalid heap
        if (left < heapSize && !less(k, left)) return false
        return if (right < heapSize && !less(k, right)) false else isMinHeap(left) && isMinHeap(right)

        // Recurse on both children to make sure they're also valid heaps
    }

    override fun toString(): String {
        return heap.toString()
    }
}
