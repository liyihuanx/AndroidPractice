package liyihuan.app.android.androidpractice.star

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.view_star_layout.view.*
import liyihuan.app.android.androidpractice.R

/**
 * @author created by liyihuanx
 * @date 2021/3/19
 * description: 类的描述
 */
class StarLayout : RelativeLayout {


    private val mAnimatorSet = AnimatorSet()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.view_star_layout, this, true)
        initAnim()
    }

    // 初始化动画
    private fun initAnim() {
        val animOutCircle = ObjectAnimator.ofFloat(view_circle, CircleView.OUT_PRESENT, 0.1f, 1f)
        animOutCircle.duration = 250
        animOutCircle.interpolator = DecelerateInterpolator()

        val animClearCircle = ObjectAnimator.ofFloat(view_circle, CircleView.CLEAR_PRESENT, 0.1f, 1f)
        animClearCircle.duration = 200
        animClearCircle.startDelay = 200
        animClearCircle.interpolator = DecelerateInterpolator()

        val animDots = ObjectAnimator.ofFloat(view_dots, ShootDotsView.DOTS_PRESENT, 0f, 1f)
        animDots.duration = 900
        animDots.startDelay = 50
        animDots.interpolator = AccelerateDecelerateInterpolator()

        // 竖直水平放大和缩小
        val animStarX = ObjectAnimator.ofFloat(iv_star, ImageView.SCALE_Y, 0.2f, 1f)
        animStarX.duration = 350
        animStarX.startDelay = 250
        animStarX.interpolator = OvershootInterpolator()

        val animStarY = ObjectAnimator.ofFloat(iv_star, ImageView.SCALE_X, 0.2f, 1f)
        animStarY.duration = 350
        animStarY.startDelay = 250
        animStarY.interpolator = OvershootInterpolator()


        mAnimatorSet.playTogether(animOutCircle, animClearCircle, animDots, animStarX, animStarY)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mAnimatorSet.start()

        return super.onTouchEvent(event)
    }
}