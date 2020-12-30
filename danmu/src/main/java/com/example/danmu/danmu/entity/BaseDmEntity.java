package com.example.danmu.danmu.entity;
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

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import com.example.danmu.danmu.Util;
import com.example.danmu.danmu.control.DamuBean;
import com.example.danmu.danmu.onMyClickListener;
import com.example.danmu.newdanmu.view.OnDanMuViewTouchListener;

/**
 * 弹幕实体类
 * Created by jiaji on 2018/2/26.
 */

public class BaseDmEntity implements OnDanMuViewTouchListener {
    public final Bitmap bitmap;
    public final RectF rect = new RectF();
    public final int priority;
    public View view;
    public onMyClickListener onMyClickListener;
    public DamuBean bean;

    public onMyClickListener getOnMyClickListener() {
        return onMyClickListener;
    }

    public void setOnMyClickListener(com.example.danmu.danmu.onMyClickListener onMyClickListener) {
        this.onMyClickListener = onMyClickListener;
    }

    public DamuBean getBean() {
        return bean;
    }

    public void setBean(DamuBean bean) {
        this.bean = bean;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public BaseDmEntity(View itemView) {
        this(itemView, 0);
    }

    public BaseDmEntity(View itemView, int priority) {
        bitmap = Util.convertViewToBitmap(itemView);
        this.view = itemView;
        this.priority = priority;
        this.rect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }



    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean onTouch(float x, float y) {
        return true;
    }


    public void release() {

    }


}
