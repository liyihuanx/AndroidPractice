package liyihuan.app.android.androidpractice.kotlinreflect

/**
 * @author created by liyihuanx
 * @date 2021/8/5
 * @description: 类的描述
 */
fun main() {
//    var x = 0
//    // 返回 ()->Unit 匿名函数
//    val lambdaf1 = lambdaF1 {
//        x + 1  // block函数
//    }
//    // 返回的 ()->Unit 可以接着执行
//    val lambdaReturn = lambdaf1()
//    println(lambdaReturn)

    lambdaF2 {
//        val innerFunction2 = innerFunction2()
//        println(innerFunction2)

        val kFunction0 = ::innerFunction2
        println(kFunction0.invoke())


        val innerFunction3 = innerFunction3("world")
        println(innerFunction3)

        lambdaF {
            println("000")
        }
    }


    lambdaF4({

    }, {
        "1234"
    })
}

// 多个函数
fun lambdaF4(block1: () -> Unit, block2: () -> String) {
    block1()
    val block21 = block2()
    println(block21)
}



fun lambdaF(block: () -> Unit) {
    block()
}


// () -> Int 看做一个类型
fun lambdaF1(block: () -> Int): () -> String {
    val block1 = block.invoke()
    return {
        (block1 + 2).toString()
    }
}


// KotlinLambda类的东西 只能在lambdaF2{} 这个闭包中执行
fun lambdaF2(block: KotlinLambda.() -> Unit) {
    block(childLambda())
}

class childLambda : KotlinLambda() {
    override fun innerFunction2(): String {
        return "hello"
    }

    override fun innerFunction3(str: String): String {
        return str
    }
}

abstract class KotlinLambda internal constructor() {
    abstract fun innerFunction2(): String

    abstract fun innerFunction3(str: String): String
}