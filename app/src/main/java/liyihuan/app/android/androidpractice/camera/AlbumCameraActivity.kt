package liyihuan.app.android.androidpractice.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_album_camera.*
import liyihuan.app.android.androidpractice.R
import java.io.FileInputStream
import java.io.FileNotFoundException

class AlbumCameraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_camera)
        btnOpen.setOnClickListener {
            PictureHelper(this).showOperate(CropInfo(1, 1), object : PicCallback {
                override fun getPicSucc(imgList: MutableList<String>) {
                    // 获取到图片
                    Glide.with(this@AlbumCameraActivity)
                            .load(imgList[0])
                            .into(ivPicture)
                }
            })
        }

    }


    private fun getLocalBitmap(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val fis = FileInputStream(url)
            //把流转化为Bitmap图片
            bitmap = BitmapFactory.decodeStream(fis)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return bitmap
    }
}