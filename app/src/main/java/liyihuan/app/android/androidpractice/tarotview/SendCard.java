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
 * @author created by liyihuanx
 * @date 2020/11/6
 * description: 类的描述
 */
public class SendCard extends FrameLayout {
    public SendCard(@NonNull Context context) {
        this(context,null);
    }

    public SendCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SendCard(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View rootview = LayoutInflater.from(context).inflate(R.layout.send_card,this);
    }
}
