package liyihuan.app.android.androidpractice.kotlinreflect

import com.google.gson.Gson
import liyihuan.app.android.androidpractice.kotlinContinuation.log
import java.util.concurrent.atomic.AtomicReference

/**
 * @ClassName: DisposableMain
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/8/9 23:15
 */

fun main() {
    val oldState = CoroutineState.InComplete() //  disposableList == null
    val disposable = DisposableImpl()
    val with = oldState.with(disposable)
    log("with" + Gson().toJson(with.disposableList))

    // 1.CoroutineState.InComplete() --> 新建一个CoroutineState(),默认DisposableList() == null，且作为新State
    // 2.from() --> 复制，把旧的State复制一份存放在新State中
    // 3.with() --> 向新的State 添加 disposable 并以Bean类 Cons() { head(Disposable)对象 和 tail(disposableList)} 存放
    val with1 = CoroutineState.InComplete().from(oldState).with(disposable)
    log("with" + Gson().toJson(with1.disposableList))

    with1.disposableList.forEach {
        it.dispose()
    }

    with1.disposableList.loopOn<DisposableImpl> { it ->
        it.dispose()
    }
}

class DisposableImpl : Disposable {
    override fun dispose() {
        log("dispose")
    }

}

