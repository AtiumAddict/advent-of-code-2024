package com.atiumaddict.day04

val letterSequence = listOf('X', 'M', 'A', 'S')

fun solveDay04First(lines: List<String>): Long {
  val grid = lines.map { it.toList() }
  var result = 0L
  for ((i, row) in grid.withIndex()) {
    for ((j, char) in row.withIndex()) {
      if (char == 'X') {
        result += searchFromX(grid, i, j)
      }
    }
  }
  return result
}

fun searchFromX(grid: List<List<Char>>, row: Int, column: Int): Int {
  val possibleNextSteps = getNextSteps(row, column)
  var count = 0
  for ((nextRow, nextCol) in possibleNextSteps) {
    if (
      nextRow in grid.indices && nextCol in grid[nextRow].indices && grid[nextRow][nextCol] == 'M'
    ) {
      if (
        searchForNextLetter(
          grid,
          nextRow,
          nextCol,
          possibleNextSteps.indexOf(Pair(nextRow, nextCol)),
        )
      ) {
        count++
      }
    }
  }
  return count
}

fun searchForNextLetter(grid: List<List<Char>>, row: Int, column: Int, direction: Int): Boolean {
  if (grid[row][column] == 'S') {
    return true
  }
  val letterIndex = letterSequence.indexOf(grid[row][column])
  val possibleNextSteps = getNextSteps(row, column)
  val (nextRow, nextCol) = possibleNextSteps[direction]
  if (
    nextRow in grid.indices &&
      nextCol in grid[nextRow].indices &&
      grid[nextRow][nextCol] == letterSequence[letterIndex + 1]
  ) {
    return searchForNextLetter(grid, nextRow, nextCol, direction)
  }
  return false
}

private fun getNextSteps(row: Int, column: Int): List<Pair<Int, Int>> {
  return listOf(
    Pair(row, column + 1),
    Pair(row + 1, column),
    Pair(row, column - 1),
    Pair(row - 1, column),
    Pair(row + 1, column + 1),
    Pair(row - 1, column - 1),
    Pair(row + 1, column - 1),
    Pair(row - 1, column + 1),
  )
}

fun solveDay04Second(lines: List<String>): Long {
  val grid = lines.map { it.toList() }
  var result = 0L
  for ((i, row) in grid.withIndex()) {
    for ((j, char) in row.withIndex()) {
      if (i < 1 || i >= grid.size - 1 || j < 1 || j >= grid[i].size - 1) {
        continue
      }
      if (char == 'A') {
        val isXMAS = searchFromA(grid, i, j)
        if (isXMAS) result++
      }
    }
  }
  return result
}

fun searchFromA(grid: List<List<Char>>, row: Int, column: Int): Boolean {
  val leftLine = Pair(grid[row - 1][column - 1], grid[row + 1][column + 1])
  val rightLine = Pair(grid[row - 1][column + 1], grid[row + 1][column - 1])
  return isMASPair(leftLine) && isMASPair(rightLine)
}

private fun isMASPair(line: Pair<Char, Char>) =
  (line.first == 'M' && line.second == 'S') || (line.second == 'M' && line.first == 'S')
