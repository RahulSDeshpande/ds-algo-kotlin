package com.rahulografy.dsalgokotlin.datastructures.priorityqueue

import java.util.TreeSet

/**
 * A min priority queue implementation using a binary heap. This implementation tracks each element
 * inside the binary heap with a hashtable for quick removals.
 *
 * @author Rahul S Deshpande
 */
class BinaryHeapQuickRemovals<T : Comparable<T>?> {

    // A dynamic list to track the elements inside the heap
    private var heap: MutableList<T>? = null

    // This map keeps track of the possible indices a particular
    // node value is found in the heap. Having this mapping lets
    // us have O(log(n)) removals and O(1) element containment check
    // at the cost of some additional space and minor overhead
    private val map: MutableMap<T, TreeSet<Int>> = HashMap()

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
        for (i in 0 until heapSize) {
            mapAdd(elems[i], i)
            heap?.add(elems[i])
        }

        // Heapify process, O(n)
        for (i in Math.max(0, heapSize / 2 - 1) downTo 0) sink(i)
    }

    // Priority queue construction, O(nlog(n))
    constructor(elems: Collection<T>) : this(elems.size) {
        for (elem in elems) add(elem)
    }

    // Returns true/false depending on if the priority queue is empty
    val isEmpty: Boolean
        get() = size() == 0

    // Clears everything inside the heap, O(n)
    fun clear() {
        heap!!.clear()
        map.clear()
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

    // Test if an element is in heap, O(1)
    operator fun contains(elem: T?): Boolean {

        // Map lookup to check containment, O(1)
        return if (elem == null) false else map.containsKey(elem)

        // Linear scan to check containment, O(n)
        // for(int i = 0; i < heapSize; i++)
        //   if (heap.get(i).equals(elem))
        //     return true;
        // return false;
    }

    // Adds an element to the priority queue, the
    // element must not be null, O(log(n))
    fun add(elem: T?) {
        requireNotNull(elem)
        heap!!.add(elem)
        val indexOfLastElem = size() - 1
        mapAdd(elem, indexOfLastElem)
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
        val i_elem = heap!![i]
        val j_elem = heap!![j]
        heap!![i] = j_elem
        heap!![j] = i_elem
        mapSwap(i_elem, j_elem, i, j)
    }

    // Removes a particular element in the heap, O(log(n))
    fun remove(element: T?): Boolean {
        if (element == null) return false

        // Linear removal via search, O(n)
        // for (int i = 0; i < heapSize; i++) {
        //   if (element.equals(heap.get(i))) {
        //     removeAt(i);
        //     return true;
        //   }
        // }

        // Logarithmic removal with map, O(log(n))
        val index = mapGet(element)
        index?.let { removeAt(it) }
        return index != null
    }

    // Removes a node at particular index, O(log(n))
    private fun removeAt(i: Int): T? {
        if (isEmpty) return null
        val indexOfLastElem = size() - 1
        val removed_data = heap!![i]
        swap(i, indexOfLastElem)

        // Obliterate the value
        heap!!.removeAt(indexOfLastElem)
        mapRemove(removed_data, indexOfLastElem)

        // Removed last element
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

    // Add a node value and its index to the map
    private fun mapAdd(value: T, index: Int) {
        var set = map[value]

        // New value being inserted in map
        if (set == null) {
            set = TreeSet()
            set.add(index)
            map[value] = set

            // Value already exists in map
        } else set.add(index)
    }

    // Removes the index at a given value, O(log(n))
    private fun mapRemove(value: T, index: Int) {
        val set = map[value]!!
        set.remove(index) // TreeSets take O(log(n)) removal time
        if (set.size == 0) map.remove(value)
    }

    // Extract an index position for the given value
    // NOTE: If a value exists multiple times in the heap the highest
    // index is returned (this has arbitrarily been chosen)
    private fun mapGet(value: T): Int? {
        val set = map[value]
        return set?.last()
    }

    // Exchange the index of two nodes internally within the map
    private fun mapSwap(val1: T, val2: T, val1Index: Int, val2Index: Int) {
        val set1: MutableSet<Int> = map[val1]!!
        val set2: MutableSet<Int> = map[val2]!!
        set1.remove(val1Index)
        set2.remove(val2Index)
        set1.add(val2Index)
        set2.add(val1Index)
    }

    override fun toString(): String {
        return heap.toString()
    }
}
