package liyihuan.app.android.androidpractice.timecount;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import liyihuan.app.android.androidpractice.R;

/**
 * @ClassName: TimeCountView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/10/22 22:48
 */
class TimeCountView extends View {

    //圆轮颜色
    private int mRingColor;
    //圆轮宽度
    private float mRingWidth;
    //圆轮进度值文本大小
    private int mRingProgessTextSize;
    //宽度
    private int mWidth;
    //高度
    private int mHeight;
    //圆环的矩形区域
    private RectF mRectF;

    private Paint mPaint;
    private int mProgessTextColor;
    private int mCountdownTime;
    private float mCurrentProgress;
    private OnCountDownFinishListener mListener;

    public TimeCountView(Context context) {
        this(context,null);
    }

    public TimeCountView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TimeCountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        mRingColor = typedArray.getColor(R.styleable.CountDownView_ringColor, context.getResources().getColor(R.color.colorAccent));
        mRingWidth = typedArray.getFloat(R.styleable.CountDownView_ringWidth, 40);
        mRingProgessTextSize = typedArray.getDimensionPixelSize(R.styleable.CountDownView_progressTextSize, sp2px(context, 20));
        mProgessTextColor = typedArray.getColor(R.styleable.CountDownView_progressTextColor, context.getResources().getColor(R.color.colorAccent));
        mCountdownTime = typedArray.getInteger(R.styleable.CountDownView_countdownTime, 60);
        typedArray.recycle();

//        Paint.ANTI_ALIAS_FLAG ：抗锯齿标志
//        Paint.FILTER_BITMAP_FLAG : 使位图过滤的位掩码标志
//        Paint.DITHER_FLAG : 使位图进行有利的抖动的位掩码标志
//        Paint.UNDERLINE_TEXT_FLAG : 下划线
//        Paint.STRIKE_THRU_TEXT_FLAG : 中划线
//        Paint.FAKE_BOLD_TEXT_FLAG : 加粗
//        Paint.LINEAR_TEXT_FLAG : 使文本平滑线性扩展的油漆标志
//        Paint.SUBPIXEL_TEXT_FLAG : 使文本的亚像素定位的绘图标志
//        Paint.EMBEDDED_BITMAP_TEXT_FLAG : 绘制文本时允许使用位图字体的绘图标志

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        this.setWillNotDraw(false);//重写onDraw方法,需要调用,略过绘制的过程，优化了性能。
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRectF = new RectF(mRingWidth / 2,mRingWidth / 2,
                mWidth - mRingWidth / 2, mHeight - mRingWidth / 2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         *圆环
         */
        //颜色
        mPaint.setColor(mRingColor);
        //空心
        mPaint.setStyle(Paint.Style.STROKE);
        //宽度
        mPaint.setStrokeWidth(mRingWidth);
        canvas.drawArc(mRectF, -90, mCurrentProgress - 360, false, mPaint);
        //绘制文本
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        String text = mCountdownTime - (int) (mCurrentProgress / 360f * mCountdownTime) + "";
        textPaint.setTextSize(mRingProgessTextSize);
        textPaint.setColor(mProgessTextColor);

        //文字居中显示
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (int) ((mRectF.bottom + mRectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        canvas.drawText(text, mRectF.centerX(), baseline, textPaint);
    }

    private ValueAnimator getValA(long countdownTime) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100);
        valueAnimator.setDuration(countdownTime);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(0);
        return valueAnimator;
    }
    /**
     * 开始倒计时
     */
    public void startCountDown() {
        setClickable(false);
        ValueAnimator valueAnimator = getValA(mCountdownTime * 1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float i = Float.valueOf(String.valueOf(animation.getAnimatedValue()));
                mCurrentProgress = (int) (360 * (i / 100f));
                invalidate();
            }
        });
        valueAnimator.start();
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //倒计时结束回调
                if (mListener != null) {
                    mListener.countDownFinished();
                }
                setClickable(true);
            }

        });
    }
    public void setAddCountDownListener(OnCountDownFinishListener mListener) {
        this.mListener = mListener;
    }
    public interface OnCountDownFinishListener {
        void countDownFinished();
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
