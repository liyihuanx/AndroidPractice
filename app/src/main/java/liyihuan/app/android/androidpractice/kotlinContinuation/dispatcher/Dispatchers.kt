package liyihuan.app.android.androidpractice.kotlinContinuation.dispatcher

import liyihuan.app.android.androidpractice.kotlinContinuation.dispatcher.ui.HandlerDispatcher

object Dispatchers {
    val Default by lazy {
        DispatcherContext(DefaultDispatcher)
    }

    val Android by lazy {
        DispatcherContext(HandlerDispatcher)
    }
}