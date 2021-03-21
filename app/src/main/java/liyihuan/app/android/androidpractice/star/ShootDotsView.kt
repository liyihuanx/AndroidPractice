package liyihuan.app.android.androidpractice.star

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Property
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author created by liyihuanx
 * @date 2021/3/18
 * description: 类的描述
 */
class ShootDotsView : View {

    private val mCirclePaints = arrayOfNulls<Paint>(4) // 4种类型的圆圈
    private val COLOR_1 = -0x3ef9
    private val COLOR_2 = -0x6800
    private val COLOR_3 = -0xa8de
    private val COLOR_4 = -0xbbcca

    // 散射点的数量和每个点之间的角度
    private var dotNum = 7
    private var dotAngle = 52.00

    // 绘制的中点
    private var centerX = 0
    private var centerY = 0

    // 外部和内部散射的最大半径
    private var outMaxCircleRadius = 0f
    private var inMaxCircleRadius = 0f

    // 散射的大点和小点的半径
    private var outDotsRadius = 0f
    private var inDotsRadius = 0f

    // 当前散射的半径长度
    private var outCircleRadius = 0f
    private var inCircleRadius = 0f

    private var mMaxDotSize = 0f

    private var currentPresent = 0f

    private lateinit var argbEvaluator: ArgbEvaluator


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }


    // 获取控件宽高
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2
        centerY = h / 2
        mMaxDotSize = 20f
        outMaxCircleRadius = w / 2 - mMaxDotSize * 2f
        inMaxCircleRadius = outMaxCircleRadius * 0.8f
    }

    private fun initView(context: Context) {
        argbEvaluator = ArgbEvaluator()
        // 填充圆圈
        for (i in mCirclePaints.indices) {
            mCirclePaints[i] = Paint(Paint.ANTI_ALIAS_FLAG)
            mCirclePaints[i]!!.style = Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawOut(canvas)
        drawIn(canvas)
    }

    private fun drawOut(canvas: Canvas) {

        repeat(dotNum) {
            var currentX = centerX + cos(Math.toRadians(dotAngle * it)) * outCircleRadius
            var currentY = centerY + sin(Math.toRadians(dotAngle * it)) * outCircleRadius
            canvas.drawCircle(currentX.toFloat(), currentY.toFloat(), outDotsRadius, mCirclePaints[it % mCirclePaints.size]!!)
        }
    }

    private fun drawIn(canvas: Canvas) {

        repeat(dotNum) {
            var currentX = centerX + cos(Math.toRadians(dotAngle * it - 10)) * inCircleRadius
            var currentY = centerY + sin(Math.toRadians(dotAngle * it - 10)) * inCircleRadius
            canvas.drawCircle(currentX.toFloat(), currentY.toFloat(), inDotsRadius, mCirclePaints[(it + 1) % mCirclePaints.size]!!)
        }
    }


    // 变化颜色
    private fun updateDotsPaints() {
        if (currentPresent < 0.5f) {
            val progress = Utils.mapValueFromRangeToRange(currentPresent.toDouble(), 0.0, 0.5, 0.0, 1.0).toFloat()
            mCirclePaints[0]!!.color = (argbEvaluator.evaluate(progress, COLOR_1, COLOR_2) as Int)
            mCirclePaints[1]!!.color = (argbEvaluator.evaluate(progress, COLOR_2, COLOR_3) as Int)
            mCirclePaints[2]!!.color = (argbEvaluator.evaluate(progress, COLOR_3, COLOR_4) as Int)
            mCirclePaints[3]!!.color = (argbEvaluator.evaluate(progress, COLOR_4, COLOR_1) as Int)
        } else {
            val progress = Utils.mapValueFromRangeToRange(currentPresent.toDouble(), 0.5, 1.0, 0.0, 1.0).toFloat()
            mCirclePaints[0]!!.color = (argbEvaluator.evaluate(progress, COLOR_2, COLOR_3) as Int)
            mCirclePaints[1]!!.color = (argbEvaluator.evaluate(progress, COLOR_3, COLOR_4) as Int)
            mCirclePaints[2]!!.color = (argbEvaluator.evaluate(progress, COLOR_4, COLOR_1) as Int)
            mCirclePaints[3]!!.color = (argbEvaluator.evaluate(progress, COLOR_1, COLOR_2) as Int)
        }
    }

    // 变化透明度
    private fun updateDotsAlpha() {
        val progress = Utils.clamp(currentPresent.toDouble(), 0.6, 1.0).toFloat() // 最小0.6, 最大1
        val alpha = Utils.mapValueFromRangeToRange(progress.toDouble(), 0.6, 1.0, 255.0, 0.0).toInt() // 直至消失
        mCirclePaints[0]!!.alpha = alpha
        mCirclePaints[1]!!.alpha = alpha
        mCirclePaints[2]!!.alpha = alpha
        mCirclePaints[3]!!.alpha = alpha
    }

    // 变化大小
    private fun updateOutSize() {
        // 半径先走的快, 后走的慢
        outCircleRadius = if (currentPresent < 0.3f) {
            Utils.mapValueFromRangeToRange(currentPresent.toDouble(), 0.0, 0.3, 0.0, outMaxCircleRadius * 0.8).toFloat()
        } else {
            Utils.mapValueFromRangeToRange(currentPresent.toDouble(), 0.3, 1.0, outMaxCircleRadius * 0.8, outMaxCircleRadius.toDouble()).toFloat()
        }

        // 点的大小, 小于0.7是最大点, 大于0.7逐渐为0.
        outDotsRadius = if (currentPresent < 0.7f) {
            mMaxDotSize
        } else {
            Utils.mapValueFromRangeToRange(currentPresent.toDouble(), 0.7, 1.0, mMaxDotSize.toDouble(), 0.0).toFloat()
        }
    }

    private fun updateInSize() {
        inCircleRadius = if (currentPresent < 0.3f) {
            Utils.mapValueFromRangeToRange(currentPresent.toDouble(), 0.0, 0.3, 0.0, inMaxCircleRadius.toDouble()).toFloat()
        } else {
            inMaxCircleRadius
        }

        // 点的缩小速度
        inDotsRadius = if (currentPresent < 0.2) {
            mMaxDotSize
        } else if (currentPresent < 0.5) {
            Utils.mapValueFromRangeToRange(currentPresent.toDouble(), 0.2, 0.5, mMaxDotSize.toDouble(), mMaxDotSize * 0.3).toFloat()
        } else {
            Utils.mapValueFromRangeToRange(currentPresent.toDouble(), 0.5, 1.0, mMaxDotSize * 0.3, 0.0).toFloat()
        }
    }


    // 通过present来控制整个视图
    public fun updateView() {
        updateDotsPaints()
        updateDotsAlpha()
        updateOutSize()
        updateInSize()
        postInvalidate()
    }

    companion object {
        // 自定义的
        val DOTS_PRESENT = object : Property<ShootDotsView, Float>(Float::class.java, "currentPresent") {
            override fun get(`object`: ShootDotsView): Float {
                return `object`.currentPresent
            }

            override fun set(`object`: ShootDotsView, value: Float) {
                `object`.currentPresent = value
                `object`.updateView()
            }
        }

    }
}