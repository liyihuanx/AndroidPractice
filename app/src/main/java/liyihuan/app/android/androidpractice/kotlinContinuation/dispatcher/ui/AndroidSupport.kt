package liyihuan.app.android.androidpractice.kotlinContinuation.dispatcher.ui

import android.os.Handler
import android.os.Looper
import liyihuan.app.android.androidpractice.kotlinContinuation.dispatcher.Dispatcher

object HandlerDispatcher: Dispatcher {
    private val handler = Handler(Looper.getMainLooper())

    override fun dispatch(block: () -> Unit) {
        handler.post(block)
    }
}