package liyihuan.app.android.androidpractice.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import liyihuan.app.android.androidpractice.R;


public class PermissionActivity extends AppCompatActivity {


//    Manifest.permission.READ_PHONE_STATE,  //读取电话状态
//    Manifest.permission.READ_EXTERNAL_STORAGE,  //写外部存储
//    Manifest.permission.WRITE_EXTERNAL_STORAGE, //读外部存储
//    Manifest.permission.ACCESS_FINE_LOCATION//位置权限获取

    private String[] permissionStr = new String[] {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

//        // 请求权限
//        ActivityCompat.requestPermissions(Activity activity, String[] permissions, int requestCode)
        findViewById(R.id.get_permission).setOnClickListener( v->{
            ActivityCompat.requestPermissions(this,permissionStr,1);
        });

//        有权限: PackageManager.PERMISSION_GRANTED = 0
//        无权限: PackageManager.PERMISSION_DENIED = 1

//        检查权限ContextCompat.checkSelfPermission(context,permission)
        findViewById(R.id.check_permission).setOnClickListener( v->{
            for (String str :permissionStr) {
                ContextCompat.checkSelfPermission(this, str);
                Log.d("QWER", "onCreate: " + ContextCompat.checkSelfPermission(this, str));
            }

        });

        findViewById(R.id.app_permission).setOnClickListener( v->{
            openAppDetails();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("备份通讯录需要访问 “通讯录” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

}