package com.rahulografy.dsalgokotlin.datastructures.queue

/**
 * @param T type of element held int the queue
 * @author Rahul S Deshpande
 */
interface Queue<T> {

    fun offer(value: T)

    fun poll(): T

    fun peek(): T

    fun size(): Int

    val isEmpty: Boolean
}
