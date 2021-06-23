package com.rahulografy.dsalgokotlin.datastructures.priorityqueue

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.util.Collections
import java.util.PriorityQueue

class BinaryHeapQuickRemovalsTest {
    @Before
    fun setup() {
    }

    @Test
    fun testEmpty() {
        val q = BinaryHeapQuickRemovals<Int>()
        Truth.assertThat(q.size()).isEqualTo(0)
        Truth.assertThat(q.isEmpty).isTrue()
        Truth.assertThat(q.poll()).isNull()
        Truth.assertThat(q.peek()).isNull()
    }

    @Test
    fun testHeapProperty() {
        var q = BinaryHeapQuickRemovals<Int>()
        val nums = arrayOf(3, 2, 5, 6, 7, 9, 4, 8, 1)

        // Try manually creating heap
        for (n in nums) q.add(n)
        for (i in 1..9) Truth.assertThat(q.poll()).isEqualTo(i)
        q.clear()

        // Try heapify constructor
        q = BinaryHeapQuickRemovals(nums)
        for (i in 1..9) Truth.assertThat(q.poll()).isEqualTo(i)
    }

    @Test
    fun testHeapify() {
        for (i in 1 until LOOPS) {
            val lst = genRandArray(i)
            val pq = BinaryHeapQuickRemovals(lst)
            val pq2 = PriorityQueue<Int>(i)
            for (x in lst) pq2.add(x)
            Truth.assertThat(pq.isMinHeap(0)).isTrue()
            while (!pq2.isEmpty()) {
                Truth.assertThat(pq.poll()).isEqualTo(pq2.poll())
            }
        }
    }

    @Test
    fun testClear() {
        val q: BinaryHeapQuickRemovals<String>
        val strs = arrayOf("aa", "bb", "cc", "dd", "ee")
        q = BinaryHeapQuickRemovals(strs)
        q.clear()
        Truth.assertThat(q.size()).isEqualTo(0)
        Truth.assertThat(q.isEmpty).isTrue()
    }

    @Test
    fun testContainment() {
        val strs = arrayOf("aa", "bb", "cc", "dd", "ee")
        val q = BinaryHeapQuickRemovals(strs)
        q.remove("aa")
        Truth.assertThat(q.contains("aa")).isFalse()
        q.remove("bb")
        Truth.assertThat(q.contains("bb")).isFalse()
        q.remove("cc")
        Truth.assertThat(q.contains("cc")).isFalse()
        q.remove("dd")
        Truth.assertThat(q.contains("dd")).isFalse()
        q.clear()
        Truth.assertThat(q.contains("ee")).isFalse()
    }

    @Test
    fun testContainmentRandomized() {
        for (i in 0 until LOOPS) {
            val randNums = genRandList(100)
            val PQ = PriorityQueue<Int>()
            val pq = BinaryHeapQuickRemovals<Int>()
            for (j in randNums.indices) {
                pq.add(randNums[j])
                PQ.add(randNums[j])
            }
            for (j in randNums.indices) {
                val randVal = randNums[j]
                Truth.assertThat(pq.contains(randVal)).isEqualTo(PQ.contains(randVal))
                pq.remove(randVal)
                PQ.remove(randVal)
                Truth.assertThat(pq.contains(randVal)).isEqualTo(PQ.contains(randVal))
            }
        }
    }

    fun sequentialRemoving(`in`: Array<Int>, removeOrder: Array<Int>) {
        Truth.assertThat(`in`.size).isEqualTo(removeOrder.size)
        val pq = BinaryHeapQuickRemovals(`in`)
        val PQ = PriorityQueue<Int>()
        for (value in `in`) PQ.offer(value)
        Truth.assertThat(pq.isMinHeap(0)).isTrue()
        for (i in removeOrder.indices) {
            val elem = removeOrder[i]
            Truth.assertThat(pq.peek()).isEqualTo(PQ.peek())
            Truth.assertThat(pq.remove(elem)).isEqualTo(PQ.remove(elem))
            Truth.assertThat(pq.size()).isEqualTo(PQ.size)
            Truth.assertThat(pq.isMinHeap(0)).isTrue()
        }
        Truth.assertThat(pq.isEmpty).isTrue()
    }

    @Test
    fun testRemoving() {
        var `in` = arrayOf(1, 2, 3, 4, 5, 6, 7)
        var removeOrder = arrayOf(1, 3, 6, 4, 5, 7, 2)
        sequentialRemoving(`in`, removeOrder)
        `in` = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
        removeOrder = arrayOf(7, 4, 6, 10, 2, 5, 11, 3, 1, 8, 9)
        sequentialRemoving(`in`, removeOrder)
        `in` = arrayOf(8, 1, 3, 3, 5, 3)
        removeOrder = arrayOf(3, 3, 5, 8, 1, 3)
        sequentialRemoving(`in`, removeOrder)
        `in` = arrayOf(7, 7, 3, 1, 1, 2)
        removeOrder = arrayOf(2, 7, 1, 3, 7, 1)
        sequentialRemoving(`in`, removeOrder)
        `in` = arrayOf(32, 66, 93, 42, 41, 91, 54, 64, 9, 35)
        removeOrder = arrayOf(64, 93, 54, 41, 35, 9, 66, 42, 32, 91)
        sequentialRemoving(`in`, removeOrder)
    }

