package liyihuan.app.android.androidpractice.swipecard;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author created by liyihuanx
 * @date 2020/10/22
 * description: 类的描述
 */
public class SwipeCardLayout extends RecyclerView.LayoutManager {
    private RecyclerView recyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private int maxCanView = 2;

    public SwipeCardLayout(RecyclerView recyclerView, ItemTouchHelper mItemTouchHelper) {
        this.recyclerView = recyclerView;
        this.mItemTouchHelper = mItemTouchHelper;
    }

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
        detachAndScrapAttachedViews(recycler);
        int position = 0;
        int itemCount = getItemCount();

        if (itemCount > maxCanView) {
            position = maxCanView;
        } else {
            position = getItemCount() - 1;
        }

        for (int i = position; i >= 0; i--) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);

            // recyclerview 布局
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view));

            view.setTranslationY(i * view.getMeasuredHeight() / 140);
            view.setTranslationX(i * view.getMeasuredWidth() / 10);

            view.setScaleX(1 - i * 0.15f);//0.75 0.5
            view.setScaleY(1 - i * 0.15f);

            view.setOnTouchListener(mOnTouchListener);
        }
    }

    private boolean canClick = false;

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        private float x = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(v);

            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                x = event.getX();
                canClick = true;
            }
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_MOVE) {
                if (Math.abs(event.getX() - x) > 40) {
                    mItemTouchHelper.startSwipe(childViewHolder);
                    if (canClick) {
                        canClick = false;
                    }
                }

            }

            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP) {
                if (canClick) {
                    // performClick模拟点击事件 ---> 执行点击事件
                    recyclerView.getChildViewHolder(v).itemView.performClick();
                    return false;
                }
            }
            return true;
        }
    };
}
