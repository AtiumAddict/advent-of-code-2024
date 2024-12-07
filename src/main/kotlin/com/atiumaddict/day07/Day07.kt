package com.atiumaddict.day07

val operators = charArrayOf('+', '*')

fun solveDay07First(lines: List<String>): Long {
  val equations = parseLines07(lines)
  var sumOfTestValues = 0L
  for (equation in equations) {
    val testValue = equation.first
    val numbers = equation.second
    val hasCorrectSolution = findCorrectCombination(numbers, testValue, operators)
    if (hasCorrectSolution) {
      sumOfTestValues += testValue
    }
  }
  return sumOfTestValues
}

fun calculate(numbers: List<Long>, combination: CharArray): Long {
  var result = numbers[0]

  for (i in combination.indices) {
    when (combination[i]) {
      '*' -> result *= numbers[i + 1]
      '+' -> result += numbers[i + 1]
      '|' -> result = (result.toString() + numbers[i + 1].toString()).toLong()
    }
  }
  return result
}

fun findCorrectCombination(numbers: List<Long>, testValue: Long, operators: CharArray): Boolean {
  val combinations = arrayListOf<CharArray>()
  val numberOfOperators = numbers.size - 1

  fun generateCombination(current: CharArray, depth: Int): Boolean {
    if (depth == numberOfOperators) {
      val result = calculate(numbers, current)
      if (result == testValue) {
        return true
      }
      combinations.add(current.clone())
      return false
    }
    for (operator in operators) {
      current[depth] = operator
      val isCorrect = generateCombination(current, depth + 1)
      if (isCorrect) {
        return true
      }
    }
    return false
  }

  val hasCorrectSolution = generateCombination(CharArray(numberOfOperators), 0)
  return hasCorrectSolution
}

val operatorsWithConcat = charArrayOf('+', '*', '|')

fun solveDay07Second(lines: List<String>): Long {
  val equations = parseLines07(lines)
  var sumOfTestValues = 0L
  for (equation in equations) {
    val testValue = equation.first
    val numbers = equation.second
    val hasCorrectSolution = findCorrectCombination(numbers, testValue, operatorsWithConcat)
    if (hasCorrectSolution) {
      sumOfTestValues += testValue
    }
  }
  return sumOfTestValues
}

fun parseLines07(lines: List<String>): List<Pair<Long, List<Long>>> {
  val equations = arrayListOf<Pair<Long, List<Long>>>()
  lines.forEach { line ->
    val parts = line.split(": ").map { it.trim() }
    val testValue = parts[0].toLong()
    val numbers = parts[1].split(" ").map { it.toLong() }
    equations.add(Pair(testValue, numbers))
  }
  return equations
}
