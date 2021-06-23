package com.rahulografy.dsalgokotlin.datastructures.stack

import java.util.EmptyStackException
import java.util.LinkedList

/**
 * A linked list implementation of a stack
 *
 * @author Rahul S Deshpande
 */
class ListStack<T> : Iterable<T>, Stack<T> {

    private val list = LinkedList<T>()

    /**
     * Create an empty stack
     */
    constructor()

    /**
     * Create a Stack with an initial element
     */
    constructor(firstElem: T) {
        push(firstElem)
    }

    // Return the number of elements in the stack
    override fun size(): Int {
        return list.size
    }

    // Check if the stack is empty
    override val isEmpty: Boolean
        get() = size() == 0

    // Push an element on the stack
    override fun push(element: T) {
        list.addLast(element)
    }

    // Pop an element off the stack
    // Throws an error is the stack is empty
    override fun pop(): T {
        if (isEmpty) throw EmptyStackException()
        return list.removeLast()
    }

    // Peek the top of the stack without removing an element
    // Throws an exception if the stack is empty
    override fun peek(): T {
        if (isEmpty) throw EmptyStackException()
        return list.peekLast()
    }

    // Searches for the element starting from top of the stack
    // Returns -1 if the element is not present in the stack
    fun search(elem: T): Int {
        return list.lastIndexOf(elem)
    }

    // Allow users to iterate through the stack using an iterator
    override fun iterator(): MutableIterator<T> {
        return list.iterator()
    }
}
