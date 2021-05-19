package liyihuan.app.android.androidpractice.chat.im

interface ImCallback {
    fun onSuc()
    fun onFail(code: Int, msg: String?)
}