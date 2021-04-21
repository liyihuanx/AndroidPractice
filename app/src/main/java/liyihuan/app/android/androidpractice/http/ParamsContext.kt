package liyihuan.app.android.androidpractice.http

import okhttp3.Request

/**
 * @author created by liyihuanx
 * @date 2021/4/20
 * description: 类的描述
 */
class ParamsContext(request: Request, parmas: Map<String, String>) {

    private var iRequestParam: IRequestParam? = null
    private var request: Request? = null
    private var parmas: Map<String, String>? = null

    init {
        this.request = request
        this.parmas = parmas
    }


    fun getInRequest(): Request {
        when(request!!.method().toUpperCase()){
            "GET" ->{}
            "POST" ->{}
            else ->{}
        }
        return iRequestParam!!.getRequest(request!!, parmas!!)
    }
}
