package com.atiumaddict.adventofcode.day01

import java.io.File
import kotlin.math.abs


fun solveDay02First(): Long {
    val lines = parseLines02()
    var safeLines = 0L
    for (line in lines) {
        if (lineIsSafe(line)) {
            safeLines++
        }
    }
    return safeLines
}

private fun lineIsSafe(line: List<Int>): Boolean {
    val increasing = line[1] - line[0] > 0
    for (i in 1 until line.size) {
        if (!isLevelTransitionSafe(increasing, Pair(line[i - 1], line[i]))) {
            return false
        }
    }
    return true
}

fun solveDay02Second(): Long {
    val lines = parseLines02()
    var safeLines = 0L
    for (line in lines) {
        if (lineIsSafeWithLevelTolerance(line, 1)) {
            safeLines++
        }
    }
    return safeLines
}

private fun lineIsSafeWithLevelTolerance(line: List<Int>, levelTolerance: Int): Boolean {
    val increasing = line[1] - line[0] > 0
    var tolerance = levelTolerance
    for (i in 1 until line.size) {
        if (!isLevelTransitionSafe(increasing, Pair(line[i - 1], line[i]))) {
            if (tolerance > 0) {
                if (i == 1) {
                    continue
                }
                if (isLevelTransitionSafe(increasing, Pair(line[i - 2], line[i]))) {
                    tolerance--
                    continue
                }
            }
            return false
        }
    }
    return true
}

private fun isLevelTransitionSafe(
    increasing: Boolean,
    pair: Pair<Int, Int>
): Boolean {
    val diff = abs(pair.second - pair.first)
    if (diff == 0 || diff > 3) {
        return false
    }
    if (increasing && pair.second - pair.first < 0 || !increasing && pair.second - pair.first > 0) {
        return false
    }
    return true
}

fun parseLines02(): List<List<Int>> {
    val inputFile = File("src/main/kotlin/com/atiumaddict/day02/finalInput.txt")

    val lines = arrayListOf<List<Int>>()
    inputFile.readLines().forEach { line ->
        val numbers = line.split("\\s+".toRegex()).map { it.trim().toInt() }
        lines.add(numbers)
    }
    return lines
}

