package com.atiumaddict.day12

import com.atiumaddict.Direction
import com.atiumaddict.Orientation
import com.atiumaddict.getGridFromLines

var visited = mutableSetOf<Pair<Int, Int>>()
fun solveDay12First(lines: List<String>): Long {
    val grid = getGridFromLines(lines)
    visited = mutableSetOf()
    var totalArea = 0L
    for (row in grid.indices) {
        for (column in grid[0].indices) {
            if (visited.contains(Pair(row, column))) {
                continue
            }
            val area = continueArea(grid[row][column], Pair(row, column), grid, Pair(0, 0))
            totalArea += area.first * area.second
        }
    }
    return totalArea
}

val directions = listOf(
    Pair(0, 1),
    Pair(1, 0),
    Pair(0, -1),
    Pair(-1, 0)
)

fun continueArea(
    previousPlant: Char,
    currentPosition: Pair<Int, Int>,
    grid: List<CharArray>,
    area: Pair<Long, Long>
): Pair<Long, Long> {
    if (visited.contains(currentPosition)) {
        return area
    }
    if (grid[currentPosition.first][currentPosition.second] == previousPlant) {
        visited.add(currentPosition)
        var updatedArea = Pair(area.first + 1, area.second)
        for (direction in directions) {
            val nextRow = currentPosition.first + direction.first
            val nextColumn = currentPosition.second + direction.second
            updatedArea = if (!isOutOfBounds(nextRow, nextColumn, grid)) {
                if (grid[nextRow][nextColumn] != previousPlant) {
                    Pair(updatedArea.first, updatedArea.second + 1)
                } else {
                    continueArea(previousPlant, Pair(nextRow, nextColumn), grid, updatedArea)
                }
            } else {
                Pair(updatedArea.first, updatedArea.second + 1)
            }
        }
        return updatedArea
    }
    return area
}

fun solveDay12Second(lines: List<String>): Long {
    val grid = getGridFromLines(lines)
    visited = mutableSetOf()
    var totalArea = 0L
    for (row in grid.indices) {
        for (column in grid[0].indices) {
            if (visited.contains(Pair(row, column))) {
                continue
            }
            val plants = getPlants(Pair(row, column), Pair(row, column), grid, mutableSetOf(Pair(row, column)))
            val area = plants.size
            val sides = countSides(plants)
            val plant = grid[row][column]
            println("Plant: $plant Area: $area Sides: $sides")
            totalArea += area * sides
        }
    }
    return totalArea
}

class Side(val betweenLines: Pair<Int, Int>, val orientation: Orientation, var betweenPlants: Pair<Int, Int>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Side) return false
        return betweenLines == other.betweenLines && orientation == other.orientation && betweenPlants == other.betweenPlants
    }

    override fun hashCode(): Int {
        return 31 * betweenLines.first + betweenLines.second + orientation.hashCode()
    }
}


fun countSides(plants: Set<Pair<Int, Int>>): Long {
    val sides = mutableSetOf<Side>()
    val sortedPlants = plants.sortedBy { it.first }.sortedBy { it.second }
    for (plant in sortedPlants) {
        for (direction in directions) {
            val nextRow = plant.first + direction.first
            val nextColumn = plant.second + direction.second
            val nextPosition = Pair(nextRow, nextColumn)
            if (nextPosition in plants) {
                continue
            }
            if (direction == Direction.UP.direction || direction == Direction.DOWN.direction) { // Horizontal border
                val between = sortedPair(plant.first, nextRow)
                val relevantSides =
                    sides.filter { it.betweenLines == between && it.orientation == Orientation.HORIZONTAL }
                if (relevantSides.isEmpty()) {
                    sides.add(Side(between, Orientation.HORIZONTAL, Pair(plant.second, plant.second)))
                }
                var appendedToExistingSide = false
                for (side in relevantSides) {
                    if (plant.second < side.betweenPlants.first) {
                        if (side.betweenPlants.first - plant.second == 1) {
                            side.betweenPlants = Pair(plant.second, side.betweenPlants.second)
                            appendedToExistingSide = true
                            break
                        }
                    } else if (plant.second > side.betweenPlants.second) {
                        if (plant.second - side.betweenPlants.second == 1) {
                            side.betweenPlants = Pair(side.betweenPlants.first, plant.second)
                            appendedToExistingSide = true
                            break
                        }
                    }
                }
                if (!appendedToExistingSide) {
                    sides.add(Side(between, Orientation.HORIZONTAL, Pair(plant.second, plant.second)))
                }
            } else { // Vertical border
                val between = sortedPair(plant.second, nextColumn)
                val relevantSides =
                    sides.filter { it.betweenLines == between && it.orientation == Orientation.VERTICAL }
                if (relevantSides.isEmpty()) {
                    sides.add(Side(between, Orientation.VERTICAL, Pair(plant.first, plant.first)))
                }
                var appendedToExistingSide = false
                for (side in relevantSides) {
                    if (plant.first < side.betweenPlants.first) {
                        if (side.betweenPlants.first - plant.first == 1) {
                            side.betweenPlants = Pair(plant.first, side.betweenPlants.second)
                            appendedToExistingSide = true
                            break
                        }
                    } else if (plant.first > side.betweenPlants.second) {
                        if (plant.first - side.betweenPlants.second == 1) {
                            side.betweenPlants = Pair(side.betweenPlants.first, plant.first)
                            appendedToExistingSide = true
                            break
                        }
                    }
                }
                if (!appendedToExistingSide) {
                    sides.add(Side(between, Orientation.VERTICAL, Pair(plant.first, plant.first)))
                }
            }
        }
    }
    return sides.size.toLong()
}

fun sortedPair(a: Int, b: Int): Pair<Int, Int> {
    return if (a <= b) Pair(a, b) else Pair(b, a)
}

private fun isOutOfBounds(
    row: Int,
    column: Int,
    grid: List<CharArray>
) = row < 0 || row >= grid.size || column < 0 || column >= grid[0].size

fun getPlants(
    previousPosition: Pair<Int, Int>,
    currentPosition: Pair<Int, Int>,
    grid: List<CharArray>,
    plants: Set<Pair<Int, Int>>
): MutableSet<Pair<Int, Int>> {
    var updatedPlants = plants.toMutableSet()
    if (visited.contains(currentPosition)) {
        return updatedPlants
    }
    val previousPlant = grid[previousPosition.first][previousPosition.second]
    val currentPlant = grid[currentPosition.first][currentPosition.second]
    if (currentPlant == previousPlant) {
        visited.add(currentPosition)
        updatedPlants.add(currentPosition)
        for (direction in directions) {
            val nextRow = currentPosition.first + direction.first
            val nextColumn = currentPosition.second + direction.second
            val nextPosition = Pair(nextRow, nextColumn)
            if (nextRow >= 0 && nextRow < grid[0].size && nextColumn >= 0 && nextColumn < grid.size) {
                updatedPlants = getPlants(currentPosition, nextPosition, grid, updatedPlants)
            }
        }
        return updatedPlants
    }
    return updatedPlants
}
