package liyihuan.app.android.androidpractice.http2

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @author liyihuan
 * @date 2022/01/10
 * @Description
 */

fun <T> AppCompatActivity.requestHttp(block: suspend () -> T) {
    lifecycleScope.launch {
        flow {
            val httpResult =  block.invoke()
            emit(httpResult)
        }.flowOn(Dispatchers.IO)
            .retryWhen { cause, attempt ->
                Log.d("QWER", "重试次数: $attempt")
                attempt < 2
            }
            .catch { ex ->
                Log.d("QWER", "error :${ex.javaClass.simpleName} : ${ex.message} ")
            }
            .collect {
                Log.d("QWER", "数据: ${Gson().toJson(it)}")
            }
    }
}

