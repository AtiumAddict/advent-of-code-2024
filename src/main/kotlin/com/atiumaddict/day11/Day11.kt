package com.atiumaddict.day11

fun solveDay11First(lines: List<String>): Long {
    cache.clear()
    val stones = parseLines11(lines)
    return stones.sumOf { blinkOnStoneWithMemoization(it, 25) }
}

/**
 * @deprecated - too slow
 */
fun countStonesAfterBlinks(stones: List<Long>, numberOfBlinks: Int): Long {
    var currentStones = blinkOnStones(stones, numberOfBlinks)
    return currentStones.size.toLong()
}

/**
 * @deprecated - too slow
 */
fun blinkOnStones(
    stones: List<Long>,
    numberOfBlinks: Int
): List<Long> {
    var currentStones = stones
    for (i in 0 until numberOfBlinks) {
        currentStones = currentStones.map { blinkOnStone(it) }.flatten()
    }
    return currentStones
}

fun blinkOnStone(stone: Long): List<Long> {
    return if (stone == 0L) {
        listOf(1)
    } else if (hasEvenDigits(stone)) {
        splitDigits(stone)
    } else {
        listOf(stone * 2024)
    }
}

fun hasEvenDigits(stone: Long): Boolean {
    return stone.toString().length % 2 == 0
}

fun splitDigits(stone: Long): List<Long> {
    val digits = stone.toString().toCharArray().map { it.toString().toLong() }
    val half = digits.size / 2
    return listOf(
        digits.subList(0, half).joinToString("").toLong(),
        digits.subList(half, digits.size).joinToString("").toLong()
    )
}

fun solveDay11Second(lines: List<String>): Long {
    cache.clear()
    val stones = parseLines11(lines)
    return stones.sumOf { blinkOnStoneWithMemoization(it, 75) }
}

val cache = mutableMapOf<Pair<Long, Int>, Long>()

fun blinkOnStoneWithMemoization(stone: Long, numberOfBlinks: Int): Long {
    if (numberOfBlinks == 0) {
        return 1
    }
    if (cache.containsKey(stone to numberOfBlinks)) {
        return cache[stone to numberOfBlinks]!!
    }

    val blinkedStones = blinkOnStone(stone)
    val finalNumberOfStones = blinkedStones.sumOf { blinkOnStoneWithMemoization(it, numberOfBlinks - 1) }

    cache[stone to numberOfBlinks] = finalNumberOfStones
    return finalNumberOfStones
}


fun parseLines11(lines: List<String>): List<Long> {
    return lines[0].split(" ").map { it.trim().toLong() }
}

