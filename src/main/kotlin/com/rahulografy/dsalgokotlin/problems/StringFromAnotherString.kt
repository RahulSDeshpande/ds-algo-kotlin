package com.rahulografy.dsalgokotlin.problems

fun makeStringFromAnotherString(
    note: String,
    doc: String
): Boolean {

    val noteCharMap = hashMapOf<Char, Int>()

    note.forEach { char ->
        if (noteCharMap.containsKey(char)) {
            noteCharMap[char] = noteCharMap[char]!! + 1
        } else {
            noteCharMap[char] = 1
        }
    }

    val docCharMap = hashMapOf<Char, Int>()

    doc.forEach { char ->
        if (docCharMap.containsKey(char)) {
            docCharMap[char] = docCharMap[char]!! + 1
        } else {
            docCharMap[char] = 1
        }
    }

    var canMakeString = true

    noteCharMap.forEach { char, count ->
        if (docCharMap.containsKey(char)) {
            if (noteCharMap[char]!! > docCharMap[char]!!) {
                canMakeString = false
                return@forEach
            }
        } else {
            canMakeString = false
            return@forEach
        }
    }

    return canMakeString
}

fun main() {

    println(
        makeStringFromAnotherString(
            note = "rahul",
            doc = "rahul deshpande",
        )
    )

    println(
        makeStringFromAnotherString(
            note = "rahul",
            doc = "rahul",
        )
    )

    println(
        makeStringFromAnotherString(
            note = "desh",
            doc = "desh",
        )
    )
}
