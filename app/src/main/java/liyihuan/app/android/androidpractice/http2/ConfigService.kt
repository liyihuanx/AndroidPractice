package liyihuan.app.android.androidpractice.http2


import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author created by liyihuanx
 * @date 2021/9/7
 * @description: 类的描述
 */
interface ConfigService {

    @GET("wxarticle/chapters/json")
    suspend fun config(): List<ChapterBean>

}