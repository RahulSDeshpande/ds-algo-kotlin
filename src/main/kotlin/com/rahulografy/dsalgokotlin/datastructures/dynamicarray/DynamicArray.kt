/**
 * A generic dynamic array implementation
 *
 * @author Rahul S Deshpande
 */
package com.rahulografy.dsalgokotlin.datastructures.dynamicarray

class DynamicArray<T> @JvmOverloads constructor(capacity: Int = 16) : Iterable<T> {
    private var arr: Array<T?>
    private var len = 0 // length user thinks array is
    private var capacity = 0 // Actual array size

    fun size(): Int {
        return len
    }

    val isEmpty: Boolean
        get() = size() == 0

    operator fun get(index: Int): T? {
        if (index >= len || index < 0) {
            throw IndexOutOfBoundsException()
        }
        return arr[index]
    }

    operator fun set(index: Int, elem: T) {
        if (index >= len || index < 0) throw IndexOutOfBoundsException()
        arr[index] = elem
    }

    fun clear() {
        for (i in 0 until len) arr[i] = null
        len = 0
    }

    fun add(elem: T) {
        // Time to resize!
        if (len + 1 >= capacity) {
            if (capacity == 0) capacity = 1 else capacity *= 2 // double the size
            val new_arr = arrayOfNulls<Any>(capacity) as Array<T?>
            for (i in 0 until len) new_arr[i] = arr[i]
            arr = new_arr // arr has extra nulls padded
        }
        arr[len++] = elem
    }

    /**
     * Removes an element at the specified index in this array.
     */
    fun removeAt(rm_index: Int): T? {
        if (rm_index >= len || rm_index < 0) throw IndexOutOfBoundsException()
        val data = arr[rm_index]
        val new_arr = arrayOfNulls<Any>(len - 1) as Array<T?>
        var i = 0
        var j = 0
        while (i < len) {
            if (i == rm_index) j-- // Skip over rm_index by fixing j temporarily
            else new_arr[j] = arr[i]
            i++
            j++
        }
        arr = new_arr
        capacity = --len
        return data
    }

    /**
     * Removes an element in this array.
     */
    fun remove(obj: Any?): Boolean {
        val index = indexOf(obj)
        if (index == -1) return false
        removeAt(index)
        return true
    }

    fun indexOf(obj: Any?): Int {
        for (i in 0 until len) {
            if (obj == null) {
                if (arr[i] == null) return i
            } else {
                if (obj == arr[i]) return i
            }
        }
        return -1
    }

    operator fun contains(obj: Any?): Boolean {
        return indexOf(obj) != -1
    }

    // Iterator is still fast but not as fast as iterative for loop
    override fun iterator(): MutableIterator<T> {
        return object : MutableIterator<T> {
            var index = 0
            override fun hasNext(): Boolean {
                return index < len
            }

            override fun next(): T {
                return arr[index++] ?: (null as T)
            }

            override fun remove() {
                throw UnsupportedOperationException()
            }
        }
    }

    override fun toString(): String {
        return if (len == 0) "[]" else {
            val sb = StringBuilder(len).append("[")
            for (i in 0 until len - 1) sb.append(arr[i].toString() + ", ")
            sb.append(arr[len - 1].toString() + "]").toString()
        }
    }

    init {
        require(capacity >= 0) { "Illegal Capacity: $capacity" }
        this.capacity = capacity
        arr = arrayOfNulls<Any>(capacity) as Array<T?>
    }
}
