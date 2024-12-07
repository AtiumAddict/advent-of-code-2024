package com.atiumaddict.day07

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateTest {

    @Test
    fun testAdditionAndMultiplication() {
        val numbers = listOf(81L, 40L, 27L)
        val combination = charArrayOf('+', '*')
        val result: Long = calculate(numbers, combination)
        assertEquals(3267L, result)
    }


    @Test
    fun testAdditionMultiplicationAndConcatenation() {
        val numbers = listOf(6L, 8L, 6L, 15L)
        val combination = charArrayOf('*', '|', '*')
        val result: Long = calculate(numbers, combination)
        assertEquals(7290, result)
    }
}

class CalculateWithConcatTest {

}
