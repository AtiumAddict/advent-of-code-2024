package com.atiumaddict.day07


val operators = charArrayOf('+', '*')

fun solveDay07First(lines: List<String>): Long {
    val equations = parseLines07(lines)
    var sumOfTestValues = 0L
    for (equation in equations) {
        val testValue = equation.first
        val numbers = equation.second
        val allPossibleCombinationsOfOperations = getCombinations(numbers.size - 1, operators)
        for (combination in allPossibleCombinationsOfOperations) {
            val result = calculate(numbers, combination)
            if (result == testValue) {
                sumOfTestValues += testValue
                break
            }
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

fun getCombinations(numberOfOperators: Int, operators: CharArray): List<CharArray> {
    val combinations = arrayListOf<CharArray>()

    fun generateCombination(current: CharArray, depth: Int) {
        if (depth == numberOfOperators) {
            combinations.add(current.clone())
            return
        }
        for (operator in operators) {
            current[depth] = operator
            generateCombination(current, depth + 1)
        }
    }

    generateCombination(CharArray(numberOfOperators), 0)
    return combinations
}

val operatorsWithConcat = charArrayOf('+', '*', '|')

fun solveDay07Second(lines: List<String>): Long {
    val equations = parseLines07(lines)
    var sumOfTestValues = 0L
    for (equation in equations) {
        val testValue = equation.first
        val numbers = equation.second
        val allPossibleCombinationsOfOperations = getCombinations(numbers.size - 1, operatorsWithConcat)
        for (combination in allPossibleCombinationsOfOperations) {
            val result = calculate(numbers, combination)
            if (result == testValue) {
                sumOfTestValues += testValue
                break
            }
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

