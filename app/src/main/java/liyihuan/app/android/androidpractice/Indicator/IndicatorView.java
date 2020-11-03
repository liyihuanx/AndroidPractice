package liyihuan.app.android.androidpractice.Indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: IndicatorView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/11/2 21:31
 */
class IndicatorView extends View implements ViewPager.OnPageChangeListener {


    private List<PointF> itemInfo;
    private int itemDistance = 20;
    private int itemRadius = 30;
    private int mWitch;
    private int mHeight;
    private Paint mPaint;
    private int minTouchMove;
    private int mSelectPosition = 0; // 选中的位置
    private int mStrokeWidth;
    private int itemCount;
    private List<String> titleList;
    private Paint mTextPaint;
    private int TextSize = 12;

    public IndicatorView(Context context) {
        this(context,null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        minTouchMove = ViewConfiguration.get(context).getScaledTouchSlop();
        itemInfo = new ArrayList<>();
        titleList = new ArrayList<>();

        mPaint = new Paint();
        mTextPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getItemInfo();
        setMeasuredDimension(mWitch,mHeight);
    }

    private void getItemInfo() {
        itemInfo.clear();
        int x = itemRadius + itemDistance;
        for (int i = 0; i < itemCount; i++) {
            PointF pointF = new PointF();
            if (i > 0) {
                x += 2 * itemRadius + itemDistance;
            }
            pointF.x = x;
            pointF.y = itemRadius + itemDistance;
            itemInfo.add(pointF);
        }
        mWitch = x + itemRadius + itemDistance;
        mHeight = 2 * (itemRadius + itemDistance);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getItemInfo();
        for (int i = 0; i < itemInfo.size(); i++) {
            if (i == mSelectPosition) {
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            } else {
                mPaint.setStyle(Paint.Style.STROKE);
            }
            canvas.drawCircle(itemInfo.get(i).x, itemInfo.get(i).y, itemRadius, mPaint);
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float x = 0;
//        float y = 0;
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                x = event.getX();
//                y = event.getY();
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                if (Math.abs(event.getX() - x) > minTouchMove) {
//                    Log.d("QWER", "ACTION_MOVE: ");
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            default:
//                break;
//        }
//        return super.onTouchEvent(event);
//    }


    public void bindViewPager(ViewPager viewPager){
        if(viewPager == null){
            return;
        }
        viewPager.addOnPageChangeListener(this);
        itemCount = viewPager.getAdapter().getCount();
        invalidate();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mSelectPosition = position;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
