package liyihuan.app.android.androidpractice.datasource.cache

/**
 * @author created by liyihuanx
 * @date 2021/4/15
 * description: 类的描述
 */
class DbCacheProvider<T>(private var db: DbProvider<T>) : LocalCacheProvide<T> {

    override fun loadFromLocal(): T? {
        return db.queryCache()
    }

    override fun saveToLocal(data: T) {
        db.insertCache(data)
    }

    interface DbProvider<T> {

        fun queryCache(): T

        fun insertCache(data: T)
    }
}