package com.rahulografy.dsalgokotlin.datastructures.priorityqueue

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.util.Arrays
import java.util.Collections
import java.util.PriorityQueue
import java.util.Random

class MinIndexedBinaryHeapTest {
    @Before
    fun setup() {
    }

    @Test(expected = IllegalArgumentException::class)
    fun testIllegalSizeOfNegativeOne() {
        MinIndexedBinaryHeap<String>(-1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testIllegalSizeOfZero() {
        MinIndexedBinaryHeap<String>(0)
    }

    @Test
    fun testLegalSize() {
        MinIndexedBinaryHeap<String>(1)
    }

    @Test
    fun testContainsValidKey() {
        val pq = MinIndexedBinaryHeap<String>(10)
        pq.insert(5, "abcdef")
        Truth.assertThat(pq.contains(5)).isTrue()
    }

    @Test
    fun testContainsInvalidKey() {
        val pq = MinIndexedBinaryHeap<String>(10)
        pq.insert(5, "abcdef")
        Truth.assertThat(pq.contains(3)).isFalse()
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDuplicateKeys() {
        val pq = MinIndexedBinaryHeap<String>(10)
        pq.insert(5, "abcdef")
        pq.insert(5, "xyz")
    }

    @Test
    fun testUpdateKeyValue() {
        val pq = MinIndexedBinaryHeap<String>(10)
        pq.insert(5, "abcdef")
        pq.update(5, "xyz")
        Truth.assertThat(pq.valueOf(5)).isEqualTo("xyz")
    }

    @Test
    fun testTestDecreaseKey() {
        val pq = MinIndexedBinaryHeap<Int>(10)
        pq.insert(3, 5)
        pq.decrease(3, 4)
        Truth.assertThat(pq.valueOf(3)).isEqualTo(4)
    }

    @Test
    fun testTestDecreaseKeyNoUpdate() {
        val pq = MinIndexedBinaryHeap<Int>(10)
        pq.insert(3, 5)
        pq.decrease(3, 6)
        Truth.assertThat(pq.valueOf(3)).isEqualTo(5)
    }

    @Test
    fun testTestIncreaseKey() {
        val pq = MinIndexedBinaryHeap<Int>(10)
        pq.insert(3, 5)
        pq.increase(3, 6)
        Truth.assertThat(pq.valueOf(3)).isEqualTo(6)
    }

    @Test
    fun testTestIncreaseKeyNoUpdate() {
        val pq = MinIndexedBinaryHeap<Int>(10)
        pq.insert(3, 5)
        pq.increase(3, 4)
        Truth.assertThat(pq.valueOf(3)).isEqualTo(5)
    }

    @Test
    fun testPeekAndPollMinIndex() {
        // pairs[i][0] is the index
        // pairs[i][1] is the value
        val pairs = arrayOf(
            arrayOf(4, 1),
            arrayOf(7, 5),
            arrayOf(1, 6),
            arrayOf(5, 8),
            arrayOf(3, 7),
            arrayOf(6, 9),
            arrayOf(8, 0),
            arrayOf(2, 4),
            arrayOf(9, 3),
            arrayOf(0, 2)
        )
        sortPairsByValue(pairs)
        val n = pairs.size
        val pq = MinIndexedBinaryHeap<Int>(n)
        for (i in 0 until n) pq.insert(pairs[i][0], pairs[i][1])
        var minIndex: Int
        for (i in 0 until n) {
            minIndex = pq.peekMinKeyIndex()
            Truth.assertThat(minIndex).isEqualTo(pairs[i][0])
            minIndex = pq.pollMinKeyIndex()
            Truth.assertThat(minIndex).isEqualTo(pairs[i][0])
        }
    }

    @Test
    fun testPeekAndPollMinValue() {
        // pairs[i][0] is the index
        // pairs[i][1] is the value
        val pairs = arrayOf(
            arrayOf(4, 1),
            arrayOf(7, 5),
            arrayOf(1, 6),
            arrayOf(5, 8),
            arrayOf(3, 7),
            arrayOf(6, 9),
            arrayOf(8, 0),
            arrayOf(2, 4),
            arrayOf(9, 3),
            arrayOf(0, 2)
        )
        sortPairsByValue(pairs)
        val n = pairs.size
        val pq = MinIndexedBinaryHeap<Int>(n)
        for (i in 0 until n) pq.insert(pairs[i][0], pairs[i][1])
        var minValue: Int?
        for (i in 0 until n) {
            Truth.assertThat(pq.valueOf(pairs[i][0])).isEqualTo(pairs[i][1])
            minValue = pq.peekMinValue()
            Truth.assertThat(minValue).isEqualTo(pairs[i][1])
            minValue = pq.pollMinValue()
            Truth.assertThat(minValue).isEqualTo(pairs[i][1])
        }
    }

    @Test
    fun testInsertionAndValueOf() {
        val names = arrayOf("jackie", "wilson", "catherine", "jason", "bobby", "sia")
        val pq = MinIndexedBinaryHeap<String>(names.size)
        for (i in names.indices) pq.insert(i, names[i])
        for (i in names.indices) Truth.assertThat(pq.valueOf(i)).isEqualTo(names[i])
    }

    @Test
    fun testOperations() {
        val n = 7
        val pq = MinIndexedBinaryHeap<Int>(n)
        pq.insert(4, 4)
        Truth.assertThat(pq.contains(4)).isTrue()
        Truth.assertThat(pq.peekMinValue()).isEqualTo(4)
        Truth.assertThat(pq.peekMinKeyIndex()).isEqualTo(4)
        pq.update(4, 8)
        Truth.assertThat(pq.peekMinValue()).isEqualTo(8)
        Truth.assertThat(pq.pollMinKeyIndex()).isEqualTo(4)
        Truth.assertThat(pq.contains(4)).isFalse()
        pq.insert(3, 99)
        pq.insert(1, 101)
        pq.insert(2, 60)
        Truth.assertThat(pq.peekMinValue()).isEqualTo(60)
        Truth.assertThat(pq.peekMinKeyIndex()).isEqualTo(2)
        pq.increase(2, 150)
        Truth.assertThat(pq.peekMinValue()).isEqualTo(99)
        Truth.assertThat(pq.peekMinKeyIndex()).isEqualTo(3)
        pq.increase(3, 250)
        Truth.assertThat(pq.peekMinValue()).isEqualTo(101)
        Truth.assertThat(pq.peekMinKeyIndex()).isEqualTo(1)
        pq.decrease(3, -500)
        Truth.assertThat(pq.peekMinValue()).isEqualTo(-500)
        Truth.assertThat(pq.peekMinKeyIndex()).isEqualTo(3)
        Truth.assertThat(pq.contains(3)).isTrue()
        pq.delete(3)
        Truth.assertThat(pq.contains(3)).isFalse()
        Truth.assertThat(pq.peekMinValue()).isEqualTo(101)
        Truth.assertThat(pq.peekMinKeyIndex()).isEqualTo(1)
        Truth.assertThat(pq.valueOf(1)).isEqualTo(101)
    }

    @Test
    fun testRandomInsertionsAndPolls() {
        for (n in 1..1499) {
            val bound = 100000
            val randomValues = genRandArray(n, -bound, +bound)
            val pq1 = MinIndexedBinaryHeap<Int>(n)
            val pq2 = PriorityQueue<Int>(n)
            val p = Math.random()
            for (i in 0 until n) {
                pq1.insert(i, randomValues[i])
                pq2.add(randomValues[i])
                if (Math.random() < p) {
                    if (!pq2.isEmpty()) {
                        Truth.assertThat(pq1.pollMinValue()).isEqualTo(pq2.poll())
                    }
                }
                Truth.assertThat(pq1.size()).isEqualTo(pq2.size)
                Truth.assertThat(pq1.isEmpty).isEqualTo(pq2.isEmpty())
                if (!pq2.isEmpty()) Truth.assertThat(pq1.peekMinValue()).isEqualTo(pq2.peek())
            }
        }
    }

    @Test
    fun testRandomInsertionsAndRemovals() {
        for (n in 1..499) {
            val indexes = genUniqueRandList(n)
            val pq1 = MinIndexedBinaryHeap<Int>(n)
            val pq2 = PriorityQueue<Int>(n)
            val indexesToRemove: MutableList<Int> = ArrayList()
            val p = Math.random()
            for (i in 0 until n) {
                val ii = indexes[i]
                pq1.insert(ii, ii)
                pq2.add(ii)
                indexesToRemove.add(ii)
                Truth.assertThat(pq1.isMinHeap).isTrue()
                if (Math.random() < p) {
                    var itemsToRemove = (Math.random() * 10).toInt()
                    while (itemsToRemove-- > 0 && indexesToRemove.size > 0) {
                        val iii = (Math.random() * indexesToRemove.size).toInt()
                        val indexToRemove = indexesToRemove[iii]
                        val contains1 = pq1.contains(indexToRemove)
                        val contains2 = pq2.contains(indexToRemove)
                        Truth.assertThat(contains1).isEqualTo(contains2)
                        Truth.assertThat(pq1.isMinHeap).isTrue()
                        if (contains2) {
                            pq1.delete(indexToRemove)
                            pq2.remove(indexToRemove)
                            indexesToRemove.removeAt(iii)
                        }
                        if (!pq2.isEmpty()) Truth.assertThat(pq1.peekMinValue()).isEqualTo(pq2.peek())
                    }
                }
                for (index in indexesToRemove) {
                    Truth.assertThat(pq2.contains(index)).isTrue() // Sanity check.
                    Truth.assertThat(pq1.contains(index)).isTrue()
                }
                Truth.assertThat(pq1.size()).isEqualTo(pq2.size)
                Truth.assertThat(pq1.isEmpty).isEqualTo(pq2.isEmpty())
                if (!pq2.isEmpty()) Truth.assertThat(pq1.peekMinValue()).isEqualTo(pq2.peek())
            }
        }
    }

    companion object {
        fun genRandArray(n: Int, lo: Int, hi: Int): IntArray {
            return Random().ints(n.toLong(), lo, hi).toArray()
        }

        fun sortPairsByValue(pairs: Array<Array<Int>>?) {
            Arrays.sort(
                pairs
            ) { pair1, pair2 -> pair1[1] - pair2[1] }
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
