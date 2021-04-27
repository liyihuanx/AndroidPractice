package liyihuan.app.android.androidpractice.datasource.cache

import android.text.TextUtils
import android.util.Log
import com.alibaba.fastjson.util.ParameterizedTypeImpl
import com.google.gson.Gson
import liyihuan.app.android.androidpractice.datasource.SpUtil
import java.lang.reflect.Type

/**
 * @author created by liyihuanx
 * @date 2021/4/15
 * description: 类的描述
 */

/**
 * 采用sp储存
 * urlKey :缓存key建议用url参数区分是哪次请求
 * cacheTime　缓存时间
 */
class SpCacheProvide<R>(private val urlKey: String, private val cacheTime: Long = -1,val clazz: Type) : LocalCacheProvide<R> {


    private fun getParameterType(): Type {
        val p = ParameterizedTypeImpl(arrayOf<Type>(clazz), CacheData::class.java, CacheData::class.java)
        return p
    }

    private fun getSpType(): Type {
        return  getParameterType()

    }

    override fun saveToLocal(data: R) {
        Log.d("QWER", "saveToLocal: ")
        val temp = CacheData(data, System.currentTimeMillis())
        SpUtil.get(urlKey).saveData("SpCacheProvide_$urlKey", Gson().toJson(temp))
    }

    override fun loadFromLocal(): R? {
        Log.d("QWER", "加载本地缓存: ")
        val now = System.currentTimeMillis()
        val jsonStr = SpUtil.get(urlKey).readString("SpCacheProvide_$urlKey", "")
        val temp = if (TextUtils.isEmpty(jsonStr)) {
            null
        } else {
            Gson().fromJson<CacheData<R>>(jsonStr, getSpType())
        }

        if (cacheTime < 0) {
            return temp?.data as R?
        }
        temp?.time?.let {
            if ((now - it) / 1000 < cacheTime) {
                return temp.data as R?
            }
        }
        return null
    }



    inner class CacheData<R> {
        var data: R? = null
        var time: Long = -1

        constructor()

        constructor(data: R, time: Long) {
            this.data = data
            this.time = time
        }
    }

    override fun checkLocalAvailable(): Boolean {
        val now = System.currentTimeMillis()
        val jsonStr = SpUtil.get(urlKey).readString("SpCacheProvide_$urlKey", "")
        val temp = if (TextUtils.isEmpty(jsonStr)) null else Gson().fromJson<CacheData<R>>(jsonStr, getSpType())

        temp?.time?.let {
            if ((now - it) / 1000  < cacheTime) {
                return true
            }
        }
        return false
    }
}