package liyihuan.app.android.androidpractice.rvlayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import liyihuan.app.android.androidpractice.R;

/**
 * @ClassName: MyRvAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/1/7 20:34
 */
class MyRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public MyRvAdapter() {
        super(R.layout.item_my_layout);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_content,item);
    }
}
