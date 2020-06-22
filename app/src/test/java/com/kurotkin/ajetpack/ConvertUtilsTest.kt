package com.kurotkin.ajetpack

import org.junit.Test

import org.junit.Assert.*

class ConvertUtilsTest {

    @Test
    fun stringToInteger() {
        assertEquals("", 0.02, 0.022, 0.00001)
        assertEquals(2, ConvertUtils.stringToInteger("2"));
        assertEquals(-2, ConvertUtils.stringToInteger("-2"));
        assertEquals(0, ConvertUtils.stringToInteger(""));
        assertEquals(0, ConvertUtils.stringToInteger("a"));
    }
}