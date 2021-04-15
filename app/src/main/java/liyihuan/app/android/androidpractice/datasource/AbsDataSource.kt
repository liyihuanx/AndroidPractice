package liyihuan.app.android.androidpractice.datasource

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import liyihuan.app.android.androidpractice.datasource.cache.LocalCacheProvide

/**
 * @author created by liyihuanx
 * @date 2021/4/15
 * description: 类的描述
 */

abstract class AbsDataSource<R>(private var cacheProvide: LocalCacheProvide<R>? = null, private var requestType: RequestType? = RequestType.OnlyRemote) : IDataResource<R> {

    override fun RequestType(): RequestType {
        return requestType ?: RequestType.OnlyRemote
    }

    override fun localRequest(): Observable<R> {
        return Observable.create<R> {
            val local = cacheProvide?.loadFromLocal()
            if (local != null) {
                it.onNext(local)
            }
            it.onComplete()
        }
    }

    override final fun remoteRequest(): Observable<R> {
        return Observable.create<R> {
            apiCall().subscribe(object : Observer<R> {
                override fun onComplete() {
                    it.onComplete()
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: R) {
                    if (t != null) {
                        it.onNext(t)
                    }
                }

                override fun onError(e: Throwable) {
                    it.onError(e)
                }
            })
        }
    }

    abstract fun apiCall(): Observable<R>

    override fun saveToLocal(data: R) {
        cacheProvide?.saveToLocal(data)
    }

    override fun checkLocalAvailable(): Boolean {
        return cacheProvide?.checkLocalAvailable()?:false
    }
}