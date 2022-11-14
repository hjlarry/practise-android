package com.example.learnkotlin

//  我们可以使用"listOf"函数来创建一个不可变的list。
val fooList = listOf("a", "bb", "c")
// 可以使用"mutableListOf"函数来创建一个可变的list。
val fooMutableList = mutableListOf("a", "b", "c")
// 我们可以使用"setOf"函数来创建一个set。
val fooSet = setOf("a", "b", "c")
// 我们可以使用"mapOf"函数来创建一个map。
val fooMap = mapOf("a" to 8, "b" to 7, "c" to 9)

/*
序列表示惰性求值集合。
我们可以使用"generateSequence"函数来创建一个序列。
*/
val fooSequence = generateSequence(1, {it + 1})
val x = fooSequence.take(10).toList()
// 一个用序列来生成斐波那契数列的例子。
fun fibonacciSequence() : Sequence<Long> {
    var a = 0L
    var b = 1L

    fun next() : Long {
        val result = a + b
        a = b
        b = result
        return a
    }

    return generateSequence(::next)
}
val y = fibonacciSequence().take(10).toList()

// Kotlin为集合提供高阶函数。
val z = (1..9).map {it * 3}
    .filter {it < 20}
    .groupBy {it % 2 == 0}
    .mapKeys {if (it.key) "even" else "odd"}


fun main() {

    println(fooList.size) // => 3
    println(fooList.first()) // => a
    println(fooList.last()) // => c
    println(fooList[1]) // => b

    fooMutableList.add("d")
    println(fooMutableList.last()) // => d
    println(fooMutableList.size) // => 4

    println(fooSet.contains("a")) // => true
    println(fooSet.contains("z")) // => false

    println(fooMap["a"]) // => 8

    println(x) // => [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    println(y) // => [1, 1, 2, 3, 5, 8, 13, 21, 34, 55]
    println(z) // => {odd=[3, 9, 15], even=[6, 12, 18]}

}

