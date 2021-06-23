package com.rahulografy.dsalgokotlin.datastructures.stack

import java.util.ArrayDeque

/**
 * This class contains an implementation of an integer only stack which is extremely quick and
 * lightweight.
 *
 * In terms of performance it can outperform java.util.ArrayDeque (Java's fastest stack
 * implementation) by a factor of 50!
 *
 * See the benchmark test below for proof.
 *
 * However, the downside is you need to know an upper bound on the number of elements that will
 * be inside the stack at any given time for it to work correctly.
 *
 * @author Rahul S Deshpande
 */
class IntStack(maxSize: Int) : Stack<Int> {

    // MaxSize is the maximum number of items that can be in the queue at any given time
    private val intArray = IntArray(maxSize)

    private var position = 0

    // Returns the number of elements insize the stack
    override fun size(): Int {
        return position
    }

    // Returns true/false on whether the stack is empty
    override val isEmpty: Boolean
        get() = position == 0

    // Returns the element at the top of the stack
    override fun peek(): Int {
        return intArray[position - 1]
    }

    // Add an element to the top of the stack
    override fun push(element: Int) {
        intArray[position++] = element
    }

    // Make sure you check that the stack is not empty before calling pop!
    override fun pop(): Int {
        return intArray[--position]
    }

    companion object {
        // Example usage
        @JvmStatic
        fun main(args: Array<String>) {
            val s = IntStack(5)
            s.push(1)
            s.push(2)
            s.push(3)
            s.push(4)
            s.push(5)
            println(s.pop()) // 5
            println(s.pop()) // 4
            println(s.pop()) // 3
            s.push(3)
            s.push(4)
            s.push(5)
            while (!s.isEmpty) println(s.pop())
            benchMarkTest()
        }

        // BenchMark IntStack vs ArrayDeque.
        private fun benchMarkTest() {
            val n = 10000000
            val intStack = IntStack(n)

            // IntStack times at around 0.0324 seconds
            var start = System.nanoTime()
            for (i in 0 until n) intStack.push(i)
            for (i in 0 until n) intStack.pop()
            var end = System.nanoTime()
            println("IntStack Time: " + (end - start) / 1e9)

            // ArrayDeque times at around 1.438 seconds
            //    java.util.ArrayDeque<Integer> arrayDeque = new java.util.ArrayDeque<>()
            //    java.util.Stack<Integer> arrayDeque = new java.util.Stack<>()
            val arrayDeque = ArrayDeque<Int>(n) // strangely the
            // ArrayQueue is slower when you give it an initial capacity.
            start = System.nanoTime()
            for (i in 0 until n) arrayDeque.push(i)
            for (i in 0 until n) arrayDeque.pop()
            end = System.nanoTime()
            println("ArrayDeque Time: " + (end - start) / 1e9)
            val listStack: Stack<Int> = ListStack()
            start = System.nanoTime()
            for (i in 0 until n) listStack.push(i)
            for (i in 0 until n) listStack.pop()
            end = System.nanoTime()
            println("ListStack Time: " + (end - start) / 1e9)
            val arrayStack: Stack<Int> = ArrayStack()
            start = System.nanoTime()
            for (i in 0 until n) arrayStack.push(i)
            for (i in 0 until n) arrayStack.pop()
            end = System.nanoTime()
            println("ArrayStack Time: " + (end - start) / 1e9)
        }
    }
}
