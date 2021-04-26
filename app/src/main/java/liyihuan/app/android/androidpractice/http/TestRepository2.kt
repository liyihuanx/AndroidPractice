package liyihuan.app.android.androidpractice.http

import io.reactivex.Observable
import liyihuan.app.android.androidpractice.datasource.SimpleDataSource
import liyihuan.app.android.androidpractice.datasource.api.ChapterBean

/**
 * @ClassName: TestRepository
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/21 22:20
 */
open class TestRepository2 : BaseRepository<TestService2>(){

    fun fakeHttp3(): Observable<ChapterBean> {
        return SimpleDataSource { // 对请求的接口进行配置，包装
            apiService.getChapters3() // 请求接口
        }.startFetchData()
    }
}