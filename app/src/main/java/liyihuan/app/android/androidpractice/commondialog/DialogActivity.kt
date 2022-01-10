package liyihuan.app.android.androidpractice.commondialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_dialog.*
import liyihuan.app.android.androidpractice.R

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        TLiveDataBus.with<String>("name").setStickyData("11111")

        btnOpenDialog.setOnClickListener {
            TLiveDataBus.with<String>("name").observe(this, Observer {
                Log.d("QWER", "普通事件: $it")
            })

            TLiveDataBus.with<String>("name").observerSticky(this, true, Observer {
                Log.d("QWER", "粘性事件: $it")
            })
        }
    }
}