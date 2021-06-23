package com.rahulografy.dsalgokotlin.datastructures.queue

import java.util.ArrayDeque

/**
 * This file contains an implementation of an integer only queue which is extremely quick and
 * lightweight.
 *
 * In terms of performance it can outperform java.util.ArrayDeque (Java's fastest queue
 * implementation) by a factor of 40+!
 *
 * See the benchmark test below for proof.
 *
 * However, the downside is you need to know an upper bound on the number of elements
 * that will be inside the queue at any given time for this queue to work.
 *
 * @author Rahul S Deshpande
 */
class IntQueue(maxSize: Int) : Queue<Int> {

    private val data: IntArray
    private var front: Int
    private var end: Int
    private var size = 0

    // Return true/false on whether the queue is empty
    override val isEmpty: Boolean
        get() = size == 0

    // Return the number of elements inside the queue
    override fun size(): Int {
        return size
    }

    override fun peek(): Int {
        if (isEmpty) {
            throw RuntimeException("Queue is empty")
        }
        front = front % data.size
        return data[front]
    }

    val isFull: Boolean
        get() = size == data.size

    // Add an element to the queue
    override fun offer(value: Int) {
        if (isFull) {
            throw RuntimeException("Queue too small!")
        }
        data[end++] = value
        size++
        end %= data.size
    }

    // Make sure you check is the queue is not empty before calling poll!
    override fun poll(): Int {
        if (size == 0) {
            throw RuntimeException("Queue is empty")
        }
        size--
        front %= data.size
        return data[front++]
    }

    companion object {
        // Example usage
        @JvmStatic
        fun main(args: Array<String>) {
            val q = IntQueue(5)
            q.offer(1)
            q.offer(2)
            q.offer(3)
            q.offer(4)
            q.offer(5)

            println(q.poll()) // 1
            println(q.poll()) // 2
            println(q.poll()) // 3
            println(q.poll()) // 4
            println(q.isEmpty) // false

            q.offer(1)
            q.offer(2)
            q.offer(3)

            println(q.poll()) // 5
            println(q.poll()) // 1
            println(q.poll()) // 2
            println(q.poll()) // 3
            println(q.isEmpty) // true

            // benchMarkTest()
        }

        // BenchMark IntQueue vs ArrayDeque.
        private fun benchMarkTest() {
            val n = 10000000
            val intQ = IntQueue(n)

            // IntQueue times at around 0.0324 seconds
            var start = System.nanoTime()
            for (i in 0 until n) intQ.offer(i)
            for (i in 0 until n) intQ.poll()
            var end = System.nanoTime()
            println("IntQueue Time: " + (end - start) / 1e9)

            // ArrayDeque times at around 1.438 seconds
            val arrayDeque = ArrayDeque<Int>()
            // java.util.ArrayDeque <Integer> arrayDeque = new java.util.ArrayDeque<>(n) // strangely the
            // ArrayQueue is slower when you give it an initial capacity.
            start = System.nanoTime()
            for (i in 0 until n) arrayDeque.offer(i)
            for (i in 0 until n) arrayDeque.poll()
            end = System.nanoTime()
            println("ArrayDeque Time: " + (end - start) / 1e9)
        }
    }

    // maxSize is the maximum number of items
    // that can be in the queue at any given time
    init {
        end = size
        front = end
        data = IntArray(maxSize)
    }
}
