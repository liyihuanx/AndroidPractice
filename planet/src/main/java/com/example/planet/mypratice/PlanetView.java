package com.example.planet.mypratice;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.planet.PlanetCalculator;
import com.example.planet.R;
import com.example.planet.adapter.NullPlanetAdapter;
import com.example.planet.adapter.PlanetAdapter;
import com.example.planet.view.PlanetModel;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * @author created by liyihuanx
 * @date 2020/11/25
 * description: 类的描述
 */
public class PlanetView extends ViewGroup {
    private MyPlanetAdapter adapter;
    private List<PlanetModel> planetModelCloud;

    public static final float MAX_SCALE = 1.3f;
    public static final float MINI_SCALE = 1f;
    public static final int MODE_DISABLE = 0;
    public static final int MODE_DECELERATE = 1;
    public static final int MODE_UNIFORM = 2;
    private static final float TOUCH_SCALE_FACTOR = 1f;
    private static final float TRACKBALL_SCALE_FACTOR = 10;
    public int mode;
    private float speed = 4f;
    private PlanetCalculator mPlanetCalculator;
    private float mAngleZ = 0;
    private float mAngleX = 0.5f;
    private float mAngleY = 0.5f;
    private float centerX, centerY;
    private float radius;
    private MarginLayoutParams layoutParams;
    private int minSize;
    private float[] tagColorDark = new float[]{1.0f, 0.0f, 0.0f, 1.0f};
    private float[] tagColorLight = new float[]{0.9412f, 0.7686f, 0.2f, 1.0f};
    private int smallest, largest;
    public PlanetView(Context context) {
        this(context, null);
    }

    public PlanetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlanetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        planetModelCloud = new ArrayList<>();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (wm != null) {
            wm.getDefaultDisplay().getSize(point);
        }
        int screenWidth = point.x;
        int screenHeight = point.y;
        minSize = screenHeight < screenWidth ? screenHeight : screenWidth;

        initView(context);
    }

    private void initView(Context context) {
        // 中心坐标
        centerX = 540;
        centerY = 500;
        // 半径
        radius = Math.min(centerX, centerY) * 0.8f;
        for (int i = 0; i < 60; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_plane,this,false);
            PlanetModel planetModel = new PlanetModel();
            planetModel.setView(view);
            planetModelCloud.add(planetModel);
        }
        create();
        update();
        resetChildren();
    }

    /**
     * 创建并初始化每个Tag的位置
     *
     */
    public void create() {
        // 计算和设置每个Tag的位置
        locationAll();
        sineCosine(mAngleX, mAngleY, mAngleZ);
        updateAll();

    }

    public void update() {
        // 如果mAngleX和mAngleY低于阈值，则跳过运动计算以获得性能
        if (Math.abs(mAngleX) > .1 || Math.abs(mAngleY) > .1) {
            sineCosine(mAngleX, mAngleY, mAngleZ);
            updateAll();
        }
    }

    /**
     * 重新设置子View
     */
    private void resetChildren() {
        removeAllViews();
        // 必须保证getChildAt(i) == mTagCloud.getTagList().get(i)
        for (PlanetModel planetModel : planetModelCloud) {
            addView(planetModel.getView());
        }
    }

    /**
     * 计算所有的位置
     * <p>
     * 球坐标系(r,θ,φ)与直角坐标系(x,y,z)的转换关系:
     * x=rsinθcosφ.
     * y=rsinθsinφ.
     * z=rcosθ.
     * <p>
     * r -> radius
     * θ -> phi
     * φ -> theta
     *
     */
    private void locationAll() {
        double phi;
        double theta;
        int count = planetModelCloud.size();
        for (int i = 1; i < count + 1; i++) {
            phi = Math.acos(-1.0 + (2.0 * i - 1.0) / count);
            theta = Math.sqrt(count * Math.PI) * phi;

            planetModelCloud.get(i - 1).setLocX((float) (radius * Math.cos(theta) * Math.sin(phi)));
            planetModelCloud.get(i - 1).setLocY((float) (radius * Math.sin(theta) * Math.sin(phi)));
            planetModelCloud.get(i - 1).setLocZ((float) (radius * Math.cos(phi)));
        }
    }

    /**
     * 返回角度转换成弧度之后各方向的值
     * <p>
     * 1度=π/180
     *
     * @param mAngleX x方向旋转距离
     * @param mAngleY y方向旋转距离
     * @param mAngleZ z方向旋转距离
     */
    private float sinAngleX, cosAngleX, sinAngleY, cosAngleY, sinAngleZ, cosAngleZ;
    private void sineCosine(float mAngleX, float mAngleY, float mAngleZ) {
        double degToRad = (Math.PI / 180);
        sinAngleX = (float) Math.sin(mAngleX * degToRad);
        cosAngleX = (float) Math.cos(mAngleX * degToRad);
        sinAngleY = (float) Math.sin(mAngleY * degToRad);
        cosAngleY = (float) Math.cos(mAngleY * degToRad);
        sinAngleZ = (float) Math.sin(mAngleZ * degToRad);
        cosAngleZ = (float) Math.cos(mAngleZ * degToRad);
    }

    /**
     * 更新所有的
     */
    private void updateAll() {
        // 更新标签透明度和比例
        int count = planetModelCloud.size();
        for (int i = 0; i < count; i++) {
            PlanetModel planetModel = planetModelCloud.get(i);
            // 此部分有两个选项：
            // 绕x轴旋转
            float rx1 = (planetModel.getLocX());
            float ry1 = (planetModel.getLocY()) * cosAngleX + planetModel.getLocZ() * -sinAngleX;
            float rz1 = (planetModel.getLocY()) * sinAngleX + planetModel.getLocZ() * cosAngleX;
            // 绕y轴旋转
            float rx2 = rx1 * cosAngleY + rz1 * sinAngleY;
            float ry2 = ry1;
            float rz2 = rx1 * -sinAngleY + rz1 * cosAngleY;
            // 绕z轴旋转
            float rx3 = rx2 * cosAngleZ + ry2 * -sinAngleZ;
            float ry3 = rx2 * sinAngleZ + ry2 * cosAngleZ;
            float rz3 = rz2;
            // 将数组设置为新位置
            planetModel.setLocX(rx3);
            planetModel.setLocY(ry3);
            planetModel.setLocZ(rz3);

            // 让我们为标签设置位置、比例和透明度
            planetModel.setLoc2DX(rx3);
            planetModel.setLoc2DY(ry3);

        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int contentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int contentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (layoutParams == null) {
            layoutParams = (MarginLayoutParams) getLayoutParams();
        }

        int dimensionX = widthMode == MeasureSpec.EXACTLY ? contentWidth : minSize - layoutParams.leftMargin - layoutParams.rightMargin;
        int dimensionY = heightMode == MeasureSpec.EXACTLY ? contentHeight : minSize - layoutParams.leftMargin - layoutParams.rightMargin;
        setMeasuredDimension(dimensionX, dimensionY);

        measureChildren(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            PlanetModel planetModel = planetModelCloud.get(i);
            if (child != null && child.getVisibility() != GONE) {

                // 设置位置
                int left = (int) (centerX + planetModel.getLoc2DX()) - child.getMeasuredWidth() / 2;
                int top = (int) (centerY + planetModel.getLoc2DY()) - child.getMeasuredHeight() / 2;
                if (i < 20) {
                    child.setAlpha(i * 0.05f);
                } else if (i>=20 && i< 40){
                    child.setAlpha(i * 0.025f);
                } else {
                    child.setAlpha(i * 0.015f);
                }
                child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());

            }
        }
    }
    
}
