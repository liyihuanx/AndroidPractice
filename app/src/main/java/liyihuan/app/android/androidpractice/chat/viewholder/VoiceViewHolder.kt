package liyihuan.app.android.androidpractice.chat.viewholder

import android.widget.TextView
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.chat.adapter.ChatAdapter
import liyihuan.app.android.androidpractice.chat.bean.TextMsgBean
import liyihuan.app.android.androidpractice.chat.bean.VoiceMsgBean

/**
 * @ClassName: TextViewHolder
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:35
 */
class VoiceViewHolder : BaseMsgViewHolder<VoiceMsgBean>() {
    protected lateinit var bodyTextView: TextView



    override fun getContentLayout(): Int {
        return R.layout.chat_message_item_text
    }

    override fun fillContentView() {
        bodyTextView = view.findViewById(R.id.message_item_text_body)
    }

    override fun bindContentView() {

    }
}