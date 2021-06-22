package com.rahulografy.dsalgokotlin.datastructures.stack

import java.util.Arrays
import java.util.EmptyStackException

class ArrayStack<T> : Stack<T> {

    private var size = 0
    private var capacity = 16
    private var data: Array<Any?>
    override fun size(): Int {
        return size
    }

    override val isEmpty: Boolean
        get() = size == 0

    override fun push(element: T) {
        if (size == capacity) {
            increaseCapacity()
        }
        data[size++] = element
    }

    /**
     * Increase the capacity to store more elements.
     */
    private fun increaseCapacity() {
        capacity *= 2
        data = Arrays.copyOf(data, capacity)
    }

    override fun pop(): T {
        if (isEmpty) throw EmptyStackException()
        val elem = data[--size] as T
        data[size] = null
        return elem
    }

    override fun peek(): T {
        if (isEmpty) throw EmptyStackException()
        return data[size - 1] as T
    }

    init {
        data = arrayOfNulls(capacity)
    }
}
