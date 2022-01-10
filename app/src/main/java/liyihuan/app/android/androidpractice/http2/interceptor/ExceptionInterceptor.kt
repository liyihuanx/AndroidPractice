package liyihuan.app.android.androidpractice.http2.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author liyihuan
 * @date 2022/01/10
 * @Description
 */
class ExceptionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {


        } catch (e:Exception){

        }
        return chain.proceed(chain.request())
    }
}