package liyihuan.app.android.androidpractice.Indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @ClassName: IndicatorView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/11/2 21:31
 */
class IndicatorView extends View {
    public IndicatorView(Context context) {
        this(context,null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context,null,0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
    }


}
