package com.rahulografy.dsalgokotlin.datastructures.queue

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.util.ArrayDeque

class IntQueueTest {

    @Before
    fun setup() {
    }

    @Test
    fun testEmptyQueue() {
        val queue = IntQueue(0)
        Truth.assertThat(queue.isEmpty).isTrue()
        Truth.assertThat(queue.size()).isEqualTo(0)
    }

    // Doesn't apply to this implementation because of wrap
    // @Test(expected=Exception.class)
    // public void testPollOnEmpty() {
    //   IntQueue queue = new IntQueue(0);
    //   queue.poll();
    // }
    // Doesn't apply to this implementation because of wrap
    // @Test(expected=Exception.class)
    // public void testPeekOnEmpty() {
    //   IntQueue queue = new IntQueue(0);
    //   queue.peek();
    // }

    @Test
    fun testofferOneElement() {
        val queue = IntQueue(1)
        queue.offer(77)
        Truth.assertThat(queue.size()).isEqualTo(1)
    }

    @Test
    fun testAll() {
        var n = 5
        val queue = IntQueue(10)
        Truth.assertThat(queue.isEmpty).isTrue()
        for (i in 1..n) {
            queue.offer(i)
            Truth.assertThat(queue.isEmpty).isFalse()
        }
        for (i in 1..n) {
            Truth.assertThat(queue.peek()).isEqualTo(i)
            Truth.assertThat(queue.poll()).isEqualTo(i)
            Truth.assertThat(queue.size()).isEqualTo(n - i)
        }
        Truth.assertThat(queue.isEmpty).isTrue()
        n = 8
        for (i in 1..n) {
            queue.offer(i)
            Truth.assertThat(queue.isEmpty).isFalse()
        }
        for (i in 1..n) {
            Truth.assertThat(queue.peek()).isEqualTo(i)
            Truth.assertThat(queue.poll()).isEqualTo(i)
            Truth.assertThat(queue.size()).isEqualTo(n - i)
        }
        Truth.assertThat(queue.isEmpty).isTrue()
        n = 9
        for (i in 1..n) {
            queue.offer(i)
            Truth.assertThat(queue.isEmpty).isFalse()
        }
        for (i in 1..n) {
            Truth.assertThat(queue.peek()).isEqualTo(i)
            Truth.assertThat(queue.poll()).isEqualTo(i)
            Truth.assertThat(queue.size()).isEqualTo(n - i)
        }
        Truth.assertThat(queue.isEmpty).isTrue()
        n = 10
        for (i in 1..n) {
            queue.offer(i)
            Truth.assertThat(queue.isEmpty).isFalse()
        }
        for (i in 1..n) {
            Truth.assertThat(queue.peek()).isEqualTo(i)
            Truth.assertThat(queue.poll()).isEqualTo(i)
            Truth.assertThat(queue.size()).isEqualTo(n - i)
        }
        Truth.assertThat(queue.isEmpty).isTrue()
    }

    @Test
    fun testPeekOneElement() {
        val queue = IntQueue(1)
        queue.offer(77)
        Truth.assertThat(queue.peek()).isEqualTo(77)
        Truth.assertThat(queue.size()).isEqualTo(1)
    }

    @Test
    fun testpollOneElement() {
        val queue = IntQueue(1)
        queue.offer(77)
        Truth.assertThat(queue.poll()).isEqualTo(77)
        Truth.assertThat(queue.size()).isEqualTo(0)
    }

    @Test
    fun testRandom() {
        for (qSize in 1..50) {
            val intQ = IntQueue(qSize)
            val javaQ = ArrayDeque<Int>(qSize)
            Truth.assertThat(javaQ.isEmpty()).isEqualTo(intQ.isEmpty)
            Truth.assertThat(javaQ.size).isEqualTo(intQ.size())
            for (operations in 0..4999) {
                val r = Math.random()
                if (r < 0.60) {
                    val elem = (1000 * Math.random()).toInt()
                    if (javaQ.size < qSize) {
                        javaQ.offer(elem)
                        intQ.offer(elem)
                    }
                } else {
                    if (!javaQ.isEmpty()) {
                        Truth.assertThat(javaQ.poll() as Int).isEqualTo(intQ.poll())
                    }
                }
                Truth.assertThat(javaQ.isEmpty()).isEqualTo(intQ.isEmpty)
                Truth.assertThat(javaQ.size).isEqualTo(intQ.size())
            }
        }
    }
}
