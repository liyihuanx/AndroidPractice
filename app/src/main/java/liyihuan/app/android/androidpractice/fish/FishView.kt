package liyihuan.app.android.androidpractice.fish

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.cos
import kotlin.math.sin

/**
 * @ClassName: FishView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/3/14 19:34
 */
class FishView : Drawable() {

    private var mPaint: Paint = Paint()
    private var mPath: Path = Path()

    private val OTHER_ALPHA = 110
    private val BODY_ALPHA = 180

    private val radius = 50
    private var centerPointF: PointF
    private lateinit var headPointF: PointF


    init {
        mPaint.isAntiAlias = true // 抗锯齿
        mPaint.isDither = true // 防抖
        mPaint.style = Paint.Style.FILL
        mPaint.setARGB(OTHER_ALPHA, 244, 92, 71)
        centerPointF = PointF(4.18f * radius, 4.18f * radius)
    }


    override fun draw(canvas: Canvas) {
        // 看一下鱼的范围
        canvas.drawCircle(centerPointF.x, centerPointF.y, 8.38f * radius, mPaint)
        // 画鱼头
        headPointF = calculatePointF(centerPointF, 2.6f * radius, 0f)
        canvas.drawCircle(headPointF.x, headPointF.y, radius.toFloat(), mPaint)
        // 画鱼鳍
        drawFishFins(canvas, true)
        drawFishFins(canvas, false)
        // 画身体
        drawBody(canvas)
    }


    /**
     * 已知临边长度和夹角
     * 求某点的坐标
     * 1角度=（π/180）弧度
     * Math.toRadians 得到弧度
     */
    private fun calculatePointF(startPointF: PointF, length: Float, angle: Float): PointF {
        val resultX = cos(Math.toRadians(angle.toDouble())) * length + startPointF.x
        val resultY = sin(Math.toRadians(angle.toDouble() - 180)) * length + startPointF.y

        return PointF(resultX.toFloat(), resultY.toFloat())
    }


    /**
     * 画鱼鳍
     */
    private fun drawFishFins(canvas: Canvas, isLeft: Boolean) {
        var startDirection = -1f
        if (isLeft) {
            startDirection = 1f
        }
        val startPointF = calculatePointF(headPointF, 0.9f * radius, 110f * startDirection)
        val endPointF = calculatePointF(startPointF, 1.5f * radius, 180f * startDirection)
        val controlPointF = calculatePointF(headPointF, 2.8f * radius, 115f * startDirection)

        mPath.reset()
        mPath.moveTo(startPointF.x, startPointF.y)
        mPath.quadTo(controlPointF.x, controlPointF.y, endPointF.x, endPointF.y)
        canvas.drawPath(mPath, mPaint)
    }

    private fun drawBody(canvas: Canvas) {
        val pointOne = PointF(headPointF.x, headPointF.y - radius)
        val pointTwo = PointF(headPointF.x, headPointF.y + radius)
        val pointC = calculatePointF(centerPointF, 1f * radius, 180f)
        val pointThree = PointF(pointC.x, pointC.y - 0.8f * radius)
        val pointFour = PointF(pointC.x, pointC.y + 0.8f * radius)
        mPath.reset()
        mPath.moveTo(pointOne.x, pointOne.y)
        mPath.lineTo(pointTwo.x, pointTwo.y)
        mPath.lineTo(pointFour.x, pointFour.y)
        mPath.lineTo(pointThree.x, pointThree.y)
        mPath.close()
        canvas.drawPath(mPath, mPaint)
    }


    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }


    override fun getIntrinsicHeight(): Int {
        return (8.38f * radius).toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return (8.38f * radius).toInt()
    }
}