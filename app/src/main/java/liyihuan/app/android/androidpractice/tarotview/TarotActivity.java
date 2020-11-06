package liyihuan.app.android.androidpractice.tarotview;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import liyihuan.app.android.androidpractice.R;

public class TarotActivity extends AppCompatActivity implements TarotView.TarotViewListener {

    TarotView tarotView;
    Button btnWash;
    ImageView tarot1;
    ImageView tarot2;
    ImageView tarot3;
    ImageView iv_move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarot);
        tarotView = findViewById(R.id.tarotview);
        tarot1 = findViewById(R.id.tarot1);
        tarot2 = findViewById(R.id.tarot2);
        tarot3 = findViewById(R.id.tarot3);
        iv_move = findViewById(R.id.iv_move);

        btnWash = findViewById(R.id.btn_wash);
        btnWash.setOnClickListener(v -> {
            tarotView.washCard();
        });

        tarotView.setTarotViewListener(this);
    }

    @Override
    public void sendCard(View view) {
        //获取将要抽出的牌在屏幕上的坐标
        int[] loc = new int[2];
        view.getLocationOnScreen(loc);
        //设置做平移动画的的View的起始坐标
        iv_move.setX(loc[0]);
        iv_move.setY(loc[1]);
        Log.d("QWER", "getX: "+iv_move.getX());
        Log.d("QWER", "getY: "+iv_move.getY());
        Log.d("QWER", "getX: "+tarot1.getX());
        Log.d("QWER", "getY: "+tarot1.getY());
        iv_move.setVisibility(View.VISIBLE);
        sendCardAnim(iv_move,tarot1);
    }

    private void sendCardAnim(View startView,View endView){
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MyTarotEvaluator(),
                new PointF(startView.getX(),startView.getY()),
                new PointF(endView.getX(),endView.getY()));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                startView.setX(pointF.x);
                startView.setY(pointF.y);
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.start();
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.d("QWER", "onAnimationEnd-getX: "+iv_move.getX());
                Log.d("QWER", "onAnimationEnd=getY: "+iv_move.getY());
            }
        });
    }
}