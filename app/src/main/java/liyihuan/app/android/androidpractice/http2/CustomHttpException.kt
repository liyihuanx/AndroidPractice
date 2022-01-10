package liyihuan.app.android.androidpractice.http2

/**
 * @author created by liyihuanx
 * @date 2021/9/9
 * @description: 类的描述
 */
class CustomHttpException(code: Int?, detailMessage: String?) : RuntimeException(detailMessage) {

}

fun CustomHttpException.show(simpleName: String) {

}