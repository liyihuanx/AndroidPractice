package liyihuan.app.android.androidpractice.lifecycle;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author created by liyihuanx
 * @date 2021/1/5
 * description: 类的描述
 */
public class LifeView extends View implements LifecycleObserver {
    public LifeView(Context context) {
        super(context);
    }

    public LifeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LifeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void TestFunction(){
        Log.d("QWER", "TestFunction_resume: ");
    }


}
