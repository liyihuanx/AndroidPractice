package com.example.planet.mypratice;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.planet.PlanetCalculator;
import com.example.planet.R;
import com.example.planet.adapter.NullPlanetAdapter;
import com.example.planet.adapter.PlanetAdapter;
import com.example.planet.view.PlanetModel;
import com.example.planet.view.SoulPlanetsView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * @author created by liyihuanx
 * @date 2020/11/25
 * description: 类的描述
 */
public class PlanetView extends ViewGroup implements PlanetAdapter.OnDataSetChangeListener, Runnable {

    public static final float MAX_SCALE = 1.3f;
    public static final float MINI_SCALE = 1f;
    public static final int MODE_DISABLE = 0;
    public static final int MODE_DECELERATE = 1;
    public static final int MODE_UNIFORM = 2;
    private static final float TOUCH_SCALE_FACTOR = 1f;
    private static final float TRACKBALL_SCALE_FACTOR = 10;
    public int mode;
    private float speed = 4f;
    private PlanetHelper mPlanetCalculator;
    private float mAngleX = 0.5f;
    private float mAngleY = 0.5f;
    private float centerX, centerY;
    private float radius;
    /**
     * 半径的百分比
     */
    private float radiusPercent = 0.8f;
    private float[] darkColor = new float[]{1.0f, 0.0f, 0.0f, 1.0f};
    private float[] lightColor = new float[]{0.9412f, 0.7686f, 0.2f, 1.0f};
    /**
     * 是否支持手动滑动
     */
    private boolean manualScroll = true;
    private MarginLayoutParams layoutParams;
    private int minSize;
    private boolean isOnTouch = false;
    private Handler handler = new Handler(Looper.getMainLooper());
    private PlanetAdapter planetAdapter = new NullPlanetAdapter();
    private OnTagClickListener onTagClickListener;
    private float downX, downY;
    private float scaleX;
    private float startDistance;
    private boolean multiplePointer;
    private float startX;
    private float startY;



    /**
     * TAG的点击事件
     */
    public interface OnTagClickListener {

        /**
         * TAG点击
         *
         * @param parent   parent
         * @param view     view
         * @param position position
         */
        void onItemClick(ViewGroup parent, View view, int position);
    }

    /**
     * 设置标签点击事件监听
     */
    public void setOnTagClickListener(OnTagClickListener listener) {
        onTagClickListener = listener;
    }

    public PlanetView(Context context) {
        this(context, null);
    }

    public PlanetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlanetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 获取屏幕宽高
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (wm != null) {
            wm.getDefaultDisplay().getSize(point);
        }
        int screenWidth = point.x;
        int screenHeight = point.y;
        minSize = screenHeight < screenWidth ? screenHeight : screenWidth;

