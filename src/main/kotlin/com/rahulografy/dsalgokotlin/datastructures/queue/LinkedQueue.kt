package com.rahulografy.dsalgokotlin.datastructures.queue

import java.util.LinkedList

/**
 * A simple queue implementation with a linked-list
 *
 * @author Rahul S Deshpande
 */
class LinkedQueue<T> : Iterable<T>, Queue<T> {

    private val list = LinkedList<T>()

    constructor()

    constructor(firstElem: T) {
        offer(firstElem)
    }

    // Return the size of the queue
    override fun size(): Int {
        return list.size
    }

    // Returns whether or not the queue is empty
    override val isEmpty: Boolean
        get() = size() == 0

    // Peek the element at the front of the queue
    // The method throws an error is the queue is empty
    override fun peek(): T {
        if (isEmpty) throw RuntimeException("Queue Empty")
        return list.peekFirst()
    }

    // Poll an element from the front of the queue
    // The method throws an error is the queue is empty
    override fun poll(): T {
        if (isEmpty) throw RuntimeException("Queue Empty")
        return list.removeFirst()
    }

    // Add an element to the back of the queue
    override fun offer(value: T) {
        list.addLast(value)
    }

    // Return an iterator to alow the user to traverse
    // through the elements found inside the queue
    override fun iterator(): MutableIterator<T> {
        return list.iterator()
    }
}
