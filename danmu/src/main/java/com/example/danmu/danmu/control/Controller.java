package com.example.danmu.danmu.control;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;


import com.example.danmu.danmu.OnDMAddListener;
import com.example.danmu.danmu.entity.BaseDmEntity;
import com.example.danmu.danmu.onMyClickListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 动画路径绘制登帮助类
 */
public class Controller {
    // 新弹幕
    private Queue<BaseDmEntity> mNewDMQueue = new ConcurrentLinkedQueue<>();
    // 已添加到屏幕的弹幕
    private Queue<BaseDmEntity> mAddedMDList = new ConcurrentLinkedQueue<>();
    private int mWidth, mHeight;
    private float offset;
    private int hSpace = 20;// 水平间距
    private int vSpace = 20;// 垂直间距
    private float span = 5F;// 刷新一次的跨度
    private int spanTime = 0; // 一个跨度需要多少时间
    private float speed = 0F; //速度
    private ExecutorService exec = Executors.newCachedThreadPool();
    private Handler mMainHandler;
    private DrawThread mDrawThread;
    private OnDMAddListener mOnDMAddListener;

    public void setOnDMAddListener(OnDMAddListener l) {
        this.mOnDMAddListener = l;
    }
    private Controller() {

    }


    public void setDrawThread(SurfaceProxy surfaceProxy) {
        mDrawThread = new DrawThread(surfaceProxy);
    }

    public void setSize(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
        initOffset();
    }

    public void initOffset() {
        offset = mWidth;
        if (span > 0) span = -span;
        updateSpeed();
    }

    public void start() {
        if (mDrawThread.isRun()) return;
        mDrawThread.setOnDrawListener(new DrawThread.OnDrawListener() {
            @Override
            public void onDraw(Canvas canvas) {
                runTask(canvas);
            }
        });
        mDrawThread.start();
    }

    private Handler getMainHandler() {
        if (mMainHandler == null) {
            return new Handler(Looper.getMainLooper());
        }
        return mMainHandler;
    }

    private long lastTime = 0L;
    private void runTask(Canvas canvas) {
        offset += span;
        drawDM(canvas, offset, false);
        if (addDMInQueue()) {

        } else if (mAddedMDList.size() == 0) {
            mDrawThread.setDraw(false);
        }
    }
    private boolean first = true;

    private void drawDM(Canvas canvas, float value, boolean isOnlyClear) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        if (isOnlyClear) return;
        canvas.save();
        canvas.translate(value, 0);

        Iterator<BaseDmEntity> iterator = mAddedMDList.iterator();
        while (iterator.hasNext()) {
            BaseDmEntity entity = iterator.next();
            boolean removeThisEntity = false;
            removeThisEntity = offset < -entity.rect.right;
            if (removeThisEntity) {
                iterator.remove();
            }
            canvas.drawBitmap(entity.bitmap, entity.rect.left, entity.rect.top, null);
        }

