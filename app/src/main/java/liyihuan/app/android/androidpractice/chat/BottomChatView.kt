package liyihuan.app.android.androidpractice.chat

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_bottom_chat.view.*
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.fish.FishRelativeLayout

/**
 * @author created by liyihuanx
 * @date 2021/5/13
 * description: 类的描述
 */
class BottomChatView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_bottom_chat, this, true)
        orientation = LinearLayout.VERTICAL

        btnEmoticon.setOnClickListener(this)
        btnVoice.setOnClickListener(this)
        btnKeyboard.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            // 表情
            btnEmoticon -> {

            }
            // 语音
            btnVoice -> {

            }
            // 输入
            btnKeyboard -> {

            }
            // 发送
            btn_send ->{

            }
        }
    }


}