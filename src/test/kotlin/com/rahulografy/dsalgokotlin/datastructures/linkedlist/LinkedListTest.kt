package com.rahulografy.dsalgokotlin.datastructures.linkedlist

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.util.Collections
import java.util.LinkedList

class LinkedListTest {

    var list: DoublyLinkedList<Int?>? = null

    @Before
    fun setup() {
        list = DoublyLinkedList()
    }

    @Test
    fun testEmptyList() {
        Truth.assertThat(list?.isEmpty).isTrue()
        Truth.assertThat(list?.size()).isEqualTo(0)
    }

    @Test(expected = Exception::class)
    fun testRemoveFirstOfEmpty() {
        list?.removeFirst()
    }

    @Test(expected = Exception::class)
    fun testRemoveLastOfEmpty() {
        list?.removeLast()
    }

    @Test(expected = Exception::class)
    fun testPeekFirstOfEmpty() {
        list?.peekFirst()
    }

    @Test(expected = Exception::class)
    fun testPeekLastOfEmpty() {
        list?.peekLast()
    }

    @Test
    fun testAddFirst() {
        list?.addFirst(3)
        Truth.assertThat(list?.size()).isEqualTo(1)
        list?.addFirst(5)
        Truth.assertThat(list?.size()).isEqualTo(2)
    }

