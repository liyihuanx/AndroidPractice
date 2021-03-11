package liyihuan.app.android.androidpractice.touchevent;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import liyihuan.app.android.androidpractice.R;

public class MyPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<String> mData ;


    public MyPagerAdapter(Context context, ArrayList<String> data) {
        mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_list, null);
        RecyclerView list = view.findViewById(R.id.rv_bad);


        BadAdapter adapter = new BadAdapter(mData);
        LinearLayoutManager layout = new LinearLayoutManager(mContext);
        list.setLayoutManager(layout);
        list.setAdapter(adapter);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
