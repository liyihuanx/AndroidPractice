package liyihuan.app.android.androidpractice.http

import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @ClassName: HttpConfig
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/19 21:35
 */
public class HttpConfig : ConfigInterface{
    /**
     * 获取头Http地址
     */
    override fun getBaseUrl(): String {
        return "https://www.wanandroid.com/"
    }

    override fun getCachePath(): String {
        TODO("Not yet implemented")
    }


    /**
     * 初始化okhttp
     */
    override fun client(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        // 添加头部拦截器
        okHttpClientBuilder.addInterceptor(HeadInterceptor())
        okHttpClientBuilder.addInterceptor(CustomLogInterceptor())
        // 超时的时间
        okHttpClientBuilder.connectTimeout(5000,TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }

    override fun build(builder: Retrofit.Builder) {
        // 添加请求的url
        builder.baseUrl(getBaseUrl())
        // 添加okhttp
        builder.client(client())
        // 添加数据处理器 影响 (@Body User ueser)
        addConverterFactory(builder)
        // 影响的就是Call或者Observable
        addCallAdapterFactory(builder)
        // 构建
        builder.build()
    }

    private fun addConverterFactory(builder: Retrofit.Builder) {
        // TODO 好像没什么必要。。
        // 添加自己的想要的处理器到list
        val factories = ArrayList<Converter.Factory>()
        factories.add(GsonConverterFactory.create())
        // 遍历添加
        factories.forEach {
            builder.addConverterFactory(it)
        }

    }

    private fun addCallAdapterFactory(builder: Retrofit.Builder) {
        val factories = ArrayList<CallAdapter.Factory>()
        factories.add(RxJava2CallAdapterFactory.create())

        factories.forEach {
            builder.addCallAdapterFactory(it)
        }
    }
}