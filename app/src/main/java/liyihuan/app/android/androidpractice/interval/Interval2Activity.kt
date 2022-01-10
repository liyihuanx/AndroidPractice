package liyihuan.app.android.androidpractice.interval

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_interval.*
import liyihuan.app.android.androidpractice.R
import java.util.concurrent.TimeUnit

/**
 * @author L.Y.
 * @date 2021/12/01 10:16
 * @Description
 */
class Interval2Activity : AppCompatActivity() {
    private val interval by lazy { LAIntervalUtils(2, TimeUnit.SECONDS,0).life(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interval)

        btnInterval.setOnClickListener {
            interval.subscribe {
                Log.d("QWER", "subscribe: $it")
            }.start {
                Log.d("QWER", "start: $it")
            }
        }
    }
}