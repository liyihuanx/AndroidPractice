package liyihuan.app.android.androidpractice.interval

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class AndroidScope(lifecycleOwner: LifecycleOwner? = null,
                        lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY) : CoroutineScope {

    init {
        lifecycleOwner?.lifecycle?.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (lifeEvent == event) {
                    cancel()
                }
            }
        })
    }

    protected var catch: (AndroidScope.(Throwable) -> Unit)? = null
    protected var finally: (AndroidScope.(Throwable?) -> Unit)? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        catch(throwable)
    }

    val uid = exceptionHandler

    override val coroutineContext: CoroutineContext =
        Dispatchers.Main + exceptionHandler + SupervisorJob()


    open fun launch(
        block: suspend CoroutineScope.() -> Unit
    ): AndroidScope {
        start()
        launch(EmptyCoroutineContext, block = block).invokeOnCompletion { finally(it) }
        return this
    }

    protected open fun start() {

    }

    protected open fun catch(e: Throwable) {
        catch?.invoke(this, e) ?: handleError(e)
    }

    protected open fun finally(e: Throwable?) {
        finally?.invoke(this, e)
    }

    /**
     * 当作用域内发生异常时回调
     */
    open fun catch(block: AndroidScope.(Throwable) -> Unit = {}): AndroidScope {
        this.catch = block
        return this
    }

    /**
     * 无论正常或者异常结束都将最终执行
     */
    open fun finally(block: AndroidScope.(Throwable?) -> Unit = {}): AndroidScope {
        this.finally = block
        return this
    }


    /**
     * 错误处理
     */
    open fun handleError(e: Throwable) {
        e.printStackTrace()
    }

    open fun cancel(cause: CancellationException? = null) {
        val job = coroutineContext[Job]
            ?: error("Scope cannot be cancelled because it does not have a job: $this")
        job.cancel(cause)
    }

    open fun cancel(message: String,
                    cause: Throwable? = null) = cancel(CancellationException(message, cause))




}

/**
 * 异步作用域
 *
 * 该作用域生命周期跟随整个应用, 注意内存泄漏
 */
fun scope(block: suspend CoroutineScope.() -> Unit): AndroidScope {
    return AndroidScope().launch(block)
}