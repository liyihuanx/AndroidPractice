package liyihuan.app.android.androidpractice.luckypan

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.utils.ScreenUtils
import liyihuan.app.android.androidpractice.utils.SizeUtils

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

    private lateinit var bgGame01: Bitmap
    private lateinit var bgGame02: Bitmap
    private lateinit var bgGame03: Bitmap
    private lateinit var startGame: Bitmap


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

//        bgGame01 = BitmapFactory.decodeResource(resources, R.mipmap.bg_game_01)
//        bgGame02 = BitmapFactory.decodeResource(resources, R.mipmap.bg_game_02)
        bgGame03 = BitmapFactory.decodeResource(resources, R.mipmap.bg_game_03)
        startGame = BitmapFactory.decodeResource(resources, R.mipmap.start_game)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        val left = paddingLeft.toFloat()
//        val top = paddingTop.toFloat()
//        val right = (width - paddingLeft - paddingRight).toFloat()
//        val bottom = (height - paddingTop - paddingBottom).toFloat()
//        val minLocation = min(right, bottom)
//        val radius = minLocation / 2
//        val rectF = RectF(left, top, minLocation, minLocation)
//
//        val textX = (1 - cos(60f)) * radius
//        val textY = (1 - sin(60f)) * radius
//
//        canvas.drawArc(rectF, startAng, sweepAng, true, mPaint)
//        canvas.drawText("数据0", radius, radius, mTextPaint)
//        repeat(6) {
//            canvas.drawArc(rectF, startAng, sweepAng, true, mPaint)
//            startAng += sweepAng
//        }

//        canvas.drawBitmap(bgGame01, 0f, 0f, mPaint)
//        canvas.drawBitmap(bgGame02, 0f, bgGame01.height.toFloat(), mPaint)
//
//        val offsetX = (bgGame01.width - bgGame03.width) / 2f
//        val offsetY = bgGame01.width / 3f - SizeUtils.dp2px(context,15f)
//        canvas.save()
//        canvas.translate(offsetX,offsetY)
////        val matrix = Matrix()
////        matrix.setTranslate(offsetX, offsetY)
//        canvas.drawBitmap(bgGame03, 0f,0f, mPaint)
//        canvas.restore()
//
//
//        val offsetX2 = (bgGame01.width - startGame.width) / 2f
//        val offsetY2 = (bgGame03.height - startGame.height) / 2f + offsetY
//        canvas.save()
//        canvas.translate(offsetX2,offsetY2)
////        val matrix2 = Matrix()
////        matrix2.setTranslate(offsetX2, offsetY2)
//        canvas.drawBitmap(startGame, 0f,0f, mPaint)
//        canvas.restore()
//        canvas.drawBitmap(bgGame03, 0f, 0f, mPaint)
//        canvas.translate(bgGame03.width.toFloat(),bgGame03.height.toFloat())
//        canvas.rotate(60f)
//        drawTextContent(canvas)

//        repeat(6) {
//            canvas.save()
//            canvas.rotate(60f * (it + 1), 0f, 0f)
//            drawTextContent(canvas)
//            canvas.restore()
//        }

        val path = Path()
        path.lineTo(centerX, centerY)
        canvas.translate(centerX, centerY)
        repeat(6) {
            canvas.rotate(60f)
            canvas.drawPath(path, mPaint)
        }

    }

    private fun drawTextContent(canvas: Canvas) {
        val left = 0f
        val top = 0f
        val right = bgGame03.width.toFloat()
        val bottom = bgGame03.height.toFloat()
        val rectf = RectF(left, top, right, bottom)

        val path = Path()
//        path.moveTo() 把起点移动到某个位置
//        path.reset()  每次用完后，把路径重置
        path.lineTo(right / 2, bottom / 2)
        mPaint.measureText("竖直的文字")
        canvas.drawPath(path, mPaint)
//        canvas.drawText("竖直的文字")
//        canvas.drawArc(rectf, startAng, sweepAng, true, mPaint)

    }
}