package com.atiumaddict

fun getGridFromLines(lines: List<String>): List<CharArray> {
    return lines.map { it.toCharArray() }
}

enum class Direction(val direction: Pair<Int, Int>) {
    UP(Pair(-1, 0)),
    RIGHT(Pair(0, 1)),
    DOWN(Pair(1, 0)),
    LEFT(Pair(0, -1))
}

val directions = Direction.entries.map { it.direction }

enum class Orientation {
    HORIZONTAL, VERTICAL
}
