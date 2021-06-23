package com.rahulografy.dsalgokotlin.datastructures.priorityqueue

/**
 * An implementation of an indexed min D-ary heap priority queue.
 *
 *
 * This implementation supports arbitrary keys with comparable values. To use arbitrary keys
 * (such as strings or objects) first map all your keys to the integer domain [0, N) where N is the
 * number of keys you have and then use the mapping with this indexed priority queue.
 *
 *
 * As convention, I denote 'ki' as the index value in the domain [0, N) associated with a key k,
 * therefore: ki = map[k]
 *
 * @author Rahul S Deshpande
 */
open class MinIndexedDHeap<T : Comparable<T>?>(degree: Int, maxSize: Int) {

    // Current number of elements in the heap.
    private var sz = 0

    // Maximum number of elements in the heap.
    private val N: Int

    // The degree of every node in the heap.
    private val D: Int

    // Lookup arrays to track the child/parent indexes of each node.
    private val child: IntArray
    private val parent: IntArray

    // The Position Map (pm) maps Key Indexes (ki) to where the position of that
    // key is represented in the priority queue in the domain [0, sz).
    val pm: IntArray

    // The Inverse Map (im) stores the indexes of the keys in the range
    // [0, sz) which make up the priority queue. It should be noted that
    // 'im' and 'pm' are inverses of each other, so: pm[im[i]] = im[pm[i]] = i
    val im: IntArray

    // The values associated with the keys. It is very important  to note
    // that this array is indexed by the key indexes (aka 'ki').
    val values: Array<Any?>
    fun size(): Int {
        return sz
    }

    val isEmpty: Boolean
        get() = sz == 0

    operator fun contains(ki: Int): Boolean {
        keyInBoundsOrThrow(ki)
        return pm[ki] != -1
    }

    fun peekMinKeyIndex(): Int {
        isNotEmptyOrThrow
        return im[0]
    }

    fun pollMinKeyIndex(): Int {
        val minki = peekMinKeyIndex()
        delete(minki)
        return minki
    }

    fun peekMinValue(): T? {
        isNotEmptyOrThrow
        return values[im[0]] as T?
    }

    fun pollMinValue(): T? {
        val minValue = peekMinValue()
        delete(peekMinKeyIndex())
        return minValue
    }

    fun insert(ki: Int, value: T) {
        require(!contains(ki)) { "index already exists; received: $ki" }
        valueNotNullOrThrow(value)
        pm[ki] = sz
        im[sz] = ki
        values[ki] = value
        swim(sz++)
    }

    fun valueOf(ki: Int): T? {
        keyExistsOrThrow(ki)
        return values[ki] as T?
    }

    fun delete(ki: Int): T? {
        keyExistsOrThrow(ki)
        val i = pm[ki]
        swap(i, --sz)
        sink(i)
        swim(i)
        val value = values[ki] as T?
        values[ki] = null
        pm[ki] = -1
        im[sz] = -1
        return value
    }

    fun update(ki: Int, value: T): T? {
        keyExistsAndValueNotNullOrThrow(ki, value)
        val i = pm[ki]
        val oldValue = values[ki] as T?
        values[ki] = value
        sink(i)
        swim(i)
        return oldValue
    }

    // Strictly decreases the value associated with 'ki' to 'value'
    fun decrease(ki: Int, value: T) {
        keyExistsAndValueNotNullOrThrow(ki, value)
        if (less(value, values[ki])) {
            values[ki] = value
            swim(pm[ki])
        }
    }

    // Strictly increases the value associated with 'ki' to 'value'
    fun increase(ki: Int, value: T) {
        keyExistsAndValueNotNullOrThrow(ki, value)
        if (less(values[ki], value)) {
            values[ki] = value
            sink(pm[ki])
        }
    }

    /* Helper functions */
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
        val to = Math.min(sz, from + D)
        for (j in from until to) if (less(j, i)) {
            i = j
            index = i
        }
        return index
    }

    private fun swap(i: Int, j: Int) {
        pm[im[j]] = i
        pm[im[i]] = j
        val tmp = im[i]
        im[i] = im[j]
        im[j] = tmp
    }

    // Tests if the value of node i < node j
    private fun less(i: Int, j: Int): Boolean {
        return (values[im[i]] as Comparable<T>).compareTo(values[im[j]] as T) < 0
    }

    private fun less(obj1: Any?, obj2: Any?): Boolean {
        return (obj1 as Comparable<T>).compareTo(obj2 as T) < 0
    }

    override fun toString(): String {
        val lst: MutableList<Int> = ArrayList(sz)
        for (i in 0 until sz) lst.add(im[i])
        return lst.toString()
    }

    /* Helper functions to make the code more readable. */
    private val isNotEmptyOrThrow: Unit
        get() {
            if (isEmpty) throw NoSuchElementException("Priority queue underflow")
        }

    private fun keyExistsAndValueNotNullOrThrow(ki: Int, value: T) {
        keyExistsOrThrow(ki)
        valueNotNullOrThrow(value)
    }

    private fun keyExistsOrThrow(ki: Int) {
        if (!contains(ki)) throw NoSuchElementException("Index does not exist; received: $ki")
    }

    private fun valueNotNullOrThrow(value: Any?) {
        requireNotNull(value) { "value cannot be null" }
    }

    private fun keyInBoundsOrThrow(ki: Int) {
        require(!(ki < 0 || ki >= N)) { "Key index out of bounds; received: $ki" }
    }

    /* Test functions */ // Recursively checks if this heap is a min heap. This method is used
    // for testing purposes to validate the heap invariant.
    val isMinHeap: Boolean
        get() = isMinHeap(0)

    private fun isMinHeap(i: Int): Boolean {
        val from = child[i]
        val to = Math.min(sz, from + D)
        for (j in from until to) {
            if (!less(i, j)) return false
            if (!isMinHeap(j)) return false
        }
        return true
    }

    // Initializes a D-ary heap with a maximum capacity of maxSize.
    init {
        require(maxSize > 0) { "maxSize <= 0" }
        D = Math.max(2, degree)
        N = Math.max(D + 1, maxSize)
        im = IntArray(N)
        pm = IntArray(N)
        child = IntArray(N)
        parent = IntArray(N)
        values = arrayOfNulls(N)
        for (i in 0 until N) {
            parent[i] = (i - 1) / D
            child[i] = i * D + 1
            im[i] = -1
            pm[i] = im[i]
        }
    }
}
