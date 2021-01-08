package liyihuan.app.android.androidpractice.rvlayout;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import liyihuan.app.android.androidpractice.utils.ScreenUtils;

/**
 * @ClassName: MyRvLayout
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/12/3 21:08
 */
class MyRvLayout extends RecyclerView.LayoutManager {
    private Context mContext;
    private int lineWidth = 0;
    private int lineHeight = 0;
    private int maxItemHeight = 0;
    private int maxItemWidth = 0;
    private ArrayList<ViewInfo> mView;

    public MyRvLayout(Context context) {
        this.mContext = context;
        mView = new ArrayList<>();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        detachAndScrapAttachedViews(recycler);
//        if (mVerticalOffset + dy < 0) {
//            dy = -mVerticalOffset;
//        } else if (mVerticalOffset + dy > mTotalHeight - getVerticalSpace()) {
//            dy = mTotalHeight - getVerticalSpace() - mVerticalOffset;
//        }
//
//        offsetChildrenVertical(-dy);
//        fill(recycler, state);
//        mVerticalOffset += dy;
//        return dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    private int testHeight = 0;
    /**
     * 1 在RecyclerView初始化时，会被调用两次。
     * 2 在调用adapter.notifyDataSetChanged()时，会被调用。
     * 3 在调用setAdapter替换Adapter时,会被调用。
     * 4 在RecyclerView执行动画时，它也会被调用。
     *
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) {
            return;
        }
        detachAndScrapAttachedViews(recycler);

        int ScreenWidth = ScreenUtils.getScreenWidth(mContext);
        int ScreenHeight = ScreenUtils.getScreenHeight(mContext);

        for (int i = 0; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            if (View.GONE == view.getVisibility()) {
                continue;
            }
            measureChildWithMargins(view, 0, 0);
            addView(view);

            // 获取每个item的宽高
            int itemWidth = getDecoratedMeasuredWidth(view);
            int itemHeight = getDecoratedMeasuredHeight(view);
            Log.d("QWER", "itemWidth: " + itemWidth);
            Log.d("QWER", "itemHeight: " + itemHeight);


            ViewInfo viewInfo = new ViewInfo();
            viewInfo.setView(view);
            mView.add(viewInfo);
            // 当前行宽 + 当前item宽度和屏幕宽度比较

            if (lineWidth + itemWidth > ScreenWidth) {
                // TODO 在空白区域填充item，以后再写
                // 需要换行
                // 行最宽 + 当前item宽 和 屏幕宽做比较
                if (maxItemWidth + itemWidth <= ScreenWidth){
                    // 小于则在最大的那个item后面继续摆放
                    // 行宽 从上一行最大的宽开始？怎么累加
                    lineWidth = maxItemWidth;
//                  // 行高
                    lineHeight += itemHeight;


                    testHeight = itemHeight;

                } else {
                    lineHeight -= testHeight;
                    // 超过屏幕宽度，重置行宽
                    lineWidth = 0;
                    // 每行的高度最大累加
                    lineHeight += maxItemHeight;

                    // 换行后要 重置maxItemHeight
                    maxItemHeight = 0;
                }
            }

            viewInfo.setLeft(lineWidth);
            viewInfo.setTop(lineHeight);
            viewInfo.setRight(lineWidth + itemWidth);
            viewInfo.setBottom(lineHeight + itemHeight);
            // 当前行宽累加
            lineWidth += itemWidth;

            // 找出当前行最大的宽高
            maxItemWidth = Math.max(itemWidth, maxItemWidth);
            maxItemHeight = Math.max(itemHeight, maxItemHeight);
        } // for end

        // 布局
        for (ViewInfo info : mView) {
            layoutDecorated(info.getView(), info.getLeft(), info.getTop(), info.getRight(), info.getBottom());
        }

    }


}
