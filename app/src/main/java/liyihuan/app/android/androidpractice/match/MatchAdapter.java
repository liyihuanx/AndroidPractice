package liyihuan.app.android.androidpractice.match;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import liyihuan.app.android.androidpractice.R;

/**
 * @author created by liyihuanx
 * @date 2020/12/9
 * description: 类的描述
 */
public class MatchAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MatchAdapter() {
        super(R.layout.item_match_friend);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setImageResource(R.id.iv_match_friend,R.mipmap.match_friend_bg);
    }
}
