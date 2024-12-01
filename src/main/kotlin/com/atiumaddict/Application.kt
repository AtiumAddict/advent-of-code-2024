package com.atiumaddict

import com.atiumaddict.adventofcode.day01.solveDay01First
import com.atiumaddict.adventofcode.day01.solveDay01Second
import io.ktor.server.application.*

fun main() {
    println(solveDay01First())
    println(solveDay01Second())
}

fun Application.module() {
    configureRouting()
}
