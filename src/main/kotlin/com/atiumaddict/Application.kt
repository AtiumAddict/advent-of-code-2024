package com.atiumaddict

import com.atiumaddict.day06.solveDay06First
import com.atiumaddict.day06.solveDay06Second
import java.io.File

fun main() {
    solve()
}

fun solve() {
    println("Advent of Code 2024")
    val lines = File("src/main/kotlin/com/atiumaddict/day06/finalInput.txt").readLines()

    val iterations = 1
    var totalTime1 = 0L
    var totalTime2 = 0L

    repeat(iterations) {
        val start1 = System.nanoTime()
        solveDay06First(lines)
        totalTime1 += System.nanoTime() - start1

        val start2 = System.nanoTime()
        solveDay06Second(lines)
        totalTime2 += System.nanoTime() - start2
    }

    val averageTime1 = totalTime1 / iterations
    val averageTime2 = totalTime2 / iterations

    println("Day 06 First Solution: ${solveDay06First(lines)}")
    println("Day 06 Second Solution: ${solveDay06Second(lines)}")
    println("Average time taken for Day 06 First Solution (without reading the file) in $iterations iterations: $averageTime1 ns")
    println("Average time taken for Day 06 Second Solution  (without reading the file) in $iterations iterations: $averageTime2 ns")
}
