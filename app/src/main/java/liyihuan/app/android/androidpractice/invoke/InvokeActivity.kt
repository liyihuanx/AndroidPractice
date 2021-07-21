package liyihuan.app.android.androidpractice.invoke

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_invoke.*
import liyihuan.app.android.androidpractice.R
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class InvokeActivity : AppCompatActivity() {

    private var obj: ILocation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoke)

        Log.d("QWER", "onCreate: ")

        btn.setOnClickListener {
//            val classLoader = ILocation::class.java.classLoader
//            Proxy.newProxyInstance(classLoader, arrayOf(ILocation::class.java), MyInvocationHandler(obj))
            tv.text = "6666"
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("QWER", "onResume: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("QWER", "onDestroy: ")
    }
}