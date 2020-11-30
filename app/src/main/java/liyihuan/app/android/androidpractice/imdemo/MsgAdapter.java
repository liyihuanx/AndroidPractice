package liyihuan.app.android.androidpractice.imdemo;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import liyihuan.app.android.androidpractice.R;

/**
 * @author created by liyihuanx
 * @date 2020/11/30
 * description: 类的描述
 */
public class MsgAdapter extends BaseQuickAdapter<MsgBean, BaseViewHolder> {

    public MsgAdapter() {
        super(R.layout.item_msg_adapter);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MsgBean item) {
        helper.setText(R.id.tv_content,item.getTextContent());
//                .setImageBitmap(R.id.iv_img,item.getImgContent());
    }
}
