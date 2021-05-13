package liyihuan.app.android.androidpractice.waveball

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import liyihuan.app.android.androidpractice.diffType.ScreenUtils
import liyihuan.app.android.androidpractice.utils.SizeUtils

/**
 * @author created by liyihuanx
 * @date 2021/3/16
 * description: 类的描述
 */
class WaveLoading : View {

    private lateinit var mPath: Path
    private lateinit var mPaint: Paint
    private lateinit var textPaint: Paint


    private var centerY = 0
    private var centerX = 0

    private var presentX = 0f
    private var presentY = 0f

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        mPath = Path()
        mPaint = Paint()
        textPaint = Paint()
        mPaint.flags = Paint.ANTI_ALIAS_FLAG
        mPaint.isDither = true
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = 10f
        mPaint.color = Color.rgb(41, 163, 254)

        centerY = ScreenUtils.getScreenHeight(context)
        centerX = ScreenUtils.getScreenWidth(context)

        //文字画笔
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.color = Color.WHITE
        textPaint.typeface = Typeface.DEFAULT_BOLD
        textPaint.textSize = SizeUtils.dp2px(context, 80f).toFloat()

        // 用sin ？？
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = 1000
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.repeatMode = ValueAnimator.RESTART
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.start()
        valueAnimator.addUpdateListener {
            presentX = it.animatedValue as Float
            invalidate()
        }

        val valueAnimator2 = ValueAnimator.ofFloat(1f, 0f)
        valueAnimator2.duration = 5000
        valueAnimator2.repeatCount = ValueAnimator.INFINITE
        valueAnimator2.repeatMode = ValueAnimator.RESTART
        valueAnimator2.interpolator = LinearInterpolator()
        valueAnimator2.start()
        valueAnimator2.addUpdateListener {
            presentY = it.animatedValue as Float
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }


    override fun onDraw(canvas: Canvas) {
        // 先写一个蓝色的字
        textPaint.color = Color.rgb(41, 163, 254)
        drawText(canvas)
        // 再根据path剪裁画布
        val clipPath = getActionPath(canvas, presentX, presentY)
        canvas.save()
        canvas.clipPath(clipPath)
        // 在裁剪下来的画布写白色的字
        textPaint.color = Color.WHITE
        drawText(canvas)
        canvas.restore()
    }


    /**
     * 绘制两个周期的贝塞尔曲线
     * presentX 控制path路径的起点 (0 --> 1)
     * presentY
     */
    private fun getActionPath(canvas: Canvas, presentX: Float, presentY: Float): Path {
        // 起点设置在一个屏幕之外
        var startX = -centerX.toFloat()
        startX += presentX * centerX
        mPath.reset()
        mPath.moveTo(startX, centerY / 2f)

        // 控制点坐标
        val controlX = centerX / 4f
        val controlY = centerY / 20f
        // dx,dy 线段移动相对距离
        // rQuadTo 终点变为起点
        mPath.rQuadTo(controlX, controlY, centerX / 2f, 0f)
        mPath.rQuadTo(controlX, -controlY, centerX / 2f, 0f)
        // 画两个周期
        mPath.rQuadTo(controlX, controlY, centerX / 2f, 0f)
        mPath.rQuadTo(controlX, -controlY, centerX / 2f, 0f)

        //右侧的直线
        mPath.lineTo(startX + centerX * 2f, centerY.toFloat())
        //下边的直线
        mPath.lineTo(startX, centerY.toFloat())
        //自动闭合补出左边的直线
        mPath.close()
        canvas.drawPath(mPath, mPaint)
        return mPath
    }

    private fun drawText(canvas: Canvas) {
        val rect = Rect(0, 0, centerX, centerY)
        textPaint.textAlign = Paint.Align.CENTER


        val fontMetrics = textPaint.fontMetrics
        val top = fontMetrics.top
        val bottom = fontMetrics.bottom

        val centerY = (rect.centerY() - top / 2 - bottom / 2)

        canvas.drawText("李逸欢", rect.centerX().toFloat(), centerY, textPaint)
    }
}