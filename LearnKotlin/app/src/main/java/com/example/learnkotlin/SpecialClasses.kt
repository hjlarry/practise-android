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
    RED(0xFF0000), GREEN(0x00FF00), BLUE(0x0000FF), YELLOW(0xFFFF00);

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

/*
密封类，其本身属于抽象类，无法实例化，也有密封接口
目的是限制类在包以外被继承
同时，when也不需要else，这点和枚举类差不多
*/
sealed class Mammal(val name: String)

class Cats(val catName: String) : Mammal(catName)
class Human(val humanName: String, val job: String) : Mammal(humanName)

fun greetMammal(mammal: Mammal): String {
    when (mammal) {
        is Human -> return "Hello ${mammal.name}; You're working as a ${mammal.job}"
        is Cats -> return "Hello ${mammal.name}"
    }
}

/*
object, 单例对象
*/

object DoAuth {
    val x = 100
    fun takeParams(username: String) {
        println("input auth params is $username")
    }
}

class Ben {
    val yy = 100

//  伴生对象类似于Java的静态成员，用于弥补kotlin中没有static关键字
//  其名称Bonger可以省略
    companion object Bonger {
    fun getBon() {
//            能获取到全局变量y，但是获取不到类成员
            println(y)
//            println(yy)
        }
    }
}

fun testObject() {
    DoAuth.takeParams("hello")
    println(DoAuth.x)

    val abc = object {
        val x = 10
        val y = 20
    }
    val total = abc.x + abc.y
    println(total)
    Ben.getBon()
}

fun main() {
    testDataClass()
    testEnumClass()
    println(greetMammal(Cats("Snow")))
    testObject()
}