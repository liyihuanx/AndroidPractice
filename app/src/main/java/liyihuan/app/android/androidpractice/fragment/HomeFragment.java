package liyihuan.app.android.androidpractice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import liyihuan.app.android.androidpractice.fragment.Home.HomeOneFragment;
import liyihuan.app.android.androidpractice.fragment.Home.HomeTwoFragment;
import liyihuan.app.android.androidpractice.R;

/**
 * @ClassName: HomeFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/10/18 14:41
 */
public class HomeFragment extends Fragment {

    ViewPager vp_content;
    HomeViewPagerAdapter homeViewPagerAdapter;
    private ArrayList<Fragment> fragmentsList;

    View rootView;
    public HomeFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home,null);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        vp_content = rootView.findViewById(R.id.vp_content);
        HomeOneFragment homeOneFragment = new HomeOneFragment();
        HomeTwoFragment homeTwoFragment = new HomeTwoFragment();
        fragmentsList = new ArrayList<>();
        fragmentsList.add(homeOneFragment);
        fragmentsList.add(homeTwoFragment);
        homeViewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), fragmentsList);
        vp_content.setAdapter(homeViewPagerAdapter);
    }
}
