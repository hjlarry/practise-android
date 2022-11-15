package com.example.learnkotlin


/*
    数据类是创建只包含数据的类的一个简洁的方法。
    "hashCode"、"equals"和"toString"方法将自动生成。
*/
data class User(val name: String, val id: Int) {
    override fun equals(other: Any?) = other is User && other.id == this.id
}

fun testDataClass() {
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

/*
    枚举类
*/
enum class State {
    IDLE, RUNNING, FINISHED
}
enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF),
    YELLOW(0xFFFF00);

    fun containsRed() = (this.rgb and 0xFF0000 != 0)
}

fun testEnumClass() {
    val state = State.RUNNING
    val message = when (state) {
        State.IDLE -> "It's idle"
        State.RUNNING -> "It's running"
        State.FINISHED -> "It's finished"
    }
    println(message)

    val red = Color.RED
    println(red)
    println(red.rgb)
    println(red.containsRed())
    println(Color.BLUE.containsRed())
}

fun main() {
    testDataClass()
    testEnumClass()
}