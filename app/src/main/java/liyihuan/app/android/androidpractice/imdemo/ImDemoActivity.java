package liyihuan.app.android.androidpractice.imdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danmu.danmu.Util;
import com.google.gson.Gson;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.conversation.Msg;
import com.tencent.imsdk.v2.V2TIMAdvancedMsgListener;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMCustomElem;
import com.tencent.imsdk.v2.V2TIMDownloadCallback;
import com.tencent.imsdk.v2.V2TIMElem;
import com.tencent.imsdk.v2.V2TIMFaceElem;
import com.tencent.imsdk.v2.V2TIMFileElem;
import com.tencent.imsdk.v2.V2TIMGroupChangeInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberChangeInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMGroupTipsElem;
import com.tencent.imsdk.v2.V2TIMImageElem;
import com.tencent.imsdk.v2.V2TIMLocationElem;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMMessageManager;
import com.tencent.imsdk.v2.V2TIMMessageReceipt;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;
import com.tencent.imsdk.v2.V2TIMSendCallback;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.imsdk.v2.V2TIMSoundElem;
import com.tencent.imsdk.v2.V2TIMTextElem;
import com.tencent.imsdk.v2.V2TIMUserInfo;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.imsdk.v2.V2TIMVideoElem;
import com.tencent.openqq.protocol.imsdk.msg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.androidpractice.imdemo.utils.IMUtil;

public class ImDemoActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_im_send_c2c,btn_im_refresh,btn_im_send_img;
    private RecyclerView rv_msg;
    private MsgAdapter msgAdapter;
    private List<MsgBean> rvMsgList;
    private EditText et_input;

    private String mUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_demo);

        btn_im_send_c2c = findViewById(R.id.btn_im_send_c2c);
        btn_im_send_c2c.setOnClickListener(this);

        btn_im_refresh = findViewById(R.id.btn_im_refresh);
        btn_im_refresh.setOnClickListener(this);

        btn_im_send_img = findViewById(R.id.btn_im_send_img);
        btn_im_send_img.setOnClickListener(this);

        et_input = findViewById(R.id.et_input);

        msgAdapter = new MsgAdapter();
        rv_msg = findViewById(R.id.rv_msg);
        rv_msg.setLayoutManager(new LinearLayoutManager(this));
        rv_msg.setAdapter(msgAdapter);

        rvMsgList = new ArrayList<>();

        Intent intent = getIntent();

        mUserID = intent.getStringExtra("phone");
        // IM初始化
        IMUtil.initIM(this);
        // 登录账号
        IMUtil.loginIM(mUserID);
        // 消息接收
        IMUtil.receiveAdvancedMsg();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_im_send_c2c:
                sendMsg();
                break;
            case R.id.btn_im_refresh:
                refreshData();
                break;
            default:
                break;
        }
    }

    private void sendMsg() {
        if (et_input.getText().toString().isEmpty()) {
            Toast.makeText(this, "text is empty", Toast.LENGTH_SHORT).show();
        } else {
            V2TIMMessage sendMsg = IMUtil.createAdvancedMsg(et_input.getText().toString().trim(), "liyihuanx");
            et_input.getText().clear();
            MsgBean msgBean = new MsgBean();
            msgBean.setTextContent(sendMsg.getTextElem().getText());
            msgBean.setType(2);
            msgBean.setUserName(sendMsg.getUserID());
            rvMsgList.add(msgBean);
            refreshData();

        }
    }

    // 刷新
    private void refreshData() {
        msgAdapter.setNewData(rvMsgList);
        msgAdapter.notifyDataSetChanged();
    }

}