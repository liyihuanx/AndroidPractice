package liyihuan.app.android.androidpractice.forresult

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_for_result2.*
import liyihuan.app.android.androidpractice.R

class ForResult2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_result2)


        BtnForResult2.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}