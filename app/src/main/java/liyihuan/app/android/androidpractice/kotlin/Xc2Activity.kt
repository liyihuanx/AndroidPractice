package liyihuan.app.android.androidpractice.kotlin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class Xc2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bg {
            doWork {

            }
            catchError {

            }
        }

        testCallBack {
            // 给方法体内的 方法变量赋值，在invoke调用时就会走到这里来了
            doFun1 {

            }
        }
        var num = 0

        GlobalScope.launch(Dispatchers.Main, start = CoroutineStart.DEFAULT) {
            Log.d("QWER","Hello-World")
            num = 1
        }



        Log.d("QWER", "onCreate:$num ")
//        val sp: SharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE)
//        val edit = sp.edit()
//        var count = 0
//        repeat(1000) {
//            count++
//            edit.putInt("count$count", count)
//            edit.commit()
//        }
//        Log.d("QWER", "$count")
    }
}


fun testCallBack( call: testWrap.() -> Unit){
    var block = testWrap()
    // 把一个有好几个方法的方法bean放入，初始化
    call.invoke(block)

    // 根据条件分别调用方法体内的方法
    block.call.invoke("1")

}

class testWrap { // 相当于一个bean类的感觉
    var call: (String) -> Unit = {}
    fun doFun1(callback: (String) -> Unit) {
        this.call = callback
    }
}


// 网络请求的例子
fun bg(c: CoroutineScopeWrap.() -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
//        withContext(Dispatchers.IO) {
//         //    切线程。。。。
//
//        }
        val block = CoroutineScopeWrap()
        c.invoke(block)
        try {
            block.work.invoke(this)
        } catch (e: Exception) {
            block.error.invoke(e)
        } finally {
            block.complete.invoke()
        }
    }
}

class CoroutineScopeWrap {
    var work: (suspend CoroutineScope.() -> Unit) = {}
    var error: (e: Exception) -> Unit = {}
    var complete: () -> Unit = {}

    fun doWork(call: suspend CoroutineScope.() -> Unit) {
        this.work = call
    }

    fun catchError(error: (e: Exception) -> Unit) {
        this.error = error
    }

    fun onFinally(call: () -> Unit) {
        this.complete = call
    }
}