package com.atiumaddict

import com.atiumaddict.day10.solveDay10First
import com.atiumaddict.day10.solveDay10Second
import java.io.File

fun main() {
    solve()
}

fun solve() {
    println("Advent of Code 2024")
//    val lines = File("src/main/kotlin/com/atiumaddict/day10/testInput.txt").readLines()
    val lines = File("src/main/kotlin/com/atiumaddict/day10/finalInput.txt").readLines()

    val iterations = 10000
    var totalTime1 = 1L
    var totalTime2 = 0L

    repeat(iterations) {
        val start1 = System.nanoTime()
        solveDay10First(lines)
        totalTime1 += System.nanoTime() - start1

        val start2 = System.nanoTime()
        solveDay10Second(lines)
        totalTime2 += System.nanoTime() - start2
    }

    val averageTime1 = totalTime1 / iterations
    val averageTime2 = totalTime2 / iterations

    println("Day 10 First Part: ${solveDay10First(lines)}")
    println("Day 10 Second Part: ${solveDay10Second(lines)}")
    println("Average time taken for Day 10 (without reading the file) in $iterations iterations:")
    println("First Solution: ${String.format("%,d", averageTime1)} ns")
    println("Second Solution: ${String.format("%,d", averageTime2)} ns")
}
