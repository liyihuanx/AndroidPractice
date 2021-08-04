package liyihuan.app.android.androidpractice.viewpager2.pager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;

import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.androidpractice.viewpager2.pager.adapter.UltraAdapter;


public class FirstFragment extends Fragment {
    private ViewPager2 banner;
    private UltraAdapter mAdapter;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<Integer> picList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container,false);
        banner = view.findViewById(R.id.frag1_pager);
        initData();
        mAdapter = new UltraAdapter();

        mAdapter.setPic_list(picList);
        banner.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        return view;
    }
    public void initData(){
        picList.add(R.drawable.banner1);
        picList.add(R.drawable.banner2);

    }

}
