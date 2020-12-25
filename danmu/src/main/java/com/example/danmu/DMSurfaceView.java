package com.example.danmu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

/**
 * @author created by liyihuanx
 * @date 2020/12/24
 * description: 类的描述
 */
public class DMSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceHolder;
    private Controller mController;
    private Controller.Builder builder;
    private int mWidth;
    private int mHeight;

    public DMSurfaceView(Context context) {
        this(context, null);
    }

    public DMSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public DMSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHolder();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DMSurfaceView, defStyleAttr, 0);
        final Direction direction = Direction.getType(a.getInt(R.styleable.DMSurfaceView_dm_direction, Direction.RIGHT_LEFT.value));

        final int span = a.getDimensionPixelOffset(R.styleable.DMSurfaceView_dm_span, 2);
        final int sleep = a.getInteger(R.styleable.DMSurfaceView_dm_sleep, 0);
        final int spanTime = a.getInteger(R.styleable.DMSurfaceView_dm_span_time, 0);
        final int vSpace = a.getDimensionPixelOffset(R.styleable.DMSurfaceView_dm_v_space, 10);
        final int hSpace = a.getDimensionPixelOffset(R.styleable.DMSurfaceView_dm_h_space, 10);

        builder = new Controller.Builder()
                .setSpan(span)
                .setDirection(direction)
                .setSleep(sleep)
                .setSpanTime(spanTime)
                .sethSpace(hSpace)
                .setvSpace(vSpace);

        a.recycle();
    }

    private void initHolder() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("QWER", "surfaceCreated: ");
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {
        Log.d("QWER", "surfaceChanged: ");
        if (mWidth == width && mHeight == height) return;
        if (mController != null) {
            mController.destroy();
        }
        this.mWidth = width;
        this.mHeight = height;
        mController = builder.setSurfaceProxy(new SurfaceProxy(mSurfaceHolder))
                .setWidth(mWidth)
                .setHeight(mHeight)
                .build();
        mController.start();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("QWER", "surfaceDestroyed: ");
    }

    public Controller getController() {
        return mController;
    }
}
