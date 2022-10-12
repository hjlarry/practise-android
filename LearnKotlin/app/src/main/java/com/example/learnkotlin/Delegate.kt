package com.example.learnkotlin


import kotlin.collections.HashSet


// 委托模式，自己实现一个Set，实际上用HashSet来实现
// 适用于大部分实现和方法调用辅助对象中的方法，少部分自己实现
class MySet<T>(val helperSet: HashSet<T>) : Set<T> {
    override val size: Int
        get() = helperSet.size

    override fun contains(element: T) = helperSet.contains(element)

    override fun containsAll(elements: Collection<T>) = helperSet.containsAll(elements)

    override fun isEmpty() = helperSet.isEmpty()

    override fun iterator(): Iterator<T> = helperSet.iterator()
}

// 借助委托关键字by辅助对象，省略很多重复代码
class MySet2<T>(val helperSet: HashSet<T>) : Set<T> by helperSet {
    override fun isEmpty() = false
    fun customMethod() {
        println("hello world!")
    }
}