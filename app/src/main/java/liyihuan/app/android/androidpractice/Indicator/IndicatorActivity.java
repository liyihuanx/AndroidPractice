package liyihuan.app.android.androidpractice.Indicator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.androidpractice.fragment.Home.HomeOneFragment;
import liyihuan.app.android.androidpractice.fragment.Home.HomeTwoFragment;
import liyihuan.app.android.androidpractice.fragment.HomeViewPagerAdapter;

public class IndicatorActivity extends AppCompatActivity {
    private ViewPager vp_indication;
    private HomeViewPagerAdapter homeViewPagerAdapter;
    private ArrayList<Fragment> fragmentsList;
    private IndicatorView indicatorView;
    private ViewPagerIndicator title;
    private IndicatorViewpager title2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);

        vp_indication = findViewById(R.id.vp_indication);
        indicatorView = findViewById(R.id.indicatorView);
        title = findViewById(R.id.title);
        title2 = findViewById(R.id.title2);

        HomeOneFragment homeOneFragment = new HomeOneFragment();
        HomeTwoFragment homeTwoFragment = new HomeTwoFragment();
        fragmentsList = new ArrayList<>();
        fragmentsList.add(homeOneFragment);
        fragmentsList.add(homeTwoFragment);

        List<Integer> colors = Arrays.asList(getResources().getColor(R.color.red),
                getResources().getColor(R.color.blue), getResources().getColor(R.color.yellow), 14, 25);
        List<String> mTitles=Arrays.asList("1","2");

        title.setTabItemTitles(mTitles, colors);
        title2.setTabItemTitles(mTitles);
        homeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(),fragmentsList);
        vp_indication.setAdapter(homeViewPagerAdapter);

        indicatorView.bindViewPager(vp_indication);
        title.setViewPager(vp_indication,0);
        title2.setViewPager2(vp_indication,0);
    }
}