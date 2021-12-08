package com.rahulografy.dsalgokotlin.datastructures.binarysearchtree

import com.google.common.truth.Truth
import com.rahulografy.dsalgokotlin.datastructures.utils.TestUtils.randomIntegerList
import com.rahulografy.dsalgokotlin.datastructures.utils.TestUtils.randomUniformUniqueIntegerList
import com.rahulografy.dsalgokotlin.datastructures.utils.TestUtils.sortedIntegerList
import org.junit.Test
import java.util.Collections
import java.util.PriorityQueue
import java.util.Queue

class SplayTreeTest {

    @Test
    fun getRoot() {
        val splayTree = SplayTree<Int>()
        val data = randomIntegerList(100, MIN, MAX)
        for (i in data) {
            splayTree.insert(i)
            Truth.assertThat(splayTree.root?.getData()).isEqualTo(i)
        }
    }

    @Test
    fun splayInsertDeleteSearch() {
        val splayTree = SplayTree<Int>()
        // Note that we dont want duplicate values here to test "search" after "delete"
        val data = randomUniformUniqueIntegerList(100)
        // Should assertNull
        for (i in data) {
            splayTree.insert(i)
            Truth.assertThat(splayTree.root?.getData()).isEqualTo(i)
        }
        for (i in data) {
            Truth.assertThat(splayTree.search(i)).isNotNull()
        }
        for (i in data) {
            splayTree.delete(i)
            Truth.assertThat(splayTree.search(i)).isNull()
        }
    }

    @Test
    fun insertSearch() {
        val splayTree = SplayTree<Int>()
        val data = randomIntegerList(100, MIN, MAX)
        for (i in data) {
            splayTree.insert(i)
            Truth.assertThat(splayTree.root?.getData()).isEqualTo(i)
        }
    }

    @Test
    fun findMax() {
        val splayTree = SplayTree<Int>()
        val data = sortedIntegerList(-50, 50)
        for (i in data) {
            splayTree.insert(i)
            Truth.assertThat(splayTree.findMax(splayTree.root)).isEqualTo(i)
        }
    }

    /**
     * Comparison With Built In Priority Queue*
     */
    @Test
    fun splayTreePriorityQueueConsistencyTest() {
        val splayTree = SplayTree<Int?>()
        val data = randomUniformUniqueIntegerList(100)
        val pq: Queue<Int?> = PriorityQueue(100, Collections.reverseOrder())

        // insertion
        for (i in data) {
            Truth.assertThat(pq.add(i)).isEqualTo(splayTree.insert(i) != null)
        }
        // searching
        for (i in data) {
            Truth.assertThat(splayTree.search(i)?.getData() == i).isEqualTo(pq.contains(i))
        }
        // findMax & delete
        while (!pq.isEmpty()) {
            val splayTreeMax = splayTree.findMax()
            Truth.assertThat(pq.peek()).isEqualTo(splayTreeMax)
            splayTree.delete(splayTreeMax)
            Truth.assertThat(splayTree.search(splayTreeMax)).isNull()
            pq.remove(splayTreeMax)
            Truth.assertThat(pq.contains(splayTreeMax)).isFalse()
        }
    }

    companion object {
        const val MAX = Int.MAX_VALUE / 4
        const val MIN = Int.MIN_VALUE / 4
    }
}
