package liyihuan.app.android.androidpractice.touchevent

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @ClassName: BadViewPager
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/3/11 21:13
 */
class BadViewPager : ViewPager {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }



}