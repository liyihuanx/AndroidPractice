package liyihuan.app.android.androidpractice.imageView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import liyihuan.app.android.androidpractice.R;

/**
 * @ClassName: CustomerImageView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/11/17 21:37
 */
class CustomerImageView extends RelativeLayout {

    private LayoutInflater layoutInflater;

    public CustomerImageView(Context context) {
        this(context,null);
    }

    public CustomerImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        layoutInflater = LayoutInflater.from(context);
        for (int i = 0; i < 5; i++) {
            View view = layoutInflater.inflate(R.layout.item_customer_iv,this,false);
            addView(view);
        }

    }
}
