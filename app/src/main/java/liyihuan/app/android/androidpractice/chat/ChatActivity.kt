package liyihuan.app.android.androidpractice.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_chat.*
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.chat.adapter.ChatAdapter
import liyihuan.app.android.androidpractice.chat.bean.IMMessage
import liyihuan.app.android.androidpractice.chat.bean.TextMsgBean
import liyihuan.app.android.androidpractice.chat.voice.IVoiceRecord

class ChatActivity : AppCompatActivity(), IVoiceRecord {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        bottomChatView.setIVoiceRecord(this)
        val chatAdapter = ChatAdapter()

        val list = ArrayList<IMMessage<*>>()
        list.add(TextMsgBean())
        list.add(TextMsgBean())
        list.add(TextMsgBean())
        list.add(TextMsgBean())
        chatAdapter.setNewData(list)
        rvChatContent.adapter = chatAdapter
        rvChatContent.layoutManager = LinearLayoutManager(this)

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
}