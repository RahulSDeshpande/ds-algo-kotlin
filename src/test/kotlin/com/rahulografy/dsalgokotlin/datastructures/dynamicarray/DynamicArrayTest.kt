package com.rahulografy.dsalgokotlin.datastructures.dynamicarray

import com.google.common.truth.Truth
import org.junit.Test

class DynamicArrayTest {
    @Test
    fun testEmptyList() {
        val list = DynamicArray<Int>()
        Truth.assertThat(list.isEmpty).isTrue()
    }

    @Test(expected = Exception::class)
    fun testRemovingEmpty() {
        val list = DynamicArray<Int>()
        list.removeAt(0)
    }

    @Test(expected = Exception::class)
    fun testIndexOutOfBounds() {
        val list = DynamicArray<Int>()
        list.add(-56)
        list.add(-53)
        list.add(-55)
        list.removeAt(3)
    }

    @Test(expected = Exception::class)
    fun testIndexOutOfBounds2() {
        val list = DynamicArray<Int>()
        for (i in 0..999) list.add(789)
        list.removeAt(1000)
    }

    @Test(expected = Exception::class)
    fun testIndexOutOfBounds3() {
        val list = DynamicArray<Int>()
        for (i in 0..999) list.add(789)
        list.removeAt(-1)
    }

    @Test(expected = Exception::class)
    fun testIndexOutOfBounds4() {
        val list = DynamicArray<Int>()
        for (i in 0..14) list.add(123)
        list.removeAt(-66)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testIndexOutOfBounds5() {
        val list = DynamicArray<Int>()
        for (i in 0..9) list.add(12)
        list[-1] = 3
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testIndexOutOfBounds6() {
        val list = DynamicArray<Int>()
        for (i in 0..9) list.add(12)
        list[10] = 3
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testIndexOutOfBounds7() {
        val list = DynamicArray<Int>()
        for (i in 0..9) list.add(12)
        list[15] = 3
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testIndexOutOfBounds8() {
        val list = DynamicArray<Int>()
        for (i in 0..9) list.add(12)
        list[-2]
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testIndexOutOfBounds9() {
        val list = DynamicArray<Int>()
        for (i in 0..9) list.add(12)
        list[10]
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testIndexOutOfBounds10() {
        val list = DynamicArray<Int>()
        for (i in 0..9) list.add(12)
        list[15]
    }

    @Test
    fun testRemoving() {
        val list = DynamicArray<String?>()
        val stringList = arrayOf("a", "b", "c", "d", "e", null, "g", "h")
        for (s in stringList) list.add(s)
        var ret = list.remove("c")
        Truth.assertThat(ret).isTrue()
        ret = list.remove("c")
        Truth.assertThat(ret).isFalse()
        ret = list.remove("h")
        Truth.assertThat(ret).isTrue()
        ret = list.remove(null)
        Truth.assertThat(ret).isTrue()
        ret = list.remove("a")
        Truth.assertThat(ret).isTrue()
        ret = list.remove("a")
        Truth.assertThat(ret).isFalse()
        ret = list.remove("h")
        Truth.assertThat(ret).isFalse()
        ret = list.remove(null)
        Truth.assertThat(ret).isFalse()
    }

    @Test
    fun testRemoving2() {
        val list = DynamicArray<String>()
        val stringList = arrayOf("a", "b", "c", "d")
        for (s in stringList) list.add(s)
        Truth.assertThat(list.remove("a")).isTrue()
        Truth.assertThat(list.remove("b")).isTrue()
        Truth.assertThat(list.remove("c")).isTrue()
        Truth.assertThat(list.remove("d")).isTrue()
        Truth.assertThat(list.remove("a")).isFalse()
        Truth.assertThat(list.remove("b")).isFalse()
        Truth.assertThat(list.remove("c")).isFalse()
        Truth.assertThat(list.remove("d")).isFalse()
    }

    @Test
    fun testIndexOfNullElement() {
        val list = DynamicArray<String?>()
        val stringList = arrayOf("a", "b", null, "d")
        for (s in stringList) list.add(s)
        Truth.assertThat(list.indexOf(null)).isEqualTo(2)
    }

    @Test
    fun testAddingElements() {
        val list = DynamicArray<Int>()
        val elements = intArrayOf(1, 2, 3, 4, 5, 6, 7)
        for (i in elements.indices) list.add(elements[i])
        for (i in elements.indices) Truth.assertThat(list[i]).isEqualTo(elements[i])
    }

    @Test
    fun testAddAndRemove() {
        val list = DynamicArray<Long>(0)
        for (i in 0..54) list.add(44L)
        for (i in 0..54) list.remove(44L)
        Truth.assertThat(list.isEmpty).isTrue()
        for (i in 0..54) list.add(44L)
        for (i in 0..54) list.removeAt(0)
        Truth.assertThat(list.isEmpty).isTrue()
        for (i in 0..154) list.add(44L)
        for (i in 0..154) list.remove(44L)
        Truth.assertThat(list.isEmpty).isTrue()
        for (i in 0..154) list.add(44L)
        for (i in 0..154) list.removeAt(0)
        Truth.assertThat(list.isEmpty).isTrue()
    }

    @Test
    fun testAddSetRemove() {
        val list = DynamicArray<Long>(0)
        for (i in 0..54) list.add(44L)
        for (i in 0..54) list[i] = 33L
        for (i in 0..54) list.remove(33L)
        Truth.assertThat(list.isEmpty).isTrue()
        for (i in 0..54) list.add(44L)
        for (i in 0..54) list[i] = 33L
        for (i in 0..54) list.removeAt(0)
        Truth.assertThat(list.isEmpty).isTrue()
        for (i in 0..154) list.add(44L)
        for (i in 0..154) list[i] = 33L
        for (i in 0..154) list.remove(33L)
        Truth.assertThat(list.isEmpty).isTrue()
        for (i in 0..154) list.add(44L)
        for (i in 0..154) list.removeAt(0)
        Truth.assertThat(list.isEmpty).isTrue()
    }

    @Test
    fun testSize() {
        val list = DynamicArray<Int?>()
        val elements = arrayOf(-76, 45, 66, 3, null, 54, 33)
        var i = 0
        var sz = 1
        while (i < elements.size) {
            list.add(elements[i])
            Truth.assertThat(list.size()).isEqualTo(sz)
            i++
            sz++
        }
    }
}
