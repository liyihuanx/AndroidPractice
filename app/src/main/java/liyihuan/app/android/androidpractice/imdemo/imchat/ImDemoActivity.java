//package liyihuan.app.android.androidpractice.imdemo.imchat;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.google.gson.Gson;
//import com.tencent.imsdk.v2.V2TIMAdvancedMsgListener;
//import com.tencent.imsdk.v2.V2TIMManager;
//import com.tencent.imsdk.v2.V2TIMMessage;
//import com.tencent.imsdk.v2.V2TIMMessageReceipt;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import liyihuan.app.android.androidpractice.R;
//import liyihuan.app.android.androidpractice.imdemo.conversation.ConversationListActivity;
//import liyihuan.app.android.androidpractice.imdemo.utils.IMUtil;
//
//public class ImDemoActivity extends AppCompatActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
//    Button btn_im_send_c2c, btn_im_refresh, btn_im_send_img;
//    private RecyclerView rv_msg;
//    private MsgAdapter msgAdapter;
//    private List<MsgBean> rvMsgList;
//    private EditText et_input;
//    private TextView tv_back;
//
//    private String mUserID;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_im_demo);
//
//        btn_im_send_c2c = findViewById(R.id.btn_im_send_c2c);
//        btn_im_send_c2c.setOnClickListener(this);
//
//        btn_im_refresh = findViewById(R.id.btn_im_refresh);
//        btn_im_refresh.setOnClickListener(this);
//
//        btn_im_send_img = findViewById(R.id.btn_im_send_img);
//        btn_im_send_img.setOnClickListener(this);
//
//        tv_back = findViewById(R.id.tv_back);
//        tv_back.setOnClickListener(this);
//
//        et_input = findViewById(R.id.et_input);
//
//        msgAdapter = new MsgAdapter();
//        rv_msg = findViewById(R.id.rv_msg);
//        rv_msg.setLayoutManager(new LinearLayoutManager(this));
//        rv_msg.setAdapter(msgAdapter);
//
//        rvMsgList = new ArrayList<>();
//
//        mUserID = IMUtil.username;
//        receiver = IMUtil.to;
//
//        // 消息接收
//        receiveAdvancedMsg();
//
//        msgAdapter.setOnItemClickListener(this);
//
//        IMUtil.getHistoryPersonalMsg(receiver, v2TIMMessages -> {
//            for (int i = 0; i < v2TIMMessages.size(); i++) {
//                Log.d("QWER", "getC2CHistory: " + v2TIMMessages.get(i).getTextElem().getText());
//            }
//        });
//    }
//
//    /**
//     * 接收消息
//     */
//    public void receiveAdvancedMsg() {
//        V2TIMManager.getMessageManager().addAdvancedMsgListener(new V2TIMAdvancedMsgListener() {
//            @Override
//            public void onRecvNewMessage(V2TIMMessage msg) {
//                super.onRecvNewMessage(msg);
//                // TODO 解析
////                IMUtil.decodeMsg(msg);
//                MsgBean msgBean = new MsgBean();
//                msgBean.setTextContent(msg.getTextElem().getText());
//                msgBean.setMsgId(msg.getMsgID());
//                msgBean.setType(1);
//                msgBean.setUserName(msg.getUserID());
//                rvMsgList.add(msgBean);
//                refreshData();
//
//                IMUtil.sendHasRead(mUserID);
//            }
//
//            /**
//             * 发送方感知消息已读
//             * @param receiptList
//             */
//            @Override
//            public void onRecvC2CReadReceipt(List<V2TIMMessageReceipt> receiptList) {
//                // 消息已读
//                super.onRecvC2CReadReceipt(receiptList);
//                // 由于接收方一次性可能会收到多个已读回执，所以这里采用了数组的回调形式
//                for (V2TIMMessageReceipt v2TIMMessageReceipt : receiptList) {
//                    // 消息接收者 receiver
//                    String userID = v2TIMMessageReceipt.getUserID();
//                    // 已读回执时间，聊天窗口中时间戳小于或等于 timestamp 的消息都可以被认为已读
//                    long timestamp = v2TIMMessageReceipt.getTimestamp();
//                }
//            }
//
//            /**
//             * 消息撤回
//             * @param msgID 撤回的消息ID
//             */
//            @Override
//            public void onRecvMessageRevoked(String msgID) {
//                super.onRecvMessageRevoked(msgID);
//                for (int i = 0; i < rvMsgList.size(); i++) {
//                    if (rvMsgList.get(i).getMsgId().equals(msgID)) {
//                        rvMsgList.get(i).setTextContent("消息已经被撤回了嘻嘻嘻");
//                    }
//                }
//                refreshData();
//            }
//        });
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_im_send_c2c:
//                sendMsg();
//                break;
//            case R.id.btn_im_refresh:
//                refreshData();
//                break;
//            case R.id.tv_back:
//                finish();
//            default:
//                break;
//        }
//    }
//
//    private V2TIMMessage sendMsg;
//    private String receiver;
//
//    private void sendMsg() {
//        if (et_input.getText().toString().isEmpty()) {
//            Toast.makeText(this, "text is empty", Toast.LENGTH_SHORT).show();
//        } else {
//            sendMsg = IMUtil.createAdvancedMsg(et_input.getText().toString().trim(), receiver);
//            et_input.getText().clear();
//            MsgBean msgBean = new MsgBean();
//            msgBean.setTextContent(sendMsg.getTextElem().getText());
//            msgBean.setType(2);
//            msgBean.setMsgId(sendMsg.getMsgID());
//            msgBean.setUserName(sendMsg.getUserID());
//            rvMsgList.add(msgBean);
//            refreshData();
//
//        }
//    }
//
//    // 刷新
//    private void refreshData() {
//        msgAdapter.setNewData(rvMsgList);
//        msgAdapter.notifyDataSetChanged();
//    }
//
//
//    @Override
//    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        IMUtil.revokeMessage(sendMsg);
//    }
//
//
//}