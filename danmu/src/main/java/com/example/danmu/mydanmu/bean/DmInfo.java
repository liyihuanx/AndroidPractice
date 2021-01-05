package com.example.danmu.mydanmu.bean;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author created by liyihuanx
 * @date 2021/1/4
 * description: 类的描述
 */
public class DmInfo {

    private View itemView;
    // 把每个弹幕View,转变成bitmap保存
    private Bitmap bitmap;
    private int top;
    private int bottom;
    private int left;
    private int right;

    public DmInfo() {
    }

    public DmInfo(View itemView) {
        this.itemView = itemView;
        bitmap = convertViewToBitmap(itemView);
        bottom = bitmap.getHeight();
        right = bitmap.getWidth();
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    private Bitmap convertViewToBitmap(View view) {
        view.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
