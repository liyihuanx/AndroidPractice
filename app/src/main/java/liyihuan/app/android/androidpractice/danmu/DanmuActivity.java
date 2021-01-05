package liyihuan.app.android.androidpractice.danmu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.danmu.mydanmu.bean.DmInfo;
import com.example.danmu.mydanmu.view.MyDmView;


import liyihuan.app.android.androidpractice.R;

public class DanmuActivity extends AppCompatActivity {

    private MyDmView dmSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danmu);
        dmSurfaceView = findViewById(R.id.my_dm_view);
        findViewById(R.id.btn_start).setOnClickListener(v -> {
            DmInfo dmInfo = new DmInfo(new DamuView(this));
            dmSurfaceView.getController().addDmItem(dmInfo);

            dmSurfaceView.getController().startThread();
        });

        findViewById(R.id.btn_stop).setOnClickListener(v -> {
            dmSurfaceView.getController().pause();
        });
    }

}