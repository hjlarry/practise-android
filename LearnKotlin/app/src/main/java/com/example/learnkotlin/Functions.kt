package com.example.learnkotlin


fun StringBuilder.build(block: StringBuilder.() -> Unit): StringBuilder {
    block()
    return this
}

// 自己实现一个和apply用法一样的函数
fun UseApply2() {
    val list = listOf<String>("apple", "oriange", "Pear")
    val result = StringBuilder().build {
        append("Start eating friuts: \n")
        for (fruit in list) {
            append(fruit).append('\n')
        }
        append("ear all friuts")
    }
    println(result.toString())
}

fun main() {
    println("-------------------UseApply2()-----------------")
    UseApply2()
    println("-------------------扩展函数-----------------")
    testLetterCount()
    println("-------------------高阶函数-----------------")
    println(num1AndNum2(3, 5, ::plus))
    println(num1AndNum2(3, 5, ::minus))
    n1n2lambda()

    testMatch()
}

// 传统写法，计算字符串包含的字母数
object StringUtil {
    fun letterCount(str: String): Int {
        var count = 0
        for (char in str) {
            if (char.isLetter()) {
                count++
            }
        }
        return count
    }
}

// 扩展函数，能不修改类的源码，给其添加额外的函数
fun String.lettersCount(): Int {
    var count = 0
    for (char in this) {
        if (char.isLetter()) {
            count++
        }
    }
    return count
}

fun testLetterCount() {
    val someStr = "abc123!@#"
    println(StringUtil.letterCount(someStr))
    println(someStr.lettersCount())
}


// 把方法作为参数
fun num1AndNum2(n1: Int, n2: Int, operation: (Int, Int) -> Int): Int {
    return operation(n1, n2)
}

fun plus(n1: Int, n2: Int): Int {
    return n1 + n2
}

fun minus(n1: Int, n2: Int): Int {
    return n1 - n2
}

fun n1n2lambda() {
    val num1 = 3
    val num2 = 5
    val result1 = num1AndNum2(num1, num2) { n1, n2 -> n1 + n2 }
    val result2 = num1AndNum2(num1, num2) { n1, n2 -> n1 - n2 }
    println("with lambda $result1")
    println("with lambda $result2")
}

interface isEvent

data class Event(val id: Int):isEvent
// 结合泛型
// 第一个<T>表示该方法可以操作任何类型T的对象，只能通过接口isEvent来进行泛型约束
fun <T: isEvent> hasMatch(list: List<T>, predit: (T) -> Boolean): Boolean {
    list.forEach {
//       直接predit(it)调用也是可以的
        if (predit.invoke(it)) return true
    }
    return false
}

fun notUseLambda(event: Event): Boolean {
    return event.id < 0
}

fun testMatch() {
    val items = listOf(Event(13), Event(14))
    val items2 = listOf<Int>(1, 2, 3)
    println(hasMatch(items) { it.id > 15 })
    println(hasMatch(items) { it.id > 12 })
//    使用泛型约束来确保该方法只能传入实现了isEvent接口的类
//    println(hasMatch(items2) { item -> item < 0 })
    println(hasMatch(items, ::notUseLambda))
}

