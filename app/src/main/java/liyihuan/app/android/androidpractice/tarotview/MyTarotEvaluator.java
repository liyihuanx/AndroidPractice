package liyihuan.app.android.androidpractice.tarotview;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @author created by liyihuanx
 * @date 2020/11/6
 * description: 类的描述
 */
public class MyTarotEvaluator implements TypeEvaluator<PointF> {
    @Override
    public PointF evaluate(float v, PointF pointF, PointF t1) {
        PointF resule = new PointF();
        resule.x = pointF.x + v * (t1.x - pointF.x);
        resule.y = pointF.y + v * (t1.y - pointF.y);
        return resule;
    }
}
