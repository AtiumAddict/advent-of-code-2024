package com.atiumaddict.adventofcode.day01

import java.io.File

fun getLists(): Pair<List<Long>, List<Long>> {
  val inputFile = File("src/main/kotlin/com/atiumaddict/day01/finalInput.txt")
  val list1 = mutableListOf<Long>()
  val list2 = mutableListOf<Long>()

  inputFile.readLines().forEach { line ->
    val (a, b) = line.split("\\s+".toRegex()).map { it.trim().toLong() }
    list1.add(a)
    list2.add(b)
  }

  return Pair(list1, list2)
}

fun solveDay01First(): Long {
  var result: Long = 0
  val (list1, list2) = getLists()
  val list1Sorted = list1.sorted()
  val list2Sorted = list2.sorted()
  for (i in list1Sorted.indices) {
    result += Math.abs(list1Sorted[i] - list2Sorted[i])
  }
  return result
}

fun solveDay01Second(): Long {
  var result = 0L
  val (list1, list2) = getLists()
  val appearances = HashMap<Long, Long>()
  list2.forEach { number: Long -> appearances[number] = appearances.getOrDefault(number, 0L) + 1 }
  list1.forEach { number -> result += number * appearances.getOrDefault(number, 0L) }
  return result
}
