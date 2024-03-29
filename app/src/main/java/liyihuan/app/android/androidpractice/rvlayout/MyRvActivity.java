package liyihuan.app.android.androidpractice.rvlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.androidpractice.rvlayout.myflow.MyFlowLayout;
import liyihuan.app.android.androidpractice.rvlayout.testlayout.MyRvLayout;

import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

public class MyRvActivity extends AppCompatActivity {
    RecyclerView rv_layout;
    MyRvAdapter rvAdapter;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rv);
        for (int i = 0; i < 10; i++) {
            list.add("-- ITEM " + i);
        }

        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.tarot);

        list.add(R.mipmap.tab_icon_lesson_default);
        list.add(R.mipmap.tab_icon_lesson_selected);
        list.add(R.mipmap.tab_icon_mine_default);
        list.add(R.mipmap.tab_icon_mine_selected);
        list.add(R.mipmap.tab_icon_qa_default);
        list.add(R.mipmap.tab_icon_qa_selected);

        list.add(R.mipmap.tab_icon_lesson_default);
        list.add(R.mipmap.tab_icon_lesson_selected);
        list.add(R.mipmap.tab_icon_mine_default);
        list.add(R.mipmap.tab_icon_mine_selected);
        list.add(R.mipmap.tab_icon_qa_default);
        list.add(R.mipmap.tab_icon_qa_selected);

        list.add(R.mipmap.tab_icon_lesson_default);
        list.add(R.mipmap.tab_icon_lesson_selected);
        list.add(R.mipmap.tab_icon_mine_default);
        list.add(R.mipmap.tab_icon_mine_selected);
        list.add(R.mipmap.tab_icon_qa_default);
        list.add(R.mipmap.tab_icon_qa_selected);

        rv_layout = findViewById(R.id.rv_layout);
        rvAdapter = new MyRvAdapter();
        rvAdapter.setNewData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        FlowLayoutManager layoutManager = new FlowLayoutManager();
        MyRvLayout myRvLayout = new MyRvLayout(this);
        MyFlowLayout myFlowLayout = new MyFlowLayout();

        rv_layout.setLayoutManager(myFlowLayout);
        rv_layout.setAdapter(rvAdapter);
    }

}