package liyihuan.app.android.androidpractice.http

import android.util.Log
import com.google.gson.Gson
import retrofit2.Retrofit
import java.util.*

/**
 * @ClassName: ApiProxy
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/21 21:59
 */
class ApiProxy(retrofit: Retrofit) {


    private var apiCache = HashMap<Class<*>, Any>()
    private var retrofit = retrofit


    //[TestService,retrofit]
    fun <T> create(tClass: Class<T>): T {
        var service = apiCache[tClass]
        if (service == null) {
            service = retrofit.create(tClass)
            apiCache[tClass] = service!!
        }
        return service as T
    }


}