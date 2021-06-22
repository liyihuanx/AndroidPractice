package liyihuan.app.android.androidpractice.forresult

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import liyihuan.app.android.androidpractice.R

/**
 * @ClassName: ForResultFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/30 22:51
 */

class ForResultFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootview = inflater.inflate(R.layout.fragment_for_result, null)

        val findViewById = rootview.findViewById<Button>(R.id.BtnForResult3)
        findViewById.setOnClickListener {
            val intent = Intent(context, ForResult2Activity::class.java)
            startActivityForResult(intent, 100)
        }
        return rootview
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            100 -> {
                Log.d("QWER", "Fragment - onActivityResult: ")
            }
        }

    }
}