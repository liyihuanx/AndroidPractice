package liyihuan.app.android.androidpractice.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_chat.*
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.chat.voice.IVoiceRecord

class ChatActivity : AppCompatActivity(), IVoiceRecord {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        bottomChatView.setIVoiceRecord(this)
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