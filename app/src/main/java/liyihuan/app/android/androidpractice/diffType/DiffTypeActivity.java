package liyihuan.app.android.androidpractice.diffType;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import java.util.ArrayList;
import java.util.List;

import liyihuan.app.android.androidpractice.R;

public class DiffTypeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DiffAdapter adapter;
    private List<CommonItemModel<Object>> userBeanList;
    private ArrayList<UserBean> mSubList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff_type);

        recyclerView = findViewById(R.id.rv);
        userBeanList = new ArrayList<>();
        List<String> otherList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                CommonItemModel userBean = new CommonItemModel("liyihuan" + i, DiffAdapter.TYPE_BIG);
                userBeanList.add(userBean);
            } else {
                otherList.add("liyihuan" + i);
                if (otherList.size() == 2) {
                    CommonItemModel userBean = new CommonItemModel(otherList, DiffAdapter.TYPE_SMALL);
                    userBeanList.add(userBean);
                    otherList = new ArrayList<>();
                }
            }
        }
        adapter = new DiffAdapter(userBeanList);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        recyclerView.setLayoutManager(gridLayoutManager);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (userBeanList.get(position).getItemType() == DiffAdapter.TYPE_BIG) {
                    return 4;
                } else {
                    return 2;
                }
            }
        });

    }
}