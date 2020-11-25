package com.example.planet.mypratice;

import android.view.View;

/**
 * @author created by liyihuanx
 * @date 2020/11/25
 * description: 类的描述
 */
public class PlanetBean {

    /**
     * 默认权重
     */
    private static final int DEFAULT_POPULARITY = 5;
    /**
     * 权重
     */
    private int popularity;
    /**
     * 3D坐标位置
     */
    private float locX, locY, locZ;
    /**
     * 2D坐标位置
     */
    private float loc2DX, loc2DY;
    /**
     * 缩放比
     */
    private float scale;
    /**
     * 透明度
     */
    private float alpha;
    /**
     * 颜色
     */
    private float[] argb;
    /**
     * View
     */
    private View mView;


    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public float getLocX() {
        return locX;
    }

    public void setLocX(float locX) {
        this.locX = locX;
    }

    public float getLocY() {
        return locY;
    }

    public void setLocY(float locY) {
        this.locY = locY;
    }

    public float getLocZ() {
        return locZ;
    }

    public void setLocZ(float locZ) {
        this.locZ = locZ;
    }

    public float getLoc2DX() {
        return loc2DX;
    }

    public void setLoc2DX(float loc2DX) {
        this.loc2DX = loc2DX;
    }

    public float getLoc2DY() {
        return loc2DY;
    }

    public void setLoc2DY(float loc2DY) {
        this.loc2DY = loc2DY;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float[] getArgb() {
        return argb;
    }

    public void setArgb(float[] argb) {
        this.argb = argb;
    }

    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }
}
