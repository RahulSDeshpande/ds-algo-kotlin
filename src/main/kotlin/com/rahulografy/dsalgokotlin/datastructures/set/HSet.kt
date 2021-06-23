package com.rahulografy.dsalgokotlin.datastructures.set

class HSet<T> @JvmOverloads constructor(
    capacity: Int = DEFAULT_CAPACITY,
    loadFactor: Float = DEFAULT_LOAD_FACTOR
) : Iterable<T> {

    private val map: MutableMap<T, Any> = HashMap(capacity, loadFactor)

    fun size() = map.size

    fun clear() {
        map.clear()
    }

    val isEmpty: Boolean
        get() = map.isEmpty()

    fun add(elem: T) = map.put(elem, DUMMY) === DUMMY

    fun remove(elem: T) = map.remove(elem) === DUMMY

    operator fun contains(elem: T) = map.containsKey(elem)

    override fun iterator() = map.keys.iterator()

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("[")
        for (key in map.keys) sb.append(key.toString() + ", ")
        sb.append("]")
        return sb.toString()
    }

    companion object {
        private val DUMMY = Any()
        private const val DEFAULT_CAPACITY = 3
        private const val DEFAULT_LOAD_FACTOR = 0.75f
    }
}
