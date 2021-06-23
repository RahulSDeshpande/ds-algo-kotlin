package com.rahulografy.dsalgokotlin.datastructures.set

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.util.Objects

// You can set the hash value of this object to be whatever you want
// This makes it great for testing special cases.
class ConstObj(var hash: Int, var data: Int) {
    override fun hashCode(): Int {
        return hash
    }

    override fun equals(o: Any?): Boolean {
        return data == (o as ConstObj?)!!.data
    }
}

class HSetTest {
    var hSet: HSet<Int>? = null

    @Before
    fun setup() {
        hSet = HSet()
    }

    @Test
    fun testAddRemove() {
        hSet!!.add(5)
        Truth.assertThat(hSet!!.size()).isEqualTo(1)
        hSet!!.remove(5)
        Truth.assertThat(hSet!!.size()).isEqualTo(0)
        hSet!!.add(0)
        Truth.assertThat(hSet!!.size()).isEqualTo(1)
        hSet!!.remove(0)
        Truth.assertThat(hSet!!.size()).isEqualTo(0)
        hSet!!.add(-5)
        Truth.assertThat(hSet!!.size()).isEqualTo(1)
        hSet!!.remove(-5)
        Truth.assertThat(hSet!!.size()).isEqualTo(0)
    }

    @Test
    fun randomizedSetTest() {
        val s = HashSet<Int>()
        for (loop in 0 until LOOPS) {
            s.clear()
            hSet!!.clear()
            val nums = genRandList(TEST_SIZE)
            for (i in 0 until TEST_SIZE) {
                val num = nums[i]
                // assertThat(hs.add(num)).isEqualTo(s.add(num));
                hSet!!.add(num)
                s.add(num)

                // Make sure this is a bijection
                for (n in s) hSet!!.contains(n)
                for (n in hSet!!) s.contains(n)
                Truth.assertThat(s.size).isEqualTo(hSet!!.size())
            }
        }
    }

    @Test
    fun randomizedSetTest2() {
        val s = HashSet<ConstObj>()
        for (loop in 0 until LOOPS) {
            val size = (Math.random() * TEST_SIZE).toInt() + 1
            val hs = HSet<ConstObj>(size)
            s.clear()
            val nums = genRandList(TEST_SIZE)
            for (i in 0 until TEST_SIZE) {
                val num = nums[i]
                val obj = ConstObj(Objects.hash(num), num)
                // assertThat(hs.add(num)).isEqualTo(s.add(num));
                hs.add(obj)
                s.add(obj)

                // Make sure this is a bijection
                for (n in s) hs.contains(n)
                for (n in hs) s.contains(n)
                Truth.assertThat(s.size).isEqualTo(hs.size())
            }
        }
    }

    @Test
    fun t() {
        val ch1 = ConstObj(29827, 1)
        val ch2 = ConstObj(29807, 3)
        val s = HSet<ConstObj>()
        s.add(ch1)
        Truth.assertThat(s.size()).isEqualTo(1)
        s.remove(ch1)
        Truth.assertThat(s.size()).isEqualTo(0)
        s.add(ch2)
        Truth.assertThat(s.size()).isEqualTo(1)
        s.remove(ch2)
        Truth.assertThat(s.size()).isEqualTo(0)
    }

    companion object {
        const val LOOPS = 100
        const val TEST_SIZE = 1000
        const val MAX_RAND_NUM = 50000

        // Generate a list of random numbers
        fun genRandList(size: Int): List<Int> {
            val lst: MutableList<Int> = ArrayList(size)
            for (i in 0 until size) lst.add((Math.random() * MAX_RAND_NUM).toInt())
            lst.shuffle()
            return lst
        }

        // Generate a list of unique random numbers
        fun genUniqueRandList(size: Int): List<Int?> {
            val lst: MutableList<Int?> = ArrayList(size)
            for (i in 0 until size) lst.add(i)
            lst.shuffle()
            return lst
        }
    }
}
