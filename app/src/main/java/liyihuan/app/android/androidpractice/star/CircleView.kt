package liyihuan.app.android.androidpractice.star

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Property
import android.view.View

/**
 * @author created by liyihuanx
 * @date 2021/3/19
 * description: 类的描述
 */
class CircleView : View {

    private val START_COLOR = -0xa8de
    private val END_COLOR = -0x3ef9

    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var clearPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val argbEvaluator = ArgbEvaluator()


    private var centerX = 0f
    private var centerY = 0f
    private var maxRadius = 0f
    private var outPresent = 0f
    private var clearPresent = 0f

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.RED
        // 设置清除用的画笔
        clearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2f
        centerY = h / 2f
        maxRadius = w / 2f

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // PorterDuff.Mode.CLEAR不支持硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        canvas.drawCircle(centerX, centerY, maxRadius * outPresent, mPaint)
        canvas.drawCircle(centerX, centerY, maxRadius * clearPresent, clearPaint)

//        // 不想关闭硬件加速也可以这样
//        mTempBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
//        mTempCanvas = Canvas(mTempBitmap) // 初始化画布
//        mTempCanvas.drawCircle(centerX, centerY, maxRadius * present, mPaint)
//        mTempCanvas.drawCircle(centerX, centerY, maxRadius * present * 0.8f, clearPaint)
//        canvas.drawBitmap(mTempBitmap, 0f, 0f, null) // 画布绘制两个圆.

    }

    // 更新圆圈的颜色变化
    private fun updateCircleColor() {
        // 0.5到1颜色渐变
        var colorProgress = Utils.clamp(outPresent.toDouble(), 0.5, 1.0).toFloat()
        // 转换映射控件
        colorProgress = Utils.mapValueFromRangeToRange(colorProgress.toDouble(), 0.5, 1.0, 0.0, 1.0).toFloat()
        mPaint.color = argbEvaluator.evaluate(colorProgress, START_COLOR, END_COLOR) as Int
    }


    companion object {
        // 自定义的
        val OUT_PRESENT = object : Property<CircleView, Float>(Float::class.java, "outPresent") {
            override fun get(`object`: CircleView): Float {
                return `object`.outPresent
            }

            override fun set(`object`: CircleView, value: Float) {
                `object`.outPresent = value
                `object`.updateCircleColor()
                `object`.postInvalidate()
            }
        }

        val CLEAR_PRESENT = object : Property<CircleView, Float>(Float::class.java, "clearPresent") {
            override fun get(`object`: CircleView): Float {
                return `object`.clearPresent
            }

            override fun set(`object`: CircleView, value: Float) {
                `object`.clearPresent = value
                `object`.postInvalidate()
            }
        }
    }

}