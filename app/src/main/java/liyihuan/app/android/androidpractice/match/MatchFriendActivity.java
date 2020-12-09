package liyihuan.app.android.androidpractice.match;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import liyihuan.app.android.androidpractice.R;

public class MatchFriendActivity extends AppCompatActivity {
    RecyclerView rv_match;
    MatchAdapter matchAdapter;
    List<String> mMatchList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_friend);

        mMatchList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            mMatchList.add("D-" + i);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        matchAdapter = new MatchAdapter();
        matchAdapter.setNewData(mMatchList);
        rv_match = findViewById(R.id.rv_match);
        rv_match.setAdapter(matchAdapter);
        rv_match.setLayoutManager(gridLayoutManager);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == mMatchList.size() - 1) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
    }
}