package liyihuan.app.android.androidpractice.http

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import retrofit2.Retrofit
import java.util.*

/**
 * @ClassName: Api
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/21 21:58
 */
object Api {

    @JvmStatic
    private val apiProxies = HashMap<String, ApiProxy>()


    /**
     * defaultConfig.class
     */
    public fun provide(clientConfig: Class<out ConfigInterface>): ApiProxy {
        requireNotNull(clientConfig) {
            "config can't be null" }
        // 获取类名
        val clientName = clientConfig.canonicalName!!
        require(!TextUtils.isEmpty(clientName)) { "class must be a Config" }

        var apiProxy = apiProxies[clientName]
        if (apiProxy == null) {
            val newRetrofit = newRetrofit(clientConfig)
            apiProxy = ApiProxy(newRetrofit)
            apiProxies[clientName] = apiProxy
            // ["defaultConfig",  ApiProxy对象]
            // ApiProxy --> 持有Retrofit, apiCache["service", service.class]
        }
        return apiProxy
    }


    private fun newRetrofit(configClass: Class<out ConfigInterface>): Retrofit {
        val builder = Retrofit.Builder()
        try {
            val config: ConfigInterface = configClass.newInstance()
            config.build(builder)
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return builder.build()
    }

}