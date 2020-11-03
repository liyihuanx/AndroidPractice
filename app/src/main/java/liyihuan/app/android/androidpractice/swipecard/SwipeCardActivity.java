package liyihuan.app.android.androidpractice.swipecard;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import liyihuan.app.android.androidpractice.R;

public class SwipeCardActivity extends AppCompatActivity implements onSwipeListener<String>, BaseQuickAdapter.OnItemClickListener {
    RecyclerView rvSwipeCard;
    SwipeAdapter swipeAdapter;
    List<String> itemData;
    MyItemTouchHelpCallback itemTouchHelpCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card);

        initView();
    }

    private void initView(){
        itemData = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            itemData.add("第" + i + "张");
        }
        rvSwipeCard = findViewById(R.id.rv_swipe_card);
        swipeAdapter = new SwipeAdapter();
        swipeAdapter.setOnItemClickListener(this);
        swipeAdapter.setNewData(itemData);
        itemTouchHelpCallback = new MyItemTouchHelpCallback(itemData);
        itemTouchHelpCallback.setListener(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelpCallback);
        itemTouchHelper.attachToRecyclerView(rvSwipeCard);
        SwipeCardLayout swipeCardLayout = new SwipeCardLayout(rvSwipeCard, itemTouchHelper);
        rvSwipeCard.setLayoutManager(swipeCardLayout);
        rvSwipeCard.setAdapter(swipeAdapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Log.d("QWER", "onItemClick: ");
    }

    @Override
    public void Swiping(View itemChild, float ratio, int direction) {
        if (direction == ItemTouchHelper.RIGHT || direction == ItemTouchHelper.LEFT) {
            itemChild.setAlpha(1 - Math.abs(ratio) * 0.2f);
        } else {
            itemChild.setAlpha(1f);
        }
    }

    @Override
    public void Swiped(String data) {

        itemData.remove(data);
        swipeAdapter.notifyItemRemoved(0);
        itemTouchHelpCallback.setItemData(itemData);


    }

    @Override
    public void SwipeNoData() {
        itemData.clear();
        for (int i = 1; i < 10; i++) {
            itemData.add("第" + i + "张");
        }
        swipeAdapter.setNewData(itemData);
        swipeAdapter.notifyDataSetChanged();
    }

    private void startAnimation(View view){

    }
}