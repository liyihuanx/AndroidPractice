package liyihuan.app.android.androidpractice.datasource.cache


/**
 * @author created by liyihuanx
 * @date 2021/4/15
 * description: 类的描述
 */
interface LocalCacheProvide<R> {

    /**
     * 获得缓存数据
     */
    fun loadFromLocal(): R?

    /**
     * 存储方案
     */
    fun saveToLocal(data: R)

    /**
     * 检查数据可用
     */
    fun checkLocalAvailable(): Boolean
}