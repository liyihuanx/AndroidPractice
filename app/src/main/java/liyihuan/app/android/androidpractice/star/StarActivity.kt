package liyihuan.app.android.androidpractice.star

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*
import liyihuan.app.android.androidpractice.MRouterConfig
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.mrouter_api.RouterManager

class StarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        btn_start.setOnClickListener {
            RouterManager.getInstance()
                    .build(MRouterConfig.module_app.MainActivity)
                    .withString("name", "liyihuanx")
                    .navigation(this)


        }


    }
}