package liyihuan.app.android.androidpractice.Indicator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import liyihuan.app.android.androidpractice.R;

import liyihuan.app.android.androidpractice.fragment.HomeViewPagerAdapter;

public class IndicatorActivity extends AppCompatActivity {
    private ViewPager vp_indication;
    private HomeViewPagerAdapter homeViewPagerAdapter;
    private ArrayList<Fragment> fragmentsList;
    private IndicatorView indicatorView;
    private IndicatorViewpager title2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);

        vp_indication = findViewById(R.id.vp_indication);
        indicatorView = findViewById(R.id.indicatorView);
        title2 = findViewById(R.id.title2);

//        HomeOneFragment homeOneFragment = new HomeOneFragment();
//        HomeTwoFragment homeTwoFragment = new HomeTwoFragment();
//        HomeThreeFragment homeThreeFragment = new HomeThreeFragment();
//        HomeFourFragment homeFourFragment = new HomeFourFragment();
//        fragmentsList = new ArrayList<>();
//        fragmentsList.add(homeOneFragment);
//        fragmentsList.add(homeTwoFragment);
//        fragmentsList.add(homeThreeFragment);
//        fragmentsList.add(homeFourFragment);
//
//        List<String> mTitles=Arrays.asList("标题1","标题2","标题3","标题4");
//
//        title2.setTabItemTitles(mTitles);
//        homeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(),fragmentsList);
//        vp_indication.setAdapter(homeViewPagerAdapter);
//
//        indicatorView.bindViewPager(vp_indication);
//        title2.setViewPager2(vp_indication,0);
    }
}