package liyihuan.app.android.androidpractice.timecount;

import android.animation.Animator;
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

import static liyihuan.app.android.androidpractice.timecount.TimeCountView.sp2px;

/**
 * @ClassName: TImeView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/11/2 22:16
 */
class CountDownView extends View {
    private Paint mPaint;
    private Paint mTextPaint;
    private int mWidth;
    private int mHeight;
    private RectF mRectF;
    private float mRingWidth; // 圆环宽度
    private int mRingColor; // 圆环颜色
    private int mTextSize; // 倒计时文本大小
    private int mTextColor; // 倒计时文本颜色
    private int mCountdownTime; // 倒计时时间
    private float mProgress;
    private ValueAnimator mValueAnimator;
    private CountDownListener countDownListener;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        mRingColor = typedArray.getColor(R.styleable.CountDownView_ringColor, context.getResources().getColor(R.color.colorAccent));
        mRingWidth = typedArray.getFloat(R.styleable.CountDownView_ringWidth, 40);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.CountDownView_progressTextSize, sp2px(context, 20));
        mTextColor = typedArray.getColor(R.styleable.CountDownView_progressTextColor, context.getResources().getColor(R.color.colorAccent));
        mCountdownTime = typedArray.getInteger(R.styleable.CountDownView_countdownTime, 60);
        typedArray.recycle();
        initView();
    }

    private void initView() {
//        Paint.ANTI_ALIAS_FLAG ：抗锯齿标志
//        Paint.FILTER_BITMAP_FLAG : 使位图过滤的位掩码标志
//        Paint.DITHER_FLAG : 使位图进行有利的抖动的位掩码标志
//        Paint.UNDERLINE_TEXT_FLAG : 下划线
//        Paint.STRIKE_THRU_TEXT_FLAG : 中划线
//        Paint.FAKE_BOLD_TEXT_FLAG : 加粗
//        Paint.LINEAR_TEXT_FLAG : 使文本平滑线性扩展的油漆标志
//        Paint.SUBPIXEL_TEXT_FLAG : 使文本的亚像素定位的绘图标志
//        Paint.EMBEDDED_BITMAP_TEXT_FLAG : 绘制文本时允许使用位图字体的绘图标志
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG); // 抗锯齿
        mPaint.setStrokeWidth(mRingWidth);
        mPaint.setColor(mRingColor);

        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        this.setWillNotDraw(false);//重写onDraw方法,需要调用,略过绘制的过程，优化了性能。
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRectF = new RectF(mRingWidth / 2, mRingWidth / 2, mWidth - mRingWidth / 2, mHeight - mRingWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(mRectF, -90, mProgress - 360, false, mPaint);
        String time = mCountdownTime - (int) (mProgress * mCountdownTime / 360f) + "";
        //文字居中显示
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int baseline = (int) ((mRectF.bottom + mRectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        canvas.drawText(time,mRectF.centerX(),baseline,mTextPaint);
    }


    private ValueAnimator countDownAnimation(long duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 360);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(0);
        return valueAnimator;
    }


    public void countDownStart() {
        setClickable(false);
        mValueAnimator = countDownAnimation(mCountdownTime * 1000);
        mValueAnimator.addUpdateListener(valueAnimator -> {
            float time = (float) valueAnimator.getAnimatedValue();
            mProgress = time * 1f;
            invalidate();
        });
        mValueAnimator.start();
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (countDownListener != null) {
                    countDownListener.CountDownStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (countDownListener != null) {
                    countDownListener.CountDownFinish();
                }
                mProgress = 0;
                invalidate();
                setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    public interface CountDownListener {
        void CountDownStart();

        void CountDownStop();

        void CountDownResume();

        void CountDownFinish();
    }

    public void setCountDownListener(CountDownListener countDownListener) {
        this.countDownListener = countDownListener;
    }
}
