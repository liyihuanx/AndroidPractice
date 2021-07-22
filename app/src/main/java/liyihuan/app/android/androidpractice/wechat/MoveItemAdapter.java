package liyihuan.app.android.androidpractice.wechat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import androidx.annotation.NonNull;
import liyihuan.app.android.androidpractice.R;

/**
 * @ClassName: MoveItemAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/11/1 21:10
 */
class MoveItemAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    public MoveItemAdapter() {
        super(R.layout.item_move);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Integer item) {
        helper.setImageResource(R.id.iv_item,item);
    }
}
