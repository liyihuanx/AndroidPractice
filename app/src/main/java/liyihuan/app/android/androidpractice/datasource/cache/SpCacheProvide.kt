//package liyihuan.app.android.androidpractice.datasource.cache
//
//import android.text.TextUtils
//import java.lang.reflect.Type
//
///**
// * @author created by liyihuanx
// * @date 2021/4/15
// * description: 类的描述
// */
//
///**
// * 采用sp储存
// * urlKey :缓存key建议用url参数区分是哪次请求
// * cacheTime　缓存时间
// */
//class SpCacheProvide<R>(private val urlKey: String, private val cacheTime: Long = -1,val clazz: Type) : LocalCacheProvide<R> {
//
//
//    private fun getParameterType(): Type {
//        val p = ParameterizedTypeImpl(arrayOf<Type>(clazz), CacheData::class.java, CacheData::class.java)
//        return p
//    }
//
//
//    private fun getSpType(): Type {
//        return  getParameterType()
//
//    }
//
//    override fun saveToLocal(data: R) {
//        val temp = CacheData(data, System.currentTimeMillis())
//        SpUtil.get(md5Url(urlKey)).saveData("SpCacheProvide_$urlKey", JsonUtil.toJson(temp))
//    }
//
//    override fun loadFromLocal(): R? {
//        val now = System.currentTimeMillis()
//        val jsonStr = SpUtil.get(md5Url(urlKey)).readString("SpCacheProvide_$urlKey", "")
//        val temp = if (TextUtils.isEmpty(jsonStr)) null else JsonUtil.fromJson<CacheData<R>>(jsonStr, getSpType())
//
//        if (cacheTime < 0) {
//            return temp?.data as R?
//        }
//        temp?.time?.let {
//            if (now - it < cacheTime) {
//                return temp.data as R?
//            }
//        }
//        return null
//    }
//
//    private fun md5Url(url: String): String {
//        return EncryptUtil.MD5(url)
//    }
//
//    inner class CacheData<R> {
//        var data: R? = null
//        var time: Long = -1
//
//        constructor()
//
//        constructor(data: R, time: Long) {
//            this.data = data
//            this.time = time
//        }
//    }
//}