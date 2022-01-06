package liyihuan.app.android.androidpractice.http2.request

import com.google.gson.Gson
import liyihuan.app.android.androidpractice.http.CustomLogInterceptor
import liyihuan.app.android.androidpractice.http2.interceptor.CustomLogInterceptor2
import liyihuan.app.android.androidpractice.http2.interceptor.HeadInterceptor

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * @ClassName: HttpConfig
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/8 21:20
 */
class HttpConfig : IHttpConfig {

    override fun getBaseUrl(): String {
        return "https://www.wanandroids.com/"
    }

    override fun client(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        // 添加头部拦截器
        okHttpClientBuilder.addInterceptor(HeadInterceptor())
        okHttpClientBuilder.addInterceptor(CustomLogInterceptor2())
        // 超时的时间
        okHttpClientBuilder.connectTimeout(5000, TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }

    override fun build(builder: Retrofit.Builder) {
        // 添加请求的url
        builder.baseUrl(getBaseUrl())
        // 添加okhttp
        builder.client(client())
        builder.addConverterFactory(HttpConverterFactory(Gson()))
        // 添加数据处理器 影响 (@Body User ueser)
//        builder.addConverterFactory(GsonConverterFactory.create())
        // 影响的就是Call或者Observable
//        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        // 构建
        builder.build()
    }

}