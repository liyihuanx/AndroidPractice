package liyihuan.app.android.androidpractice.DiffType;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import java.util.ArrayList;
import java.util.List;

import liyihuan.app.android.androidpractice.R;

public class DiffTypeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    mKotlinAdapter adapter;
    private List<UserBean> userBeanList;
    private ArrayList<UserBean> mSubList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff_type);

        recyclerView = findViewById(R.id.rv);
        userBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserBean userBean = new UserBean();
            userBean.setUsername("liyihuan" + i);
            if (i < 1) {
                userBean.setType(1);
            } else {
                userBean.setType(2);
            }
            userBeanList.add(userBean);
        }
        adapter = new mKotlinAdapter();
        adapter.setNewData(userBeanList);
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                Log.d("QWER", "getSpanSize: "+position);
//                if (position < 2){
//                    return 3;
//                } else {
//                    return 2;
//                }
//            }
//        });

    }
}