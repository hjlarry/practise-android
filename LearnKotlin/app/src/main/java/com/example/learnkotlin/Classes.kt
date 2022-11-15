package com.example.learnkotlin

class ExampleClass(val x: Int) {
    infix fun infixMemberFunction(y: Int): Int {
        return x * y
    }
}

/*
我们可以通过使用"is"操作符来检查一个对象是否是某个类型的。
如果对象通过了类型检查那么它可以作为该类型使用而不需要强制转换它。
*/
fun smartCastExample(x: Any): Boolean {
    if (x is Boolean) {
        // x自动转换为Boolean
        return x
    } else if (x is Int) {
        // x自动转换为Int
        return x > 0
    } else if (x is String) {
        // x自动转换为String
        return x.isNotEmpty()
    } else {
        return false
    }
}

data class ABC(val x: Int, val y: Int, val z:Int)

fun main() {
    //  如果使用"infix"关键字来标记一个函数, 那么可以使用中缀表示法来调用该函数。
    val fooExampleClass = ExampleClass(7)
    println(fooExampleClass infixMemberFunction 4) // => 28

    // 对象可以被解构成为多个变量
    val foo = ABC(1, 100, 4)
    val (a, b, c) = foo
    println("$a $b $c") // => 1 100 4

    println(smartCastExample("Hello, world!")) // => true
    println(smartCastExample("")) // => false
    println(smartCastExample(5)) // => true
    println(smartCastExample(0)) // => false
    println(smartCastExample(true)) // => true
}