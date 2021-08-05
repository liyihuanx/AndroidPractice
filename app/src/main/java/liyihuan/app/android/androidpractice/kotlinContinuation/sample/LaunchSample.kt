package liyihuan.app.android.androidpractice.kotlinContinuation.sample


import kotlinx.coroutines.CoroutineExceptionHandler
import liyihuan.app.android.androidpractice.kotlinContinuation.Job
import liyihuan.app.android.androidpractice.kotlinContinuation.launch
import liyihuan.app.android.androidpractice.kotlinContinuation.log
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        log(coroutineContext[Job], throwable)
    }

    val job = launch(exceptionHandler) {
        log(1)
        log(2)
        hello()
        log(3)
    }

    log(job.isActive)
    job.join()
}


suspend fun hello() = suspendCoroutine<Int> {
    thread(isDaemon = true) {
        Thread.sleep(1000)
        it.resume(10086)
    }
}