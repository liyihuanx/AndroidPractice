//package liyihuan.app.android.androidpractice.wechat;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.RecyclerView;
//import liyihuan.app.android.androidpractice.R;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WechatActivity extends AppCompatActivity implements MoveInterface {
//
//    RecyclerView recyclerView;
//    MoveItemAdapter moveItemAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wechat);
//        initRv();
//    }
//
//    private void initRv() {
//        List<Integer> list = new ArrayList<>();
//        list.add(R.mipmap.tab_icon_homework_default);
//        list.add(R.mipmap.tab_icon_homework_selected);
//        list.add(R.mipmap.tab_icon_lesson_default);
//        list.add(R.mipmap.tab_icon_lesson_selected);
//        list.add(R.mipmap.tab_icon_mine_default);
//        list.add(R.mipmap.tab_icon_mine_selected);
//        list.add(R.mipmap.tab_icon_qa_default);
//        list.add(R.mipmap.tab_icon_qa_selected);
//        recyclerView = findViewById(R.id.rv_move);
//        moveItemAdapter = new MoveItemAdapter();
//        moveItemAdapter.setNewData(list);
//        recyclerView.setAdapter(moveItemAdapter);
//        MoveTouchHelperCallback moveTouchHelperCallback = new MoveTouchHelperCallback(list);
//        moveTouchHelperCallback.setMoveInterface(this);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(moveTouchHelperCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
//
//    }
//
//    @Override
//    public void moving() {
//        Log.d("QWER", "moving: ");
//    }
//
//    @Override
//    public void moved() {
//        Log.d("QWER", "moved: ");
//
//    }
//
//    @Override
//    public void moveEnd() {
//        Log.d("QWER", "moveEnd: ");
//
//    }
//}