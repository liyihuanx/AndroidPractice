package liyihuan.app.android.androidpractice.http

import okhttp3.Request

/**
 * @author created by liyihuanx
 * @date 2021/4/20
 * description: 类的描述
 */
interface IRequestParam {

    fun getRequest(request: Request, params: HashMap<String, String>): Request
}