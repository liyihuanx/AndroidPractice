package liyihuan.app.android.androidpractice.datasource

import io.reactivex.Observable
import liyihuan.app.android.androidpractice.datasource.cache.LocalCacheProvide

/**
 * @author created by liyihuanx
 * @date 2021/4/15
 * description: 类的描述
 */
class SimpleDataSource<R>(
        cacheProvide: LocalCacheProvide<R>? = null,
        requestType: RequestType? = RequestType.OnlyRemote,
        private var remoteQuest: () -> Observable<R>)
    : AbsDataSource<R>(cacheProvide, requestType) {

    override fun apiCall(): Observable<R> {
        return remoteQuest.invoke()
    }
}