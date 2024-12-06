package com.atiumaddict.day06

fun solveDay06First(lines: List<String>): Long {
    val grid = lines.map { it.toList() }
    val start = findStartingPosition(grid)
    val visited = mutableSetOf<Pair<Int, Int>>()
    return continuePath(grid, start.first, start.second, visited)
}

fun continuePath(grid: List<List<Char>>, currentPosition: Pair<Int, Int>, direction: Pair<Int, Int>, visited: MutableSet<Pair<Int, Int>>): Long {
    val nextPosition = Pair(currentPosition.first + direction.first, currentPosition.second + direction.second)
    visited.add(currentPosition)
    if (nextPosition.first < 0 || nextPosition.first >= grid.size || nextPosition.second < 0 || nextPosition.second >= grid[0].size) {
        return visited.size.toLong()
    }
    if (grid[nextPosition.first][nextPosition.second] == '#') {
        val nextDirection = directions[(directions.indexOf(direction) + 1) % 4]
        return continuePath(grid, currentPosition, nextDirection, visited)
    } else {
        return continuePath(grid, nextPosition, direction, visited)
    }
}

val directionsByChar = mapOf('^' to Pair(-1, 0), '>' to Pair(0, 1), 'v' to Pair(1, 0), '<' to Pair(0, -1))
val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))

fun findStartingPosition(grid: List<List<Char>>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    for ((i, row) in grid.withIndex()) {
        for ((j, char) in row.withIndex()) {
            if (char != '.' && char != '#') {
                return Pair(Pair(i, j), directionsByChar[char]!!)
            }
        }
    }
    return Pair(Pair(-1, -1), Pair(0, 0))
}

fun solveDay06Second(lines: List<String>): Long {
    return 0
}


