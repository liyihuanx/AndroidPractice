package liyihuan.app.android.androidpractice.tarotview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import liyihuan.app.android.androidpractice.R;

public class TarotActivity extends AppCompatActivity {

    TarotView tarotView;
    Button btnWash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarot);
        tarotView = findViewById(R.id.tarotview);
        btnWash = findViewById(R.id.btn_wash);
        btnWash.setOnClickListener(v -> {
            tarotView.washCard();
        });
    }
}