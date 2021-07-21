package liyihuan.app.android.androidpractice.invoke

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * @author created by liyihuanx
 * @date 2021/7/7
 * description: 类的描述
 */
class MyInvocationHandler(obj: ILocation?) : InvocationHandler {
    private var mTarget: Any? = obj

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        val result = method?.invoke(mTarget, args)
        return result ?: ""
    }
}