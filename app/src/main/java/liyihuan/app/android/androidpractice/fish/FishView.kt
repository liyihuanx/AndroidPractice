package liyihuan.app.android.androidpractice.fish

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator
import kotlin.math.cos
import kotlin.math.sin

/**
 * @ClassName: FishView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/3/14 19:34
 */
class FishView : Drawable() {

    val HEAD_RADIUS = 30f
    private val BODY_LENGHT = HEAD_RADIUS * 3.2f //第一节身体长度
    private val FINS_LENGTH = HEAD_RADIUS * 1.3f

    private var finsAnimator: ObjectAnimator? = null
    private val mContext: Context? = null

    private val FINS_ALPHA = 100
    private val OTHER_ALPHA = 160

    private var mPaint: Paint = Paint()
    private var mPath: Path = Path()

    private val finsAngle = 0f
    private var startAngle = 0f

    private var centerPointF: PointF
    private lateinit var headPoint: PointF

    private val FINS_RIGHT = -1
    private val FINS_LEFT = 1 //左鱼鳍


    init {
        // 路径

        // 画笔
        mPaint.isAntiAlias = true // 抗锯齿
        mPaint.isDither = true // 防抖
        mPaint.style = Paint.Style.FILL
        mPaint.setARGB(OTHER_ALPHA, 244, 92, 71)
        // 鱼的重心
        centerPointF = PointF(4.18f * FishDrawable.HEAD_RADIUS, 4.18f * FishDrawable.HEAD_RADIUS)


        val valueAnimator = ValueAnimator.ofFloat(-20f, 20f)
        valueAnimator.duration = 3000
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.repeatCount = ValueAnimator.INFINITE;
        valueAnimator.repeatMode = ValueAnimator.REVERSE;
        valueAnimator.addUpdateListener {
            startAngle = it.animatedValue as Float
            invalidateSelf()
        }
        valueAnimator.start()
    }


    override fun draw(canvas: Canvas) {
        val angle = startAngle
        // 看一下鱼的范围
        canvas.drawCircle(centerPointF.x, centerPointF.y, 8.38f * HEAD_RADIUS, mPaint)
        // 画鱼头
        headPoint = calculatePointF(centerPointF, BODY_LENGHT / 2, startAngle)
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS.toFloat(), mPaint)
        // 画鱼鳍
        //右鳍 起点
        //右鳍 起点
        val pointFinsRight = calculatePointF(headPoint, HEAD_RADIUS * 0.9f, angle - 110)
        makeFins(canvas, pointFinsRight, FINS_RIGHT, angle)
        //左鳍 起点
        //左鳍 起点
        val pointFinsLeft = calculatePointF(headPoint, HEAD_RADIUS * 0.9f, angle + 110)
        makeFins(canvas, pointFinsLeft, FINS_LEFT, angle)
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

    private fun makeFins(canvas: Canvas, startPoint: PointF, type: Int, fatherAngle: Float) {
        val contralAngle = 115f //鱼鳍三角控制角度
        mPath.reset()
        mPath.moveTo(startPoint.x, startPoint.y)
        val endPoint = calculatePointF(startPoint, FINS_LENGTH, if (type == FINS_RIGHT) fatherAngle - finsAngle - 180 else fatherAngle + finsAngle + 180)
        val contralPoint = calculatePointF(startPoint, FINS_LENGTH * 1.8f, if (type == FINS_RIGHT) fatherAngle - contralAngle - finsAngle else fatherAngle + contralAngle + finsAngle)
        mPath.quadTo(contralPoint.x, contralPoint.y, endPoint.x, endPoint.y)
        mPath.lineTo(startPoint.x, startPoint.y)
        mPaint.color = Color.argb(FINS_ALPHA, 244, 92, 71)
        canvas.drawPath(mPath, mPaint)
        mPaint.color = Color.argb(OTHER_ALPHA, 244, 92, 71)
    }

