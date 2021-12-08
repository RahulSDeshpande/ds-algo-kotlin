// Taken from @MightyPork at:
// https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
package com.rahulografy.dsalgokotlin.datastructures.utils

object TreePrinter {

    fun getTreeDisplay(root: PrintableNode?): String {
        val sb = StringBuilder()
        val lines: MutableList<List<String?>> = ArrayList()
        var level: MutableList<PrintableNode?> = ArrayList()
        var next: MutableList<PrintableNode?> = ArrayList()
        level.add(root)
        var nn = 1
        var widest = 0
        while (nn != 0) {
            nn = 0
            val line: MutableList<String?> = ArrayList()
            for (n in level) {
                if (n == null) {
                    line.add(null)
                    next.add(null)
                    next.add(null)
                } else {
                    val aa = n.text
                    line.add(aa)
                    if (aa.length > widest) widest = aa.length
                    next.add(n.left)
                    next.add(n.right)
                    if (n.left != null) nn++
                    if (n.right != null) nn++
                }
            }
            if (widest % 2 == 1) widest++
            lines.add(line)
            val tmp = level
            level = next
            next = tmp
            next.clear()
        }
        var perpiece = lines[lines.size - 1].size * (widest + 4)
        for (i in lines.indices) {
            val line = lines[i]
            val hpw = Math.floor((perpiece / 2f).toDouble()).toInt() - 1
            if (i > 0) {
                for (j in line.indices) {

                    // split node
                    var c = ' '
                    if (j % 2 == 1) {
                        if (line[j - 1] != null) {
                            c = if (line[j] != null) '#' else '#'
                        } else {
                            if (j < line.size && line[j] != null) c = '#'
                        }
                    }
                    sb.append(c)

                    // lines and spaces
                    if (line[j] == null) {
                        for (k in 0 until perpiece - 1) {
                            sb.append(' ')
                        }
                    } else {
                        for (k in 0 until hpw) {
                            sb.append(if (j % 2 == 0) " " else "#")
                        }
                        sb.append(if (j % 2 == 0) "#" else "#")
                        for (k in 0 until hpw) {
                            sb.append(if (j % 2 == 0) "#" else " ")
                        }
                    }
                }
                sb.append('\n')
            }
            for (j in line.indices) {
                var f = line[j]
                if (f == null) f = ""
                val gap1 = Math.ceil((perpiece / 2f - f.length / 2f).toDouble()).toInt()
                val gap2 = Math.floor((perpiece / 2f - f.length / 2f).toDouble()).toInt()
                for (k in 0 until gap1) {
                    sb.append(' ')
                }
                sb.append(f)
                for (k in 0 until gap2) {
                    sb.append(' ')
                }
            }
            sb.append('\n')
            perpiece /= 2
        }
        return sb.toString()
    }

    /**
     * Node that can be printed
     */
    interface PrintableNode {

        // Left child
        val left: PrintableNode?

        // Right child
        val right: PrintableNode?

        // Text to be printed
        val text: String
    }
}
