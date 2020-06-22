package com.kurotkin.ajetpack

import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class CalculatorTest {

    lateinit var calc: Calculator

    @Before
    fun setUp() {
        calc = Calculator()
    }

    @After
    fun tearDown() {
        println("tearDown")
    }

    @Test
    fun add() {
        assertEquals(5, calc.add(2, 3))
    }

    @Test
    fun subtract() {
        assertEquals(3, calc.subtract(5, 2))
    }

    @Test
    fun multiply() {
        assertEquals(4, calc.multiply(2, 2))
    }

    @Test
    fun divide() {
        assertEquals(3, calc.divide(12, 4))
    }
}