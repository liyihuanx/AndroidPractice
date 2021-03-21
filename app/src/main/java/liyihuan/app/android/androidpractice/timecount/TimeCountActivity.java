package liyihuan.app.android.androidpractice.timecount;

import androidx.appcompat.app.AppCompatActivity;

import liyihuan.app.android.androidpractice.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TimeCountActivity extends AppCompatActivity {
    CountDownView cdv;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_count);

        cdv = findViewById(R.id.time);

        cdv.setCountDownListener(new CountDownView.CountDownListener() {
            @Override
            public void CountDownStart() {

            }

            @Override
            public void CountDownStop() {

            }

            @Override
            public void CountDownResume() {

            }

            @Override
            public void CountDownFinish() {
                Toast.makeText(TimeCountActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();

            }
        });


        cdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdv.countDownStart();
            }
        });


    }
}