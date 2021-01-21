package liyihuan.app.android.androidpractice.imdemo.conversation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.androidpractice.imdemo.imchat.ImDemoActivity;
import liyihuan.app.android.androidpractice.imdemo.utils.IMUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMCreateGroupMemberInfo;

import java.util.ArrayList;
import java.util.List;

public class ConversationListActivity extends AppCompatActivity {

    private ConversationAdapter conversationAdapter;
    private RecyclerView recyclerView;
    private Button btn_conversation,btn_createGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);


        btn_conversation = findViewById(R.id.btn_conversation);
        btn_conversation.setOnClickListener(v -> {
            IMUtil.getConversationHistory(new ConversationCallback() {
                @Override
                public void getConversation(List<V2TIMConversation> convList) {
                    // TODO 更新UI
                    conversationAdapter.setNewData(convList);
                    conversationAdapter.notifyDataSetChanged();
                }

                @Override
                public void muteMember() {

                }
            });
        });

        btn_createGroup = findViewById(R.id.btn_createGroup);
        btn_createGroup.setOnClickListener(v->{
            List<V2TIMCreateGroupMemberInfo> memberInfoList = new ArrayList<>();
            V2TIMCreateGroupMemberInfo memberA = new V2TIMCreateGroupMemberInfo();
            memberA.setUserID(IMUtil.username);
            V2TIMCreateGroupMemberInfo memberB = new V2TIMCreateGroupMemberInfo();
            memberB.setUserID(IMUtil.to);
            memberInfoList.add(memberA);
            memberInfoList.add(memberB);
            IMUtil.createGroup("GroupA","Public",memberInfoList);
        });

        conversationAdapter = new ConversationAdapter();
        recyclerView = findViewById(R.id.rv_conversation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(conversationAdapter);

        conversationAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(this, ImDemoActivity.class);
            startActivity(intent);
        });
    }
}