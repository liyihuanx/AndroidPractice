package liyihuan.app.android.androidpractice.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * @ClassName: configInterface
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/19 21:38
 */
interface ConfigInterface {

    fun getBaseUrl(): String
    fun getCachePath(): String
    fun client(): OkHttpClient
    fun build(builder: Retrofit.Builder)
}