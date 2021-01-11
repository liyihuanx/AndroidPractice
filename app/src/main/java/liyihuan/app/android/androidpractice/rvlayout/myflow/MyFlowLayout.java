package liyihuan.app.android.androidpractice.rvlayout.myflow;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @ClassName: MyFlowLayout
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/1/11 20:29
 */
public class MyFlowLayout extends RecyclerView.LayoutManager {

    private int lineHeight;
    private int lineWidth;
    private int maxLineHeight;
    private int maxLineWidth;
    private int verticalScrollOffset;
    private ArrayList<itemInfo> itemInfos;
    private int canUseHeight;
    private int canUseWidth;
    private int mTotalHeight;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置主动测量规则,适应recyclerView高度为wrap_content
     *
     * @return
     */
    @Override
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 && state.isPreLayout()) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        // 先获取可用的宽和高
        canUseWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        canUseHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        itemInfos = new ArrayList<>();

        lineWidth = getPaddingLeft();
        lineHeight = getPaddingTop();

        for (int i = 0; i < getItemCount(); i++) {
            View child = recycler.getViewForPosition(i);
            if (View.GONE == child.getVisibility()) {
                continue;
            }
            measureChildWithMargins(child, 0, 0);
            int childWidth = getDecoratedMeasuredWidth(child);
            int childHeight = getDecoratedMeasuredHeight(child);
            if (childWidth + lineWidth <= canUseWidth) {
                // 添加
                itemInfo item = new itemInfo();
                item.setItemView(child);
                item.rect.left = lineWidth;
                item.rect.right = lineWidth + childWidth;
                item.rect.top = lineHeight;
                item.rect.bottom = lineHeight + childHeight;
                itemInfos.add(item);

                // 行宽累加
                lineWidth += childWidth;
                // 找出最item最大的高度
                maxLineHeight = Math.max(maxLineHeight, childHeight);

            } else {
                // 换行
                // 行宽重置
                lineWidth = getPaddingLeft();
                // 行高累加
                lineHeight += maxLineHeight;
                mTotalHeight += lineHeight;
            }
            if (i == getItemCount() - 1) {
                mTotalHeight = mTotalHeight + maxLineHeight;
            }



        } // for end

        // 布局
        fill(recycler, state);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (itemInfo item : itemInfos) {
            layoutDecoratedWithMargins(item.getItemView(), item.rect.left, item.rect.top, item.rect.right, item.rect.bottom);
        }
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        if (verticalScrollOffset + dy < 0) {
            dy = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy > mTotalHeight - getVerticalSpace()) {
            dy = mTotalHeight - getVerticalSpace() - verticalScrollOffset;
        }

        offsetChildrenVertical(-dy);
        fill(recycler, state);
        verticalScrollOffset += dy;
        return dy;
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    private class itemInfo {
        Rect rect = new Rect();
        View itemView;

        public Rect getRect() {
            return rect;
        }

        public void setRect(Rect rect) {
            this.rect = rect;
        }

        public View getItemView() {
            return itemView;
        }

        public void setItemView(View itemView) {
            this.itemView = itemView;
        }
    }
}
