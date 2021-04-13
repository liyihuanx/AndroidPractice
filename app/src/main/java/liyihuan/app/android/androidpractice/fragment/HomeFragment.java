package liyihuan.app.android.androidpractice.fragment;

import android.app.MediaRouteActionProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import liyihuan.app.android.androidpractice.MRouterConfig;
import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.mrouter_api.RouterManager;

/**
 * @ClassName: HomeFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/10/18 14:41
 */
public class HomeFragment extends LazyFragment {



    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(@NotNull View rootView) {
        rootView.findViewById(R.id.tvToMain).setOnClickListener(v -> {
            RouterManager.getInstance()
                    .build(MRouterConfig.module_app.MainActivity)
                    .withString("name", "liyihuanx")
                    .navigation(getContext());
        });
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("QWER", "HomeFragment-onCreate: ");
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("QWER", "HomeFragment-onActivityCreated: ");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("QWER", "HomeFragment-onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("QWER", "HomeFragment-onPause: ");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("QWER", "HomeFragment-onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("QWER", "HomeFragment-onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("QWER", "HomeFragment-onDestroy: ");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("QWER", "HomeFragment-onDetach: ");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("QWER", "HomeFragment-setUserVisibleHint: " + isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("QWER", "HomeFragment-onHiddenChanged: "+hidden);
    }

    @Override
    protected void onFragmentFirstVisible() {
        Log.d("QWER", "onFragmentFirstVisible: HomeFragment");
    }


    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        Log.d("QWER", "onFragmentPause: HomeFragment-停止加载数据");
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        Log.d("QWER", "onFragmentResume: HomeFragment-正在加载数据");
    }
}
