package liyihuan.app.android.androidpractice.viewpager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_viewpager2.*
import liyihuan.app.android.androidpractice.R

class Viewpager2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager2)


        val pageImg = arrayListOf(R.drawable.bg_header,R.drawable.microphone1)

        val imageAdapter = ImageAdapter()
        imageAdapter.setNewData(pageImg)
        vp2.adapter = imageAdapter
    }
}