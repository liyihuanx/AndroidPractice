package liyihuan.app.android.androidpractice.http2.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.util.*
import kotlin.collections.HashMap

/**
 * @ClassName: HeadInterceptor
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/19 21:43
 */
class HeadInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request()
            .newBuilder()
            .addHeader("APPID", "12345")
            .addHeader("VERSION", "1.0.0")
            .addHeader("token", "1234")
        // 传递给下一层拦截器获取他的返回结果
        return chain.proceed(builder.build())
    }
}