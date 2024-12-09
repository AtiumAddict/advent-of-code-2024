package com.atiumaddict

import com.atiumaddict.day09.solveDay09First
import com.atiumaddict.day09.solveDay09Second
import java.io.File

fun main() {
    solve()
}

fun solve() {
    println("Advent of Code 2024")
//    val lines = File("src/main/kotlin/com/atiumaddict/day09/testInput.txt").readLines()
    val lines = File("src/main/kotlin/com/atiumaddict/day09/finalInput.txt").readLines()

    val iterations = 100
    var totalTime1 = 1L
    var totalTime2 = 0L

    repeat(iterations) {
        val start1 = System.nanoTime()
        solveDay09First(lines)
        totalTime1 += System.nanoTime() - start1

        val start2 = System.nanoTime()
        solveDay09Second(lines)
        totalTime2 += System.nanoTime() - start2
    }

    val averageTime1 = totalTime1 / iterations
    val averageTime2 = totalTime2 / iterations

    println("Day 09 First Part: ${solveDay09First(lines)}")
    println("Day 09 Second Part: ${solveDay09Second(lines)}")
    println("Average time taken for Day 09 (without reading the file) in $iterations iterations:")
    println("First Solution: ${String.format("%,d", averageTime1)} ns")
    println("Second Solution: ${String.format("%,d", averageTime2)} ns")
}
