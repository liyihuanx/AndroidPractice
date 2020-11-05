package liyihuan.app.android.androidpractice.Indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import liyihuan.app.android.androidpractice.R;

/**
 * @author created by liyihuanx
 * @date 2020/11/4
 * description: 类的描述
 */
public class IndicatorViewpager extends LinearLayout implements ViewPager.OnPageChangeListener {

    // 画笔
    private Paint mPaint;
    // 文本画笔
    private Paint mTextPaint;
    // 默认的Tab数量
    private static final int COUNT_DEFAULT_TAB = 2;
    // tab数量
    private int mTabVisibleCount = COUNT_DEFAULT_TAB;
    // 线条默认颜色
    private static final int DEFAULT_COLOR = 0xFFFFFF00;
    // 指示器线条的颜色
    private int mLineColor = DEFAULT_COLOR;
    // tab上的内容
    private List<String> mTabTitles;
    // 与指示器绑定的 viewpager
    private ViewPager mViewPager;
    // 指示器(矩形)
    private Rect mRect;
    // 指示器的宽度
    private int mLineWidth;
    // 标题正常时的颜色
    private static final int COLOR_TEXT_NORMAL = 0x77FFFFFF;
    // 标题选中时的颜色
    private static final int COLOR_TEXT_HIGHLIGHTCOLOR = 0xFFFFFFFF;
    // 手指滑动时的偏移量
    private float mTranslationX;
    // 文本选中颜色
    private int textSelectColor;
    // 文本没有选中的颜色
    private int textUnSelectColor;


    public IndicatorViewpager(Context context) {
        this(context, null);
    }

    public IndicatorViewpager(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorViewpager(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获得自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ViewPagerLineIndicator);
        mLineColor = a.getInt(R.styleable.ViewPagerLineIndicator_line_color, DEFAULT_COLOR);
        mTabVisibleCount = a.getInt(R.styleable.ViewPagerLineIndicator_item_count, COUNT_DEFAULT_TAB);
        textSelectColor = a.getInt(R.styleable.ViewPagerLineIndicator_text_select, Color.BLACK);
        textUnSelectColor = a.getInt(R.styleable.ViewPagerLineIndicator_text_unselect, Color.BLACK);
        initView();
    }

    private void initView() {

        mPaint = new Paint();
        mPaint.setColor(mLineColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new TextPaint();
    }

    // 获取tab的文本数据
    public void setTabItemTitles(List<String> mTitles) {
        for (int i = 0; i < mTitles.size(); i++) {
            addView(generateTextView(mTitles.get(i)));

        }
        // 设置点击事件
        setItemClickEvent();

//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        lp.width = (int) (getScreenWidth() / getChildCount());
    }

    // 生成文本
    private TextView generateTextView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20f);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = 100;
        mLineWidth = lp.width;
        textView.setLayoutParams(lp);
        return textView;
    }

    // 给每个tab添加点击事件
    private void setItemClickEvent() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            final int postion = i;
            view.setOnClickListener(v -> {
                mViewPager.setCurrentItem(postion);
            });
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // 初始化tab的矩形
        mRect = new Rect(0,0,mLineWidth,10);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        // 偏移到 tab 的底部，因为指示器是在底部的
        canvas.translate(mTranslationX, getHeight() - 10);
        // 画出矩形指示器
        canvas.drawRect(mRect, mPaint);
        canvas.restore();


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setViewPager2 (ViewPager viewPager, int position) {
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        highLightTextView(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        scroll(position, positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        resetTextViewColor();
        highLightTextView(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void scroll(int position, float offset){
        // 计算偏移量
        mTranslationX = mLineWidth * (position + offset);

        // 重画，刷新界面
        invalidate();

    }

    private void resetTextViewColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            ((TextView) view).setTextColor(textUnSelectColor);
        }
    }

    private void highLightTextView(int position) {
        View view = getChildAt(position);
        ((TextView) view).setTextColor(textSelectColor);
    }

    private float getScreenWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
