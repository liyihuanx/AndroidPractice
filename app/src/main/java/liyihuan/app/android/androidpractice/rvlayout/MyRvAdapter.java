package liyihuan.app.android.androidpractice.rvlayout;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.danmu.danmu.Util;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.androidpractice.utils.ScreenUtils;

/**
 * @ClassName: MyRvAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/1/7 20:34
 */
class MyRvAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {


    public MyRvAdapter() {
        super(R.layout.item_my_layout);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Integer item) {
        helper.setBackgroundRes(R.id.iv_image,item);
//        helper.setText(R.id.tv_content, item);

//        ViewGroup.LayoutParams layoutParams = helper.itemView.getRootView().getLayoutParams();
//        if (helper.getAdapterPosition() % 3 == 0) {
//            layoutParams.width = ScreenUtils.getScreenWidth(mContext) * 2 / 3;
//            layoutParams.height = ScreenUtils.getScreenWidth(mContext) * 2 / 3;
//        } else {
//            layoutParams.width = ScreenUtils.getScreenWidth(mContext) * 1 / 3;
//            layoutParams.height = ScreenUtils.getScreenWidth(mContext) * 1 / 3;
//        }
//        helper.itemView.getRootView().setLayoutParams(layoutParams);

    }
}
