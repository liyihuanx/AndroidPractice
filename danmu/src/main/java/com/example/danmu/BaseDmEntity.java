package com.example.danmu;
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
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;

/**
 * 弹幕实体类
 * Created by jiaji on 2018/2/26.
 */

public class BaseDmEntity {
    public final Bitmap bitmap;
    public final RectF rect = new RectF();
    public final int priority;

    public BaseDmEntity(View itemView) {
        this(itemView, 0);
    }

    public BaseDmEntity(View itemView, int priority) {
        bitmap = convertViewToBitmap(itemView);
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

    public static Bitmap convertViewToBitmap(View view) {
        view.setLayoutParams(new ViewGroup.LayoutParams(0,0));
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
