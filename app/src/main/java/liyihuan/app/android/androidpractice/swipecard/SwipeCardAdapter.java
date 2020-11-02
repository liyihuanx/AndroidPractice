package liyihuan.app.android.androidpractice.swipecard;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import liyihuan.app.android.androidpractice.R;

/**
 * @ClassName: SwipeCardAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/10/28 21:13
 */
class SwipeCardAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {


    public SwipeCardAdapter() {
        super(R.layout.item_swip_card);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Integer item) {
        helper.setImageResource(R.id.iv_item,item);
    }
}
