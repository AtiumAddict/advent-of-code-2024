package com.atiumaddict

import com.atiumaddict.adventofcode.day02.solveDay02First
import com.atiumaddict.adventofcode.day02.solveDay02Second
import com.atiumaddict.day03.solveDay03First
import com.atiumaddict.day03.solveDay03Second
import io.ktor.server.application.*
import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    println("Advent of Code 2024")
    val lines = File("src/main/kotlin/com/atiumaddict/day03/finalInput.txt").readLines()
    val time1 = measureTimeMillis {
        println("Day 03 First Solution: ${solveDay03First(lines)}")
    }
    println("Time taken for Day 03 First Solution: $time1 ms")

    val time2 = measureTimeMillis {
        println("Day 03 Second Solution: ${solveDay03Second(lines)}")
    }
    println("Time taken for Day 03 Second Solution: $time2 ms")

}

fun Application.module() {
}
