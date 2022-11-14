package com.example.learnkotlin

class ExampleClass(val x: Int) {
    infix fun infixMemberFunction(y: Int) : Int {
        return x * y
    }
}

/*
    数据类是创建只包含数据的类的一个简洁的方法。
    "hashCode"、"equals"和"toString"方法将自动生成。
*/
data class DataClassExample (val x: Int, val y: Int, val z: Int)
val fooData = DataClassExample(1, 2, 4)
// 数据类有一个"copy"函数
val fooCopy = fooData.copy(y = 100)

/*
我们可以通过使用"is"操作符来检查一个对象是否是某个类型的。
如果对象通过了类型检查那么它可以作为该类型使用而不需要强制转换它。
*/
fun smartCastExample(x: Any) : Boolean {
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

fun main(){
    //  如果使用"infix"关键字来标记一个函数, 那么可以使用中缀表示法来调用该函数。
    val fooExampleClass = ExampleClass(7)
    println(fooExampleClass infixMemberFunction 4) // => 28

    println(fooData) // => DataClassExample(x=1, y=2, z=4)
    println(fooCopy) // => DataClassExample(x=1, y=100, z=4)
    // 对象可以被解构成为多个变量
    val (a, b, c) = fooCopy
    println("$a $b $c") // => 1 100 4

    println(smartCastExample("Hello, world!")) // => true
    println(smartCastExample("")) // => false
    println(smartCastExample(5)) // => true
    println(smartCastExample(0)) // => false
    println(smartCastExample(true)) // => true
}