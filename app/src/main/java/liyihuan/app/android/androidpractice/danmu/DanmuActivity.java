package liyihuan.app.android.androidpractice.danmu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danmu.danmu.OnDMAddListener;
import com.example.danmu.danmu.entity.BaseDmEntity;
import com.example.danmu.danmu.widget.DMSurfaceView;
import com.example.danmu.newdanmu.DanMuView;

import liyihuan.app.android.androidpractice.R;

public class DanmuActivity extends AppCompatActivity {


    private DanMuView mDanMuContainerRoom;
    private DanMuHelper mDanMuHelper;
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danmu);

        mDanMuHelper = new DanMuHelper(this);

        // 当前房间内的弹幕
        mDanMuContainerRoom = (DanMuView) findViewById(R.id.danmaku_container_room);
        mDanMuContainerRoom.prepare();
        mDanMuHelper.add(mDanMuContainerRoom);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 35; i++) {
                    DanmakuEntity danmakuEntity = new DanmakuEntity();
                    danmakuEntity.setType(DanmakuEntity.DANMAKU_TYPE_USERCHAT);
                    danmakuEntity.setName("小A" + i);
                    danmakuEntity.setAvatar("https://q.qlogo.cn/qqapp/100229475/E573B01150734A02F25D8E9C76AFD138/100");
                    danmakuEntity.setLevel(23);
                    danmakuEntity.setText("滚滚长江东逝水，浪花淘尽英雄~~");

                    addRoomDanmaku(danmakuEntity);
                }
            }
        });
    }



    /**
     * 发送一条房间内的弹幕
     */
    private void addRoomDanmaku(DanmakuEntity danmakuEntity) {
        if (mDanMuHelper != null) {
            mDanMuHelper.addDanMu(danmakuEntity);
        }
    }


    @Override
    protected void onDestroy() {
        if (mDanMuHelper != null) {
            mDanMuHelper.release();
            mDanMuHelper = null;
        }

        super.onDestroy();
    }
}