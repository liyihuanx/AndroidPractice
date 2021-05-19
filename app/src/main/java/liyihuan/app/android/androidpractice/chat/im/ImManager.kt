package liyihuan.app.android.androidpractice.chat.im

import android.app.Application
import android.text.TextUtils
import android.util.Log
import com.tencent.imsdk.*
import com.tencent.imsdk.conversation.Conversation
import com.tencent.imsdk.ext.message.TIMMessageReceipt
import com.tencent.imsdk.ext.message.TIMMessageReceiptListener

object ImManager {

    var isLogin = false


    var newMsgExtrNotification: ((var1: List<TIMMessage>) -> Unit)? = null
    fun init(context: Application, userConfig: TIMUserConfig, config: TIMSdkConfig) {

        TIMManager.getInstance().init(context, config).toString()
        TIMManager.getInstance().userConfig = userConfig
        TIMManager.getInstance().addMessageListener { msgs ->
            newMsgExtrNotification?.invoke(msgs)
            /**
             * 收到新消息回调
             * @param msgs 收到的新消息
             * @return 正常情况下，如果注册了多个listener, SDK会顺序回调到所有的listener。当碰到listener的回调返回true的时候，将终止继续回调后续的listener。
             */
            msgs?.forEach { msg ->
                ImMsgDispatcher.onNewMsg(msg)
            }
            true
        }
    }

    private var lastUid: String = ""
    private var lastSign: String = ""


    fun login(uid: String, sign: String, callback: ImCallback?) {
        lastUid = uid
        lastSign = sign
        TIMManager.getInstance().login(uid, sign, object : TIMCallBack {
            override fun onSuccess() {
                isLogin = true
                lastUid = uid
                lastSign = sign
                callback?.onSuc()
            }

            override fun onError(p0: Int, p1: String?) {
                callback?.onFail(p0, p1)
            }
        })
    }


    fun loginOut(callback: ImCallback?) {
        TIMManager.getInstance().logout(object : TIMCallBack {
            override fun onSuccess() {
                lastUid = ""
                lastSign = ""
                isLogin = false
                callback?.onSuc()
            }

            override fun onError(p0: Int, p1: String?) {
                callback?.onFail(p0, p1)
            }

        })
    }

    fun reLogin(imCallback: ImCallback?) {
        if (!TextUtils.isEmpty(lastSign)) {
            login(lastUid, lastSign, imCallback)
        }
    }

    /**
     * 检查登录
     */
    private fun checkLogin(
        onlyNoticeError: ImCallback? = null,
        successBlock: ImManager.() -> Unit
    ) {
        if (!isLogin) {
            login(lastUid, lastSign, object : ImCallback {
                override fun onSuc() {
                    successBlock.invoke(this@ImManager)
                }

                override fun onFail(code: Int, msg: String?) {
                    onlyNoticeError?.onFail(code, msg)
                }
            })
        } else {
            successBlock.invoke(this)
        }
    }

    fun checkLogin(call: ImCallback? = null) {
        if (!isLogin) {
            login(lastUid, lastSign, call)
        } else {
            call?.onSuc()
        }
    }


    /**
     * 发消息 并检查登录
     */
    fun sendC2cMessage(chatId: String, msg: TIMMessage, callback: ImCallback?) {
        checkLogin(callback) {
            val conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.C2C, chatId
            )
            conversation.sendMessage(msg, TIMValueCallBackWarp(callback))
        }
    }


    fun sendGroupOnlineMessage(gropId: String, msg: TIMMessage, callback: ImCallback?) {
        //获取会话扩展实例
        checkLogin(callback) {
            val con = TIMManager.getInstance().getConversation(
                TIMConversationType.Group,
                gropId
            )
            con.sendOnlineMessage(msg, TIMValueCallBackWarp(callback))
        }
    }

    fun sendGroupMessage(gropId: String, msg: TIMMessage, callback: ImCallback?) {
        //获取会话扩展实例
        checkLogin(callback) {
            val con = TIMManager.getInstance().getConversation(
                TIMConversationType.Group,
                gropId
            )
            con.sendMessage(msg, TIMValueCallBackWarp(callback))
        }
    }


}