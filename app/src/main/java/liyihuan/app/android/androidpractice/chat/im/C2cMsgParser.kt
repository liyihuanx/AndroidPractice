package liyihuan.app.android.androidpractice.chat.im

import com.tencent.imsdk.TIMMessage
import liyihuan.app.android.androidpractice.chat.bean.IMMessage

interface C2cMsgParser {
    fun parse(msg: TIMMessage): IMMessage<*>?
}