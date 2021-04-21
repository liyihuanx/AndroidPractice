package liyihuan.app.android.androidpractice.http

import android.util.Log
import okhttp3.Request

/**
 * @author created by liyihuanx
 * @date 2021/4/21
 * description: 类的描述
 */
class GetRequestParams : IRequestParam {
    override fun getRequest(request: Request, params: Map<String, String>): Request {
        val oriHttpUrl = request.url()
        val httpUrlBuilder = oriHttpUrl.newBuilder()

        //添加公共参数
        for (key in params.keys) {
            httpUrlBuilder.addEncodedQueryParameter(key, params[key])
        }
        //收集所有参数
        for (i in 0 until oriHttpUrl.querySize()) {
            params.put(oriHttpUrl.queryParameterName(i), oriHttpUrl.queryParameterValue(i))
        }
        // 做一个加密
        // String sign = MD5加密
        // 添加到请求参数中去
        // httpUrlBuilder.addQueryParameter("_sign", sign);

        return request.newBuilder()
                .url(httpUrlBuilder.build())
                .build()
    }


}