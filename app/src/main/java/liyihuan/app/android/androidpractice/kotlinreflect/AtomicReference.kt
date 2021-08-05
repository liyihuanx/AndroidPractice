package liyihuan.app.android.androidpractice.kotlinreflect

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.concurrent.atomic.AtomicReference

/**
 * @author created by liyihuanx
 * @date 2021/8/5
 * @description: 类的描述
 */
@RequiresApi(Build.VERSION_CODES.N)
fun main() {

    val status = AtomicReference<Status>()

    // TODO 返回的是pre
    // 传入onCreate得到onCreate 先返回再更新
    status.set(Status.onCreate())
    val andUpdate = status.getAndUpdate {
        when (it) {
            is Status.onCreate -> {
                Status.onResume()
            }
            is Status.onResume -> {
                Status.onFinish()
            }
            is Status.onFinish -> {
                it
            }
        }
    }
    println("getAndUpdate:$andUpdate")


    // TODO 返回的是next
    // 传入onCreate得到onResume 先更新在返回
    status.set(Status.onCreate())
    val updateAndGet = status.updateAndGet {
        when (it) {
            is Status.onCreate -> {
                Status.onResume()
            }
            is Status.onResume -> {
                Status.onFinish()
            }
            is Status.onFinish -> {
                it
            }
        }
    }
    println("getAndUpdate:$updateAndGet")

}


sealed class Status {

    class onCreate : Status()
    class onResume : Status()
    class onFinish : Status()

}