package liyihuan.app.android.androidpractice.datasource.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET


/**
 * @ClassName: ApiService
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/15 21:59
 */
interface ApiService  {

    @GET("wxarticle/chapters/json")
    fun getChapters(): Call<ChapterBean>

    @GET("wxarticle/chapters/json")
    fun getChapters2(): Observable<ChapterBean>
}