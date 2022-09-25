package com.example.learnkotlin

class Functions {
}

fun notUseWith(){
    val list = listOf<String>("apple", "oriange", "Pear")
    val builder = StringBuilder()
    builder.append("Start eating friuts: \n")
    for (fruit in list){
        builder.append(fruit).append('\n')
    }
    builder.append("ear all friuts")
    val result = builder.toString()
    println(result)
}

/*
with函数接收两个参数，第一个参数的对象会被代入第二个参数的lambda表达式中
并且使用lambda表达式的最后一行代码作为返回值返回
run函数和with函数非常类似，某个对象调用则把该对象传入lambda表达式，同样返回最后一行代码
apply函数和run函数非常类似，但不会返回特定值，只返回调用对象本身
 */
fun UseWith(){
    val list = listOf<String>("apple", "oriange", "Pear")
    val result = with(StringBuilder()){
        append("Start eating friuts: \n")
        for (fruit in list){
            append(fruit).append('\n')
        }
        append("ear all friuts")
        toString()
    }
    println(result)
}

fun UseRun(){
    val list = listOf<String>("apple", "oriange", "Pear")
    val result = StringBuilder().run{
        append("Start eating friuts: \n")
        for (fruit in list){
            append(fruit).append('\n')
        }
        append("ear all friuts")
        toString()
    }
    println(result)
}

fun UseApply(){
    val list = listOf<String>("apple", "oriange", "Pear")
    val result = StringBuilder().apply{
        append("Start eating friuts: \n")
        for (fruit in list){
            append(fruit).append('\n')
        }
        append("ear all friuts")
    }
    println(result.toString())
}

fun main(){
    notUseWith()
    println("-------------------UseWith()-----------------")
    UseWith()
    println("-------------------UseRun()-----------------")
    UseRun()
    println("-------------------UseApply()-----------------")
    UseApply()
}