package liyihuan.app.android.androidpractice.http2.interceptor

import android.util.Log
import liyihuan.app.android.androidpractice.http2.HttpHostUrl
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http2.Http2

/**
 * @author liyihuan
 * @date 2022/01/10
 * @Description
 */
class HostReplaceInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = if (HttpHostUrl.isRetry) {
            // 替换Host
            Log.d("QWER", "old-Host: ${chain.request().url().host()}")
            val newHost = HttpUrl.parse(HttpHostUrl.getNextHttpUtl())?.host()

            Log.d("QWER", "new-Host: $newHost")

            val newHttpUrl = chain.request().url().newBuilder()
                .host(newHost)
                .build()

            chain.request().newBuilder()
                .url(newHttpUrl)
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(builder)
    }
}