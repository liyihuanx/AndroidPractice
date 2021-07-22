package liyihuan.app.android.androidpractice.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import liyihuan.app.android.androidpractice.R

class HalfActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_half)

    }

    override fun onResume() {
        super.onResume()
        Log.d("QWER", "HalfActivity: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("QWER", "HalfActivity: onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("QWER", "HalfActivity: onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("QWER", "HalfActivity: onDestroy")
    }
}