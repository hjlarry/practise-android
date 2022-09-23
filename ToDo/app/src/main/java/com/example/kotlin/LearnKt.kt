package com.commonsware.todo

import kotlin.math.max

fun main(){
    println(largeNumber(10, 20))
    for (i in 10 downTo 0 step 2){
        println(i)
    }
}

fun largeNumber(a:Int, b:Int):Int{
    return max(a, b)
}