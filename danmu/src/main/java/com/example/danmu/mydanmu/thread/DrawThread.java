package com.example.danmu.mydanmu.thread;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * @author created by liyihuanx
 * @date 2021/1/4
 * description: 类的描述
 */
public class DrawThread extends Thread {

    // 是否在run和绘制
    private boolean isRun = false;
    private boolean isDraw = false;
    private onDrawListener drawListener;
    private SurfaceHolder surfaceHolder;

    public void setDrawListener(onDrawListener drawListener) {
        this.drawListener = drawListener;
    }

    public DrawThread() {
    }

    @Override
    public synchronized void start() {
        isDraw = true;
        isRun = true;
        super.start();
    }

    @Override
    public void run() {
        super.run();
        while (isRun) {
            draw();
        }
    }

    private void draw() {
        if (isDraw) {
            Log.d("QWER", "draw: ");
            // 锁定并且获得当前canvas
            Canvas canvas = surfaceHolder.lockCanvas();
            drawListener.onDraw(canvas);
            Log.d("QWER", "draw: 222");

            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
                Log.d("QWER", "draw: 3333");

            }

        }
    }


    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }


    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

}
