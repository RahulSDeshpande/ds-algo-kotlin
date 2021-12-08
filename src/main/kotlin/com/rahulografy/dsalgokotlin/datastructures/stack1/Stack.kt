package com.rahulografy.dsalgokotlin.datastructures.stack1

interface Stack<T> {

    fun push(value: T)

    fun pop(): T?

    fun peek(): T?

    fun isEmpty(): Boolean

    val size: Int
}
