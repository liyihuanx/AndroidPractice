package com.example.danmu.mydanmu.controller;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.danmu.mydanmu.bean.DmInfo;
import com.example.danmu.mydanmu.thread.DrawThread;
import com.example.danmu.mydanmu.thread.onDrawListener;

import java.util.ArrayList;

/**
 * @author created by liyihuanx
 * @date 2021/1/4
 * description: 类的描述
 */
public class Controller implements ControllerInterface {

    private DrawThread drawThread;
    private int offsetX = 0;


    private ArrayList<DmInfo> dmList;

    public Controller() {
        init();
    }

    private void init() {
        dmList = new ArrayList<>();
        drawThread = new DrawThread();
    }

    /**
     * 开启线程绘制
     */
    @Override
    public void startThread() {
        if (drawThread.isRun()) return;
        drawThread.start();

        drawThread.setDrawListener(new onDrawListener() {
            @Override
            public void onDraw(Canvas canvas) {
                runDrawTask(canvas);
            }
        });
    }

    /**
     * 线程调用的方法，绘制
     *
     * @param canvas
     */
    private void runDrawTask(Canvas canvas) {
        canvas.save();
        // 平移offsetX 绘制下一个，offsetX怎么更新??
        canvas.translate(offsetX,0);

        canvas.restore();
    }


    /**
     * 添加弹幕数据
     */
    @Override
    public void addDmList(ArrayList<DmInfo> data) {
        dmList.clear();
        dmList.addAll(data);
    }

    /**
     * 本来打算用来初始化controller的
     */
    @Override
    public void start() {

    }

    @Override
    public void pause() {
        drawThread.setDraw(false);
        drawThread.setRun(false);
        Log.d("QWER", "pause: 停止绘制");
    }

    @Override
    public void resume() {
        drawThread.setDraw(true);

    }

    @Override
    public void destroy() {

    }

    /**
     * 一些个配置数据
     */
    private int width;
    private int height;
    private int speed; // 速度
    private int span; // 跨度
    private int padding_TB; // 上下间距
    private int padding_RL; // 左右间距
    private Paint paint; // 画笔

    public Controller setWidth(int width) {
        this.width = width;
        return this;
    }

    public Controller setHeight(int height) {
        this.height = height;
        return this;
    }

    public Controller setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public Controller setSpan(int span) {
        this.span = span;
        return this;
    }

    public Controller setPadding_TB(int padding_TB) {
        this.padding_TB = padding_TB;
        return this;
    }

    public Controller setPadding_RL(int padding_RL) {
        this.padding_RL = padding_RL;
        return this;
    }

    // 把SurfaceHolder设置给线程类，这样可以拿到canvas
    public Controller setSurfaceHolder(SurfaceHolder surfaceHolder) {
        drawThread.setSurfaceHolder(surfaceHolder);
        return this;
    }

    public Controller setPaint(Paint paint) {
        this.paint = paint;
        return this;
    }

}