        canvas.restore();
    }


    public void add(final View templateView) {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                BaseDmEntity entity = new BaseDmEntity(templateView);
                addToQueue(entity);
            }
        });
    }

    public void addToQueue(BaseDmEntity entity) {
        if (entity == null) throw new RuntimeException("entity cannot null");
        mNewDMQueue.add(entity);
        if (!mDrawThread.isDraw()) {
            initOffset();
            mDrawThread.setDraw(true);
        }
    }

    private SparseArray<LinkedList<BaseDmEntity>> hierarchy = new SparseArray<>();

    private boolean addDMInQueue() {
        BaseDmEntity entity = mNewDMQueue.peek();

        if (entity == null) return false;
        final float minLimit;
        final float maxLimit;
        minLimit = mWidth - offset;
        maxLimit = minLimit + mWidth;
        // 没有添加过弹幕的时候
        if (mAddedMDList.size() == 0) {
            addToDisplay(entity);
            return true;
        }

        for (BaseDmEntity addedDM : mAddedMDList) {
            if (hierarchy.get((int) addedDM.rect.top) == null) {// 没有初始化的情况
                hierarchy.put((int) addedDM.rect.top, new LinkedList<BaseDmEntity>());
            }
            hierarchy.get((int) addedDM.rect.top).addFirst(addedDM);
        }

        BaseDmEntity lastDm = null;
        for (int i = 0; i < hierarchy.size(); i++) {
            LinkedList<BaseDmEntity> linkedList = hierarchy.get(hierarchy.keyAt(i));
            if (linkedList.size() == 0) continue;
            BaseDmEntity lastEntity = linkedList.getFirst();

            // 上面或左边被空起来的情况
            if (lastDm == null && lastEntity.rect.top >= lastEntity.rect.height() + vSpace) {
                entity.rect.offsetTo(minLimit, 0);
                addToDisplay(entity);
                return true;
            }

            // 中间有可能被空起来的情况
            if (lastDm != null && lastDm.rect.bottom + lastEntity.rect.height() < lastEntity.rect.top) {
                entity.rect.offsetTo(minLimit, lastDm.rect.bottom + vSpace);
                addToDisplay(entity);
                return true;
            }

            lastDm = lastEntity;

            if (lastEntity.rect.right < maxLimit) {
                entity.rect.offsetTo((Math.max(lastEntity.rect.right, minLimit)) + hSpace, lastEntity.rect.top);
                addToDisplay(entity);
                return true;
            }

        }

        if (lastDm == null) throw new RuntimeException("lastDm can not null");

        if (lastDm.rect.bottom < mHeight - lastDm.rect.height()) {
            entity.rect.offsetTo(minLimit, lastDm.rect.bottom + vSpace);
            addToDisplay(entity);
            return true;
        }


        return false;
    }

    private synchronized void addToDisplay(final BaseDmEntity entity) {
        if (entity == null) return;
        // 移除当前弹幕item
        mNewDMQueue.remove(entity);
        // 添加到待绘制弹幕列表中
        mAddedMDList.add(entity);

        hierarchy.clear();

    }


    public void resume() {
        mDrawThread.setDraw(true);
    }

    public void pause() {
        mDrawThread.setDraw(false);
    }

    public void clean() {
        mNewDMQueue.clear();
        mAddedMDList.clear();
        mDrawThread.setDraw(false);
        initOffset();
    }

    public void destroy() {
        clean();
        mMainHandler = null;
        mDrawThread.setRun(false);
        mDrawThread.interrupt();
    }


    public void sethSpace(int hSpace) {
        this.hSpace = hSpace;
    }

    public void setvSpace(int vSpace) {
        this.vSpace = vSpace;
    }

    public void setSpan(int span) {
        if (span == 0) span = 2;
        this.span = this.span < 0 ? -span : span;
        updateSpeed();
    }

    public void setSpanTime(int spanTime) {
        this.spanTime = spanTime;
        updateSpeed();
    }

    private void updateSpeed() {
        if (spanTime > 0L && span != 0) {
            speed = span / spanTime;
        }
    }

    public static class Builder {
        private SurfaceProxy surfaceProxy;
        private int span;
        private int sleep;
        private int spanTime;
        private int vSpace;
        private int hSpace;
        private int width;
        private int height;
        private OnDMAddListener mOnDMAddListener;

        public Builder() {
        }

        public Builder setSurfaceProxy(SurfaceProxy surfaceProxy) {
            this.surfaceProxy = surfaceProxy;
            return this;
        }


        public Builder setSpan(int span) {
            this.span = span;
            return this;
        }

        public Builder setSleep(int sleep) {
            this.sleep = sleep;
            return this;
        }

        public Builder setSpanTime(int spanTime) {
            this.spanTime = spanTime;
            return this;
        }

        public Builder setvSpace(int vSpace) {
            this.vSpace = vSpace;
            return this;
        }

        public Builder sethSpace(int hSpace) {
            this.hSpace = hSpace;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public void setOnDMAddListener(OnDMAddListener l) {
            this.mOnDMAddListener = l;
        }


        public Controller build() {
            Controller controller = new Controller();
            controller.setDrawThread(surfaceProxy);
            controller.setSpan(span);
            controller.setSpanTime(spanTime == 0 ? sleep : spanTime);
            controller.setvSpace(vSpace);
            controller.sethSpace(hSpace);
            controller.setSize(width, height);
            controller.setOnDMAddListener(mOnDMAddListener);
            return controller;
        }
    }
}
