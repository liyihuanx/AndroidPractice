package liyihuan.app.android.androidpractice.datasource

import io.reactivex.Observable

/**
 * @author created by liyihuanx
 * @date 2021/4/15
 * description: 类的描述
 */
interface IDataResource<R> {


    /**
     * 请求网络或者读取缓存
     */
    fun startFetchData(): Observable<R> {
        var obs: Observable<R>?=null
        when (RequestType()) {
            RequestType.OnlyCache -> {
                obs = localRequest()
            }
            RequestType.OnlyRemote -> {
                obs = remoteRequest()
            }
            RequestType.CacheFirst -> {
                obs = Observable.concat(localRequest(), remoteRequest().map {
                    saveToLocal(it)
                    it
                }).firstElement().toObservable()
            }

            RequestType.Both -> {
                obs = Observable.concat(localRequest(), remoteRequest().map {
                    saveToLocal(it)
                    it
                })
            }

            RequestType.AppFirst ->{
                if(checkLocalAvailable()){
                    obs = localRequest()
                }else{
                    obs = Observable.concat(localRequest(), remoteRequest().map {
                        saveToLocal(it)
                        it
                    })
                }
            }
        }

        return obs!!
    }

    /**
     * 请求策略
     */
    fun RequestType(): RequestType

    /**
     * 本地请求
     */
    fun localRequest(): Observable<R>
    /**
     * 网络请求
     */
    fun remoteRequest(): Observable<R>

    /**
     * 存储缓存
     */
    fun saveToLocal(data: R)

    /**
     * 检查数据可用
     */
    fun checkLocalAvailable(): Boolean
}