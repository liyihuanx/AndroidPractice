package liyihuan.app.android.androidpractice.touchevent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dispatch_touch_event.*
import liyihuan.app.android.androidpractice.R

class DispatchTouchEventActivity : AppCompatActivity() {

    private var data = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch_touch_event)
        repeat(50) {
            data.add("----- $it -----")
        }
        val adapter = MyPagerAdapter(this, data)
        vp_bad.adapter = adapter
    }
}