    private fun drawBody(canvas: Canvas) {
        // 控制点
        val contralLeft = calculatePointF(headPoint, FishDrawable.BODY_LENGHT * 0.56f, startAngle - 130)
        val contralRight = calculatePointF(headPoint, FishDrawable.BODY_LENGHT * 0.56f, startAngle + 130)

        val endPoint = calculatePointF(headPoint, FishDrawable.BODY_LENGHT, startAngle - 180)
        val point1 = calculatePointF(headPoint, HEAD_RADIUS, startAngle - 80)
        val point2 = calculatePointF(endPoint, HEAD_RADIUS * 0.7f, startAngle - 90)
        val point3 = calculatePointF(endPoint, HEAD_RADIUS * 0.7f, startAngle + 90)
        val point4 = calculatePointF(headPoint, HEAD_RADIUS, startAngle + 80)
        mPath.reset()
        mPath.moveTo(point1.x, point1.y)
        mPath.quadTo(contralLeft.x, contralLeft.y, point2.x, point2.y)
        mPath.lineTo(point3.x, point3.y)
        mPath.quadTo(contralRight.x, contralRight.y, point4.x, point4.y)
        mPath.lineTo(point1.x, point1.y)
        canvas.drawPath(mPath, mPaint)

        val mainPoint = PointF(endPoint.x, endPoint.y)
        makeSegments(canvas, mainPoint, HEAD_RADIUS * 0.7f, 0.6f, startAngle)

    }

    private fun makeSegments(canvas: Canvas, mainPoint: PointF, segmentRadius: Float, MP: Float, fatherAngle: Float) {
        val angle = startAngle
        //身长
        val segementLenght = segmentRadius * (MP + 1)
        val endPoint = calculatePointF(mainPoint, segementLenght, angle - 180)

        val point1 = calculatePointF(mainPoint, segmentRadius, angle - 90)
        val point2 = calculatePointF(endPoint, segmentRadius * MP, angle - 90)
        val point3 = calculatePointF(endPoint, segmentRadius * MP, angle + 90)
        val point4 = calculatePointF(mainPoint, segmentRadius, angle + 90)
        canvas.drawCircle(mainPoint.x, mainPoint.y, segmentRadius, mPaint)
        canvas.drawCircle(endPoint.x, endPoint.y, segmentRadius * MP, mPaint)
        mPath.reset()
        mPath.moveTo(point1.x, point1.y)
        mPath.lineTo(point2.x, point2.y)
        mPath.lineTo(point3.x, point3.y)
        mPath.lineTo(point4.x, point4.y)
        canvas.drawPath(mPath, mPaint)

        //躯干2
        val mainPoint2 = PointF(endPoint.x, endPoint.y)
        makeSegmentsLong(canvas, mainPoint2, segmentRadius * 0.6f, 0.4f, angle)
    }

    private fun makeSegmentsLong(canvas: Canvas, mainPoint: PointF, segmentRadius: Float, MP: Float, fatherAngle: Float) {
        val angle = startAngle
        //身长
        val segementLenght = segmentRadius * (MP + 2.7f)
        val endPoint = calculatePointF(mainPoint, segementLenght, angle - 180)
        val point1: PointF
        val point2: PointF
        val point3: PointF
        val point4: PointF
        point1 = calculatePointF(mainPoint, segmentRadius, angle - 90)
        point2 = calculatePointF(endPoint, segmentRadius * MP, angle - 90)
        point3 = calculatePointF(endPoint, segmentRadius * MP, angle + 90)
        point4 = calculatePointF(mainPoint, segmentRadius, angle + 90)
//        makeTail(canvas, mainPoint, segementLenght, segmentRadius, angle)
        canvas.drawCircle(endPoint.x, endPoint.y, segmentRadius * MP, mPaint)
        mPath.reset()
        mPath.moveTo(point1.x, point1.y)
        mPath.lineTo(point2.x, point2.y)
        mPath.lineTo(point3.x, point3.y)
        mPath.lineTo(point4.x, point4.y)
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
        return (8.38f * HEAD_RADIUS).toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return (8.38f * HEAD_RADIUS).toInt()
    }
}