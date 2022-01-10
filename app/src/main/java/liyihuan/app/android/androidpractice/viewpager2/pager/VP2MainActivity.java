package liyihuan.app.android.androidpractice.viewpager2.pager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.androidpractice.viewpager2.pager.adapter.MyFragmentPageAdapter;

public class VP2MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    ArrayList<Fragment> viewList;
    ViewPager viewPager;
    TextView t1, t2;
    ImageView indicator;
    int curIndex = 0;
    private float mTranslationX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp2_main);
        init();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first:
                viewPager.setCurrentItem(0);
                t1.setTextColor(getResources().getColor(R.color.rose));
                t2.setTextColor(Color.GRAY);
                break;
            case R.id.second:
                viewPager.setCurrentItem(1);
                t2.setTextColor(getResources().getColor(R.color.rose));
                t1.setTextColor(Color.GRAY);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mTranslationX = (t1.getWidth() + getMargin()) * (position + positionOffset);
        indicator.setTranslationX(mTranslationX);
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                t1.setTextColor(getResources().getColor(R.color.rose));
                t2.setTextColor(Color.GRAY);
                break;
            case 1:
                t2.setTextColor(getResources().getColor(R.color.rose));
                t1.setTextColor(Color.GRAY);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void init() {
        LayoutInflater inflater = getLayoutInflater();
        t1 = findViewById(R.id.first);
        t2 = findViewById(R.id.second);
        viewPager = findViewById(R.id.view_pager);
        indicator = findViewById(R.id.indicator);
        viewList = new ArrayList<>();
        viewList.add(new FirstFragment());
        viewList.add(new SecondFragment());
        viewPager.setAdapter(new MyFragmentPageAdapter(getSupportFragmentManager(), viewList));
        viewPager.setCurrentItem(curIndex);
        viewPager.addOnPageChangeListener(this);
        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
    }

    //获取边界值
    public float getMargin() {
        float margin = 0;
        LinearLayout.LayoutParams t1Layout = (LinearLayout.LayoutParams) t1.getLayoutParams();
        LinearLayout.LayoutParams t2Layout = (LinearLayout.LayoutParams) t2.getLayoutParams();
        margin = t1Layout.getMarginEnd() + t2Layout.getMarginStart();
        return margin;
    }
}