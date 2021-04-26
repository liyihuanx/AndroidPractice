package liyihuan.app.android.androidpractice.http

import io.reactivex.Observable
import liyihuan.app.android.androidpractice.datasource.api.ChapterBean
import retrofit2.Call
import retrofit2.http.GET

/**
 * @ClassName: TestService
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/21 22:21
 */
public interface TestService2 {

    @GET("wxarticle/chapters/json")
    fun getChapters3(): Observable<ChapterBean>
}