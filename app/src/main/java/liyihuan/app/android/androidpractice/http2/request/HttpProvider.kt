package liyihuan.app.android.androidpractice.http2.request

import android.util.Log
import liyihuan.app.android.androidpractice.http2.HttpHostUrl
import okhttp3.HttpUrl
import retrofit2.Retrofit
import java.util.*

/**
 * @ClassName: Api
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/21 21:58
 */
object HttpProvider {

    @JvmStatic
    private val apiProxies = HashMap<String, Any>()
    // 实际想要的效果
    // [configService,configService的实例] --> retrofit.create(tClass)


    @JvmStatic
    private val retrofitMap = HashMap<String, Retrofit>()


    /**
     * 传入要用的interface
     */
    @JvmStatic
    fun <T> defaultCreate(service: Class<T>): T {
        return newRetrofit(HttpConfig::class.java).newCreate(service)
    }

//    @JvmStatic
//    fun <T> customCreate(configClass: Class<out IHttpConfig>): T {
//        return newRetrofit(configClass).newCreate(service)
//    }


    var isRetry = false
    var retryCount = 0
    /**
     * 创建Retrofit
     */
    @JvmStatic
    fun newRetrofit(configClass: Class<out IHttpConfig>): Retrofit {
        var retrofit = retrofitMap[configClass.simpleName]
        // 没创建过，或者需要重试需要换一个域名的
        if (retrofit == null) {
            try {
                // retrofit
                val builder = Retrofit.Builder()
                // 我的Http配置 重试的话就会新建一个代替前面的
                val config = configClass.newInstance()
                config.build(builder)
                val build = builder.build()
                // 保存起来复用
                retrofitMap[configClass.simpleName] = build
                // 把返回值赋值
                retrofit = build
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // 通过反射修改baseUrl

        if (isRetry) {
            retryCount += 1

        }
        val baseUrlField = Retrofit::class.java.getDeclaredField("baseUrl")
        baseUrlField.isAccessible = true
        baseUrlField.set(
            retrofit!!,
            HttpUrl.parse(HttpHostUrl.httpUrl)
        )
        Log.d("QWER", "新的Url地址为: ${retrofit.baseUrl().host()}")

        isRetry = false
        return retrofit!!
    }


    /**
     * 创建interface
     */
    private fun <T> Retrofit.newCreate(sClass: Class<T>): T {
        var service = apiProxies[sClass.simpleName]
        if (service == null) {
            service = this.create(sClass)
            apiProxies[sClass.simpleName] = service!!
        }
        return service as T
    }
}