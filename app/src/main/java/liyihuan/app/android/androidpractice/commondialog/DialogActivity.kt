package liyihuan.app.android.androidpractice.commondialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_dialog.*
import liyihuan.app.android.androidpractice.R

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        btnOpenDialog.setOnClickListener {
            BaseCommonDialog.Builder
                    .setContent("111111")
                    .setBtnCancel("Cancel")
                    .setBtnConfirm("Ok")
                    .setCoverLayout(R.layout.dialog_cover)
                    .setListener(object : CommonDialogListener {
                        override fun onCancel(dialog: DialogFragment, any: Any) {
                            Toast.makeText(dialog.context, "onCancel", Toast.LENGTH_SHORT).show()
                        }

                        override fun onConfirm(dialog: DialogFragment, any: Any) {
                            Toast.makeText(dialog.context, "onConfirm", Toast.LENGTH_SHORT).show()
                        }
                    })
                    .build().show(supportFragmentManager,"")

        }
    }
}