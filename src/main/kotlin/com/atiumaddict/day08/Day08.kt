package com.atiumaddict.day08

fun solveDay08First(lines: List<String>): Long {
    val listOfCharArrays = lines.map { it.toCharArray() }
    println(listOfCharArrays)
    val frequencies = mutableMapOf<Char, HashSet<Pair<Int, Int>>>()
    val antidotes = HashSet<Pair<Int, Int>>()
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
                val antidotePositions = findAntidotesForFrequencies(pair.value, otherPair.value, listOfCharArrays)
                antidotes.addAll(antidotePositions)
            }
        }
    }
    return antidotes.size.toLong()
}

fun findAntidotesForFrequencies(
    firstFrequency: Pair<Int, Int>,
    secondFrequency: Pair<Int, Int>,
    lines: List<CharArray>
): List<Pair<Int, Int>> {
    val antidotes = mutableListOf<Pair<Int, Int>>()
    // find the two antidotes for the two frequencies. In particular, an antidote occurs at any point that is perfectly in line with two antennas of the same frequency - but only when one of the antennas is twice as far away as the other. This means that for any pair of antennas with the same frequency, there are two antinodes, one on either side of them.
    // If the antidotes are outside the lines, they are not added to the antidotes list.
    val firstAntidotePosition = Pair(
        firstFrequency.first + (firstFrequency.first - secondFrequency.first),
        firstFrequency.second + (firstFrequency.second - secondFrequency.second)
    )
    val secondAntidotePosition = Pair(
        secondFrequency.first + (secondFrequency.first - firstFrequency.first),
        secondFrequency.second + (secondFrequency.second - firstFrequency.second)
    )
    if (firstAntidotePosition.first >= 0 && firstAntidotePosition.first < lines.size && firstAntidotePosition.second >= 0 && firstAntidotePosition.second < lines[firstAntidotePosition.first].size) {
        antidotes.add(firstAntidotePosition)
    }
    if (secondAntidotePosition.first >= 0 && secondAntidotePosition.first < lines.size && secondAntidotePosition.second >= 0 && secondAntidotePosition.second < lines[secondAntidotePosition.first].size) {
        antidotes.add(secondAntidotePosition)
    }
    return antidotes
}

fun solveDay08Second(lines: List<String>): Long {
    return 0
}

