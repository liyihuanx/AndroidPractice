package liyihuan.app.android.androidpractice.fish

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.fish_rl.view.*
import liyihuan.app.android.androidpractice.R

/**
 * @ClassName: FishRelativeLayout
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/3/15 23:05
 */
class FishRelativeLayout : RelativeLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    var startX = 0f
    var startY = 0f
    var touchX = 0f
    var touchY = 0f


    private fun initView(context: Context) {
        val rootview = LayoutInflater.from(context).inflate(R.layout.fish_rl, this, true)
        val fishView = FishView()
        iv_fish.setImageDrawable(fishView)

        startX = iv_fish.width / 2f
        startY = iv_fish.height / 2f
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d("QWER", "onTouchEvent: ${event.action}")
        when (event.action) {

            MotionEvent.ACTION_UP -> {
                touchX = event.x
                touchY = event.y
                anima()
            }
        }

        return super.onTouchEvent(event)
    }

    private fun anima() {
        val translateX = ObjectAnimator.ofFloat(iv_fish, "translationX", startX, touchY)
        val translateY = ObjectAnimator.ofFloat(iv_fish, "translationY", startY, touchY)
        val set = AnimatorSet()
        set.duration = 1000
        set.playTogether(translateX, translateY)
        set.start()
    }


}