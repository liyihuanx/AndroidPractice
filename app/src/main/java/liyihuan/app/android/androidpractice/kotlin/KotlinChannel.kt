package liyihuan.app.android.androidpractice.kotlin

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

/**
 * @author created by liyihuanx
 * @date 2021/2/19
 * description: 类的描述
 */
class KotlinChannel {

}

fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) send(x * x)
}



fun main() {
//    channel1()
    pipeLine2()
}

fun channel1() {
    runBlocking {
        //sampleStart
        val channel = Channel<Int>()
        launch {
            // 这里可能是非常消耗 CPU 的计算工作, 或者是一段异步逻辑, 但在这个例子中我们只是简单地发送 5 个平方数
            for (x in 1..5) {
                channel.send(x * x)
            }
        }
        // 我们在这里打印收到的整数:
        repeat(5) { println(channel.receive()) }
        println("Done!")
    }
}


fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
    var x = start
    while (true) send(x++) // 从 start 开始递增的无限整数流
}
fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
    // 2.3.4.5.6.7.8.9.10
    // 遍历一串数字，
    for (x in numbers) if (x % prime != 0) send(x)
}

fun pipeLine(){
    runBlocking {
        var cur = numbersFrom(2)
        for (i in 1..10) {
            val prime = cur.receive() // 2
            println(prime) // 2
            cur = filter(cur, prime) // (一串数字，2)

        }
        coroutineContext.cancelChildren() // 取消所有的子协程, 让 main 函数结束
    }
}


data class Ball(var hits: Int)
suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) { // 使用 for 循环不断地接收 ball
        ball.hits++
        println("$name $ball")
        delay(300) // 延迟一段时间
        table.send(ball) // 更新完Ball对象后，再扔进管道
    }
    // ping Ball(1)
    // table 里面有啥？
}
fun pipeLine2(){
    runBlocking {
        val table = Channel<Ball>() // 一个公用的通道
        launch { player("ping", table) }
        launch { player("pong", table) }
        table.send(Ball(0)) // 丢一个Ball对象，hits=0,
        delay(1000) // 延迟 1 秒
        coroutineContext.cancelChildren() // 游戏结束, 取消所有的协程
    }
}