package liyihuan.app.android.androidpractice.Indicator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.androidpractice.fragment.Home.HomeOneFragment;
import liyihuan.app.android.androidpractice.fragment.Home.HomeTwoFragment;
import liyihuan.app.android.androidpractice.fragment.HomeViewPagerAdapter;

public class IndicatorActivity extends AppCompatActivity {
    private ViewPager vp_indication;
    private HomeViewPagerAdapter homeViewPagerAdapter;
    private ArrayList<Fragment> fragmentsList;
    private IndicatorView indicatorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);

        vp_indication = findViewById(R.id.vp_indication);
        indicatorView = findViewById(R.id.indicatorView);

        HomeOneFragment homeOneFragment = new HomeOneFragment();
        HomeTwoFragment homeTwoFragment = new HomeTwoFragment();
        fragmentsList = new ArrayList<>();
        fragmentsList.add(homeOneFragment);
        fragmentsList.add(homeTwoFragment);

        homeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(),fragmentsList);
        vp_indication.setAdapter(homeViewPagerAdapter);

        indicatorView.bindViewPager(vp_indication);
    }
}