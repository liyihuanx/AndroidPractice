//package liyihuan.app.android.androidpractice.video;
//
//import android.graphics.BitmapFactory;
//
//import androidx.annotation.NonNull;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//
//import liyihuan.app.android.androidpractice.R;
//
///**
// * @author created by liyihuanx
// * @date 2021/1/15
// * description: 类的描述
// */
//public class LocalVideoAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {
//    public LocalVideoAdapter() {
//        super(R.layout.item_local_video);
//    }
//
//    @Override
//    protected void convert(@NonNull BaseViewHolder helper, VideoInfo item) {
//        helper.setImageBitmap(R.id.iv_data, BitmapFactory.decodeFile(item.getThumbnailData()))
//                .setText(R.id.tv_name,item.getDisplayName());
//    }
//}
