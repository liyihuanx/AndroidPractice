package liyihuan.app.android.androidpractice.timecount;

import androidx.appcompat.app.AppCompatActivity;
import liyihuan.app.android.androidpractice.MainActivity;
import liyihuan.app.android.androidpractice.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TimeCountActivity extends AppCompatActivity {
    TimeCountView cdv;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_count);

        cdv = (TimeCountView) findViewById(R.id.time_count);

        cdv.setAddCountDownListener(new TimeCountView.OnCountDownFinishListener() {
            @Override
            public void countDownFinished() {
                Toast.makeText(TimeCountActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();
            }
        });

        cdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdv.startCountDown();
            }
        });
    }
}