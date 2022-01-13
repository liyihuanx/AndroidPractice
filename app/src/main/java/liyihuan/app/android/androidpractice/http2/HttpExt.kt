package liyihuan.app.android.androidpractice.http2

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * @author liyihuan
 * @date 2022/01/10
 * @Description
 */


fun <ResultType> AppCompatActivity.requestHttp2(){
    val httpFlow = flow<ResultType> {

    }.onStart {

    }.onCompletion {

    }


    lifecycleScope.launch {

    }
}

//public fun <T> Flow<T>.onStart(
//    action: suspend FlowCollector<T>.() -> Unit
//): Flow<T> = unsafeFlow { // Note: unsafe flow is used here, but safe collector is used to invoke start action
//    val safeCollector = SafeCollector<T>(this, currentCoroutineContext())
//    try {
//        safeCollector.action()
//    } finally {
//        safeCollector.releaseIntercepted()
//    }
//    collect(this) // directly delegate
//}








fun <ResultType> AppCompatActivity.requestHttp(
    requestWrap: CoroutineScopeWrap<ResultType>.() -> Unit,
) {
    lifecycleScope.launch {
        val block = CoroutineScopeWrap<ResultType>()
        requestWrap.invoke(block)
        flow {
            val httpResult = block.work?.invoke(this@launch)
            emit(httpResult)
        }.flowOn(Dispatchers.IO)
            .retryWhen { cause, attempt ->
                Log.d("QWER", "重试次数: $attempt")
                HttpHostUrl.isRetry = true
                HttpHostUrl.retryCount = attempt.toInt()
                attempt < HttpHostUrl.HttpList.size
            }
            .catch { ex ->
                block.error.invoke(ex)
                Log.d("QWER", "error :${ex.javaClass.simpleName} : ${ex.message} ")
            }
            .onCompletion {
                HttpHostUrl.isRetry = false
                HttpHostUrl.retryCount = 0
                block.complete.invoke()
            }
            .collect {
                block.result.invoke(it)
            }
    }
}



class CoroutineScopeWrap<ResultType> {
    var work: (suspend CoroutineScope.() -> ResultType)? = null
    var error: (e: Throwable) -> Unit = {}
    var complete: () -> Unit = {}
    var result: (ResultType?) -> Unit = {}


    fun onResult(block: (ResultType?) -> Unit){
        this.result = block
    }

    fun doWork(call: suspend CoroutineScope.() -> ResultType) {
        this.work = call
    }

    fun catchError(error: (e: Throwable) -> Unit) {
        this.error = error
    }

    fun onFinally(call: () -> Unit) {
        this.complete = call
    }
}
