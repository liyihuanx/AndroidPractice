package liyihuan.app.android.androidpractice.kotlinreflect

import java.lang.IllegalArgumentException
import java.security.Key
import kotlin.reflect.KClass
import kotlin.reflect.full.allSupertypes
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.typeOf

/**
 * @author created by liyihuanx
 * @date 2021/8/4
 * @description: 类的描述
 */
@ExperimentalStdlibApi
fun main() {
//    TODO toMap的使用
//    val map = arrayListOf("1", "2").map {
//        "name$it" to it
//    }.toMap()
//    print("$map")

//    TODO kotlin的反射的使用
//  KotlinReflect::class
//  KotlinReflect::class.java
    var kClazz = KotlinReflect::class
//    println("$kClazz")
//    TODO 内部类
//    println("${kClazz.nestedClasses}")

//    TODO 类或可调用类型的类型参数的声明 [T,R]
//    println(kClazz::class.typeParameters)

//    TODO 参数和类型
//    kClazz.declaredMemberProperties.forEach {
//        println("${it.name} ---  ${it.returnType}")
//    }
//    TODO 返回主构造函数
//    println("${kClazz.primaryConstructor}")
//    TODO 返回所有构造函数
//    println("${kClazz.constructors}")
//    TODO 主构造器的参数
//    println("${UserData::class.primaryConstructor!!.parameters}")

//    TODO 该类所继承的所有类
//    val childClass = ChildClass::class
//    childClass.supertypes.forEach {
//        println(it)
//    }

//    TODO 该类的方法
//    GrandSonClass::class.declaredFunctions.forEach {
//        println(it)
//        println(it.returnType)  // 返回值
//        println(it.parameters)  // 方法参数
//    }

//    TODO arguments 获取到泛型参数列表
    KotlinReflect::class.declaredFunctions.forEach {
        println(it.returnType.arguments)
    }
}


class KotlinReflect<T : Any, R : Any>(val name: String = "0") {
    constructor() : this("1") {
    }



    private var age: Int = 0
    private var sex: T? = null

    fun fun1(): T? {
        return sex
    }

    private class NestedClass {
    }
}

data class UserData(var name: String, var age: String) {

}

abstract class FatherClass<T>() {

}

open class ChildClass : FatherClass<String>() {
    fun getX(): List<UserData> {
        return arrayListOf(UserData("1", "2"))
    }
}

interface GrandSonClass {
    fun getUser(): String = "1"
    fun getUser1(name: String) {}
    fun getUser2(): List<UserData>
}


