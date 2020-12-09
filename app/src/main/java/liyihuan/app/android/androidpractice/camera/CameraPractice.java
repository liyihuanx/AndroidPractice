package liyihuan.app.android.androidpractice.camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import liyihuan.app.android.androidpractice.R;

public class CameraPractice extends AppCompatActivity implements View.OnClickListener {
    Button btn_Camera,btn_Album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_practice);

        initView();
        permissionCheck();
    }

    private void initView() {
        btn_Camera = findViewById(R.id.btn_Camera);
        btn_Album = findViewById(R.id.btn_Album);
        btn_Camera.setOnClickListener(this);
        btn_Album.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_Camera:
                CameraUtils.openCamera();
                break;
            case R.id.btn_Album:
                CameraUtils.openAlbum();
                break;
        }
    }



    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String[] permissionManifest = {
            Manifest.permission.CAMERA,//照相机权限
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @SuppressLint("WrongConstant")
    public void permissionCheck() {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (String permission : permissionManifest) {
            if (PermissionChecker.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionCheck = PackageManager.PERMISSION_DENIED;
            }
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissionManifest, PERMISSION_REQUEST_CODE);
        }
    }
}