package com.atiumaddict.day10

fun solveDay10First(lines: List<String>): Long {
    return lines.mapIndexed { r, row ->
        row.withIndex()
            .filter { it.value == '0' }
            .map { col ->
                findNines(Pair(r, col.index), -1, mutableSetOf(), lines).size
            }
    }.flatten().sum().toLong()
}


val directions = listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))

fun findNines(
    current: Pair<Int, Int>,
    currentHeight: Int,
    visited: MutableSet<Pair<Int, Int>>,
    lines: List<String>
): Set<Pair<Int, Int>> {
    val (r, c) = current
    if (r !in lines.indices || c !in lines[r].indices ||
        current in visited ||
        lines[r][c].digitToInt() != currentHeight + 1
    ) {
        return emptySet()
    }

    visited.add(current)
    val ninePositions = if (lines[r][c] == '9') setOf(current) else emptySet()

    return ninePositions + directions.flatMap { (dr, dc) ->
        findNines(Pair(r + dr, c + dc), lines[r][c].digitToInt(), visited, lines)
    }
}

fun solveDay10Second(lines: List<String>): Long {
    return lines.mapIndexed { r, row ->
        row.withIndex()
            .filter { it.value == '0' }
            .map { col ->
                countTrails(Pair(r, col.index), -1, lines, 0)
            }
    }.flatten().sum().toLong()
}

fun countTrails(
    current: Pair<Int, Int>,
    currentHeight: Int,
    lines: List<String>,
    countOfNines: Int,
    comingFrom: Pair<Int, Int> = Pair(-1, -1)
): Int {
    if (current.first !in lines.indices || current.second !in 0 until lines[0].length ||
        lines[current.first][current.second].digitToInt() != currentHeight + 1
    ) {
        return 0
    }

    if (lines[current.first][current.second] == '9') {
        return countOfNines + 1
    }

    var newCountOfNines = countOfNines
    for (direction in directions) {
        val nextPosition = Pair(current.first + direction.first, current.second + direction.second)
        if (nextPosition == comingFrom) {
            continue
        }
        newCountOfNines += countTrails(
            Pair(current.first + direction.first, current.second + direction.second),
            lines[current.first][current.second].digitToInt(),
            lines,
            0,
            current,
        )
    }
    return newCountOfNines
}

