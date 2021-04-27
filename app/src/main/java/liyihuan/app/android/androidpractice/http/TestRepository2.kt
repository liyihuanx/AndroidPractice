package liyihuan.app.android.androidpractice.http

import io.reactivex.Observable
import liyihuan.app.android.androidpractice.datasource.RequestType
import liyihuan.app.android.androidpractice.datasource.SimpleDataSource
import liyihuan.app.android.androidpractice.datasource.api.ChapterBean
import liyihuan.app.android.androidpractice.datasource.cache.SpCacheProvide

/**
 * @ClassName: TestRepository
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/21 22:20
 */
open class TestRepository2 : BaseRepository<TestService2>(){

    fun fakeHttp3(): Observable<ChapterBean> {
        return SimpleDataSource(
                SpCacheProvide(
                        "TestRepository2",
                        1000 * 60 * 60, // 1小时
                        ChapterBean::class.java), RequestType.OnlyRemote
        ) { // 对请求的接口进行配置，包装
            apiService.getChapters3() // 请求接口
        }.startFetchData()
    }
}