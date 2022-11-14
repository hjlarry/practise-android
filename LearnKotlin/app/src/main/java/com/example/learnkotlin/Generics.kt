package com.example.learnkotlin


// 泛型的目的：减少代码，不用为每一种类型都写一套方法，并能通过通配符做一定的类型约束
// 编译后的字节码是没有传入的泛型类型的，这个过程叫泛型擦除；是通过class文件的Signature和LocalVariableTypeTable等属性来识别出泛型
fun test() {
    val items: ArrayList<String> = ArrayList()
    items.add("a")
//    编译器通过泛型检测到传入的不是字符串，不会编译通过
//    items.add(123)
//    编译器通过泛型，取值的时候做一个强制的类型转换
    val b = items.get(0)
}

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
val res2 = mycls2.method2(1.2)


fun <T> test2(value: T) {
//    因为泛型擦除，获取不到T的类型
//    val tmp = T::class.java
}

// 而内联函数+泛型实化，可以获取到
inline fun <reified T : Number> test3(value: T) {
    val tmp = T::class.java
    println(tmp)
}
// 使用示例
//inline fun <reified T>startActivity(context: Context){
//    val intent = Intent(context, T::class.java)
//    context.startActivity(intent)
//}


// 协变  逆变
open class Father
class Son : Father()
class Girl : Father()
class SimpleData<T> {
    private var data: T? = null
    fun set(t: T?) {
        data = t
    }

    fun get(): T? {
        return data
    }
}

fun handleSimpleData(data: SimpleData<Father>) {
    val girl = Girl()
    data.set(girl)
}

fun test4(){
    val son = Son()
    val data = SimpleData<Son>()
    data.set(son)
//    编译报错，虽然son是father的子类，但是SimpleData<Son>不是SimpleData<Father>的子类
//    编译器如果不这样处理，就可能造成SimpleData中的Son被强制转换为了Girl，两个子类转换显然是不安全的
//    handleSimpleData(data)
}

// 协变就要求不能改变数据
// 所以T类型的数据要作为参数，就要声明@UnsafeVariance，例如
// fun set(t: @UnsafeVariance T)
// 这样之后编译器进行类型转换出了异常就需要开发者负责了
class SimpleData2<out T> {
    private var data: T? = null
    fun get(): T? {
        return data
    }
}
fun handleSimpleData2(data: SimpleData2<Father>) {
    val girl = Girl()
    data.get()
}
fun test5(){
    val son = Son()
    val data = SimpleData2<Son>()
//  现在通过协变，只读取而不写入数据，就没有问题
    handleSimpleData2(data)
}

/*
协变意味着，若B是A的子类，则可以认为 Class<B>也是 Class<A>的子类
逆变则是 Class<A> 是 Class<B>的子类，数据只能写入，而不能读取
 */
fun main() {
    test3(1)
    test3(1.2)

}