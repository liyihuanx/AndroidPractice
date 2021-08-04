package liyihuan.app.android.androidpractice.viewpager2.pager.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentPageAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    private ArrayList<Fragment> list;

    public MyFragmentPageAdapter(@NonNull FragmentManager fm,ArrayList<Fragment> list) {
        super(fm);
        this.fm = fm;
        this.list = list;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
