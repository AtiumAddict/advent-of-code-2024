package com.atiumaddict

import com.atiumaddict.day13.solveDay13First
import com.atiumaddict.day13.solveDay13Second
import java.io.File

fun main() {
    solve()
}

fun solve() {
    println("Advent of Code 2024")
//    val lines = File("src/main/kotlin/com/atiumaddict/day13/testInput.txt").readLines()
    val lines = File("src/main/kotlin/com/atiumaddict/day13/finalInput.txt").readLines()

    val iterations = 10000
    var totalTime1 = 1L
    var totalTime2 = 0L

    repeat(iterations) {
        val start1 = System.nanoTime()
        solveDay13First(lines)
        totalTime1 += System.nanoTime() - start1

        val start2 = System.nanoTime()
        solveDay13Second(lines)
        totalTime2 += System.nanoTime() - start2
    }

    val averageTime1 = totalTime1 / iterations
    val averageTime2 = totalTime2 / iterations

    println("Day 13 First Part: ${solveDay13First(lines)}")
    println("Day 13 Second Part: ${solveDay13Second(lines)}")
    println("Average time taken for Day 13 (without reading the file) in $iterations iterations:")
    println("First Solution: ${String.format("%,d", averageTime1)} ns")
    println("Second Solution: ${String.format("%,d", averageTime2)} ns")
}
