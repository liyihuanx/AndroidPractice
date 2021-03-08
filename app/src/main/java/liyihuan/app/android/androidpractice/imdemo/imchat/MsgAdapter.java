//package liyihuan.app.android.androidpractice.imdemo.imchat;
//
//
//import androidx.annotation.NonNull;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.chad.library.adapter.base.util.MultiTypeDelegate;
//
//import liyihuan.app.android.androidpractice.R;
//
///**
// * @author created by liyihuanx
// * @date 2020/11/30
// * description: 类的描述
// */
//public class MsgAdapter extends BaseQuickAdapter<MsgBean, BaseViewHolder> {
//    private static final int TYPE_LEFT = 1;
//    private static final int TYPE_RIGHT = 2;
//
//    public MsgAdapter() {
//        super(null);
//
//        setMultiTypeDelegate(new MultiTypeDelegate<MsgBean>() {
//
//            @Override
//            protected int getItemType(MsgBean msgBean) {
//                return msgBean.getType();
//            }
//        });
//
//        getMultiTypeDelegate().registerItemType(TYPE_LEFT, R.layout.item_chat_type_left)
//                .registerItemType(TYPE_RIGHT, R.layout.item_chat_type_right);
//    }
//
//    @Override
//    protected void convert(@NonNull BaseViewHolder helper, MsgBean item) {
//        helper.setText(R.id.tv_user_name,item.getUserName())
//                .setText(R.id.tv_chat_content,item.getTextContent());
//    }
//}
