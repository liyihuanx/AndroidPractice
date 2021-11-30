package liyihuan.app.android.androidpractice.interval

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_interval.*
import liyihuan.app.android.androidpractice.R
import java.util.concurrent.TimeUnit

class IntervalActivity : AppCompatActivity() {
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