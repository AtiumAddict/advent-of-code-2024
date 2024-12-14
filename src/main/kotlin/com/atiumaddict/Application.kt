package com.atiumaddict

import com.atiumaddict.day14.solveDay14First
import com.atiumaddict.day14.solveDay14Second
import java.io.File

fun main() {
    solve()
}

fun solve() {
    println("Advent of Code 2024")
//    val lines = File("src/main/kotlin/com/atiumaddict/day14/testInput.txt").readLines()
    val lines = File("src/main/kotlin/com/atiumaddict/day14/finalInput.txt").readLines()

    val iterations = 1
    var totalTime1 = 1L
    var totalTime2 = 0L

    repeat(iterations) {
        val start1 = System.nanoTime()
        solveDay14First(lines)
        totalTime1 += System.nanoTime() - start1

        val start2 = System.nanoTime()
        solveDay14Second(lines)
        totalTime2 += System.nanoTime() - start2
    }

    val averageTime1 = totalTime1 / iterations
    val averageTime2 = totalTime2 / iterations

    println("Day 14 First Part: ${solveDay14First(lines)}")
    println("Day 14 Second Part: ${solveDay14Second(lines)}")
    println("Average time taken for Day 14 (without reading the file) in $iterations iterations:")
    println("First Solution: ${String.format("%,d", averageTime1)} ns")
    println("Second Solution: ${String.format("%,d", averageTime2)} ns")
}
