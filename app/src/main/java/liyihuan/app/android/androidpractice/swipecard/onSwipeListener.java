package liyihuan.app.android.androidpractice.swipecard;

import android.view.View;

/**
 * @author created by liyihuanx
 * @date 2020/10/30
 * description: 类的描述
 */
public interface onSwipeListener<T> {
    // 滑动中
    void Swiping(View itemChild, float ratio, int direction);

    // 滑动结束
    void Swiped(T itemData);

    // 滑动数据没有了
    void SwipeNoData();
}
