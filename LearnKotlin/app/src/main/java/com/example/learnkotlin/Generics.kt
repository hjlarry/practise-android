package com.example.learnkotlin

// 定义泛型类
class MyClass<T> {
    //    定义泛型方法
    fun method(param: T): T {
        return param
    }
}

// 调用时再指定具体类型和参数
val mycls = MyClass<Int>()
val res = mycls.method(123)


class MyClass2 {
    //  也可以不定义泛型类，只定义泛型方法
    fun <T> method(param: T): T {
        return param
    }

    //  可以限制传入该方法的类型只能是Int、Float、Double等
    fun <T : Number> method2(param: T): T {
        return param
    }
}

// 则这样调用，能根据参数推导出类型
val mycls2 = MyClass2()
val res2 = mycls2.method(123)


// 那么就可以自己写一个apply函数
fun <T> T.apply2(block: T.() -> Unit): T {
    block()
    return this
}