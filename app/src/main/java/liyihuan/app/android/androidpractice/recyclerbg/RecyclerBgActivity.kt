package liyihuan.app.android.androidpractice.recyclerbg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_recycler_bg.*
import liyihuan.app.android.androidpractice.R

class RecyclerBgActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_bg)


        recyclerBg.layoutManager = ScollLinearLayoutManager(this)
        recyclerBg.adapter = SplashAdapter(this)
        recyclerBg.smoothScrollToPosition(Int.MAX_VALUE / 2)


    }
}