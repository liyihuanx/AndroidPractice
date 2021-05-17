package liyihuan.app.android.androidpractice.chat

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.view_bottom_chat.view.*
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.chat.voice.IVoiceRecord

/**
 * @author created by liyihuanx
 * @date 2021/5/13
 * description: 类的描述
 */
class BottomChatView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        LinearLayout(context, attrs, defStyleAttr),
        View.OnClickListener,
        View.OnTouchListener {
    private var rlEmojiView: RelativeLayout? = null
    private var tvEmojiTip: TextView? = null

    private var rlOperate: RelativeLayout? = null
    private var rvOperate: RecyclerView? = null

    private var iVoiceRecord: IVoiceRecord? = null
    public fun setIVoiceRecord(iVoiceRecord: IVoiceRecord) {
        this.iVoiceRecord = iVoiceRecord
    }

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
        voicePanelText.setOnTouchListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            // 表情
            btnEmoji -> {
                changePanelState("Emoji")
            }
            btnOperate -> {
                changePanelState("Operate")
            }
            // 语音
            btnVoice -> {
                showVoiceOrEdit("Voice")
            }
            btnKeyboard -> {
                showVoiceOrEdit("Edit", true)
            }
            // 发送
            btnSend -> {


            }
        }
    }


    /**
     * 表情和其他的切换
     */
    private fun changePanelState(type: String) {
        if (!isBottomViewShow()) { // 承载布局没有显示
            showVoiceOrEdit("Edit")
            hideAllPanel() // 隐藏所有
            postDelayed({
                // 延迟显示
                if (type == "Emoji") showEmoji(true) else showOtherOperate(true)
            }, 50)
        } else {
            val isPanelShow = if (type == "Emoji") !isEmojiPanelShow() else !isOperatePanelShow()
            if (isPanelShow) {
                hideAllPanel() // 隐藏所有
                if (type == "Emoji") showEmoji(true) else showOtherOperate(true)
            }
        }
    }


    /**
     * 左侧语音和键盘icon切换
     */
    private fun showVoiceOrEdit(flag: String, isNeedKeyBoard: Boolean = false) {
        hideAllPanel()
        if (flag == "Voice") {
            // 显示voice相关
            inputEt.visibility = View.GONE
            voicePanelText.visibility = View.VISIBLE
            btnKeyboard.visibility = View.VISIBLE
            btnVoice.visibility = View.GONE
        } else {
            // 显示输入相关
            inputEt.visibility = View.VISIBLE
            voicePanelText.visibility = View.GONE
            btnKeyboard.visibility = View.GONE
            btnVoice.visibility = View.VISIBLE

            inputEt.requestFocus()
            if (isNeedKeyBoard) {
                KeyBoardUtil.openKeyBoard(inputEt)
            }
        }
    }


    /**
     * 表情面板显示状态
     */
    private fun isEmojiPanelShow(): Boolean {
        return (rlEmojiView?.visibility ?: View.GONE) == View.VISIBLE  // 为null就是没唤起过,就是没显示过
    }

    /**
     * 其他操作面板显示状态
     */
    private fun isOperatePanelShow(): Boolean {
        return (rlOperate?.visibility ?: View.GONE) == View.VISIBLE
    }

    /**
     * 承载布局的显示状态
     */
    private fun isBottomViewShow(): Boolean {
        return bottomView.visibility == View.VISIBLE
    }


    private fun hideKeyBoard() {
        KeyBoardUtil.hideKeyBoardByView(inputEt)
    }


    /**
     * 加载emoji表情面板
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
     * 加载其他操作面板
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
                    voicePanelText.visibility = View.GONE
                    KeyBoardUtil.openKeyBoard(inputEt)
                }
                return true
            }

            voicePanelText -> {
                var y = 0f
                val offSetY = 200f // 取消发送需要的最小距离
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        y = event.y // 获取按下的位置
                        iVoiceRecord?.voiceRecordStart()
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val currentY = event.y
                        if (y - currentY > offSetY) {
                            iVoiceRecord?.voiceRecordCancel()
                        } else {
                            iVoiceRecord?.voiceRecordIng()
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        iVoiceRecord?.voiceRecordFinish()
                    }
                }
                return true
            }

        }
        return false
    }
}