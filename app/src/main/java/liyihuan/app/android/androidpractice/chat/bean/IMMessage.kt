package liyihuan.app.android.androidpractice.chat.bean

import com.tencent.imsdk.*

/**
 * @ClassName: IMMessage
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:08
 */
abstract class IMMessage<T : TIMElem> {

    /**
     * TIMMessage{
     *   ConversationType:C2C
     *   ConversationId:chenyalun
     *   MsgId:144115233967083050-1621432370-424465018
     *   MsgSeq:6477
     *   Rand:424465018
     *   time:1621432371
     *   isSelf:true
     *   Status:SendSucc
     *   Sender:liyihuan
     *   elements:[{
     *          Type:Text,
     *          Content:{
     *          这里都是自己配置的
     *              "headPic":"",
     *              "nickName":"liyihuan",
     *              "textContent":"我发送一条数据给你",
     *              "userId":"liyihuan",
     *              "mTxMessage":{"msg":{}},"userAction":0}
     *              }
     *      }]
     *
     *   }
     */

    /**
     * SDK消息内部实体
     */
    var timMessage: TIMMessage = TIMMessage()
    var timElem: T? = null


    var userId = ""
    var userName = ""
    var userHeadImg = ""
    var msgContent = ""

    fun setTxMsg(txMessage: TIMMessage) {
        timMessage = txMessage
    }

    /**
     * 是否为发送方
     */
    fun isSelf(): Boolean {
        return timMessage.isSelf
    }

    fun getMsgType(): TIMElemType? {
        return timElem?.type
    }

    open fun parseFrom(): IMMessage<*>? {
        if (isSelf()) {

        } else {
            getSenderProfile(object : TIMValueCallBack<TIMUserProfile> {
                override fun onSuccess(timUserProfile: TIMUserProfile) {
                    userId = timUserProfile.identifier
                    userName = timUserProfile.nickName
                    userHeadImg = timUserProfile.faceUrl
                }

                override fun onError(code: Int, desc: String?) {

                }

            })
        }
        if (timMessage.elementCount > 0) {
            timElem = timMessage.getElement(0) as T
            parseIMMessage(timElem!!)
        }
        return this
    }

    /**
     * 获取消息发送者的信息
     *
     * @return
     */
    open fun getSenderProfile(callBack: TIMValueCallBack<TIMUserProfile>) {
        timMessage.getSenderProfile(callBack)
    }

    /**
     * 解析数据
     */
    protected abstract fun parseIMMessage(elem: T)
}