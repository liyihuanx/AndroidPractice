package liyihuan.app.android.androidpractice.loopview;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import liyihuan.app.android.androidpractice.R;

public class LoopActivity extends AppCompatActivity {

    private LoopWallView mLoopWallView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);
        initView();
        initData();
    }

    private void initView() {
        mLoopWallView = (LoopWallView) findViewById(R.id.loop_view);
        mLoopWallView.setScrollV(0.2f); // 设置滚动速度，值越大，速度越快（默认0.2f）


    }

    private void initData() {
        getDataFromXX();
    }

    private void getDataFromXX() {
        //模拟数据
        List<ModeItem> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String index = i < 10 ? "0" + i : "" + i;
            String phone = "188****88" + index;
            String time = "2017-04-12 12：" + index;
            String prize = "礼品" + index;
            data.add(new ModeItem(phone, time, prize));
        }
       initPage(data);
    }

    private void initPage(List<ModeItem> data) {
        mLoopWallView.setAdapter(new MyAdapter(this, data));
    }



}
