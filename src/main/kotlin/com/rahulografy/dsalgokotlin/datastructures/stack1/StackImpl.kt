package com.rahulografy.dsalgokotlin.datastructures.stack1

import java.util.LinkedList

open class StackImpl<T> : Stack<T> {

    private var list = LinkedList<T>()

    override val size = list.size

    override fun push(value: T) {
        list.addLast(value)
    }

    override fun pop(): T? =
        if (list.isNotEmpty()) {
            list.removeLast()
        } else {
            null
        }

    override fun peek(): T? =
        if (list.isNotEmpty()) {
            list.last
        } else {
            null
        }

    override fun isEmpty(): Boolean =
        list.isEmpty()

    fun findMin(): T? {
        var min = Int.MAX_VALUE

        list.forEach {
            val itInt = it.asInt()
            if (itInt < min) {
                min = itInt
            }
        }

        return min as T
    }

    private fun T.asInt() = this as Int

    override fun toString(): String {
        var output = ""
        list.forEach {
            output += "$it "
        }
        return output
    }

    fun fun1(i: Int): Boolean {
        return false
    }

    fun fun1(): Int {
        return 1
    }
}

fun main() {
    val stackImpl = StackImpl<Int>()

    stackImpl.push(10)
    stackImpl.push(12)
    stackImpl.push(9)
    stackImpl.push(32)
    stackImpl.push(52)
    stackImpl.push(2)

    println(stackImpl.toString())

    println("Find min: ${stackImpl.findMin()}")
    println("Pop: ${stackImpl.pop()}")
    println("Find min: ${stackImpl.findMin()}")
}
