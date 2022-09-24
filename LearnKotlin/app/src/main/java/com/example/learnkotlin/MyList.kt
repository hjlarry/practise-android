package com.example.learnkotlin

class MyList {
}

fun addList(): ArrayList<String> {
    val list = ArrayList<String>()
    list.add("sss")
    list.add("ddd")
    return list
}

fun main() {
    for (i in addList()) {
        println(i)
    }
    val list = listOf("abc",  "apple", "oriange")
    for (i in list) {
        println(i)
    }

    val maxLength = list.maxByOrNull { it.length }
    println(maxLength)

    val newList = list.map { it.uppercase() }
    for (i in newList) {
        println(i)
    }
}

/*
一、lambda函数可以这样理解
val list = listOf("abc",  "apple", "oriange")
val lambda =
val maxLengthFruit = list.maxByOrNull(lambda)

maxByOrNull方法接收的参数是lambda表达式，而lambda作为函数的最后一个参数时可以放在函数外面，就变成：
val maxLengthFruit = list.maxByOrNull(){friut:String -> friut.length}
如果lambda是函数的唯一参数，那么函数不需要括号：
val maxLengthFruit = list.maxByOrNull{friut:String -> friut.length}
由于类型推导：
val maxLengthFruit = list.maxByOrNull{friut -> friut.length}
当lambda只有一个入参时，可以不用声明，使用it替代:
val maxLengthFruit = list.maxByOrNull{it.length}

 */