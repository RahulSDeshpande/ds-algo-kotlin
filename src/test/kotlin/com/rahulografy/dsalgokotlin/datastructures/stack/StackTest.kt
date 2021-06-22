package com.rahulografy.dsalgokotlin.datastructures.stack

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class StackTest {

    private val stacks: MutableList<Stack<Int>> = ArrayList()

    @Before
    fun setup() {
        stacks.add(ListStack())
        stacks.add(ArrayStack())
        stacks.add(IntStack(2))
    }

    @Test
    fun testEmptyStack() {
        for (stack in stacks) {
            Truth.assertThat(stack.isEmpty).isTrue()
            Truth.assertThat(stack.size()).isEqualTo(0)
        }
    }

    @Test(expected = Exception::class)
    fun testPopOnEmpty() {
        for (stack in stacks) {
            stack.pop()
        }
    }

    @Test(expected = Exception::class)
    fun testPeekOnEmpty() {
        for (stack in stacks) {
            stack.peek()
        }
    }

    @Test
    fun testPush() {
        for (stack in stacks) {
            stack.push(2)
            Truth.assertThat(stack.size()).isEqualTo(1)
        }
    }

    @Test
    fun testPeek() {
        for (stack in stacks) {
            stack.push(2)
            Truth.assertThat(stack.peek()).isEqualTo(2)
            Truth.assertThat(stack.size()).isEqualTo(1)
        }
    }

    @Test
    fun testPop() {
        for (stack in stacks) {
            stack.push(2)
            Truth.assertThat(stack.pop()).isEqualTo(2)
            Truth.assertThat(stack.size()).isEqualTo(0)
        }
    }

    @Test
    fun testExhaustively() {
        for (stack in stacks) {
            Truth.assertThat(stack.isEmpty).isTrue()
            stack.push(1)
            Truth.assertThat(stack.isEmpty).isFalse()
            stack.push(2)
            Truth.assertThat(stack.size()).isEqualTo(2)
            Truth.assertThat(stack.peek()).isEqualTo(2)
            Truth.assertThat(stack.size()).isEqualTo(2)
            Truth.assertThat(stack.pop()).isEqualTo(2)
            Truth.assertThat(stack.size()).isEqualTo(1)
            Truth.assertThat(stack.peek()).isEqualTo(1)
            Truth.assertThat(stack.size()).isEqualTo(1)
            Truth.assertThat(stack.pop()).isEqualTo(1)
            Truth.assertThat(stack.size()).isEqualTo(0)
            Truth.assertThat(stack.isEmpty).isTrue()
        }
    }
}
