package liyihuan.app.android.androidpractice.chat.im

import liyihuan.app.android.androidpractice.chat.bean.IMMessage


interface ImMsgListener {

    fun onNewMsg(msg: IMMessage<*>)

}