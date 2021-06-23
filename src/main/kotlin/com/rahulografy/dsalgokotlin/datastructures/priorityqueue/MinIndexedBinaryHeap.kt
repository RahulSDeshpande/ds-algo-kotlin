package com.rahulografy.dsalgokotlin.datastructures.priorityqueue

/**
 * An implementation of an indexed binary heap priority queue.
 *
 *
 * This implementation supports arbitrary keys with comparable values. To use arbitrary keys
 * (such as strings or objects) first map all your keys to the integer domain [0, N) where N is the
 * number of keys you have and then use the mapping with this indexed priority queue.
 *
 *
 * As convention, I denote 'ki' as the index value in the domain [0, N) associated with key k,
 * therefore: ki = map[k]
 *
 * @author Rahul S Deshpande
 */
class MinIndexedBinaryHeap<T : Comparable<T>?>(maxSize: Int) : MinIndexedDHeap<T>(2, maxSize)
