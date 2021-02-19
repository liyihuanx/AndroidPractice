package liyihuan.app.android.androidpractice.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import liyihuan.app.android.androidpractice.R
import kotlin.system.measureTimeMillis

class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        functionA(1, 2) {
            it
        }
    }
}


/**
 *
 */
fun functionA(a: Int, b: Int, callback: ((c: Int) -> Int)) {
    callback.invoke(a + b)
}


fun main() {
//    runBlocking<Unit> { // 阻塞函数
//        val time = measureTimeMillis {
//            // async异步
////            val one = async { doSomethingUsefulOne() }
////            val two = async { doSomethingUsefulTwo() }
//
//            //
//            val three = doSomethingUsefulOne()
//            if (three == 13) {
//                doSomethingUsefulTwo()
//            }
//
//        }
//        println("Completed in $time ms")
//    }
    xieCheng3()
}




/**
 * 协程
 */
fun xieCheng() {
    GlobalScope.launch { // 在后台启动新的协程, 然后继续执行当前程序
        delay(1000L) // 非阻塞, 等待 1 秒 (默认的时间单位是毫秒)
        println("World!") // 等待完成后打印信息
    }
    println("Hello,") // 当协程在后台等待时, 主线程继续执行
    // 手动等待
    Thread.sleep(2000L) // 阻塞主线程 2 秒, 保证 JVM 继续存在
}

suspend fun xieCheng2(){
    val job = GlobalScope.launch { // 启动新的协程, 并保存它的执行任务的引用
        delay(1000L)
        println("World!")
    }
    println("Hello,")
    // 相当于自动等待着
    job.join() // 等待, 直到子协程执行完毕
}

// coroutineScope 在等待子协程运行时, 不会阻塞当前线程
fun xieCheng3(){

    runBlocking { // this: CoroutineScope
        launch {
            delay(100L)
            println("Task from runBlocking") //
        }
        println("Task from runBlocking scope") //

        coroutineScope { // 创建新的协程作用范围
            launch {
                delay(200L)
                println("Task from nested launch") //
            }
            delay(100L)
            println("Task from coroutine scope") //  在嵌套的 launch 之前, 这一行会打印
        }
        println("Coroutine scope is over") // 直到嵌套的 launch 运行结束后, 这一行才会打印
    }
}

// 在 GlobalScope 作用范围内启动的活跃的协程, 不会保持应用程序的整个进程存活. 它们的行为就象守护线程一样.
fun xieCheng4(){
    runBlocking {
        GlobalScope.launch {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(1300L)
    }

}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // 假设我们在这里做了某些有用的工作
    println("doSomethingUsefulOne")
    return 13
}
suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // 假设我们在这里也做了某些有用的工作
    println("doSomethingUsefulTwo")
    return 29
}
