package com.example.learnkotlin

class Money(val value: Int) {
    operator fun plus(money: Money): Money {
        val sum = money.value + value
        return Money(sum)
    }

    operator fun plus(newValue: Int): Money {
        val sum = newValue + value
        return Money(sum)
    }
}

fun main(){
    val m1 = Money(5)
    val m2 = Money(10)
    val m3 = m1 + m2
    val m4 = m2 + 30
    println(m3.value)
    println(m4.value)

    val str = "abc"
    println(str * 3)

    println(getRandomString(str))
}

private operator fun String.times(n: Int) = repeat(n)
fun getRandomString(str:String) = str * (1..20).random()