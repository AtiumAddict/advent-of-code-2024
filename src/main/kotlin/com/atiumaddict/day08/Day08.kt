package com.atiumaddict.day08

fun solveDay08First(lines: List<String>): Long {
    val listOfCharArrays = lines.map { it.toCharArray() }
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
        if (entry.value.size == 1) continue

        for (pair in entry.value.withIndex()) {
            for (otherPair in entry.value.withIndex()) {
                if (pair.index == otherPair.index) continue
                val antinodePositions = findAntinodes(pair.value, otherPair.value, listOfCharArrays)
                antinodes.addAll(antinodePositions)
            }
        }
    }
    return antinodes.size.toLong()
}

fun findAntinodes(
    firstFrequency: Pair<Int, Int>,
    secondFrequency: Pair<Int, Int>,
    lines: List<CharArray>
): List<Pair<Int, Int>> {
    val antinodes = mutableListOf<Pair<Int, Int>>()
    val firstAntinodePosition = Pair(
        firstFrequency.first + (firstFrequency.first - secondFrequency.first),
        firstFrequency.second + (firstFrequency.second - secondFrequency.second)
    )
    val secondAntinodePosition = Pair(
        secondFrequency.first + (secondFrequency.first - firstFrequency.first),
        secondFrequency.second + (secondFrequency.second - firstFrequency.second)
    )
    if (firstAntinodePosition.first >= 0 && firstAntinodePosition.first < lines.size && firstAntinodePosition.second >= 0 && firstAntinodePosition.second < lines[firstAntinodePosition.first].size) {
        antinodes.add(firstAntinodePosition)
    }
    if (secondAntinodePosition.first >= 0 && secondAntinodePosition.first < lines.size && secondAntinodePosition.second >= 0 && secondAntinodePosition.second < lines[secondAntinodePosition.first].size) {
        antinodes.add(secondAntinodePosition)
    }
    return antinodes
}

fun solveDay08Second(lines: List<String>): Long {
    val listOfCharArrays = lines.map { it.toCharArray() }
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
        if (entry.value.size == 1) continue

        for (pair in entry.value.withIndex()) {
            antinodes.add(pair.value)
            for (otherPair in entry.value.withIndex()) {
                if (pair.index == otherPair.index) continue
                val antinodePositions = findAntinodesWithResonance(pair.value, otherPair.value, listOfCharArrays)
                antinodes.addAll(antinodePositions)
            }
        }
    }
    println(antinodes)
    return antinodes.size.toLong()
}

fun findAntinodesWithResonance(
    firstFrequency: Pair<Int, Int>,
    secondFrequency: Pair<Int, Int>,
    lines: List<CharArray>
): List<Pair<Int, Int>> {
    var antinodes = mutableListOf<Pair<Int, Int>>()
    val firstDistance = Pair(
        firstFrequency.first - secondFrequency.first,
        firstFrequency.second - secondFrequency.second
    )
    val secondDistance = Pair(
        secondFrequency.first - firstFrequency.first,
        secondFrequency.second - firstFrequency.second
    )
    antinodes = addResonances(firstFrequency, firstDistance, lines, antinodes)
    antinodes = addResonances(secondFrequency, secondDistance, lines, antinodes)
    return antinodes
}

private fun addResonances(
    frequency: Pair<Int, Int>,
    distances: Pair<Int, Int>,
    lines: List<CharArray>,
    antinodes: MutableList<Pair<Int, Int>>
): MutableList<Pair<Int, Int>> {
    var inBounds = true
    var currentFrequency = frequency
    while (inBounds) {
        val antinodePosition = Pair(
            currentFrequency.first + distances.first,
            currentFrequency.second + distances.second
        )
        if (antinodePosition.first >= 0 && antinodePosition.first < lines.size && antinodePosition.second >= 0 && antinodePosition.second < lines[antinodePosition.first].size) {
            antinodes.add(antinodePosition)
            currentFrequency = antinodePosition
        } else {
            inBounds = false
        }
    }
    return antinodes
}
