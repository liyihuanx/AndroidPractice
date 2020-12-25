package liyihuan.app.android.androidpractice.danmu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.danmu.widget.DMSurfaceView;

import liyihuan.app.android.androidpractice.R;

public class DanmuActivity extends AppCompatActivity {

    private DMSurfaceView dmSurfaceView;

    private final Handler handler = new Handler();

    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danmu);
        dmSurfaceView = findViewById(R.id.dmView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        dmSurfaceView.getController().pause();

        if (runnable != null) {
            handler.removeCallbacks(runnable);
            runnable = null;
        }
    }

    public void onClickAdd5NoOver(View view) {


        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    addDM("小明11", "1111111111111111111111111111111111 11111111111 ", "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3512331237,2033775251&fm=27&gp=0.jpg");
                    addDM("小明21", "21212121212121212121212121212121212121消息2 sdaf", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1881776517,987084327&fm=27&gp=0.jpg");
                    addDM("小明31", "31313131313131313131313131313131313131313消息3dsfabcdefghi", "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=16550438,2220103346&fm=27&gp=0.jpg");
                    addDM("小明41", "4141414141414141414141414141414141414141414141414141414141414消息4ds", "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2237097328,2363045038&fm=27&gp=0.jpg");
                    addDM("小明51", "51515151515151515151515151515151515151515151515151消息5", "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=586574978,3261086036&fm=27&gp=0.jpg");

                }
            };
            handler.post(runnable);
        }
    }

    private void addDM(String name, String msg, String imgUrl) {
        final View templateView = LayoutInflater.from(this).inflate(R.layout.barrage, null);
        final ViewHolder mViewHolder = new ViewHolder(templateView);
        mViewHolder.tvBarrageName.setText(name);
        mViewHolder.tvBarrageMsg.setText(msg);

        Glide.with(this)
                .load(imgUrl)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        mViewHolder.imgBarrageHead.setImageDrawable(resource);
                        dmSurfaceView.getController().add(templateView);

                    }
                });
    }


    public void onClickClearScreen(View view) {
        dmSurfaceView.getController().clean();
    }

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