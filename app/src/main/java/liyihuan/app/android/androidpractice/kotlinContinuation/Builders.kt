package liyihuan.app.android.androidpractice.kotlinContinuation

import com.bennyhuo.kotlin.coroutines.context.CoroutineName
import kotlinx.coroutines.CoroutineScope
import liyihuan.app.android.androidpractice.kotlinContinuation.core.StandardCoroutine
import liyihuan.app.android.androidpractice.kotlinContinuation.dispatcher.Dispatchers
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

private var coroutineIndex = AtomicInteger(0)

fun launch(context: CoroutineContext = EmptyCoroutineContext,
                          block: suspend () -> Unit): Job {
    val completion = StandardCoroutine(newCoroutineContext(context))
    block.startCoroutine(completion)
    return completion
}


fun newCoroutineContext(context: CoroutineContext): CoroutineContext {
    val combined = context + CoroutineName("@coroutine#${coroutineIndex.getAndIncrement()}")
    return combined
}
