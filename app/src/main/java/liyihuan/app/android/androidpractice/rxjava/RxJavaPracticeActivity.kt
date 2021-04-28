package liyihuan.app.android.androidpractice.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_rx_java_practice.*
import liyihuan.app.android.androidpractice.R

class RxJavaPracticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java_practice)
        BtnTimeCount.setOnClickListener {
            RxJavaHelper.bindData()
        }
    }
}