package liyihuan.app.android.androidpractice.tarotview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import liyihuan.app.android.androidpractice.R;

/**
 * @ClassName: TarotView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/10/21 21:05
 */
class TarotView extends FrameLayout {

    private int totalcount = 10;
    private LayoutInflater layoutInflater;

    public TarotView(@NonNull Context context) {
        super(context,null);
    }

    public TarotView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public TarotView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        for (int i = 0; i < totalcount; i++) {
            View rootView = layoutInflater.inflate(R.layout.tarot_view, this, false);

        }
    }
}
