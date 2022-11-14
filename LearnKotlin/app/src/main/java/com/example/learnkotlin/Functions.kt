package com.example.learnkotlin

// 函数的可变参数可使用 "vararg" 关键字来修饰
fun varargExample(vararg names: Int) {
    println("Argument has ${names.size} elements")
}

// 当函数只包含一个单独的表达式时，大括号可以省略。
fun odd(x: Int): Boolean = x % 2 == 1
// 如果返回值类型可以推断，那么我们不需要指定它。
fun even(x: Int) = x % 2 == 0
// 函数可以用函数作为参数并且可以返回函数。
fun not(f: (Int) -> Boolean) : (Int) -> Boolean {
    return {n -> !f.invoke(n)}
}
// 普通函数可以用::运算符传入引用作为函数参数。
val notOdd = not(::odd)
val notEven = not(::even)
// lambda 表达式可以直接作为参数传递。
val notZero = not {n -> n == 0}
/*
如果一个 lambda 表达式只有一个参数
那么它的声明可以省略（连同->），内部以 "it" 引用。
*/
val notPositive = not {it > 0}

// 扩展是用来给一个类添加新的功能的。
fun String.remove(c: Char): String {
    return this.filter {it != c}
}

// 自己实现一个和apply用法一样的函数
fun StringBuilder.build(block: StringBuilder.() -> Unit): StringBuilder {
    block()
    return this
}
fun UseApply2() {
    val result = StringBuilder().build {
        append("Start eating friuts: \n")
        append("ear all friuts")
    }
    println(result.toString())
}

fun main() {
    println("-------------------可变参数-----------------")
    varargExample() // => 传入 0 个参数
    varargExample(1) // => 传入 1 个参数
    varargExample(1, 2, 3) // => 传入 3 个参数
    println("-------------------高阶函数-----------------")
    for (i in 0..4) {
        println("${notOdd(i)} ${notEven(i)} ${notZero(i)} ${notPositive(i)}")
    }
    println("-------------------扩展函数-----------------")
    println("Hello, world!".remove('l')) // => Heo, word!
    UseApply2()
}


