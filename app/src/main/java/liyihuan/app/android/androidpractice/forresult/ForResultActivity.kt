package liyihuan.app.android.androidpractice.forresult

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_for_result.*
import liyihuan.app.android.androidpractice.R

class ForResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_result)

        BtnForResult.setOnClickListener {
            val intent = Intent(this, ForResult2Activity::class.java)
            startActivityForResult(intent, 100)
        }
        val forResultFragment = ForResultFragment()

        supportFragmentManager
                .beginTransaction()
                .add(R.id.flViewGroup, forResultFragment, "").show(forResultFragment)
                .commitNowAllowingStateLoss()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            100 -> {
                Log.d("QWER", "Activity - onActivityResult: ")
            }
        }

    }

}