    @Test
    fun testRemovingDuplicates() {
        val `in` = arrayOf(2, 7, 2, 11, 7, 13, 2)
        val pq = BinaryHeapQuickRemovals(`in`)
        Truth.assertThat(pq.peek()).isEqualTo(2)
        pq.add(3)
        Truth.assertThat(pq.poll()).isEqualTo(2)
        Truth.assertThat(pq.poll()).isEqualTo(2)
        Truth.assertThat(pq.poll()).isEqualTo(2)
        Truth.assertThat(pq.poll()).isEqualTo(3)
        Truth.assertThat(pq.poll()).isEqualTo(7)
        Truth.assertThat(pq.poll()).isEqualTo(7)
        Truth.assertThat(pq.poll()).isEqualTo(11)
        Truth.assertThat(pq.poll()).isEqualTo(13)
    }

    @Test
    fun testRandomizedPolling() {
        for (i in 0 until LOOPS) {
            val randNums = genRandList(i)
            val pq1 = PriorityQueue<Int>()
            val pq2 = BinaryHeapQuickRemovals<Int>()

            // Add all the elements to both priority queues
            for (value in randNums) {
                pq1.offer(value)
                pq2.add(value)
            }
            while (!pq1.isEmpty()) {
                Truth.assertThat(pq2.isMinHeap(0)).isTrue()
                Truth.assertThat(pq1.size).isEqualTo(pq2.size())
                Truth.assertThat(pq1.peek()).isEqualTo(pq2.peek())
                Truth.assertThat(pq1.contains(pq1.peek())).isEqualTo(pq2.contains(pq2.peek()))
                val v1 = pq1.poll()
                val v2 = pq2.poll()
                Truth.assertThat(v1).isEqualTo(v2)
                Truth.assertThat(pq1.peek()).isEqualTo(pq2.peek())
                Truth.assertThat(pq1.size).isEqualTo(pq2.size())
                Truth.assertThat(pq2.isMinHeap(0)).isTrue()
            }
        }
    }

    @Test
    fun testRandomizedRemoving() {
        for (i in 0 until LOOPS) {
            val randNums = genRandList(i)
            val pq1 = PriorityQueue<Int>()
            val pq2 = BinaryHeapQuickRemovals<Int>()

            // Add all the elements to both priority queues
            for (value in randNums) {
                pq1.offer(value)
                pq2.add(value)
            }
            Collections.shuffle(randNums)
            var index = 0
            while (!pq1.isEmpty()) {
                val removeNum = randNums[index++]
                Truth.assertThat(pq2.isMinHeap(0)).isTrue()
                Truth.assertThat(pq1.size).isEqualTo(pq2.size())
                Truth.assertThat(pq1.peek()).isEqualTo(pq2.peek())
                pq1.remove(removeNum)
                pq2.remove(removeNum)
                Truth.assertThat(pq1.peek()).isEqualTo(pq2.peek())
                Truth.assertThat(pq1.size).isEqualTo(pq2.size())
                Truth.assertThat(pq2.isMinHeap(0)).isTrue()
            }
        }
    }

    @Test
    fun testPQReusability() {
        val SZs = genUniqueRandList(LOOPS)
        val PQ = PriorityQueue<Int>()
        val pq = BinaryHeapQuickRemovals<Int>()
        for (sz in SZs) {
            pq.clear()
            PQ.clear()
            val nums = genRandList(sz)
            for (n in nums) {
                pq.add(n)
                PQ.add(n)
            }
            Collections.shuffle(nums)
            for (i in 0 until sz / 2) {

                // Sometimes add a new number into the BinaryHeapQuickRemovals
                if (0.25 < Math.random()) {
                    val randNum = (Math.random() * 10000).toInt()
                    PQ.add(randNum)
                    pq.add(randNum)
                }
                val removeNum = nums[i]
                Truth.assertThat(pq.isMinHeap(0)).isTrue()
                Truth.assertThat(PQ.size).isEqualTo(pq.size())
                Truth.assertThat(PQ.peek()).isEqualTo(pq.peek())
                PQ.remove(removeNum)
                pq.remove(removeNum)
                Truth.assertThat(PQ.peek()).isEqualTo(pq.peek())
                Truth.assertThat(PQ.size).isEqualTo(pq.size())
                Truth.assertThat(pq.isMinHeap(0)).isTrue()
            }
        }
    }

    companion object {
        const val LOOPS = 100
        const val MAX_SZ = 100
        fun genRandArray(sz: Int): Array<Int?> {
            val lst = arrayOfNulls<Int>(sz)
            for (i in 0 until sz) lst[i] = (Math.random() * MAX_SZ).toInt()
            return lst
        }

        // Generate a list of random numbers
        fun genRandList(sz: Int): List<Int> {
            val lst: MutableList<Int> = ArrayList(sz)
            for (i in 0 until sz) lst.add((Math.random() * MAX_SZ).toInt())
            return lst
        }

        // Generate a list of unique random numbers
        fun genUniqueRandList(sz: Int): List<Int> {
            val lst: MutableList<Int> = ArrayList(sz)
            for (i in 0 until sz) lst.add(i)
            Collections.shuffle(lst)
            return lst
        }
    }
}
