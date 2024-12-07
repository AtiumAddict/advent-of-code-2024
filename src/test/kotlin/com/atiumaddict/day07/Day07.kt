package com.atiumaddict.day07

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class CalculateTest :
  FunSpec({
    test("should correctly calculate the result for addition and multiplication") {
      val numbers = listOf(81L, 40L, 27L)
      val combination = charArrayOf('+', '*')
      val result: Long = calculate(numbers, combination)
      result shouldBe 3267L
    }

    test("should correctly calculate the result for addition, multiplication, and concatenation") {
      val numbers = listOf(6L, 8L, 6L, 15L)
      val combination = charArrayOf('*', '|', '*')
      val result: Long = calculate(numbers, combination)
      result shouldBe 7290
    }
  })
