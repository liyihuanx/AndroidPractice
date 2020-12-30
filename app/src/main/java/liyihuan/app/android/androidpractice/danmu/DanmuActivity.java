package liyihuan.app.android.androidpractice.danmu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.danmu.danmu.OnDMAddListener;
import com.example.danmu.danmu.control.DamuBean;
import com.example.danmu.danmu.entity.BaseDmEntity;
import com.example.danmu.danmu.widget.DMSurfaceView;


import liyihuan.app.android.androidpractice.R;

public class DanmuActivity extends AppCompatActivity {

    private DMSurfaceView dmSurfaceView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danmu);
        dmSurfaceView = findViewById(R.id.dmView);
        dmSurfaceView.setOnDMAddListener(new OnDMAddListener() {
            @Override
            public void added(BaseDmEntity dmEntity) {

            }

            @Override
            public void addedAll() {
                Toast.makeText(DanmuActivity.this, "弹幕该轮显示完毕", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < 35; i++) {
                    DamuBean damuBean = new DamuBean();
                    damuBean.setName("小明" + i);
                    damuBean.setMsg("1111111111111222222222222");
                    addDM(damuBean);
                }
            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        dmSurfaceView.getController().pause();

    }

    public void onClickAdd5NoOver(View view) {
        DamuBean damuBean = new DamuBean();
        damuBean.setName("小明");
        damuBean.setMsg("1111111111111222222222222222222");
        addDM(damuBean);
    }

    private void addDM(DamuBean damuBean) {
        final View templateView = LayoutInflater.from(this).inflate(R.layout.barrage, null,false);
        final ViewHolder mViewHolder = new ViewHolder(templateView);
        mViewHolder.tvBarrageName.setText(damuBean.getName());
        mViewHolder.tvBarrageMsg.setText(damuBean.getMsg());
        dmSurfaceView.Viewadd(templateView,damuBean);

    }


    // 相当于一个bean类
    private static class ViewHolder {
        TextView tvBarrageName;
        TextView tvBarrageMsg;
        ImageView imgBarrageHead;

        ViewHolder(View view) {
            tvBarrageName = view.findViewById(R.id.tvBarrageName);
            tvBarrageMsg = view.findViewById(R.id.tvBarrageMsg);
            imgBarrageHead = view.findViewById(R.id.imgBarrageHead);
        }
    }
}