        mPlanetCalculator = new PlanetHelper();
        initItemView();
    }

    private void initItemView() {
        // 通过异步获取控件宽高
        this.post(new Runnable() {
            @Override
            public void run() {
                // 中心坐标
                centerX = (getRight() - getLeft()) / 2f;
                centerY = (getBottom() - getTop()) / 2f;
                // 半径
                radius = Math.min(centerX, centerY) * radiusPercent;

                mPlanetCalculator.setRadius((int) radius);

                mPlanetCalculator.setTagColorLight(lightColor);
                mPlanetCalculator.setTagColorDark(darkColor);

                mPlanetCalculator.clearItem();

                // 遍历planetAdapter中的item数量
                for (int i = 0; i < planetAdapter.getCount(); i++) {
                    // 给每个item设置一个bean类保存信息
                    PlanetModel planetModel = new PlanetModel(planetAdapter.getPopularity(i));
                    // 获取每个item-view
                    View view = planetAdapter.getView(getContext(), i, PlanetView.this);
                    planetModel.setView(view);

                    mPlanetCalculator.addItem(planetModel);
                    // 点击事件监听
//                    addListener(view, i);
                }

                mPlanetCalculator.create(true);
                mPlanetCalculator.setAngleX(mAngleX);
                mPlanetCalculator.setAngleY(mAngleY);
                mPlanetCalculator.update();

                resetChildren();
            }
        });
    }

    /**
     * 重新设置子View
     */
    private void resetChildren() {
        removeAllViews();
        // 必须保证getChildAt(i) == mTagCloud.getTagList().get(i)
        for (PlanetModel planetModel : mPlanetCalculator.getTagList()) {
            addView(planetModel.getView());
        }
    }

    @Override
    public boolean onTrackballEvent(MotionEvent e) {
        if (manualScroll) {
            float x = e.getX();
            float y = e.getY();

            mAngleX = (y) * speed * TRACKBALL_SCALE_FACTOR;
            mAngleY = (-x) * speed * TRACKBALL_SCALE_FACTOR;

            mPlanetCalculator.setAngleX(mAngleX);
            mPlanetCalculator.setAngleY(mAngleY);
            mPlanetCalculator.update();

            resetChildren();
        }
        return true;
    }


    /**
     * adapter的更新函数
     */
    @Override
    public void onChange() {
        initItemView();
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
            PlanetModel planetModel = mPlanetCalculator.getItem(i);
            if (child != null && child.getVisibility() != GONE) {
                planetAdapter.onThemeColorChanged(child, planetModel.getColor());
                // 设置缩放
                if (planetModel.getScale() < 1f) {
                    child.setScaleX(planetModel.getScale());
                    child.setScaleY(planetModel.getScale());
                    // 设置透明度
                    child.setAlpha(planetModel.getScale());
                } else {
                    child.setAlpha(1f);
                }
                // 设置位置
                int left = (int) (centerX + planetModel.getLoc2DX()) - child.getMeasuredWidth() / 2;
                int top = (int) (centerY + planetModel.getLoc2DY()) - child.getMeasuredHeight() / 2;

                child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
                // 设置位置信息的TAG
                child.setTag(new int[]{left, top});
            }
        }
    }


    /**
     * 更新视图
     */
    private void processTouch() {
        // 设置旋转的X,Y
        if (mPlanetCalculator != null) {
            mPlanetCalculator.setAngleX(mAngleX);
            mPlanetCalculator.setAngleY(mAngleY);
            mPlanetCalculator.update();
        }
        for (int i = 0; i < getChildCount(); i++) {
            PlanetModel planetModel = mPlanetCalculator.getItem(i);
            View child = planetModel.getView();
            // 更新每一个ChildView
            if (child != null && child.getVisibility() != GONE) {
                planetAdapter.onThemeColorChanged(child, planetModel.getColor());
                // 缩放小于1的设置不可点击
                if (planetModel.getScale() < 1.0f) {
                    child.setScaleX(planetModel.getScale());
                    child.setScaleY(planetModel.getScale());
                    child.setClickable(false);
                } else {
                    child.setClickable(true);
                }
                // 设置透明度
                child.setAlpha(planetModel.getScale());
                int left = (int) (centerX + planetModel.getLoc2DX()) - child.getMeasuredWidth() / 2;
                int top = (int) (centerY + planetModel.getLoc2DY()) - child.getMeasuredHeight() / 2;
                // 从View的Tag里取出位置之前的位置信息，平移新旧位置差值
                int[] originLocation = (int[]) child.getTag();
                if (originLocation != null && originLocation.length > 0) {
                    child.setTranslationX((float) (left - originLocation[0]));
                    child.setTranslationY((float) (top - originLocation[1]));
                    // 小于移动速度，刷新
                    if (Math.abs(mAngleX) <= speed && Math.abs(mAngleY) <= speed) {
                        child.invalidate();
                    }
                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        handler.post(this);
    }

    @Override
    public void run() {
        // 非用户触摸状态，和非不可滚动状态
        if (!isOnTouch) {
            // 减速模式（均速衰减）
            if (mode == MODE_DECELERATE) {
                if (Math.abs(mAngleX) > 0.2f) {
                    mAngleX -= mAngleX * 0.1f;
                }
                if (Math.abs(mAngleY) > 0.2f) {
                    mAngleY -= mAngleY * 0.1f;
                }
            }
            processTouch();
        }
        //Activity退出的时候，能够避免内存泄露
        handler.removeCallbacksAndMessages(null);
        // 延时
        handler.postDelayed(this, 30);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 设置适配器，刷新布局
     * @param planetAdapter
     */
    public void setPlanetAdapter(PlanetAdapter planetAdapter) {
        this.planetAdapter = planetAdapter;
        planetAdapter.setOnDataSetChangeListener(this);
        onChange();
    }
}
