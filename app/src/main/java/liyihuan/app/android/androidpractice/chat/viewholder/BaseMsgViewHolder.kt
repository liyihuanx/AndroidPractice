package liyihuan.app.android.androidpractice.chat.viewholder

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.chad.library.adapter.base.BaseViewHolder
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.chat.adapter.ChatAdapter
import liyihuan.app.android.androidpractice.chat.bean.IMMessage
import com.example.module_fangroup.util.*
/**
 * @ClassName: BaseMsgViewHolder
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:03
 */
abstract class BaseMsgViewHolder<K : IMMessage<*>>(adapter: ChatAdapter? = null) : RecyclerViewHolder<K>(adapter) {

    // view
    protected lateinit var view: View

    protected lateinit var alertButton: View
    protected lateinit var timeTextView: TextView
    protected lateinit var progressBar: ProgressBar
    protected lateinit var nameTextView: TextView
    protected lateinit var contentContainer: FrameLayout // 消息内容
    protected lateinit var contentView: View // 子View的布局
    protected lateinit var readReceiptTextView: TextView
    protected lateinit var avatarLeft: ImageView
    protected lateinit var avatarRight: ImageView

    // data
    protected lateinit var message: K
    protected lateinit var mContext: Context
    protected lateinit var mViewHolder: BaseViewHolder


    //
    protected val LeftPadding: Int = ViewUtil.dip2px(10f)
    protected val RightPadding: Int = ViewUtil.dip2px(10f)
    protected val TopPadding: Int = ViewUtil.dip2px(6f)
    protected val BottomPadding: Int = ViewUtil.dip2px(6f)

    override fun convert(holder: BaseViewHolder, data: K, position: Int) {
        view = holder.itemView
        message = data
        mContext = view.context
        mViewHolder = holder

        inflateView()
        setItemViewData()
        bindContentView()
    }


    /**
     * 初始化基类布局控件，添加子类数据项布局，初始化子类布局
     */
    private fun inflateView() {
        timeTextView = view.findViewById<TextView>(R.id.message_item_time)
        avatarLeft = view.findViewById(R.id.message_item_portrait_left)
        avatarRight = view.findViewById(R.id.message_item_portrait_right)
        alertButton = view.findViewById<View>(R.id.message_item_send_status)
        progressBar = view.findViewById<ProgressBar>(R.id.message_item_progress)
        nameTextView = view.findViewById<TextView>(R.id.message_item_nickname)
        contentContainer = view.findViewById<FrameLayout>(R.id.message_item_content)


        // contentContainer 整条消息的布局
        // contentView 消息内容的布局 --> 数据项
        if (contentContainer.childCount == 0) {
            contentView = LayoutInflater.from(view.context).inflate(getContentLayout(), contentContainer, false)
            contentContainer.addView(contentView)
        } else {
            contentView = contentContainer.getChildAt(0)
        }

        fillContentView()
    }

    /**
     * 设置每条消息Item的所有信息
     */
    private fun setItemViewData() {
        setMsgContent()
    }

    private fun setMsgContent() {
        val bodyContainer = view.findViewById<View>(R.id.message_item_body) as LinearLayout

        // 调整container的位置
        // bodyContainer 里面有三个控件,
        // 接收的消息，如果0不是放数据项的view，添加到第一个去
        // 自己发出的消息，放在第三个位置
        val index = if (isReceivedMessage()) 0 else 2
        if (bodyContainer.getChildAt(index) !== contentContainer) {
            bodyContainer.removeView(contentContainer)
            bodyContainer.addView(contentContainer, index)
        }
        if (isReceivedMessage()) {
            setGravity(bodyContainer, Gravity.LEFT)
            contentContainer.setBackgroundResource(leftBackground())
            avatarLeft.visibility = View.VISIBLE
            avatarRight.visibility = View.GONE

            nameTextView.text = message.userName
            nameTextView.visibility = View.VISIBLE
        } else {
            setGravity(bodyContainer, Gravity.RIGHT)
            contentContainer.setBackgroundResource(rightBackground())
            avatarLeft.visibility = View.GONE
            avatarRight.visibility = View.VISIBLE
        }
    }

    /**
     * 设置左右
     */
    private fun setGravity(bodyContainer: LinearLayout, gravity: Int) {
        val params = bodyContainer.layoutParams as FrameLayout.LayoutParams
        params.gravity = gravity
    }


    /**
     * 判断消息方向，是否是接收到的消息
     * message.isSelf() == false 是接收消息
     */
    protected open fun isReceivedMessage(): Boolean {
        return !message.isSelf()
    }


    /**
     * 接收到的消息时的内容区域背景
     */
    protected open fun leftBackground(): Int {
        return R.drawable.left_text_back
    }

    /**
     * 发送出去的消息时的内容区域背景
     */
    protected open fun rightBackground(): Int {
        return R.drawable.right_text_back
    }

    /**
     * 获取数据项的布局
     */
    abstract fun getContentLayout(): Int

    /**
     * 初始化View
     */
    abstract fun fillContentView()

    /**
     * 将消息数据项与内容的view进行绑定
     */
    abstract fun bindContentView()
}