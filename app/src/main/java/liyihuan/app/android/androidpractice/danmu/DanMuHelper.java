package liyihuan.app.android.androidpractice.danmu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.Log;


import com.example.danmu.newdanmu.model.DanMuModel;
import com.example.danmu.newdanmu.model.utils.DimensionUtil;
import com.example.danmu.newdanmu.view.IDanMuParent;
import com.example.danmu.newdanmu.view.OnDanMuTouchCallBackListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import liyihuan.app.android.androidpractice.R;

/**
 * 弹幕库使用帮助类
 *
 * 建议凡是弹幕中涉及到的图片，大小控制在50kb以内，尺寸控制在100x100以内（单位像素）
 *
 * Created by android_ls on 2016/12/18.
 */
public final class DanMuHelper {

    private ArrayList<WeakReference<IDanMuParent>> mDanMuViewParents;
    private Context mContext;

    public DanMuHelper(Context context) {
        this.mContext = context.getApplicationContext();
        this.mDanMuViewParents = new ArrayList<>();
    }

    public void release() {
        if (mDanMuViewParents != null) {
            for (WeakReference<IDanMuParent> danMuViewParentsRef : mDanMuViewParents) {
                if (danMuViewParentsRef != null) {
                    IDanMuParent danMuParent = danMuViewParentsRef.get();
                    if (danMuParent != null)
                        danMuParent.release();
                }
            }
            mDanMuViewParents.clear();
            mDanMuViewParents = null;
        }

        mContext = null;
    }

    public void add(final IDanMuParent danMuViewParent) {
        if (danMuViewParent != null) {
            danMuViewParent.clear();
        }

        if (mDanMuViewParents != null) {
            mDanMuViewParents.add(new WeakReference<>(danMuViewParent));
        }
    }

    public void addDanMu(DanmakuEntity danmakuEntity) {
        if (mDanMuViewParents != null) {
            WeakReference<IDanMuParent> danMuViewParent = mDanMuViewParents.get(0);
            DanMuModel danMuView = createDanMuView(danmakuEntity);
            if (danMuViewParent != null && danMuView != null && danMuViewParent.get() != null) {
                danMuViewParent.get().add(danMuView);
            }
        }
    }

    private DanMuModel createDanMuView(final DanmakuEntity entity) {
        final DanMuModel danMuView = new DanMuModel();
        danMuView.setDisplayType(DanMuModel.RIGHT_TO_LEFT);
        danMuView.setPriority(DanMuModel.NORMAL);
        danMuView.marginLeft = DimensionUtil.dpToPx(mContext, 30);

        if (entity.getType() == DanmakuEntity.DANMAKU_TYPE_USERCHAT) {

            // 加载头像
            String avatarImageUrl = entity.getAvatar();

            int avatarSize = DimensionUtil.dpToPx(mContext, 30);
            danMuView.avatarWidth = avatarSize;
            danMuView.avatarHeight = avatarSize;
            danMuView.avatar = drawable2Bitmap(ContextCompat.getDrawable(mContext, R.drawable.boy_head));

            // 显示的文本内容
            String name = entity.getName() + "：";
            String content = entity.getText();


            danMuView.textSize = DimensionUtil.spToPx(mContext, 14);
            danMuView.textColor = ContextCompat.getColor(mContext, R.color.black);
            danMuView.textMarginLeft = DimensionUtil.dpToPx(mContext, 5);
            danMuView.text =  name + content;

            // 弹幕文本背景
            danMuView.textBackground = ContextCompat.getDrawable(mContext, R.drawable.shape_barrage_bg);
            danMuView.textBackgroundMarginLeft = DimensionUtil.dpToPx(mContext, 15);
            danMuView.textBackgroundPaddingTop = DimensionUtil.dpToPx(mContext, 3);
            danMuView.textBackgroundPaddingBottom = DimensionUtil.dpToPx(mContext, 3);
            danMuView.textBackgroundPaddingRight = DimensionUtil.dpToPx(mContext, 15);

            danMuView.enableTouch(true);
            danMuView.setOnTouchCallBackListener(new OnDanMuTouchCallBackListener() {

                @Override
                public void callBack(DanMuModel danMuView) {
                    Log.d("QWER", "callBack: "+danMuView.text);
                }
            });
        }

        return danMuView;
    }

    /**
     * Drawable转换成Bitmap
     *
     * @param drawable
     * @return
     */
    public Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            // 转换成Bitmap
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            // .9图片转换成Bitmap
            Bitmap bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ?
                            Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }
}