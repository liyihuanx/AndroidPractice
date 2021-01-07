package com.example.danmu.mydanmu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.danmu.R;
import com.example.danmu.mydanmu.bean.DmInfo;
import com.example.danmu.mydanmu.controller.Controller;

import java.util.ArrayList;

/**
 * @author created by liyihuanx
 * @date 2021/1/4
 * description: 类的描述
 */
public class MyDmView extends SurfaceView implements SurfaceHolder.Callback, dmViewInterface {

    // 提供访问和控制SurfaceView背后的Surface相关的方法
    private SurfaceHolder mSurfaceHolder;
    private Controller controller;
    private Paint mPaint;

    public MyDmView(Context context) {
        this(context, null);
    }

    public MyDmView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDmView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSurfaceHolder();
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(2);
        // 初始化controller
        controller = new Controller()
                .setPaint(mPaint)
                .setPadding_RL(10)
                .setPadding_TB(20)
                .setSpeed(1)
                .setSpan(2);
    }

    private void initSurfaceHolder() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("QWER", "surfaceCreated: " + controller);

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("QWER", "width: " + width + "---" + "height:" + height);
        controller.setWidth(width)
                .setHeight(height)
                .setSurfaceHolder(mSurfaceHolder);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("QWER", "surfaceDestroyed: ");
    }

    /**
     * 绘制每一个item的方法
     * 交给controller开启线程去绘制
     */
    @Override
    public void dmItemDraw() {

    }

    @Override
    public Controller getController() {
        if (controller != null) {
            return controller;
        }
        return null;
    }

    @Override
    public void addDmList(ArrayList<DmInfo> data) {

    }
}
