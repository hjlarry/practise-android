package com.example.learnkotlin

/*
    数据类是创建只包含数据的类的一个简洁的方法。
    "hashCode"、"equals"和"toString"方法将自动生成。
*/
data class User(val name: String, val id: Int) {
    override fun equals(other: Any?) = other is User && other.id == this.id
}

fun testDataclass() {
    val user = User("Alex", 1)
    println(user)

    val secondUser = User("Alex", 1)
    val thirdUser = User("Max", 2)
    println("user == secondUser: ${user == secondUser}")
    println("user == thirdUser: ${user == thirdUser}")

    // hashCode() function
    println(user.hashCode())
    println(secondUser.hashCode())
    println(thirdUser.hashCode())

    // copy() function
    println(user.copy())
    println(user === user.copy())
    println(user.copy("Max"))
    println(user.copy(id = 3))

    // 自动生成的componentN方法，可以按声明的顺序获取属性值
    println("name = ${user.component1()}")
    println("id = ${user.component2()}")
}


fun main() {
    testDataclass()
}