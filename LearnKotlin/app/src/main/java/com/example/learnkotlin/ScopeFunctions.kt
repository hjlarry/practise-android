package com.example.learnkotlin

data class IntPropertyBag(private val pieces: MutableMap<String, Int> = mutableMapOf()) {
    fun set(key: String, value: Int) {
        pieces[key] = value
    }
}

fun useLet(){
    val sometime = IntPropertyBag().let {
        it.set("ID", 123)
        it.set("YEAR", 1987)
        it.toString()
    }
    println(sometime)
}

fun useApply() {
    val sometime = IntPropertyBag().apply {
        set("ID", 123)
        set("YEAR", 1987)
    }
    println(sometime)
}

fun useRun(){
    val sometime = IntPropertyBag().run {
        set("ID", 123)
        set("YEAR", 1987)
        toString()
    }
    println(sometime)
}

fun useWith(){
    val sometime = with(IntPropertyBag()) {
        set("ID", 123)
        set("YEAR", 1987)
        toString()
    }
    println(sometime)
}

fun useAlso(){
    val sometime = IntPropertyBag().also {
        it.set("ID", 123)
        it.set("YEAR", 1987)
    }
    println(sometime)
}

/*
Function | How you call It | How you reference the object | What it returns
let()      call on an obj                 it                  block result
apply()    call on an obj                 this                 the object
run()      call on an obj                 this                block result
with()     pass obj as param              this                block result
also()     call on an obj                 it                   the object
use()      call on a closeable            it                  block result

 */

fun main(){
    useLet()
    useApply()
    useRun()
    useWith()
    useAlso()
}