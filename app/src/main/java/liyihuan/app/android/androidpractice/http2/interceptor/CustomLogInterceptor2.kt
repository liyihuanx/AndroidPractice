package liyihuan.app.android.androidpractice.http2.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * 打印出请求的Url和结果
 */
class CustomLogInterceptor2 : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d(
            "QWER", "请求参数: " + String.format(
                "result\nUrl=%s\nbody=%s",
                request.url(),
                request.headers().toString()
            )
        )

        val response = chain.proceed(request)
        val body = response.body()
        val bodyString = body?.string()

        Log.d(
            "QWER", "返回结果: " + String.format(
                "result\nUrl=%s\nbody=%s",
                request.url(),
                bodyString
            )
        )
        // 解决java.lang.IllegalStateException: closed，因为Response.body().string()方法只能读取一次

        return response.newBuilder()
            .body(ResponseBody.create(body?.contentType(), bodyString))
            .build()

    }


    private fun printParams(url: String, params: Map<String, String>?) {
        if (params != null && params.isNotEmpty()) {
            val paramSb = StringBuilder()
            for ((key, value) in params) {
                paramSb.append("\n")
                paramSb.append("$key=$value")
            }
            paramSb.append("\n")
            Log.v("QWER", "请求参数 \n" + String.format("Url=%s\nParams: {%s}", url, paramSb.toString()))

        }
    }

}