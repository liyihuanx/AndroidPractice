//package liyihuan.app.android.androidpractice.imdemo.conversation;
//
//import android.text.TextUtils;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.tencent.imsdk.v2.V2TIMConversation;
//
//import androidx.annotation.NonNull;
//import liyihuan.app.android.androidpractice.R;
//
///**
// * @ClassName: ConversationAdapter
// * @Description: java类作用描述
// * @Author: liyihuan
// * @Date: 2021/1/17 17:19
// */
//class ConversationAdapter extends BaseQuickAdapter<V2TIMConversation, BaseViewHolder> {
//
//    public ConversationAdapter() {
//        super(R.layout.item_conversation);
//    }
//
//    @Override
//    protected void convert(@NonNull BaseViewHolder helper, V2TIMConversation item) {
//        ImageView iv_head = helper.getView(R.id.iv_head);
//        Glide.with(mContext)
//                .load(item.getFaceUrl())
//                .into(iv_head);
//
//        helper.setText(R.id.tv_name, item.getShowName());
////        if (!TextUtils.isEmpty(item.getLastMessage().getTextElem().getText())){
////            helper.setText(R.id.tv_last_msg, item.getLastMessage().getTextElem().getText());
////        }
//
//    }
//}
