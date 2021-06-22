package com.rahulografy.dsalgokotlin.datastructures.stack

interface Stack<T> {

    /**
     * Return the number of elements in the stack
     */
    fun size(): Int

    /**
     * Return if the stack is empty
     */
    val isEmpty: Boolean

    /**
     * Push the element on the stack
     */
    fun push(element: T)

    /**
     * Pop the element off the stack
     */
    fun pop(): T

    fun peek(): T
}
