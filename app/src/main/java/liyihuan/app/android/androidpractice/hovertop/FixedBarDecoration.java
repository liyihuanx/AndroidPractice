package liyihuan.app.android.androidpractice.hovertop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import com.example.module_fangroup.util.ViewUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.androidpractice.refresh.SmartRefreshAdapter;

/**
 * Created by lihongxin on 2019/1/22
 */
public class FixedBarDecoration extends RecyclerView.ItemDecoration {

    private int mItemHeaderHeight;
    private Paint mLinePaint;
    private Paint mItemHeaderPaint;
    private Paint mTextPaint;
    private Rect mTextRect;
    private View header;
    public FixedBarDecoration(Context context) {
        header = LayoutInflater.from(context).inflate(R.layout.header_layout, null, false);

        mItemHeaderHeight = ViewUtil.dip2px(40f);

        mTextRect = new Rect();
        mItemHeaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mItemHeaderPaint.setColor(Color.BLUE);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.GRAY);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(46);
        mTextPaint.setColor(Color.WHITE);
    }


    //吸顶效果的主要实现方法
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        if (position == 0) {
            return;
        }

        //如果不是头部view 那就直接在当前第一个可见的item顶部画一个固定栏即可
//            View view = parent.findViewHolderForAdapterPosition(position).itemView;
        c.drawRect(0, 0, parent.getWidth(), mItemHeaderHeight, mItemHeaderPaint);
        mTextPaint.getTextBounds("悬浮固定栏", 0, "悬浮固定栏".length(), mTextRect);
        c.drawText("悬浮固定栏", parent.getWidth() / 2 - mTextRect.width() / 2, mItemHeaderHeight / 2 + mTextRect.height() / 2, mTextPaint);


    }

    //绘制分割线和固定栏
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int count = parent.getChildCount();
        Log.d("QWER", "onDraw: " + count);
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildLayoutPosition(view);
            if (position == 1) {
                c.drawRect(0, view.getTop() - mItemHeaderHeight, parent.getWidth(), view.getTop(), mItemHeaderPaint);
                mTextPaint.getTextBounds("悬浮固定栏", 0, "悬浮固定栏".length(), mTextRect);
                c.drawText("悬浮固定栏", parent.getWidth() / 2 - mTextRect.width() / 2, (view.getTop() - mItemHeaderHeight) + mItemHeaderHeight / 2 + mTextRect.height() / 2, mTextPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if (position == 1) {
            outRect.top = mItemHeaderHeight;
        } else {
            outRect.top = 1;
        }
    }

}
