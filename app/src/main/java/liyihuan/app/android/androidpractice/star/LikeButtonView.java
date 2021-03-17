package liyihuan.app.android.androidpractice.star;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import liyihuan.app.android.androidpractice.R;

/**
 * @author created by liyihuanx
 * @date 2021/3/17
 * description: 类的描述
 */
public class LikeButtonView extends FrameLayout {

    CircleView mCvCircle; // 圆形
    ImageView mIvStar; // 星星
     DotsView mDvDots; // 圆圈

    private DecelerateInterpolator mDecelerate; // 减速插值
    private OvershootInterpolator mOvershoot; // 超出插值
    private AccelerateDecelerateInterpolator mAccelerateDecelerate; // 加速度减速插值
    private AnimatorSet mAnimatorSet; // 动画集合

    private boolean mIsChecked; // 点击状态

    public LikeButtonView(Context context) {
        super(context);
        init();
    }

    public LikeButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LikeButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LikeButtonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    // 初始化视图
    private void init() {
        isInEditMode();

        View rootview = LayoutInflater.from(getContext()).inflate(R.layout.view_like_button, this, true);
        mCvCircle = rootview.findViewById(R.id.like_button_cv_circle);
        mIvStar = rootview.findViewById(R.id.like_button_iv_star);
        mDvDots = rootview.findViewById(R.id.like_button_dv_dots);

        mDecelerate = new DecelerateInterpolator(); // 减速插值器
        mOvershoot = new OvershootInterpolator(4); // 超出插值器
        mAccelerateDecelerate = new AccelerateDecelerateInterpolator(); // 加速再减速插值器

        setOnClickListener(this::clickView);
    }

    // 点击视图
    private void clickView(View view) {
        mIsChecked = !mIsChecked;
        mIvStar.setImageResource(mIsChecked ? R.mipmap.ic_star_rate_on : R.mipmap.ic_star_rate_off);

        // 情况状态
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }

        if (mIsChecked) {
            mIvStar.animate().cancel();
            mIvStar.setScaleX(0);
            mIvStar.setScaleY(0);
            mCvCircle.setInnerCircleRadiusProgress(0);
            mCvCircle.setOuterCircleRadiusProgress(0);
            mDvDots.setCurrentProgress(0);

            mAnimatorSet = new AnimatorSet();

            ObjectAnimator outerCircleAnimator = ObjectAnimator.ofFloat(mCvCircle, CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
            outerCircleAnimator.setDuration(250);
            outerCircleAnimator.setInterpolator(mDecelerate);

            // 延迟擦除
            ObjectAnimator innerCircleAnimator = ObjectAnimator.ofFloat(mCvCircle, CircleView.INNER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
            innerCircleAnimator.setDuration(200);
            innerCircleAnimator.setStartDelay(200);
            innerCircleAnimator.setInterpolator(mDecelerate);

            // 竖直水平放大和缩小
            ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(mIvStar, ImageView.SCALE_Y, 0.2f, 1f);
            starScaleYAnimator.setDuration(350);
            starScaleYAnimator.setStartDelay(250);
            starScaleYAnimator.setInterpolator(mOvershoot);

            ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(mIvStar, ImageView.SCALE_X, 0.2f, 1f);
            starScaleXAnimator.setDuration(350);
            starScaleXAnimator.setStartDelay(250);
            starScaleXAnimator.setInterpolator(mOvershoot);

            // 先快后慢.
            ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(mDvDots, DotsView.DOTS_PROGRESS, 0, 1f);
            dotsAnimator.setDuration(900);
            dotsAnimator.setStartDelay(50);
            dotsAnimator.setInterpolator(mAccelerateDecelerate);

            // 放入动画集合
            mAnimatorSet.playTogether(
                    outerCircleAnimator,
                    innerCircleAnimator,
                    starScaleYAnimator,
                    starScaleXAnimator,
                    dotsAnimator
            );

            // 动画集合监听
            mAnimatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    // 初始值
                    mIvStar.setScaleX(1);
                    mIvStar.setScaleY(1);
                    mCvCircle.setInnerCircleRadiusProgress(0);
                    mCvCircle.setOuterCircleRadiusProgress(0);
                    mDvDots.setCurrentProgress(0);
                }
            });

            mAnimatorSet.start();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIvStar.animate().scaleX(0.7f).scaleY(0.7f).setDuration(150).setInterpolator(mDecelerate);
                setPressed(true);
                break;

            // 在控件内移动, 判断为点击.
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                boolean isInside = (x > 0 && x < getWidth() && y > 0 && y < getHeight());
                if (isPressed() != isInside) {
                    setPressed(isInside);
                }
                break;

            case MotionEvent.ACTION_UP:
                mIvStar.animate().scaleX(1).scaleY(1).setInterpolator(mDecelerate);
                if (isPressed()) {
                    performClick();
                    setPressed(false);
                }
                break;
        }
        return true;
    }
}
