/**
 * Most of the time when you use an array it's to place integers inside of it, so why not have a
 * super fast integer only array? This file contains an implementation of an integer only array
 * which can outperform Java's ArrayList by about a factor of 10-15x! Enjoy!
 *
 * @author Rahul S Deshpande
 */
package com.rahulografy.dsalgokotlin.datastructures.dynamicarray

import java.util.Arrays

class IntArray : Iterable<Int?> {
    var arr: kotlin.IntArray
    var len = 0
    private var capacity = 0

    // Initialize the array with a certain capacity
    // Initialize the array with a default capacity
    @JvmOverloads
    constructor(capacity: Int = DEFAULT_CAP) {
        require(capacity >= 0) { "Illegal Capacity: $capacity" }
        this.capacity = capacity
        arr = kotlin.IntArray(capacity)
    }

    // Given an array make it a dynamic array!
    constructor(array: kotlin.IntArray?) {
        requireNotNull(array) { "Array cannot be null" }
        arr = Arrays.copyOf(array, array.size)
        len = array.size
        capacity = len
    }

    // Returns the size of the array
    fun size(): Int {
        return len
    }

    // Returns true/false on whether the array is empty
    val isEmpty: Boolean
        get() = len == 0

    // To get/set values without method call overhead you can do
    // array_obj.arr[index] instead, you can gain about 10x the speed!
    operator fun get(index: Int): Int {
        return arr[index]
    }

    operator fun set(index: Int, elem: Int) {
        arr[index] = elem
    }

    // Add an element to this dynamic array
    fun add(elem: Int) {
        if (len + 1 >= capacity) {
            if (capacity == 0) capacity = 1 else capacity *= 2 // double the size
            arr = Arrays.copyOf(arr, capacity) // pads with extra 0/null elements
        }
        arr[len++] = elem
    }

    // Removes the element at the specified index in this list.
    // If possible, avoid calling this method as it take O(n) time
    // to remove an element (since you have to reconstruct the array!)
    fun removeAt(rm_index: Int) {
        System.arraycopy(arr, rm_index + 1, arr, rm_index, len - rm_index - 1)
        --len
        --capacity
    }

    // Search and remove an element if it is found in the array
    // If possible, avoid calling this method as it take O(n) time
    fun remove(elem: Int): Boolean {
        for (i in 0 until len) {
            if (arr[i] == elem) {
                removeAt(i)
                return true
            }
        }
        return false
    }

    // Reverse the contents of this array
    fun reverse() {
        for (i in 0 until len / 2) {
            val tmp = arr[i]
            arr[i] = arr[len - i - 1]
            arr[len - i - 1] = tmp
        }
    }

    // Perform a binary search on this array to find an element in O(log(n)) time
    // Make sure this array is sorted! Returns a value < 0 if item is not found
    fun binarySearch(key: Int): Int {
        // if (index < 0) index = -index - 1 // If not found this will tell you where to insert
        return Arrays.binarySearch(arr, 0, len, key)
    }

    // Sort this array
    fun sort() {
        Arrays.sort(arr, 0, len)
    }

    // Iterator is still fast but not as fast as iterative for loop
    override fun iterator(): MutableIterator<Int> {
        return object : MutableIterator<Int> {
            var index = 0
            override fun hasNext(): Boolean {
                return index < len
            }

            override fun next(): Int {
                return arr[index++]
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

    companion object {
        private const val DEFAULT_CAP = 1 shl 3

        // Example usage
        @JvmStatic
        fun main(args: Array<String>) {
            val ar = IntArray(50)
            ar.add(3)
            ar.add(7)
            ar.add(6)
            ar.add(-2)
            ar.sort() // [-2, 3, 6, 7]

            // Prints [-2, 3, 6, 7]
            for (i in 0 until ar.size()) println(ar[i])

            // Prints [-2, 3, 6, 7]
            println(ar)
        }
    }
}
