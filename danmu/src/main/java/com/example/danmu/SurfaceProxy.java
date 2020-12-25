package com.example.danmu;

import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;

/**
 * @author created by liyihuanx
 * @date 2020/12/24
 * description: 类的描述
 */
public class SurfaceProxy {
    private Surface mSurface;
    private SurfaceHolder mSurfaceHolder;

    public SurfaceProxy(Surface surface) {
        this.mSurface = surface;
    }

    public SurfaceProxy(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
    }

    public Canvas lockCanvas() {
        if (mSurfaceHolder != null) {
            return mSurfaceHolder.lockCanvas();
        } else {
            return mSurface.lockCanvas(null);
        }
    }

    public void unlockCanvasAndPost(Canvas canvas) {
        if (mSurfaceHolder != null) {
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        } else {
            mSurface.unlockCanvasAndPost(canvas);
        }
    }
}
