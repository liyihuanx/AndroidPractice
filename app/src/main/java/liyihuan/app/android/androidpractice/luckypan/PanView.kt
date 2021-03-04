package liyihuan.app.android.androidpractice.luckypan

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.planet.utils.SizeUtils
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.utils.ScreenUtils
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * @author created by liyihuanx
 * @date 2021/3/4
 * description: 类的描述
 */

class PanView : View {
    private lateinit var mPaint: Paint
    private lateinit var mTextPaint: Paint
    private var centerX = -1f
    private var centerY = -1f
    private var radius = -1f
    private var startAng = -120f
    private var sweepAng = 60f

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)

    }

    private fun initView(context: Context) {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 5f
        mPaint.color = resources.getColor(R.color.red)

        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.textSize = SizeUtils.dp2px(context, 20f).toFloat()
        mTextPaint.textAlign = Paint.Align.CENTER
        mTextPaint.color = resources.getColor(R.color.red)


        centerX = ScreenUtils.getScreenWidth(context) / 2f
        centerY = ScreenUtils.getScreenHeight(context) / 2f
        radius = ScreenUtils.getScreenWidth(context) / 3f
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val left = paddingLeft.toFloat()
        val top = paddingTop.toFloat()
        val right = (width - paddingLeft - paddingRight).toFloat()
        val bottom = (height - paddingTop - paddingBottom).toFloat()
        val minLocation = min(right, bottom)
        val radius = minLocation / 2
        val rectF = RectF(left, top, minLocation, minLocation)

        val textX = (1 - cos(60f)) * radius
        val textY = (1 - sin(60f)) * radius

        canvas.drawArc(rectF, startAng, sweepAng, true, mPaint)
        canvas.drawText("数据0", radius, radius, mTextPaint)

//        repeat(6) {
//            canvas.drawArc(rectF, startAng, sweepAng, true, mPaint)
//            startAng += sweepAng
//        }

    }
}