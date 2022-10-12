package com.example.learnkotlin


import kotlin.collections.HashSet
import kotlin.reflect.KProperty


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


// 委托属性，类似于python的描述符
class CustomClass {
// 如果使用val来声明p变量，则不需要MyDelegate具备setValue方法
    var p by MyDelegate()
}

class MyDelegate {
    var propValue: Any? = null

    operator fun getValue(myClass: CustomClass, property: KProperty<*>): Any? {
        return propValue
    }

    operator fun setValue(myClass: CustomClass, property: KProperty<*>, value: Any?) {
        propValue = value
    }

}