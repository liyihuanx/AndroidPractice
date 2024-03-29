package liyihuan.app.android.androidpractice.Indicator.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.androidpractice.fragment.LazyFragment;

/**
 * @ClassName: HomeFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/10/18 14:41
 */
public class HomeOneFragment extends LazyFragment {

    public HomeOneFragment(){

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_one;
    }

    @Override
    public void initView(@NotNull View rootView) {

    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        Log.d("QWER", "onFragmentPause: HomeOneFragment-停止加载数据");
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        Log.d("QWER", "onFragmentResume: HomeOneFragment-正在加载数据");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("QWER", "HomeOneFragment-setUserVisibleHint: " + isVisibleToUser);
    }
}
