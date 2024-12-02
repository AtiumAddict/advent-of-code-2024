package com.atiumaddict

import com.atiumaddict.adventofcode.day01.solveDay01First
import com.atiumaddict.adventofcode.day01.solveDay01Second
import com.atiumaddict.adventofcode.day01.solveDay02First
import com.atiumaddict.adventofcode.day01.solveDay02Second
import io.ktor.server.application.*

fun main() {
    println(solveDay02First())
    println(solveDay02Second())
}

fun Application.module() {
}
