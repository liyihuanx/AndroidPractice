package liyihuan.app.android.androidpractice.fish

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.fish_rl.view.*
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.diffType.ScreenUtils

/**
 * @ClassName: FishRelativeLayout
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/3/15 23:05
 */
class FishRelativeLayout : RelativeLayout {
    private var touchX = 0f
    private var touchY = 0f
    private var radius = 0f
    private var alpha = 0
    private lateinit var fishView: FishView
    private lateinit var mPath: Path
    private lateinit var mPaint: Paint

    private lateinit var rippleAnimator: ObjectAnimator

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }


    private fun initView(context: Context) {
        val rootview = LayoutInflater.from(context).inflate(R.layout.fish_rl, this, true)
        fishView = FishView()
        iv_fish.setImageDrawable(fishView)
        mPath = Path()
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 8f
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // [1080,2134] 540,1028
        setMeasuredDimension(ScreenUtils.getScreenWidth(context), ScreenUtils.getScreenHeight(context))
    }

    override fun onDraw(canvas: Canvas?) {

        //方便刷新透明度
        mPaint.setARGB(alpha, 0, 125, 251)
        canvas!!.drawCircle(touchX, touchY, radius, mPaint)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        touchX = event.x
        touchY = event.y

        // 因为没有radius关键字 --> 自己要创建对应的set方法(setRadius)
        rippleAnimator = ObjectAnimator.ofFloat(this, "radius", 0f, 1f).setDuration(1000)
        rippleAnimator.start()
        anim()

        return super.onTouchEvent(event)
    }


    private fun anim() {
//        val parentLocation = IntArray(2)
//        rl_root.getLocationInWindow(parentLocation) // [0 ,227]


//        var ivFishLocation = IntArray(2)
//        iv_fish.getLocationInWindow(ivFishLocation) // [288.5,969.5]
//        val fishCenterX = ivFishLocation[0] - fishView.intrinsicWidth / 2f
//        val fishCenterY = ivFishLocation[1] - fishView.intrinsicHeight / 2f

        // 1.获取imageView的中心点坐标 [539.5,993.5]
        val fishCenterX = iv_fish.x + fishView.intrinsicWidth / 2f
        val fishCenterY = iv_fish.y + fishView.intrinsicHeight / 2f

        // 2.将路径起始点移动到中心
        mPath.reset()
        mPath.moveTo(fishCenterX, fishCenterY)

        // 3.根据Touch点的坐标，计算路径Path和角度
        // 角度
        val calculateAngle = calculateAngle(fishCenterX, fishCenterY, touchX, touchY)
        // 计算路径
        mPath.lineTo(touchX, touchY)
        val mPathMeasure = PathMeasure(mPath, false)

        // 4.调转鱼头，沿着Path路径游动
        fishView.startAngle = calculateAngle

        // TODO 如果忘记了 可以和 DispatchGold 结合起来看
        val valueAnimator = ObjectAnimator.ofFloat(iv_fish, "x", "y", mPath)
//        val valueAnimator = ValueAnimator.ofFloat(0f, mPathMeasure.length)
        valueAnimator.duration = 2000
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.start()
//        valueAnimator.addUpdateListener {
//            val location = FloatArray(2)
//            mPathMeasure.getPosTan(it.animatedValue as Float, location, null)
//            iv_fish.translationX = location[0]
//            iv_fish.translationY = location[1]
//        }


    }


    /**
     * 利用向量的夹角公式计算夹角
     * cosBAC = (AB*AC)/(|AB|*|AC|)
     * 其中AB*AC是向量的数量积AB=(Bx-Ax,By-Ay)  AC=(Cx-Ax,Cy-Ay),AB*AC=(Bx-Ax)*(Cx-Ax)+(By-Ay)*(Cy-Ay)
     *
     * @param center 顶点 A
     * @param fakePoint  假设出来的点 和起点是在一水平线上
     * @param touch  点2  C
     * @return
     */
    private fun calculateAngle(fishCenterX: Float, fishCenterY: Float, touchX: Float, touchY: Float): Float {
        val center = PointF(fishCenterX, fishCenterY)
        val touch = PointF(touchX, touchY)
        val fakePoint = PointF(fishCenterX + 1, fishCenterY)

        val abc: Float = (fakePoint.x - center.x) * (touch.x - center.x) + (fakePoint.y - center.y) * (touch.y - center.y)
        val angleCos = (abc /
                (Math.sqrt((fakePoint.x - center.x) * (fakePoint.x - center.x) + (fakePoint.y - center.y) * (fakePoint.y - center.y).toDouble())
                        * Math.sqrt((touch.x - center.x) * (touch.x - center.x) + (touch.y - center.y) * (touch.y - center.y).toDouble()))).toFloat()
        println(angleCos.toString() + "angleCos")

        val temAngle = Math.toDegrees(Math.acos(angleCos.toDouble())).toFloat()
        //判断方向  正左侧  负右侧 0线上,但是Android的坐标系Y是朝下的，所以左右颠倒一下
        val direction: Float = (center.x - touch.x) * (fakePoint.y - touch.y) - (center.y - touch.y) * (fakePoint.x - touch.x)
        return if (direction == 0f) {
            if (abc >= 0) {
                0f
            } else 180f
        } else {
            if (direction > 0) { //右侧顺时针为负
                -temAngle
            } else {
                temAngle
            }
        }
    }

    fun setRadius(currentValue: Float) {
        alpha = (100 * (1 - currentValue) / 2).toInt()
        radius = 50 * currentValue
        invalidate()
    }
}