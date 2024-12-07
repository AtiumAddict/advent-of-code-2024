@file:Suppress("t")

package com.atiumaddict.day03

val charToAcceptedString = mapOf('m' to "", 'u' to "m", 'l' to "mu", '(' to "mul")

val regexForDigit = Regex("mul\\(\\d{0,2}(,\\d{0,2})?|mul\\(\\d{1,3},\\d{0,2}")
val regexForClosingParenthesis = Regex("mul\\(\\d{1,3},\\d{1,3}")
val regexForComma = Regex("mul\\(\\d{1,3}")

fun solveDay03First(lines: List<String>): Long {
  var result = 0L
  for (line in lines) {
    var runningMultiplication = ""
    for (char in line) {
      when {
        char.isDigit() -> {
          runningMultiplication = handleDigit(runningMultiplication, char)
        }
        char == ')' -> {
          if (regexForClosingParenthesis.matches(runningMultiplication)) {
            extractNumbers(runningMultiplication).let { result += it.first * it.second }
            runningMultiplication = ""
          }
        }
        char == ',' && regexForComma.matches(runningMultiplication) -> {
          runningMultiplication += char
        }
        charToAcceptedString.containsKey(char) &&
          charToAcceptedString[char] == runningMultiplication -> {
          runningMultiplication += char
        }
        else -> {
          runningMultiplication = ""
        }
      }
    }
  }
  return result
}

private fun extractNumbers(input: String): Pair<Int, Int> {
  var firstNumber = ""
  var secondNumber = ""
  var foundComma = false
  for (char in input) {
    if (char.isDigit() && !foundComma) {
      firstNumber += char
    } else if (char.isDigit() && foundComma) {
      secondNumber += char
    } else if (char == ',') {
      foundComma = true
    }
  }
  return Pair(firstNumber.toInt(), secondNumber.toInt())
}

private fun handleDigit(runningMultiplication: String, char: Char): String {
  var runningMultiplication1 = runningMultiplication
  if (regexForDigit.matches(runningMultiplication1)) {
    runningMultiplication1 += char
  } else {
    runningMultiplication1 = ""
  }
  return runningMultiplication1
}

val doCharacters = listOf('d', 'o', '(', ')')

fun solveDay03Second(lines: List<String>): Long {
  var result = 0L
  var enabled = true
  for (line in lines) {
    val pair = handleLineWithDos(line, result, enabled)
    result = pair.first
    enabled = pair.second
  }
  return result
}

val charToAcceptedStringForDont =
  mapOf('d' to "", 'o' to "d", 'n' to "do", '\'' to "don", 't' to "don'", '(' to "don't")

val charToAcceptedStringForDo = mapOf('d' to "", 'o' to "d", '(' to "do", ')' to "do(")

private fun handleLineWithDos(
  line: String,
  previousResult: Long,
  enabled: Boolean,
): Pair<Long, Boolean> {
  var result = previousResult
  var runningMultiplication = ""
  var runningDoOrDont = ""
  var newEnabled = enabled
  for (char in line) {
    //        println("Char: $char, Enabled: $newEnabled, runningDoOrDont: $runningDoOrDont,
    // runningMultiplication: $runningMultiplication")
    if (!newEnabled) {
      if (
        charToAcceptedStringForDo.containsKey(char) &&
          charToAcceptedStringForDo[char] == runningDoOrDont
      ) {
        runningDoOrDont += char
        if (char == ')') {
          newEnabled = true
          runningDoOrDont = ""
          runningMultiplication = ""
        }
      } else {
        runningDoOrDont = ""
      }
      continue
    }
    when {
      char.isDigit() -> {
        runningMultiplication = handleDigit(runningMultiplication, char)
      }

      char == ')' -> {
        if (regexForClosingParenthesis.matches(runningMultiplication)) {
          extractNumbers(runningMultiplication).let { result += it.first * it.second }
          runningMultiplication = ""
        } else if (runningDoOrDont == "don't(") {
          newEnabled = false
          runningDoOrDont = ""
          runningMultiplication = ""
        } else {
          runningMultiplication = ""
          runningDoOrDont = ""
        }
      }

      char == ',' && regexForComma.matches(runningMultiplication) -> {
        runningMultiplication += char
      }

      charToAcceptedString.containsKey(char) &&
        charToAcceptedString[char] == runningMultiplication -> {
        runningMultiplication += char
      }
      charToAcceptedStringForDont.containsKey(char) &&
        charToAcceptedStringForDont[char] == runningDoOrDont -> {
        runningDoOrDont += char
        if (runningDoOrDont == "don't(") {
          newEnabled = false
          runningDoOrDont = ""
          runningMultiplication = ""
        }
      }
      charToAcceptedStringForDo.containsKey(char) &&
        charToAcceptedStringForDo[char] == runningDoOrDont -> {
        runningDoOrDont += char
        if (runningDoOrDont == "do()") {
          newEnabled = true
          runningDoOrDont = ""
          runningMultiplication = ""
        }
      }
      else -> {
        runningMultiplication = ""
        runningDoOrDont = ""
      }
    }
  }
  return Pair(result, newEnabled)
}
