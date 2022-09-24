package com.example.learnkotlin

open class Animal(var age:Int) {
    var name = ""
}

class Cat(var type: String, age:Int) :Animal(age), Cry{
    constructor(age: Int) : this("hello", 0){
    }

    override fun docry() {
        println("an animal cry")
    }
}

class People:Cry{
    override fun docry() {
        println("people cry")
    }

}

fun doSome(c:Cry){
    c.docry()
}

fun main(){
    val c = Cat("mycat", 20)
    val c2 = Cat(15)
    println(c.type)
    println(c2.age)

    val p = People()
    doSome(c2)
    doSome(p)


}