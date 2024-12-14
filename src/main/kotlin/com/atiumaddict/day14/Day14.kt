package com.atiumaddict.day14

import kotlin.math.abs

var finalRobotPositions = mutableMapOf<Pair<Int, Int>, Int>()
fun solveDay14First(lines: List<String>): Long {
    val robots = getRobots(lines)
    finalRobotPositions = mutableMapOf()
    val grid = Pair(103, 101)
//    val grid = Pair(7, 11)
    for (robot in robots) {
        val finalPosition = getFinalPosition(robot, 100, grid)
        finalRobotPositions[finalPosition] = finalRobotPositions.getOrDefault(finalPosition, 0) + 1
    }
    return getQuadrants(finalRobotPositions, grid).reduce(Long::times)
}

fun getQuadrants(finalRobotPositions: MutableMap<Pair<Int, Int>, Int>, grid: Pair<Int, Int>): List<Long> {
    var quadrant1 = 0L
    var quadrant2 = 0L
    var quadrant3 = 0L
    var quadrant4 = 0L
    for (position in finalRobotPositions.keys) {
        if (position.first < grid.first / 2 && position.second < grid.second / 2) {
            quadrant1 += finalRobotPositions[position]!!
        } else if (position.first < grid.first / 2 && position.second > grid.second / 2) {
            quadrant2 += finalRobotPositions[position]!!
        } else if (position.first > grid.first / 2 && position.second < grid.second / 2) {
            quadrant3 += finalRobotPositions[position]!!
        } else if (position.first > grid.first / 2 && position.second > grid.second / 2) {
            quadrant4 += finalRobotPositions[position]!!
        }
    }
    return listOf(quadrant1, quadrant2, quadrant3, quadrant4)
}

fun getFinalPosition(robot: Robot, seconds: Int, grid: Pair<Int, Int>): Pair<Int, Int> {
    val start = robot.start
    val velocity = robot.velocity
    val totalVerticalMovement = velocity.first * seconds
    val totalHorizontalMovement = velocity.second * seconds
    val finalRow = if (totalVerticalMovement < 0) {
        (grid.first - abs((start.first + totalVerticalMovement) % grid.first)) % grid.first
    } else {
        abs((start.first + totalVerticalMovement) % grid.first)
    }
    val finalColumn = if (totalHorizontalMovement < 0) {
        (grid.second - abs((start.second + totalHorizontalMovement) % grid.second)) % grid.second
    } else {
        abs((start.second + totalHorizontalMovement) % grid.second)
    }
    return Pair(finalRow, finalColumn)
}

class Robot(val start: Pair<Int, Int>, val velocity: Pair<Int, Int>) {
    override fun toString(): String {
        return "Robot(position=$start, velocity=$velocity)"
    }
}

fun solveDay14Second(lines: List<String>): Long {
    return 0
}


fun getRobots(lines: List<String>): List<Robot> {
    val robots = mutableListOf<Robot>()
    lines.forEach { line ->
        // Parse lines like "p=0,4 v=3,-3" to get a robot
        val parts = line.split(" ")
        val robot = Robot(
            Pair(parts[0].substringAfter(",").toInt(), parts[0].substringAfter("p=").substringBefore(",").toInt()),
            Pair(parts[1].substringAfter(",").toInt(), parts[1].substringBefore(",").substringAfter("v=").toInt())
        )
        robots.add(robot)
    }
    return robots
}

