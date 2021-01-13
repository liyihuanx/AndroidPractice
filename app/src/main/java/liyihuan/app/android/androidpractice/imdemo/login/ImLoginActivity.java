package liyihuan.app.android.androidpractice.imdemo.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.androidpractice.imdemo.ImDemoActivity;

public class ImLoginActivity extends AppCompatActivity {
    EditText phone;
    EditText password;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_login);
        phone = findViewById(R.id.et_phone);
        password = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, ImDemoActivity.class);
            intent.putExtra("phone",phone.getText().toString().trim());
            intent.putExtra("password",password.getText().toString().trim());
            startActivity(intent);
        });
    }
}