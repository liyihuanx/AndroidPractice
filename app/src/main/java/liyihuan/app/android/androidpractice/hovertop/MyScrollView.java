package liyihuan.app.android.androidpractice.hovertop;

/**
 * @ClassName: MyScrollView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/26 21:45
 */
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    private OnScrollistener onScrollistener;

    public OnScrollistener getOnScrollistener() {
        return onScrollistener;
    }

    public void setOnScrollistener(OnScrollistener onScrollistener) {
        this.onScrollistener = onScrollistener;
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public interface OnScrollistener {

        void onScroll(int startY, int endY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        onScrollistener.onScroll(oldt, t);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
