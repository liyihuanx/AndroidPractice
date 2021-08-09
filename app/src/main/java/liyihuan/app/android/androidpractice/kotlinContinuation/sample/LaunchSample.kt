package liyihuan.app.android.androidpractice.kotlinContinuation.sample


import android.util.Log
import com.bennyhuo.kotlin.coroutines.context.CoroutineName
import kotlinx.coroutines.CoroutineExceptionHandler
import liyihuan.app.android.androidpractice.kotlinContinuation.Job
import liyihuan.app.android.androidpractice.kotlinContinuation.launch
import liyihuan.app.android.androidpractice.kotlinContinuation.log
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED

suspend fun main() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        log(coroutineContext[Job], throwable)
    }

    val job = launch {
        log(1)
        log(2)
        val hello = hello()
        log(hello)
        log(3)
        val job1 = launch {
            log(4)
            val hello = hello()
            log(hello)
            log(5)
        }
        job1.join()
    }

    log(job.isActive)
    job.join()


//    continuation {
//        log(continuation1())
//        "1111"
////        COROUTINE_SUSPENDED
//    }
//    val testContinuation = testContinuation()
//    testContinuation.resume("1234")
}


suspend fun hello() = suspendCoroutine<Int> {
    thread(isDaemon = true) {
        Thread.sleep(1000)
        it.resume(10086)
    }
}

lateinit var createCoroutine: Continuation<Unit>
fun <T> continuation(block: suspend () -> T): Any {
    createCoroutine = block.createCoroutine(object : Continuation<Any?> {
        override val context = EmptyCoroutineContext
        override fun resumeWith(result: Result<Any?>) {
            log(Result.success(result.getOrNull()))
        }
    })
    createCoroutine.resume(Unit)
    return COROUTINE_SUSPENDED
}


suspend fun continuation1() = suspendCoroutine<Any> {
    it.resume("6")
}


class testContinuation(override val context: CoroutineContext = EmptyCoroutineContext) : Continuation<Any> {
    override fun resumeWith(result: Result<Any>) {
        println(result)
    }
}
