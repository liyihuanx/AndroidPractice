package liyihuan.app.android.androidpractice.touchevent

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

/**
 * @ClassName: BadViewPager
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/3/11 21:13
 */
class BadViewPager : ViewPager {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    /**
     * 内部拦截
     */
//    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
//        if (ev.action == MotionEvent.ACTION_DOWN) {
//            super.onInterceptTouchEvent(ev)
//            return false
//        }
//        return true
//    }


    /**
     * 外部拦截
     */
    private var lastX = -1
    private var lastY = -1
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var touchX = ev.x.toInt()  // 记录touch坐标
        var touchY = ev.y.toInt()
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = touchX
                lastY = touchY
            }
            MotionEvent.ACTION_MOVE -> {
                // 用现在的坐标 - 上一次的坐标
                if (abs(ev.x - lastX) > abs(ev.y - lastY)) {
                    return true

                }
            }
        }
        return super.onInterceptTouchEvent(ev)

    }
}