package liyihuan.app.android.androidpractice.timecount;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @ClassName: TImeView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/11/2 22:16
 */
class TimeView extends View {
    private Paint mPaint;
    public TimeView(Context context) {
        this(context, null);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20f);
        int center = getWidth() / 2;
        canvas.drawCircle(center, center, 20, mPaint);
    }
}
