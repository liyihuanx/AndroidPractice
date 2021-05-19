package liyihuan.app.android.androidpractice.chat.im

import com.google.gson.Gson
import liyihuan.app.android.androidpractice.chat.bean.IMMessage

import com.tencent.imsdk.TIMMessage
import com.tencent.imsdk.TIMTextElem


/**
 * 发各种消息 统一写这里
 */
object ImSender {


    fun sendC2CTextMessage(peer: String, timMessage: IMMessage<*>, callback: ImCallback? = null) {

        val json = Gson().toJson(timMessage)
        val timMsg = TIMMessage()
        val elem = TIMTextElem()
        elem.text = json
        timMsg.addElement(elem)
        ImManager.sendC2cMessage(peer, timMsg, callback)
    }
}