    @Test
    fun testAddLast() {
        list?.addLast(3)
        Truth.assertThat(list?.size()).isEqualTo(1)
        list?.addLast(5)
        Truth.assertThat(list?.size()).isEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun testAddAt() {
        list?.addAt(0, 1)
        Truth.assertThat(list?.size()).isEqualTo(1)
        list?.addAt(1, 2)
        Truth.assertThat(list?.size()).isEqualTo(2)
        list?.addAt(1, 3)
        Truth.assertThat(list?.size()).isEqualTo(3)
        list?.addAt(2, 4)
        Truth.assertThat(list?.size()).isEqualTo(4)
        list?.addAt(1, 8)
        Truth.assertThat(list?.size()).isEqualTo(5)
    }

    @Test
    fun testRemoveFirst() {
        list?.addFirst(3)
        Truth.assertThat(list?.removeFirst()).isEqualTo(3)
        Truth.assertThat(list?.isEmpty)
    }

    @Test
    fun testRemoveLast() {
        list?.addLast(4)
        Truth.assertThat(list?.removeLast()).isEqualTo(4)
        Truth.assertThat(list?.isEmpty).isTrue()
    }

    @Test
    fun testPeekFirst() {
        list?.addFirst(4)
        Truth.assertThat(list?.peekFirst()).isEqualTo(4)
        Truth.assertThat(list?.size()).isEqualTo(1)
    }

    @Test
    fun testPeekLast() {
        list?.addLast(4)
        Truth.assertThat(list?.peekLast()).isEqualTo(4)
        Truth.assertThat(list?.size()).isEqualTo(1)
    }

    @Test
    fun testPeeking() {
        // 5
        list?.addFirst(5)
        Truth.assertThat(list?.peekFirst()).isEqualTo(5)
        Truth.assertThat(list?.peekLast()).isEqualTo(5)

        // 6 - 5
        list?.addFirst(6)
        Truth.assertThat(list?.peekFirst()).isEqualTo(6)
        Truth.assertThat(list?.peekLast()).isEqualTo(5)

        // 7 - 6 - 5
        list?.addFirst(7)
        Truth.assertThat(list?.peekFirst()).isEqualTo(7)
        Truth.assertThat(list?.peekLast()).isEqualTo(5)

        // 7 - 6 - 5 - 8
        list?.addLast(8)
        Truth.assertThat(list?.peekFirst()).isEqualTo(7)
        Truth.assertThat(list?.peekLast()).isEqualTo(8)

        // 7 - 6 - 5
        list?.removeLast()
        Truth.assertThat(list?.peekFirst()).isEqualTo(7)
        Truth.assertThat(list?.peekLast()).isEqualTo(5)

        // 7 - 6
        list?.removeLast()
        Truth.assertThat(list?.peekFirst()).isEqualTo(7)
        Truth.assertThat(list?.peekLast()).isEqualTo(6)

        // 6
        list?.removeFirst()
        Truth.assertThat(list?.peekFirst()).isEqualTo(6)
        Truth.assertThat(list?.peekLast()).isEqualTo(6)
    }

    @Test
    fun testRemoving() {
        val strs = DoublyLinkedList<String>()
        strs.add("a")
        strs.add("b")
        strs.add("c")
        strs.add("d")
        strs.add("e")
        strs.add("f")
        strs.remove("b")
        strs.remove("a")
        strs.remove("d")
        strs.remove("e")
        strs.remove("c")
        strs.remove("f")
        Truth.assertThat(strs.size()).isEqualTo(0)
    }

    @Test
    fun testRemoveAt() {
        list?.add(1)
        list?.add(2)
        list?.add(3)
        list?.add(4)
        list?.removeAt(0)
        list?.removeAt(2)
        Truth.assertThat(list?.peekFirst()).isEqualTo(2)
        Truth.assertThat(list?.peekLast()).isEqualTo(3)
        list?.removeAt(1)
        list?.removeAt(0)
        Truth.assertThat(list?.size()).isEqualTo(0)
    }

    @Test
    fun testClear() {
        list?.add(22)
        list?.add(33)
        list?.add(44)
        Truth.assertThat(list?.size()).isEqualTo(3)
        list?.clear()
        Truth.assertThat(list?.size()).isEqualTo(0)
        list?.add(22)
        list?.add(33)
        list?.add(44)
        Truth.assertThat(list?.size()).isEqualTo(3)
        list?.clear()
        Truth.assertThat(list?.size()).isEqualTo(0)
    }

    @Test
    fun testRandomizedRemoving() {
        val javaLinkedList = LinkedList<Int?>()
        for (loops in 0 until LOOPS) {
            list?.clear()
            javaLinkedList.clear()
            val randNums = genRandList(TEST_SZ)
            for (value in randNums) {
                javaLinkedList.add(value)
                list?.add(value)
            }
            Collections.shuffle(randNums)
            for (i in randNums.indices) {
                val rm_val = randNums[i]
                Truth.assertThat(javaLinkedList.remove(rm_val)).isEqualTo(list?.remove(rm_val))
                Truth.assertThat(javaLinkedList.size).isEqualTo(list?.size())
                var iter1 = javaLinkedList.iterator()
                var iter2 = list?.iterator()
                while (iter1.hasNext()) Truth.assertThat(iter1.next()).isEqualTo(iter2?.next())
                iter1 = javaLinkedList.iterator()
                iter2 = list?.iterator()
                while (iter1.hasNext()) Truth.assertThat(iter1.next()).isEqualTo(iter2?.next())
            }
            list?.clear()
            javaLinkedList.clear()
            for (value in randNums) {
                javaLinkedList.add(value)
                list?.add(value)
            }

            // Try removing elements whether or not they exist
            for (i in randNums.indices) {
                val rm_val = (MAX_RAND_NUM * Math.random()).toInt()
                Truth
                    .assertThat(javaLinkedList.remove(rm_val))
                    .isEqualTo(list?.remove(rm_val))
                Truth
                    .assertThat(javaLinkedList.size)
                    .isEqualTo(list?.size())
                val iter1 = javaLinkedList.iterator()
                val iter2 = list?.iterator()
                while (iter1.hasNext()) {
                    Truth
                        .assertThat(iter1.next())
                        .isEqualTo(iter2?.next())
                }
            }
        }
    }

    @Test
    fun testRandomizedRemoveAt() {
        val javaLinkedList = LinkedList<Int?>()
        for (loops in 0 until LOOPS) {
            list?.clear()
            javaLinkedList.clear()
            val randNums = genRandList(TEST_SZ)
            for (value in randNums) {
                javaLinkedList.add(value)
                list?.add(value)
            }
            for (i in randNums.indices) {
                val rm_index = (list?.size()?.times(Math.random()))?.toInt() ?: 0
                val num1 = javaLinkedList.removeAt(rm_index)
                val num2 = list?.removeAt(rm_index)
                Truth.assertThat(num1).isEqualTo(num2)
                Truth.assertThat(javaLinkedList.size).isEqualTo(list?.size())
                val iter1 = javaLinkedList.iterator()
                val iter2 = list?.iterator()
                while (iter1.hasNext()) {
                    Truth
                        .assertThat(iter1.next())
                        .isEqualTo(iter2?.next())
                }
            }
        }
    }

    @Test
    fun testRandomizedIndexOf() {
        val javaLinkedList = LinkedList<Int?>()
        for (loops in 0 until LOOPS) {
            javaLinkedList.clear()
            list?.clear()
            val randNums = genUniqueRandList(TEST_SZ)
            for (value in randNums) {
                javaLinkedList.add(value)
                list?.add(value)
            }
            Collections.shuffle(randNums)
            for (i in randNums.indices) {
                val elem = randNums[i]
                val index1 = javaLinkedList.indexOf(elem)
                val index2 = list?.indexOf(elem)
                Truth.assertThat(index1).isEqualTo(index2)
                Truth.assertThat(javaLinkedList.size).isEqualTo(list?.size())
                val iter1 = javaLinkedList.iterator()
                val iter2 = list?.iterator()
                while (iter1.hasNext()) {
                    Truth
                        .assertThat(iter1.next())
                        .isEqualTo(iter2?.next())
                }
            }
        }
    }

    @Test
    fun testToString() {
        val strs = DoublyLinkedList<String>()
        Truth.assertThat(strs.toString()).isEqualTo("[  ]")
        strs.add("a")
        Truth.assertThat(strs.toString()).isEqualTo("[ a ]")
        strs.add("b")
        Truth.assertThat(strs.toString()).isEqualTo("[ a, b ]")
        strs.add("c")
        strs.add("d")
        strs.add("e")
        strs.add("f")
        Truth.assertThat(strs.toString()).isEqualTo("[ a, b, c, d, e, f ]")
    }

    companion object {
        private const val LOOPS = 10000
        private const val TEST_SZ = 40
        private const val NUM_NULLS = TEST_SZ / 5
        private const val MAX_RAND_NUM = 250

        // Generate a list of random numbers
        fun genRandList(size: Int): List<Int?> {
            val list: MutableList<Int?> = ArrayList(size)
            for (i in 0 until size) list.add((Math.random() * MAX_RAND_NUM).toInt())
            for (i in 0 until NUM_NULLS) list.add(null)
            list.shuffle()
            return list
        }

        // Generate a list of unique random numbers
        fun genUniqueRandList(size: Int): List<Int?> {
            val list: MutableList<Int?> = ArrayList(size)
            for (i in 0 until size) list.add(i)
            for (i in 0 until NUM_NULLS) list.add(null)
            list.shuffle()
            return list
        }
    }
}
