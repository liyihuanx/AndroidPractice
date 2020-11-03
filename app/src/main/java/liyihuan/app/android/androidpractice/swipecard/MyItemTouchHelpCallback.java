package liyihuan.app.android.androidpractice.swipecard;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author created by liyihuanx
 * @date 2020/10/30
 * description: 类的描述
 */
public class MyItemTouchHelpCallback<T> extends ItemTouchHelper.Callback {

    private onSwipeListener<T> listener;
    private List<T> itemData;

    public MyItemTouchHelpCallback(List<T> itemData) {
        this.itemData = itemData;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // itemData每次都会更新
        viewHolder.itemView.setOnTouchListener(null);
        if (itemData.size() > 1){
            T myItem = itemData.get(0);
            listener.Swiped(myItem);
        } else {
            listener.SwipeNoData();

        }
    }

    /**
     * 设置不可左右滑动，在自定义Layout的onTouch中调用滑动方法，这样可以根据条件来判断是否可以滑动
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    /**
     * 滑动或者拖动结束以后执行
     */
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setRotation(0f);
        viewHolder.itemView.setAlpha(1f);
        super.clearView(recyclerView, viewHolder);
    }


    /**
     * 针对swipe和drag状态，整个过程中一直会调用这个函数
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View child = viewHolder.itemView;
        float ratio = dX / getThreshold(recyclerView, viewHolder);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // ratio 最大为 1 或 -1
            if (ratio > 1) {
                ratio = 1;
            } else if (ratio < -1) {
                ratio = -1;
            }
            child.setRotation(ratio * 15f);
            int childCount = recyclerView.getChildCount();
            // 对后面的item操作
            int position = 0;
            // 大于可见个数，直接对后面的item做操作
            // 小于2个 --> 则childCount == 1或者0, position不能取1
            if (childCount > 2) {
                position = 1;
            } else {
                position = 0;
            }
            for (; position < childCount - 1; position++) {
                int index = childCount - position - 1;
                View view = recyclerView.getChildAt(position);
                float scaleNum = 1 - index * 0.15f + Math.abs(ratio) * 0.15f;
                view.setScaleX(scaleNum);
                view.setScaleY(scaleNum);
                view.setTranslationX((index - Math.abs(ratio)) * child.getMeasuredWidth() / 10);
                view.setTranslationY((index - Math.abs(ratio)) * child.getMeasuredHeight() / 140);
            }
        }

        listener.Swiping(child, ratio, ratio > 0 ? ItemTouchHelper.LEFT : ItemTouchHelper.RIGHT);
    }

    public void setListener(onSwipeListener<T> listener) {
        this.listener = listener;
    }

    /**
     * 滑动remove一个item调用，更新数据
     * @param itemData 数据源
     */
    public void setItemData(List<T> itemData) {
        this.itemData = itemData;
    }

    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }
}
