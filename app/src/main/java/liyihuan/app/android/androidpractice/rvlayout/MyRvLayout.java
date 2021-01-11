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

    // 竖向滑动总距离
    private int mVerticalOffset;
    // 总高度
    private int mTotalHeight;


    public MyRvLayout(Context context) {
        this.mContext = context;
        mView = new ArrayList<>();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置主动测量规则,适应recyclerView高度为wrap_content
     * @return
     */
    @Override
    public boolean isAutoMeasureEnabled() {
        return true;
    }


    @Override
    public boolean canScrollVertically() {
        return true;
    }

    /**
     *
     * @param dy 滑动差
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        detachAndScrapAttachedViews(recycler);

        // 滑到顶部
        if (mVerticalOffset + dy < 0) {
            // 下滑，向上移，dy<0, mVerticalOffset=0,
            dy = -mVerticalOffset;
        } else if (mVerticalOffset + dy  > mTotalHeight) {
            // 滑到底部
            dy = mTotalHeight - mVerticalOffset;
        }

        offsetChildrenVertical(-dy);
//        fill(recycler, state);
        mVerticalOffset += dy;
        Log.d("QWER", "mVerticalOffset: " + mVerticalOffset);
        return dy;
    }

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

            ViewInfo viewInfo = new ViewInfo();
            viewInfo.setView(view);
            mView.add(viewInfo);

            // TODO 这样最后一行的高度无法累加进来
            if (lineWidth + itemWidth > ScreenWidth) {
                // 超过屏幕宽度，重置行宽
                lineWidth = 0;
                // 每行的高度最大累加
                lineHeight += maxItemHeight;
                // 换行后要 重置maxItemHeight
                maxItemHeight = 0;
            }

            // TODO 在空白区域填充item，以后再写
            // 当前行宽 + 当前item宽度和屏幕宽度比较
//            if (lineWidth + itemWidth > ScreenWidth) {
//                // 需要换行
//                // 行最宽 + 当前item宽 和 屏幕宽做比较
//                if (maxItemWidth + itemWidth <= ScreenWidth){
//                    // 小于则在最大的那个item后面继续摆放
//                    // 行宽 从上一行最大的宽开始？怎么累加
//                    lineWidth = maxItemWidth;
////                  // 行高
//                    lineHeight += itemHeight;
//
//
//                    testHeight = itemHeight;
//
//                } else {
//                    lineHeight -= testHeight;
//                    // 超过屏幕宽度，重置行宽
//                    lineWidth = 0;
//                    // 每行的高度最大累加
//                    lineHeight += maxItemHeight;
//
//                    // 换行后要 重置maxItemHeight
//                    maxItemHeight = 0;
//                }
//            }

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

        mTotalHeight += lineHeight;
        fill(recycler, state);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 布局
        for (ViewInfo info : mView) {
            layoutDecorated(info.getView(), info.getLeft(), info.getTop(), info.getRight(), info.getBottom());
        }
    }


}
