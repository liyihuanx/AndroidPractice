package liyihuan.app.android.androidpractice.kotlin

import kotlinx.coroutines.*

/**
 * @author created by liyihuanx
 * @date 2021/2/19
 * description: 类的描述
 */
class KotlinDispatcher {

}

fun main() {
    dispatcher1()
}

fun dispatcher1() {
    runBlocking<Unit> {

        launch { // 使用父协程的上下文, 也就是 main 函数中的 runBlocking 协程
            println(" delay-before main runBlocking : I'm working in thread ${Thread.currentThread().name}")
            delay(500)
            println("delay-after main runBlocking : I'm working in thread ${Thread.currentThread().name}")

        }
        launch(Dispatchers.Unconfined) { // 非受限 -- 将会在主线程中执行
            println("delay-before Unconfined : I'm working in thread ${Thread.currentThread().name}")
            delay(500)
            println("delay-after Unconfined : I'm working in thread ${Thread.currentThread().name}")

        }
        launch(Dispatchers.Default) { // 会被派发到 DefaultDispatcher
            println("delay-before Default : I'm working in thread ${Thread.currentThread().name}")

        }
        launch(newSingleThreadContext("MyOwnThread")) { // 将会在独自的新线程内执行
            println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
        }

    }
}