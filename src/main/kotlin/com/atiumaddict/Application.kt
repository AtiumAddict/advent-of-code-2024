package com.atiumaddict

import com.atiumaddict.adventofcode.day02.solveDay02First
import com.atiumaddict.adventofcode.day02.solveDay02Second
import com.atiumaddict.day04.solveDay04First
import com.atiumaddict.day04.solveDay04Second
import io.ktor.server.application.*
import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    println("Advent of Code 2024")
    val lines = File("src/main/kotlin/com/atiumaddict/day04/finalInput.txt").readLines()

    val time1 = measureTimeMillis {
        println("Day 04 First Solution: ${solveDay04First(lines)}")
    }
    println("Time taken for Day 04 First Solution: $time1 ms")
    val time2 = measureTimeMillis {
        println("Day 04 Second Solution: ${solveDay04Second(lines)}")
    }

    println("Time taken for Day 04 Second Solution: $time2 ms")

}

fun Application.module() {
}
