package com.rahulografy.dsalgokotlin.datastructures.utils

object TestUtils {

    /**
     * Generates a list of random values where every number is between
     * [min, max) and there are possible repeats.
     */
    fun randomIntegerList(sz: Int, min: Int, max: Int): List<Int> {
        val list: MutableList<Int> = ArrayList(sz)
        for (i in 0 until sz) list.add(randInt(min, max))
        return list
    }

    /**
     * Generates a list of shuffled values where every number in the
     * array is in the range of [0, sz)
     */
    fun randomUniformUniqueIntegerList(sz: Int): List<Int> {
        val list: MutableList<Int> = ArrayList(sz)
        for (i in 0 until sz) list.add(i)
        list.shuffle()
        return list
    }

    fun randomUniformUniqueIntegerList(min: Int, max: Int): List<Int> {
        val list: MutableList<Int> = ArrayList(max - min)
        for (i in min until max) list.add(i)
        list.shuffle()
        return list
    }

    /**
     * Generates a random int between [min, max)
     */
    fun randInt(min: Int, max: Int): Int {
        return min + (Math.random() * (max - min)).toInt()
    }

    /**
     * Generates a random long between [min, max)
     */
    fun randLong(min: Long, max: Long): Long {
        return min + (Math.random() * (max - min)).toLong()
    }

    /**
     * Generates sorted data in the range of [min,max[
     */
    fun sortedIntegerList(min: Int, max: Int): List<Int> {
        val list: MutableList<Int> = ArrayList(max - min)
        for (i in min until max) list.add(i)
        return list
    }
}
