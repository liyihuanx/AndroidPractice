package liyihuan.app.android.androidpractice.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import liyihuan.app.android.androidpractice.R;

public class LifeActivity extends AppCompatActivity {

    LifeView lifeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        lifeView = findViewById(R.id.life_view);
        getLifecycle().addObserver(lifeView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("QWER", "Activity_onResume: ");
    }
}