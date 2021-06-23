package com.rahulografy.dsalgokotlin.datastructures.queue

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class QueueTest {

    private val queues: MutableList<Queue<Int>> = ArrayList()

    @Before
    fun setup() {
        queues.add(ArrayQueue(2))
        queues.add(LinkedQueue())
        queues.add(IntQueue(2))
    }

    @Test
    fun testEmptyQueue() {
        for (queue in queues) {
            Truth.assertThat(queue.isEmpty).isTrue()
            Truth.assertThat(queue.size()).isEqualTo(0)
        }
    }

    @Test(expected = Exception::class)
    fun testPollOnEmpty() {
        for (queue in queues) {
            queue.poll()
        }
    }

    @Test(expected = Exception::class)
    fun testPeekOnEmpty() {
        for (queue in queues) {
            queue.peek()
        }
    }

    @Test
    fun testOffer() {
        for (queue in queues) {
            queue.offer(2)
            Truth.assertThat(queue.size()).isEqualTo(1)
        }
    }

    @Test
    fun testPeek() {
        for (queue in queues) {
            queue.offer(2)
            Truth.assertThat(queue.peek()).isEqualTo(2)
            Truth.assertThat(queue.size()).isEqualTo(1)
        }
    }

    @Test
    fun testPoll() {
        for (queue in queues) {
            queue.offer(2)
            Truth.assertThat(queue.poll()).isEqualTo(2)
            Truth.assertThat(queue.size()).isEqualTo(0)
        }
    }

    @Test
    fun testExhaustively() {
        for (queue in queues) {
            Truth.assertThat(queue.isEmpty).isTrue()
            queue.offer(1)
            Truth.assertThat(queue.isEmpty).isFalse()
            queue.offer(2)
            Truth.assertThat(queue.size()).isEqualTo(2)
            Truth.assertThat(queue.peek()).isEqualTo(1)
            Truth.assertThat(queue.size()).isEqualTo(2)
            Truth.assertThat(queue.poll()).isEqualTo(1)
            Truth.assertThat(queue.size()).isEqualTo(1)
            Truth.assertThat(queue.peek()).isEqualTo(2)
            Truth.assertThat(queue.size()).isEqualTo(1)
            Truth.assertThat(queue.poll()).isEqualTo(2)
            Truth.assertThat(queue.size()).isEqualTo(0)
            Truth.assertThat(queue.isEmpty).isTrue()
        }
    }
}
