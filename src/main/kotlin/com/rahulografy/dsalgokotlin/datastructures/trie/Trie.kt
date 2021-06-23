package com.rahulografy.dsalgokotlin.datastructures.trie

class Trie {

    // The root character is an arbitrarily picked
    // character chosen for the root node.
    private val rootCharacter = '\u0000'
    private var root = Node(rootCharacter)

    private class Node(var char: Char) {
        var count = 0
        var isWordEnding = false
        var children: MutableMap<Char, Node>? = HashMap()
        fun addChild(node: Node, c: Char) {
            children!![c] = node
        }
    }

    // Returns true if the string being inserted
    // contains a prefix already in the trie
    // Returns true if the string being inserted
    // contains a prefix already in the trie
    @JvmOverloads
    fun insert(key: String?, numInserts: Int = 1): Boolean {
        requireNotNull(key) { "Null not permitted in trie" }
        require(numInserts > 0) { "numInserts has to be greater than zero" }
        var node = root
        var createdNewNode = false
        var isPrefix = false

        // Process each character one at a time
        for (element in key) {
            val ch = element
            var nextNode = node.children!![ch]

            // The next character in this string does not yet exist in trie
            if (nextNode == null) {
                nextNode = Node(ch)
                node.addChild(nextNode, ch)
                createdNewNode = true

                // Next character exists in trie.
            } else {
                if (nextNode.isWordEnding) isPrefix = true
            }
            node = nextNode
            node.count += numInserts
        }

        // The root itself is not a word ending. It is simply a placeholder.
        if (node !== root) node.isWordEnding = true
        return isPrefix || !createdNewNode
    }

    // This delete function allows you to delete keys from the trie
    // (even those which were not previously inserted into the trie).
    // This means that it may be the case that you delete a prefix which
    // cuts off the access to numerous other strings starting with
    // that prefix.
    @JvmOverloads
    fun delete(key: String, numDeletions: Int = 1): Boolean {

        // We cannot delete something that doesn't exist
        if (!contains(key)) return false
        require(numDeletions > 0) { "numDeletions has to be positive" }
        var node: Node? = root
        for (i in 0 until key.length) {
            val ch = key[i]
            var curNode = node!!.children!![ch]
            curNode!!.count -= numDeletions

            // Cut this edge if the current node has a count <= 0
            // This means that all the prefixes below this point are inaccessible
            if (curNode!!.count <= 0) {
                node.children!!.remove(ch)
                curNode.children = null
                curNode = null
                return true
            }
            node = curNode
        }
        return true
    }

    // Returns true if this string is contained inside the trie
    operator fun contains(key: String?): Boolean {
        return count(key) != 0
    }

    // Returns the count of a particular prefix
    fun count(key: String?): Int {
        requireNotNull(key) { "Null not permitted" }
        var node: Node? = root

        // Dig down into trie until we reach the bottom or stop
        // early because the string we're looking for doesn't exist
        for (i in 0 until key.length) {
            val ch = key[i]
            if (node == null) return 0
            node = node.children!![ch]
        }
        return node?.count ?: 0
    }

    // Recursively clear the trie freeing memory to help GC
    private fun clear(node: Node?) {
        if (node == null) return
        for (ch in node.children!!.keys) {
            var nextNode = node.children!![ch]
            clear(nextNode)
            nextNode = null
        }
        node.children!!.clear()
        node.children = null
    }

    // Clear the trie
    fun clear() {
        root.children = null
        root = Node(rootCharacter)
    }
}
