package com.atiumaddict.day08

fun solveDay08First(lines: List<String>): Long {
    val listOfCharArrays = lines.map { it.toCharArray() }
    println(listOfCharArrays)
    val frequencies = mutableMapOf<Char, HashSet<Pair<Int, Int>>>()
    val antinodes = HashSet<Pair<Int, Int>>()
    for (i in listOfCharArrays.indices) {
        for ((j, c) in listOfCharArrays[i].withIndex()) {
            if (c == '.') continue
            val positions = frequencies.getOrDefault(c, HashSet())
            positions.add(Pair(i, j))
            frequencies[c] = positions
        }
    }
    for (entry in frequencies) {
        if (entry.value.size == 1) {
            continue
        }
        for (pair in entry.value.withIndex()) {
            for (otherPair in entry.value.withIndex()) {
                if (pair.index == otherPair.index) continue
                val antinodePositions = findAntidotes(pair.value, otherPair.value, listOfCharArrays)
                antinodes.addAll(antinodePositions)
            }
        }
    }
    return antinodes.size.toLong()
}

fun findAntidotes(
    firstFrequency: Pair<Int, Int>,
    secondFrequency: Pair<Int, Int>,
    lines: List<CharArray>
): List<Pair<Int, Int>> {
    val antinodes = mutableListOf<Pair<Int, Int>>()
    val firstAntidotePosition = Pair(
        firstFrequency.first + (firstFrequency.first - secondFrequency.first),
        firstFrequency.second + (firstFrequency.second - secondFrequency.second)
    )
    val secondAntidotePosition = Pair(
        secondFrequency.first + (secondFrequency.first - firstFrequency.first),
        secondFrequency.second + (secondFrequency.second - firstFrequency.second)
    )
    if (firstAntidotePosition.first >= 0 && firstAntidotePosition.first < lines.size && firstAntidotePosition.second >= 0 && firstAntidotePosition.second < lines[firstAntidotePosition.first].size) {
        antinodes.add(firstAntidotePosition)
    }
    if (secondAntidotePosition.first >= 0 && secondAntidotePosition.first < lines.size && secondAntidotePosition.second >= 0 && secondAntidotePosition.second < lines[secondAntidotePosition.first].size) {
        antinodes.add(secondAntidotePosition)
    }
    return antinodes
}

fun solveDay08Second(lines: List<String>): Long {
    return 0
}

