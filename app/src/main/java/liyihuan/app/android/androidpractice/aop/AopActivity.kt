package liyihuan.app.android.androidpractice.aop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_aop.*
import liyihuan.app.android.androidpractice.R

class AopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aop)

        btnAop.setOnClickListener {
            aopLog()
        }
    }


    @LogBehavior("AOP_LOG")
    private fun aopLog(): String {
        return "方法的返回值"
    }
}