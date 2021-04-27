package liyihuan.app.android.androidpractice.http

import android.util.Log
import okhttp3.Request

/**
 * @author created by liyihuanx
 * @date 2021/4/21
 * description: 类的描述
 */
class GetRequestParams : IRequestParam {
    override fun getRequest(request: Request, params: HashMap<String, String>): Request {
        val oriHttpUrl = request.url()
        val httpUrlBuilder = oriHttpUrl.newBuilder()

        //添加公共参数
        for (key in params.keys) {
            httpUrlBuilder.addEncodedQueryParameter(key, params[key])
        }
        //收集所有参数
        for (i in 0 until oriHttpUrl.querySize()) {
            params[oriHttpUrl.queryParameterName(i)] = oriHttpUrl.queryParameterValue(i)
        }
        // 做一个加密
        // String sign = MD5加密
        // 添加到请求参数中去
        // httpUrlBuilder.addQueryParameter("_sign", sign);
        printParams(oriHttpUrl.uri().toString(), params)

        return request.newBuilder()
                .url(httpUrlBuilder.build())
                .build()
    }

    private fun printParams(url: String, params: Map<String, String>?) {
        if (params != null && params.isNotEmpty()) {
            val paramSb = StringBuilder()
            for ((key, value) in params) {
                paramSb.append("\n")
                paramSb.append("$key=$value")
            }
            paramSb.append("\n")
            Log.v("Http_Request", "printParams: " + String.format("Url=%s\nParams: {%s}", url, paramSb.toString()))
        }
    }

}