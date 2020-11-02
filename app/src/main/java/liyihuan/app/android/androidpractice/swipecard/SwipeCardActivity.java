package liyihuan.app.android.androidpractice.swipecard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import liyihuan.app.android.androidpractice.R;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SwipeCardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeCardAdapter swipeCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card);
        initRv();
    }

    private void initRv() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.img1);
        list.add(R.mipmap.img2);
        list.add(R.mipmap.img3);
        list.add(R.mipmap.img4);

//        list.add(R.mipmap.tab_icon_homework_default);
//        list.add(R.mipmap.tab_icon_homework_selected);
//        list.add(R.mipmap.tab_icon_lesson_default);
//        list.add(R.mipmap.tab_icon_lesson_selected);
//        list.add(R.mipmap.tab_icon_mine_default);
//        list.add(R.mipmap.tab_icon_mine_selected);
//        list.add(R.mipmap.tab_icon_qa_default);
//        list.add(R.mipmap.tab_icon_qa_selected);

        swipeCardAdapter = new SwipeCardAdapter();
        swipeCardAdapter.setNewData(list);
        recyclerView = findViewById(R.id.rv_swipe);

        myItemTouchHelperCallback itemTouchHelperCallback = new myItemTouchHelperCallback();
        itemTouchHelperCallback.setSwipeInterface(new SwipeInterface() {
            @Override
            public void onSwiping() {
                Log.d("QWER", "onSwiping: ");
            }

            @Override
            public void onSwiped() {
                Log.d("QWER", "onSwiped: ");
            }

        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        SwipeCardLayout swipeCardLayout = new SwipeCardLayout();
        recyclerView.setLayoutManager(swipeCardLayout);
        recyclerView.setAdapter(swipeCardAdapter);
    }
}