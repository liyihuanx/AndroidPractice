package liyihuan.app.android.androidpractice.http

import android.content.Context
import com.google.gson.Gson

/**
 * @ClassName: Renovace
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/21 21:57
 */
public class Renovace {


//    public fun init(clientConfig : Class<out ConfigInterface){
//        mDefaultConfig = clientConfig
//    }

    companion object {

        private val mContext: Context? = null
        private var mDefaultConfig: Class<out ConfigInterface> = HttpConfig::class.java
        private val mGson: Gson? = null
//        private val respCodeinteceptorList: List<RespCodeInteceptor>? = null

        @JvmStatic
        fun <T> create(service: Class<T>): T {
            if (mDefaultConfig == null) {
                throw RuntimeException("mDefaultConfig == null, you must set a default config before!")
            }
            return Api.provide(mDefaultConfig!!).create(service)
        }
    }

}