package liyihuan.app.android.androidpractice.danmu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import liyihuan.app.android.androidpractice.R;

/**
 * @ClassName: DamuView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/12/27 15:38
 */
public class DamuView extends FrameLayout {

    private LayoutInflater layoutInflater;


    public DamuView(@NonNull Context context) {
        this(context, null);
    }

    public DamuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DamuView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.barrage, this,false);
        addView(view);
    }

}
