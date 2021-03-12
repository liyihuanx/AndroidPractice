package liyihuan.app.android.androidpractice.touchevent

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * @ClassName: BadRecyclerView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/3/11 21:16
 */

class BadRecyclerView : RecyclerView {
    private var lastX = -1
    private var lastY = -1

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 内部拦截
     */
//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        var touchX = ev.x.toInt()  // 记录touch坐标
//        var touchY = ev.y.toInt()
//        when (ev.action) {
//            MotionEvent.ACTION_DOWN -> { // 父亲 --> return false,遍历子View询问是否有人处理？？
//                // 请求是否允许拦截事件
//                parent.requestDisallowInterceptTouchEvent(true) // 告诉父View不允许拦截
//            }
//
//            MotionEvent.ACTION_MOVE -> {
//                // 用现在的坐标 - 上一次的坐标
//                if (abs(ev.x - lastX) > abs(ev.y - lastY)) {
//                    parent.requestDisallowInterceptTouchEvent(false)
//                    return false
//                }
//            }
//
//            MotionEvent.ACTION_UP -> {
//
//            }
//        }
//
//        lastX = touchX // 保存上一次的坐标
//        lastY = touchY
//
//        return super.dispatchTouchEvent(ev) // 自己处理
//    }
}