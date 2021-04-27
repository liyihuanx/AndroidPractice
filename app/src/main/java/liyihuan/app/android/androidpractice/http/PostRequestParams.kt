package liyihuan.app.android.androidpractice.http

import okhttp3.FormBody
import okhttp3.Request

/**
 * @ClassName: PostRequestParams
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/26 20:45
 */
class PostRequestParams : IRequestParam {
    override fun getRequest(request: Request, params: HashMap<String, String>): Request {
        var newRequest: Request? = null
        val oriBody = request.body()
        if (oriBody is FormBody) {
            val bodyBuilder = FormBody.Builder()
            var formBody = request.body() as FormBody
            //收集所有参数
            for (i in 0 until formBody.size()) {
                val key = formBody.name(i)
                val value = formBody.value(i)
//                params.put(key, value)
            }
            //添加公共参数到新的body
            for (key in params.keys) {
                bodyBuilder.add(key, params[key])
            }

            newRequest = request.newBuilder()
                    .post(formBody)
                    .build()
        }
        return newRequest!!
    }

}