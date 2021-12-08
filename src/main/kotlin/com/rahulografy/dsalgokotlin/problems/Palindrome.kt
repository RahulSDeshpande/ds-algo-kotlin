package com.rahulografy.dsalgokotlin.problems

fun main() {
    // println(checkPalindrome(string = "Anna")) // -> annA
    // println(checkPalindrome(string = "anna")) // -> anna
    // println(checkPalindrome(string = "aNa")) // -> aNa
    // println(checkPalindrome(string = "asdfsdfsd")) // -> dsfdsfdsa

    // val arr: List<Int> = listOf(0, 0, 0, 0, 0, 1, 1, 1)
    // println(findIndexOfOne(arr))
    // print(arr.binarySearch(1))

    // replace2ndChar("")

    val words = listOf("atee", "eeat", "tan", "teea", "bat", "nat")

    // groupAnagramWords(words)

    groupAnagrams(words)
}

fun checkPalindrome(string: String): Boolean {

    var stringReversed = ""

    val length = string.length
    var i = length - 1

    while (i >= 0) {
        stringReversed += string[i]
        i--
    }

    return string == stringReversed
}

fun findIndexOfOne(arr: List<Int>): Int {
    var cont = true

    var start = 0
    var end = (arr.size / 2) - 1

    while (cont) {
        if (arr[end] == 1) {
            return end
        }

        start = (start / 2)
        end = (end / 2) - 1

        cont = false
    }

    return -1
}

fun replace2ndChar(symbol: String) {
    val spaceAscii = 32
    val string = "rahul deshpande rahul deshpande"
    var newString = ""
    string.forEachIndexed { index, char ->
        if (char.toInt() != spaceAscii && string[index - 2].toInt() == spaceAscii) {
            newString = string.replaceRange(index, index, symbol)
        }
    }

    print(newString)
}

fun groupAnagramWords(words: List<String>) {

    val anagramWords = arrayListOf<ArrayList<String>>()

    var continueLoop = true

    words.forEach { word ->

        if (anagramWords.isEmpty()) {
            anagramWords.add(arrayListOf(word))
        } else {

            run fe1@{

                anagramWords.forEach { aWords ->

                    run fe2@{

                        aWords.forEach { aWord ->
                            if (word.ascending().contentEquals(aWord.ascending())) {

                                aWords.add(aWord)
                                anagramWords.add(aWords)

                                continueLoop = false
                                return@fe2
                            }
                        }
                    }

                    if (continueLoop.not()) {
                        return@fe1
                    }
                }
            }
        }
    }

    print(anagramWords)
}

fun String.ascending() = toCharArray().sortedArray()

fun groupAnagrams(strings: List<String>) {
    val resultingMap = mutableMapOf<Map<Char, Int>, List<String>>()

    strings.forEach { str ->
        val keyMap = mutableMapOf<Char, Int>()
        str.forEach {
            keyMap[it] = (keyMap[it] ?: 0) + 1
        }

        resultingMap[keyMap] = (resultingMap[keyMap] ?: listOf()) + str
    }

    println(resultingMap)
    print(resultingMap.values.toList())
}
