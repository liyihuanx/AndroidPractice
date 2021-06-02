package liyihuan.app.android.androidpractice.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_album_camera.*
import liyihuan.app.android.androidpractice.R

class AlbumCameraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_camera)
        btnOpen.setOnClickListener {
            PictureHelper(this).showOperate(object : PicCallback{
                override fun getPicSucc(imgList: MutableList<String>) {
                    // 获取到图片
                }
            })
        }

    }
}