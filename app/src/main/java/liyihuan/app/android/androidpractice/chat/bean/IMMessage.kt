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
     *              "textContent":
     *              "我发送一条数据给你",
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
    protected var timMessage: TIMMessage = TIMMessage()
    protected var timElem: T? = null


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
}