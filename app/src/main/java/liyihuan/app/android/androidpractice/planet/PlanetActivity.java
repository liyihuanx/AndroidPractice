package liyihuan.app.android.androidpractice.planet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.planet.view.SoulPlanetsView;

import java.util.ArrayList;

import liyihuan.app.android.androidpractice.R;

public class PlanetActivity extends AppCompatActivity {
    private mPlanetAdapter labelAdapter = new mPlanetAdapter();
    private ArrayList<String> labelList = new ArrayList<>();
    private SoulPlanetsView soulPlanetView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_friend);

        for (int i = 0; i < 60; i++) {
            labelList.add("star" + i);
        }
        labelAdapter.setLabelList(labelList);
        soulPlanetView = findViewById(R.id.soulPlanetView2);
        soulPlanetView.setAdapter(labelAdapter);
    }
}