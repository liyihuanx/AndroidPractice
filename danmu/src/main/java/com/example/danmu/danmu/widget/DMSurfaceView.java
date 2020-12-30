package com.example.danmu.danmu.widget;
/*
 * Copyright 2018 xujiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.danmu.danmu.OnDMAddListener;
import com.example.danmu.R;
import com.example.danmu.danmu.Util;
import com.example.danmu.danmu.control.Controller;
import com.example.danmu.danmu.control.DamuBean;
import com.example.danmu.danmu.control.SurfaceProxy;
import com.example.danmu.danmu.entity.BaseDmEntity;
import com.example.danmu.danmu.onMyClickListener;
import com.example.danmu.newdanmu.model.DanMuModel;
import com.example.danmu.newdanmu.view.OnDanMuViewTouchListener;

import java.util.ArrayList;


/**
 * 用SurfaceView实现弹幕
 * Created by jiaji on 2018/2/19.
 */

public class DMSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceHolder;
    private Controller mController;
    private int mWidth;
    private int mHeight;
    private Controller.Builder builder;
    private volatile ArrayList<OnDanMuViewTouchListener> onDanMuViewTouchListeners;


    public DMSurfaceView(Context context) {
        this(context, null);
    }

    public DMSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DMSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHolder();
        onDanMuViewTouchListeners = new ArrayList<>();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DMSurfaceView, defStyleAttr, 0);

        final int span = a.getDimensionPixelOffset(R.styleable.DMSurfaceView_dm_span, Util.dp2px(context, 2));
        final int sleep = a.getInteger(R.styleable.DMSurfaceView_dm_sleep, 0);
        final int spanTime = a.getInteger(R.styleable.DMSurfaceView_dm_span_time, 0);
        final int vSpace = a.getDimensionPixelOffset(R.styleable.DMSurfaceView_dm_v_space, Util.dp2px(context, 10));
        final int hSpace = a.getDimensionPixelOffset(R.styleable.DMSurfaceView_dm_h_space, Util.dp2px(context, 10));

        builder = new Controller.Builder()
                .setSpan(span)
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

    public void Viewadd(View templateView, DamuBean bean){
        BaseDmEntity entity = new BaseDmEntity(templateView);
        entity.setBean(bean);
        entity.setView(templateView);
        mController.add(entity);
        entity.setOnMyClickListener(new onMyClickListener() {
            @Override
            public void onClickItem(BaseDmEntity baseDmEntity) {
                Log.d("QWER", "onClickItem: "+baseDmEntity.getBean().getName());
            }
        });
        onDanMuViewTouchListeners.add(entity);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
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
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            mController.resume();
        } else {
            mController.pause();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mController.destroy();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mController.pause();
    }

    public void setOnDMAddListener(OnDMAddListener l) {
        builder.setOnDMAddListener(l);
    }

    public Controller getController() {
        return mController;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < onDanMuViewTouchListeners.size(); i++) {
                    OnDanMuViewTouchListener onDanMuViewTouchListener = onDanMuViewTouchListeners.get(i);
                    boolean onTouched = onDanMuViewTouchListener.onTouch(event.getX(), event.getY());

                    if (((BaseDmEntity) onDanMuViewTouchListener).getOnMyClickListener() != null && onTouched) {
                        ((BaseDmEntity) onDanMuViewTouchListener).getOnMyClickListener().onClickItem((BaseDmEntity) onDanMuViewTouchListener);
                        return true;
                    }
                }
                break;
        }
        return true;
    }
}
