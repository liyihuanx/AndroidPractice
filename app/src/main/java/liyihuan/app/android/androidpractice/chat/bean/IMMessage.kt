package liyihuan.app.android.androidpractice.chat.bean

import com.tencent.imsdk.TIMElem
import com.tencent.imsdk.TIMElemType
import com.tencent.imsdk.TIMMessage

/**
 * @ClassName: IMMessage
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:08
 */
abstract class IMMessage<T : TIMElem> {

    /**
     * SDK消息内部实体
     */
    protected var timMessage: TIMMessage = TIMMessage()
    protected var timElem: T? = null


    fun isSelf(): Boolean {
        return timMessage.isSelf
    }

    fun getMsgType(): TIMElemType? {
        return timElem?.type
    }
}