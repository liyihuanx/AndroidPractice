//package liyihuan.app.android.androidpractice.imdemo.login;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//
//import liyihuan.app.android.androidpractice.R;
//import liyihuan.app.android.androidpractice.imdemo.conversation.ConversationListActivity;
//import liyihuan.app.android.androidpractice.imdemo.imchat.ImDemoActivity;
//import liyihuan.app.android.androidpractice.imdemo.utils.IMUtil;
//
//public class ImLoginActivity extends AppCompatActivity {
//    EditText phone;
//    EditText password;
//    Button btnLogin,btnlyh,btncyl;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_im_login);
//        phone = findViewById(R.id.et_phone);
//        password = findViewById(R.id.et_password);
//
//        // IM初始化
//        IMUtil.initIM(this);
//
//        btnLogin = findViewById(R.id.btn_login);
//        btnLogin.setOnClickListener(v -> {
//            Intent intent = new Intent(this, ConversationListActivity.class);
//            IMUtil.username = phone.getText().toString().trim();
//            IMUtil.to = password.getText().toString().trim();
//            // 登录账号
//            IMUtil.loginIM(IMUtil.username);
//            startActivity(intent);
//        });
//
//        btnlyh = findViewById(R.id.btn_lyh);
//        btnlyh.setOnClickListener(v -> {
//            Intent intent = new Intent(this, ConversationListActivity.class);
//            IMUtil.username = "liyihuanx";
//            IMUtil.to = "chenyalunx";
//            // 登录账号
//            IMUtil.loginIM(IMUtil.username);
//            startActivity(intent);
//        });
//
//        btncyl = findViewById(R.id.btn_cyl);
//        btncyl.setOnClickListener(v -> {
//            Intent intent = new Intent(this, ConversationListActivity.class);
//            IMUtil.username = "chenyalunx";
//            IMUtil.to = "liyihuanx";
//            // 登录账号
//            IMUtil.loginIM(IMUtil.username);
//            startActivity(intent);
//        });
//    }
//}