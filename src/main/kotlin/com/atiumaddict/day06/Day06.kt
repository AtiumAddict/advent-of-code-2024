package com.atiumaddict.day06

fun solveDay06First(lines: List<String>): Long {
  val grid = lines.map { it.toList() }
  val start = findStartingPosition(grid)
  val visited = mutableSetOf<Pair<Int, Int>>()
  return continuePath(grid, start.first, start.second, visited)
}

fun continuePath(
  grid: List<List<Char>>,
  currentPosition: Pair<Int, Int>,
  direction: Pair<Int, Int>,
  visited: MutableSet<Pair<Int, Int>>,
): Long {
  val nextPosition = getNextPosition(currentPosition, direction)
  visited.add(currentPosition)
  if (hasExited(nextPosition, grid)) {
    return visited.size.toLong()
  }
  if (grid[nextPosition.first][nextPosition.second] == '#') {
    val nextDirection = getDirectionToTheRight(direction)
    return continuePath(grid, currentPosition, nextDirection, visited)
  } else {
    return continuePath(grid, nextPosition, direction, visited)
  }
}

private fun getDirectionToTheRight(direction: Pair<Int, Int>) =
  directions[(directions.indexOf(direction) + 1) % 4]

private fun getNextPosition(currentPosition: Pair<Int, Int>, direction: Pair<Int, Int>) =
  Pair(currentPosition.first + direction.first, currentPosition.second + direction.second)

private fun hasExited(nextPosition: Pair<Int, Int>, grid: List<List<Char>>) =
  nextPosition.first < 0 ||
    nextPosition.first >= grid.size ||
    nextPosition.second < 0 ||
    nextPosition.second >= grid[0].size

val directionsByChar =
  mapOf('^' to Pair(-1, 0), '>' to Pair(0, 1), 'v' to Pair(1, 0), '<' to Pair(0, -1))
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

class Node(val position: Pair<Int, Int>, val direction: Pair<Int, Int>)

fun solveDay06Second(lines: List<String>): Long {
  val grid = lines.map { it.toList() }
  val start = findStartingPosition(grid)
  val path = mutableListOf<Node>()
  val obstaclePositions = mutableSetOf<Pair<Int, Int>>()
  continuePathMarkingObstacles(grid, start.first, start.second, path, obstaclePositions)
  val infiniteLoopObstacles = mutableSetOf<Pair<Int, Int>>()
  for ((i, node) in path.withIndex()) {
    if (i == 0) continue
    if (
      isInfiniteLoop(
        grid,
        path[i - 1].position,
        node.position,
        path[i - 1].direction,
        mutableSetOf(),
      )
    ) {
      infiniteLoopObstacles.add(node.position)
    }
  }
  println(infiniteLoopObstacles)
  return infiniteLoopObstacles.size.toLong()
}

fun continuePathMarkingObstacles(
  grid: List<List<Char>>,
  currentPosition: Pair<Int, Int>,
  direction: Pair<Int, Int>,
  path: MutableList<Node>,
  obstaclePositions: MutableSet<Pair<Int, Int>>,
): Long {
  val nextPosition = getNextPosition(currentPosition, direction)
  if (hasExited(nextPosition, grid)) {
    return path.size.toLong()
  }
  if (grid[nextPosition.first][nextPosition.second] == '#') {
    obstaclePositions.add(nextPosition)
    val nextDirection = getDirectionToTheRight(direction)
    path.add(Node(currentPosition, nextDirection))
    val nextPositionWithNewDirection = getNextPosition(currentPosition, nextDirection)
    return continuePathMarkingObstacles(
      grid,
      nextPositionWithNewDirection,
      nextDirection,
      path,
      obstaclePositions,
    )
  }
  path.add(Node(currentPosition, direction))
  return continuePathMarkingObstacles(grid, nextPosition, direction, path, obstaclePositions)
}

fun isInfiniteLoop(
  grid: List<List<Char>>,
  currentPosition: Pair<Int, Int>,
  newObstacle: Pair<Int, Int>,
  direction: Pair<Int, Int>,
  visitedTurns: MutableSet<Pair<Int, Int>>,
): Boolean {
  val nextPosition = getNextPosition(currentPosition, direction)
  if (hasExited(nextPosition, grid)) {
    return false
  }
  if (visitedTurns.contains(nextPosition)) {
    return true
  }
  if (grid[nextPosition.first][nextPosition.second] == '#' || nextPosition == newObstacle) {
    visitedTurns.add(nextPosition)
    val nextDirection = getDirectionToTheRight(direction)
    return isInfiniteLoop(grid, currentPosition, newObstacle, nextDirection, visitedTurns)
  }
  return isInfiniteLoop(grid, nextPosition, newObstacle, direction, visitedTurns)
}
