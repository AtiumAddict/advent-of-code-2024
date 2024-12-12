package com.atiumaddict

import com.atiumaddict.day12.solveDay12First
import com.atiumaddict.day12.solveDay12Second
import java.io.File

fun main() {
    solve()
}

fun solve() {
    println("Advent of Code 2024")
    val lines = File("src/main/kotlin/com/atiumaddict/day12/testInput.txt").readLines()
//    val lines = File("src/main/kotlin/com/atiumaddict/day12/finalInput.txt").readLines()

    val iterations = 1
    var totalTime1 = 1L
    var totalTime2 = 0L

    repeat(iterations) {
        val start1 = System.nanoTime()
        solveDay12First(lines)
        totalTime1 += System.nanoTime() - start1

        val start2 = System.nanoTime()
        solveDay12Second(lines)
        totalTime2 += System.nanoTime() - start2
    }

    val averageTime1 = totalTime1 / iterations
    val averageTime2 = totalTime2 / iterations

    println("Day 12 First Part: ${solveDay12First(lines)}")
    println("Day 12 Second Part: ${solveDay12Second(lines)}")
    println("Average time taken for Day 12 (without reading the file) in $iterations iterations:")
    println("First Solution: ${String.format("%,d", averageTime1)} ns")
    println("Second Solution: ${String.format("%,d", averageTime2)} ns")
}
