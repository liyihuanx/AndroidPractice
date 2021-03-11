package liyihuan.app.android.androidpractice.glodgame

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import liyihuan.app.android.androidpractice.R

/**
 * @ClassName: GoldDialog
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/3/9 22:04
 */
class GoldDialog : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_gold, null)
        val dialog = Dialog(activity!!, R.style.ThemeOverlay_AppCompat_Dialog)
        dialog.setContentView(view)
        // 设置宽度为屏宽、靠近屏幕底部。
        val window = dialog.window
        val wlp = window!!.attributes
        wlp.gravity = Gravity.BOTTOM
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT
        window!!.attributes = wlp
        isCancelable = true


        val ivgold : ImageView = view.findViewById(R.id.iv_gold)
        val viewputgold : View = view.findViewById(R.id.view_put_gold)
        val llroot : RelativeLayout = view.findViewById(R.id.ll_root)
        val view_point : View = view.findViewById(R.id.view_point)
        ivgold.setOnClickListener {

            var outLocation = IntArray(2)
            viewputgold.getLocationInWindow(outLocation)

            Log.d("QWER", "outLocationX: ${outLocation[0]}")
            Log.d("QWER", "outLocationY: ${outLocation[1]}")


            var rootLocation = IntArray(2)
            llroot.getLocationInWindow(rootLocation)
            Log.d("QWER", "rootLocationX: ${rootLocation[0]}")
            Log.d("QWER", "rootLocationY: ${rootLocation[1]}")

            view_point.translationX = outLocation[0].toFloat()
            view_point.translationY = outLocation[1].toFloat()


        }

        return dialog
    }

}