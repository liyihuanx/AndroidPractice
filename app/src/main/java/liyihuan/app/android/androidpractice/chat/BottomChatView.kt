package liyihuan.app.android.androidpractice.chat

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.view_bottom_chat.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.fish.FishRelativeLayout
import java.security.Key

/**
 * @author created by liyihuanx
 * @date 2021/5/13
 * description: 类的描述
 */
class BottomChatView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener, View.OnTouchListener {

    private var rlEmojiView: RelativeLayout? = null
    private var tvEmojiTip: TextView? = null

    private var rlOperate: RelativeLayout? = null
    private var rvOperate: RecyclerView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_bottom_chat, this, true)
        orientation = LinearLayout.VERTICAL

        // 根据输入框内容展示发送键或者更多键
        checkEditText()

        btnEmoji.setOnClickListener(this)
        btnVoice.setOnClickListener(this)
        btnKeyboard.setOnClickListener(this)
        btnSend.setOnClickListener(this)
        btnOperate.setOnClickListener(this)
        inputEt.setOnTouchListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            // 表情
            btnEmoji -> {
                if (isEmojiPanelShow()) {
                    hideAllPanel()
                    postDelayed({
                        showEmoji(true)
                    }, 50)
                } else {
                    showEmoji(true)
                }
            }
            // 语音
            btnVoice -> {

            }

            btnKeyboard -> {

            }
            // 发送
            btnSend -> {

            }
            btnOperate -> {
                if (isOperatePanelShow()) {
                    hideAllPanel()
                    postDelayed({
                        showOtherOperate(true)
                    }, 50)
                } else {
                    showOtherOperate(true)
                }

            }
        }
    }

    /**
     * 表情面板显示状态
     */
    private fun isEmojiPanelShow(): Boolean {
        if (rlEmojiView != null) {
            return rlEmojiView!!.visibility == View.GONE
        }
        return false
    }

    private fun isOperatePanelShow(): Boolean {
        if (rlOperate != null) {
            return rlOperate!!.visibility == View.GONE
        }
        return false
    }


    private fun hideKeyBoard() {
        KeyBoardUtil.hideKeyBoardByView(inputEt)
    }

    /**
     * 展示表情面板 or 其他操作面板
     */
//    private fun showEmojiOrOperate(isShowEmoji: Boolean, isShowOperate: Boolean) {
//        bottomView.visibility = if (isShowEmoji or isShowEmoji) View.VISIBLE else View.GONE
//        showEmoji(isShowEmoji)
//        showOtherOperate(isShowOperate)
//    }

    /**
     * 显示emoji表情面板
     * 使用ViewStub懒加载
     */
    private fun showEmoji(show: Boolean) {
        if (show && rlEmojiView == null) {
            var view = viewStubEmoji.inflate()
            rlEmojiView = view.findViewById(R.id.rlEmojiView)
            tvEmojiTip = view.findViewById(R.id.tvEmojiTip)
        }
        bottomView.visibility = if (show) View.VISIBLE else View.GONE
        rlEmojiView?.visibility = if (show) View.VISIBLE else View.GONE
    }

    /**
     * 显示其他操作面板
     */
    private fun showOtherOperate(show: Boolean) {
        if (show && rlOperate == null) {
            val operateView = viewStubOperate.inflate()
            rlOperate = operateView.findViewById(R.id.rlOperate)
            rvOperate = operateView.findViewById(R.id.rvOperate)
        }
        bottomView.visibility = if (show) View.VISIBLE else View.GONE
        rlOperate?.visibility = if (show) View.VISIBLE else View.GONE
    }


    /**
     * 隐藏所有面板
     */
    private fun hideAllPanel() {
        hideKeyBoard()
        bottomView.visibility = View.GONE
        rlEmojiView?.visibility = View.GONE
        rlOperate?.visibility = View.GONE
    }

    @SuppressLint("CheckResult")
    fun checkEditText() {
        RxTextView.textChanges(inputEt)
                .map {
                    it.trim().isNotEmpty()
                }
                .subscribe {
                    if (it) {
                        btnOperate.visibility = View.GONE
                        btnSend.visibility = View.VISIBLE
                        btnSend.isEnabled = true
                    } else {
                        btnOperate.visibility = View.VISIBLE
                        btnSend.visibility = View.GONE
                        btnSend.isEnabled = false
                    }
                }
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (v) {
            inputEt -> {
                // 设置焦点
                inputEt.requestFocus()

                if (MotionEvent.ACTION_DOWN == event.action) {
                    hideAllPanel()
                    KeyBoardUtil.openKeyBoard(inputEt)
                }
                return true
            }

        }
        return false
    }
}