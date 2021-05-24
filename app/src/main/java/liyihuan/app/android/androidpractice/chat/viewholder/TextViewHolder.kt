package liyihuan.app.android.androidpractice.chat.viewholder

import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.chat.bean.TextMsgBean

/**
 * @ClassName: TextViewHolder
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:35
 */
class TextViewHolder : BaseMsgViewHolder<TextMsgBean>() {
    protected lateinit var bodyTextView: TextView



    override fun getContentLayout(): Int {
        return R.layout.chat_message_item_text
    }

    override fun fillContentView() {
        bodyTextView = view.findViewById(R.id.message_item_text_body)
    }

    override fun bindContentView() {
        bodyTextView.text = message.msgContent

        if (isReceivedMessage()) {
//            bodyTextView.setBackgroundResource(leftBackground())
            bodyTextView.setPadding(LeftPadding, bodyTextView.paddingTop, RightPadding, bodyTextView.paddingBottom)
        } else {
//            bodyTextView.setBackgroundResource(rightBackground())
            bodyTextView.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            bodyTextView.setPadding(LeftPadding, bodyTextView.paddingTop, RightPadding, bodyTextView.paddingBottom)
        }

    }
}