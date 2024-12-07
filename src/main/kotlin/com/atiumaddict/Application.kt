package com.atiumaddict

import com.atiumaddict.day07.solveDay07First
import com.atiumaddict.day07.solveDay07Second
import java.io.File

fun main() {
    solve()
}

fun solve() {
    println("Advent of Code 2024")
    val lines = File("src/main/kotlin/com/atiumaddict/day07/finalInput.txt").readLines()

    val iterations = 1
    var totalTime1 = 1L
    var totalTime2 = 0L

    repeat(iterations) {
        val start1 = System.nanoTime()
        solveDay07First(lines)
        totalTime1 += System.nanoTime() - start1

        val start2 = System.nanoTime()
        solveDay07Second(lines)
        totalTime2 += System.nanoTime() - start2
    }

    val averageTime1 = totalTime1 / iterations
    val averageTime2 = totalTime2 / iterations

    println("Day 07 First Solution: ${solveDay07First(lines)}")
    println("Day 07 Second Solution: ${solveDay07Second(lines)}")
    println("Average time taken for Day 07 First Solution (without reading the file) in $iterations iterations: $averageTime1 ns")
    println("Average time taken for Day 07 Second Solution  (without reading the file) in $iterations iterations: $averageTime2 ns")
}
