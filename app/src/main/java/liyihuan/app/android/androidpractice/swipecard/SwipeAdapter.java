package liyihuan.app.android.androidpractice.swipecard;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import liyihuan.app.android.androidpractice.R;


/**
 * @author created by liyihuanx
 * @date 2020/10/22
 * description: 类的描述
 */
public class SwipeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SwipeAdapter() {
        super(R.layout.item_swipe_card);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setBackgroundRes(R.id.iv_img, R.mipmap.tarot)
                .setText(R.id.tv_name, item);
    }
}
