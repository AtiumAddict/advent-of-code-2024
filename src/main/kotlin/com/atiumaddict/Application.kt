package com.atiumaddict

import com.atiumaddict.day05.solveDay05First
import com.atiumaddict.day05.solveDay05Second
import java.io.File

fun main() {
    solve()
}

fun solve() {
    println("Advent of Code 2024")
    val lines = File("src/main/kotlin/com/atiumaddict/day05/testInput.txt").readLines()

    val iterations = 1
    var totalTime1 = 0L
    var totalTime2 = 0L

    repeat(iterations) {
        val start1 = System.nanoTime()
        solveDay05First(lines)
        totalTime1 += System.nanoTime() - start1

        val start2 = System.nanoTime()
        solveDay05Second(lines)
        totalTime2 += System.nanoTime() - start2
    }

    val averageTime1 = totalTime1 / iterations
    val averageTime2 = totalTime2 / iterations

    println("Day 05 First Solution: ${solveDay05First(lines)}")
    println("Day 05 Second Solution: ${solveDay05Second(lines)}")
    println("Average time taken for Day 05 First Solution (without reading the file) in $iterations iterations: $averageTime1 ns")
    println("Average time taken for Day 05 Second Solution  (without reading the file) in $iterations iterations: $averageTime2 ns")
}
