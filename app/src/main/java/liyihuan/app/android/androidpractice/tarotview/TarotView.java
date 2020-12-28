package liyihuan.app.android.androidpractice.tarotview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Random;

import liyihuan.app.android.androidpractice.R;

/**
 * @ClassName: TarotView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/10/21 21:05
 */
class TarotView extends FrameLayout implements View.OnLongClickListener, View.OnClickListener {

    private int totalcount = 10;
    private LayoutInflater layoutInflater;
    private TarotViewListener tarotViewListener;

    public TarotView(@NonNull Context context) {
        this(context, null);
    }

    public TarotView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TarotView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        layoutInflater = LayoutInflater.from(context);
        for (int i = 0; i < totalcount; i++) {

        }
        View view = layoutInflater.inflate(R.layout.tarot_view, this, false);

        view.setOnClickListener(this);
        addView(view);
    }

    public void washCard() {
        for (int i = 0; i < totalcount; i++) {
            View view = getChildAt(i);
            washStep1(view, i * 5, i * 5, i);
        }


    }

    public void washStep1(View view, int fromX, int fromY, int position) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", fromX, 0);
        translationX.setInterpolator(new LinearInterpolator());
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", fromY, 0);
        translationY.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.playTogether(translationX, translationY);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                washStep2();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    public void washStep2() {
        int distance = 0;
        for (int i = 0; i < totalcount - 1; i++) {
            if (i % 2 == 0) {
                distance = 1;
            } else {
                distance = -1;
            }
            View view = getChildAt(i);
            ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", 0, distance * 200, 0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(1000);
            animatorSet.setStartDelay(i * 300);
            animatorSet.play(translationX);
            animatorSet.start();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(view, "rotation", 0, 360,0);
        rotateAnimator.setDuration(500);
        rotateAnimator.start();
        return false;
    }

    @Override
    public void onClick(View view) {
        tarotViewListener.sendCard(view);
    }

    interface TarotViewListener{
        void sendCard(View view);
    }

    public void setTarotViewListener(TarotViewListener tarotViewListener) {
        this.tarotViewListener = tarotViewListener;
    }
}
