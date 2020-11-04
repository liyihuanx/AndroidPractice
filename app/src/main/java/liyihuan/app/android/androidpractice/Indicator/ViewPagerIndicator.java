package liyihuan.app.android.androidpractice.Indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

/**
 * @author created by liyihuanx
 * @date 2020/11/4
 * description: 类的描述
 */
public class ViewPagerIndicator extends LinearLayout {
    private Paint mPaint;// 画笔
    private Path mPath;// 三角形的类
    private int mTriangleWidth;// 三角形宽度
    private int mTriangleHeight;// 三角形高度
    private int[] mTriangleWidths = new int[6];// 宽度集合
    private int[] mTriangleWidthss = new int[7];// 前n个宽度和
    private int spacePx;// 间距
    private int mTranslationX = 0;// 移动的距离
    private static final int COLOR_TEXT_NORMAL = 0x77ffffff;
    private static final int COLOR_TEXT_HIGHLIGHT = 0xffffffff;

    private int normal_color;
    private int select_color;
    private int indicator_color;
    private int title_size;
    private int title_space;

    private List<String> mTitles;// tab标题集合

    public ViewPagerIndicator(Context context) {
        this(context,null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mTranslationX, getHeight() + 2);
        canvas.drawPath(mPath, mPaint);

        canvas.restore();

        super.dispatchDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = mTriangleWidths[0];
        initTriangle();
    }

    /**
     * 初始化三角形
     */
    private void initTriangle() {
        mTriangleHeight = 8;

        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);

        mPath.lineTo(mTriangleWidth, -mTriangleHeight);
        mPath.lineTo(0, -mTriangleHeight);
        mPath.close();
    }

    /**
     * 指示器跟随手指进行滚动
     *
     * @param position
     * @param offset
     */
    public void scroll(int position, float offset) {

        mTranslationX = (int) (mTriangleWidthss[position] + (mTriangleWidths[position] + spacePx) * offset);
        invalidate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setItemClickEvent();
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 初始化
     *
     * @param titles
     * @param colors
     */
    public void setTabItemTitles(List<String> titles, List<Integer> colors) {

        if (titles != null && titles.size() > 0) {
            this.removeAllViews();

            normal_color = colors.get(0);
            select_color = colors.get(1);
            indicator_color = colors.get(2);
            title_size = colors.get(3);
            title_space = colors.get(4);
            spacePx = dip2px(getContext(), title_space);

            // 初始化画笔
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(indicator_color);// 画笔颜色
            mPaint.setStyle(Paint.Style.FILL);// 设置填充
            mPaint.setPathEffect(new CornerPathEffect(1));// 圆角

            mTitles = titles;
            mTriangleWidths = new int[titles.size()];
            mTriangleWidthss = new int[titles.size() + 1];
            mTriangleWidthss[0] = spacePx / 2;
            for (int i = 0; i < mTitles.size(); i++) {
                addView(generateTextView(mTitles.get(i), i));
            }
            setItemClickEvent();
        }
    }

    /**
     * 根据title创建tab
     *
     * @param title
     * @return
     */
    private View generateTextView(String title, int i) {
        // LinearLayout layout = new LinearLayout(getContext());
        // layout.setOrientation(VERTICAL);

        TextView tv = new TextView(getContext());
        tv.setText(title);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, title_size);
        tv.setTextColor(normal_color);

        Paint paint = new Paint();
        paint.setTextSize(sp2px(getContext(), title_size));
        float size = paint.measureText(title);
        mTriangleWidths[i] = (int) size;
        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = (int) size + spacePx;
        mTriangleWidthss[i + 1] = mTriangleWidthss[i] + lp.width;
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(lp);
        return tv;
    }

    private ViewPager mViewPager;

    /**
     * indicator页面切换回调接口
     *
     * @author cc
     */
    public interface PageOnchangeListener {
        public void onPageSelected(int arg0);

        public void onPageScrolled(int position, float offset, int arg2);

        public void onPageScrollStateChanged(int arg0);
    }

    private PageOnchangeListener mListener;

    public void setPageOnChangeListener(PageOnchangeListener listener) {
        this.mListener = listener;
    }

    /**
     * 设置关联的viewpager
     *
     * @param viewPager
     * @param pos
     */
    public void setViewPager(ViewPager viewPager, int pos) {
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                if (mListener != null) {
                    mListener.onPageSelected(arg0);
                }
                highLightTextView(arg0);

                mTriangleWidth = mTriangleWidths[arg0];
                initTriangle();

                // // 容器移动，在tab处于移动至最后一个时

                if (arg0 > 1) {
                    if (mTriangleWidthss[arg0] - mTriangleWidthss[2] < mTriangleWidthss[6] - spacePx / 2
                            - getScreenWidth()) {
                        ViewPagerIndicator.this.scrollTo(mTriangleWidthss[arg0] - mTriangleWidthss[2], 0);
                    } else {
                        ViewPagerIndicator.this.scrollTo(mTriangleWidthss[6] - getScreenWidth() - spacePx / 2, 0);
                    }
                }

            }

            @Override
            public void onPageScrolled(int position, float offset, int arg2) {
                if (mListener != null) {
                    mListener.onPageScrolled(position, offset, arg2);
                }
                scroll(position, offset);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                if (mListener != null) {
                    mListener.onPageScrollStateChanged(arg0);
                }
            }
        });
        mViewPager.setCurrentItem(pos);
        highLightTextView(pos);
    }

    private void resetTextViewColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(normal_color);
            }
        }

    }

    /**
     * 高亮某个tab文本
     *
     * @param pos
     */
    private void highLightTextView(int pos) {
        resetTextViewColor();
        View view = getChildAt(pos);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(select_color);
        }
    }

    private void setItemClickEvent() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}

