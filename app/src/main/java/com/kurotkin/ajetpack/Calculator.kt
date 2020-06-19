package com.kurotkin.ajetpack

class Calculator {
    fun add(a:Int, b: Int) = a + b

    fun subtract(a:Int, b: Int) = a - b

    fun multiply(a:Int, b: Int) = a * b

    fun divide(a:Int, b: Int) : Int{
        if(b != 0) return a / b
        return 0
    }
}