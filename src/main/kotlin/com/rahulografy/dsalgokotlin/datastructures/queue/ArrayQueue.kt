package com.rahulografy.dsalgokotlin.datastructures.queue

/**
 * Besides the Generics, the loss of property of size is another difference between ArrayQueue and
 * IntQueue. The size of ArrayQueue is calculated by the formula, as are empty status and full
 * status.
 *
 *
 * ArrayQueue maximum size is data.length - 1. The place of the variable rear is always in front
 * of the variable front logistically if regard the data array as circular. so the number of states
 * of the combination of rear and front is the length of the data array. And one of the total states
 * is used to be the judge if the queue is empty or full.
 *
 * @author Rahul S Deshpande
 */
class ArrayQueue<T>(capacity: Int) : Queue<T> {

    private val data: Array<Any?> = arrayOfNulls(capacity + 1)
    private var front: Int = 0
    private var rear: Int = 0

    override fun offer(value: T) {
        if (isFull) {
            throw RuntimeException("Queue is full")
        }
        data[rear++] = value as T
        rear = adjustIndex(rear, data.size)
    }

    override fun poll(): T {
        if (isEmpty) {
            throw RuntimeException("Queue is empty")
        }
        front = adjustIndex(front, data.size)
        return data[front++] as T
    }

    override fun peek(): T {
        if (isEmpty) {
            throw RuntimeException("Queue is empty")
        }
        front = adjustIndex(front, data.size)
        return data[front] as T
    }

    override fun size(): Int {
        return adjustIndex(rear + data.size - front, data.size)
    }

    override val isEmpty: Boolean
        get() = rear == front

    val isFull: Boolean
        get() = (front + data.size - rear) % data.size == 1

    private fun adjustIndex(index: Int, size: Int): Int {
        return if (index >= size) index - size else index
    }
}
