package liyihuan.app.android.androidpractice.datasource.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


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

    @POST("user/register")
    @FormUrlEncoded
    fun register(
            @Field("username") uid: String,
            @Field("password") password: String,
            @Field("repassword") repassword: String
    ):Observable<Any>
}