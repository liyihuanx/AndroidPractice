package liyihuan.app.android.androidpractice.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.tencent.imsdk.TIMConversationType
import com.tencent.imsdk.TIMManager
import com.tencent.imsdk.TIMMessage
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.ext.message.TIMConversationExt
import liyihuan.app.android.androidpractice.chat.im.ImSender
import kotlinx.android.synthetic.main.activity_chat.*
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.chat.adapter.ChatAdapter
import liyihuan.app.android.androidpractice.chat.bean.IMMessage
import liyihuan.app.android.androidpractice.chat.bean.TextMsgBean
import liyihuan.app.android.androidpractice.chat.im.*
import liyihuan.app.android.androidpractice.chat.im.EnvironmentConfig.RECEIVER_ID
import liyihuan.app.android.androidpractice.chat.im.EnvironmentConfig.USER_ID
import liyihuan.app.android.androidpractice.chat.voice.IVoiceRecord
import liyihuan.app.android.androidpractice.imdemo.GenerateTestUserSig

class ChatActivity : AppCompatActivity(), IVoiceRecord, ImMsgListener {

    private var anchor: TIMMessage? = null
    private val chatAdapter = ChatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        bottomChatView.setIVoiceRecord(this)
        ImHelper.initIm(application)
        ImManager.login(
                USER_ID,
                GenerateTestUserSig.genTestUserSig(USER_ID),
                object : ImCallback {
                    override fun onSuc() {
                        Log.d("QWER", "onSuc: 登录成功")
                        getConversation()
                    }

                    override fun onFail(code: Int, msg: String?) {
                        Log.d("QWER", "onFail: $code  ----  $msg")
                    }

                })

        ImMsgDispatcher.addC2CListener(this)


        rvChatContent.adapter = chatAdapter
        rvChatContent.layoutManager = LinearLayoutManager(this)



    }

    private fun sendMsg(){
        val msgBean = TextMsgBean()
        msgBean.createMsg("我发送一条数据给你")
        ImSender.sendC2CTextMessage(RECEIVER_ID, msgBean, object : ImCallback {
            override fun onSuc() {
                Log.d("QWER", "onSuc: 发送成功")
            }

            override fun onFail(code: Int, msg: String?) {
                Log.d("QWER", "onFail: $code  ----  $msg")
            }
        })
    }

    private fun getConversation() {
        val conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, RECEIVER_ID)
        val timConversationExt = TIMConversationExt(conversation)
        timConversationExt.getMessage(5, anchor, object : TIMValueCallBack<List<TIMMessage>> {
            override fun onSuccess(list: List<TIMMessage>?) {
                list?.get(0)?.let {
                    C2cMessageParser().parse(it)
                }
                val newlist = list?.map {
                    C2cMessageParser().parse(it)
                }?.reversed()?.filterNotNull()
                if (newlist != null) {
                    chatAdapter.addData(0,newlist)
                }

            }

            override fun onError(code: Int, desc: String?) {
                Log.d("QWER", "onError: $code  ----  $desc")
            }

        })
    }

    override fun voiceRecordStart() {
        voiceView.visibility = View.VISIBLE
        voiceView.showRecordStart()
    }

    override fun voiceRecordIng() {
        voiceView.showRecordIng()
    }

    override fun voiceRecordFinish() {
        voiceView.visibility = View.GONE
        voiceView.showRecordCancel()
    }

    override fun voiceRecordCancel() {
        voiceView.showRecordCancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        ImMsgDispatcher.removeC2CListener(this)
    }

    override fun onNewMsg(msg: IMMessage<*>) {
        Log.d("QWER", "onNewMsg: ${Gson().toJson(msg)}")
    }
}