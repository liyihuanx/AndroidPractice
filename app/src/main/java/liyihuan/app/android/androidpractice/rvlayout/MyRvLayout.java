package liyihuan.app.android.androidpractice.rvlayout;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @ClassName: MyRvLayout
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/12/3 21:08
 */
class MyRvLayout extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        for (int i = 0; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int itemWidth = getDecoratedMeasuredWidth(view);
            int itemHeight = getDecoratedMeasuredHeight(view);
            Log.d("QWER", "itemWidth: " + i + " =====" + itemWidth);
            Log.d("QWER", "itemHeight: " + i + " =====" + itemHeight);

            // 布局
            layoutDecorated(view, 10, i * 100, itemWidth, i * itemHeight);


        }

    }


}
