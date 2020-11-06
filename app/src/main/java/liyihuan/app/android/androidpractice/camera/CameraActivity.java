package liyihuan.app.android.androidpractice.camera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import liyihuan.app.android.androidpractice.R;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_camera;
    Button btn_album;
    ImageView iv_picture;
    /**
     * 请求不同状态的状态码
     */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int SELECT_PIC_KITKAT = 3;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    private final String TAG = getClass().getSimpleName();
    private Button mTakePhoto, mChooseFromAlbum;
    private ImageView mPicture;
    private static final String PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int REQUEST_PERMISSION_CODE = 267;
    private static final int TAKE_PHOTO = 189;
    private static final int CHOOSE_PHOTO = 385;
    private static final String FILE_PROVIDER_AUTHORITY = "cn.fonxnickel.officialcamerademo.fileprovider";
    private Uri mImageUri, mImageUriFromFile;
    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        btn_camera = findViewById(R.id.btn_Camera);
        btn_camera.setOnClickListener(this);
        btn_album = findViewById(R.id.btn_Album);
        btn_album.setOnClickListener(this);

        iv_picture = findViewById(R.id.iv_picture);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_Camera:

                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//用来打开相机的Intent
                //这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
                if(takePhotoIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(takePhotoIntent,CAMERA_REQUEST_CODE);//启动相机
                }
                break;
            case R.id.btn_Album:
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, IMAGE_REQUEST_CODE);//打开相册

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case CAMERA_REQUEST_CODE:
                    /*缩略图信息是储存在返回的intent中的Bundle中的，
                     * 对应Bundle中的键为data，因此从Intent中取出
                     * Bundle再根据data取出来Bitmap即可*/
                    Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    iv_picture.setImageBitmap(bitmap);
                    break;
                case IMAGE_REQUEST_CODE:
                    break;
            }
        }
    }

    private void takePhoto(){
        //打开相机的Intent
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
        if(takePhotoIntent.resolveActivity(getPackageManager())!=null){
            File imageFile = createImageFile();//创建用来保存照片的文件
            if(imageFile!=null){
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    /*7.0以上要通过FileProvider将File转化为Uri*/
                    mImageUri = FileProvider.getUriForFile(this,FILE_PROVIDER_AUTHORITY,imageFile);
                }else {
                    /*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
                    mImageUri = Uri.fromFile(imageFile);
                }
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);//将用于输出的文件Uri传递给相机
                startActivityForResult(takePhotoIntent, CAMERA_REQUEST_CODE);//打开相机
            }
        }
    }
    /**
     * 创建用来存储图片的文件，以时间来命名就不会产生命名冲突
     * @return 创建的图片文件
     */
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName,".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }
}