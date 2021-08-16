package liyihuan.app.android.androidpractice.commondialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_base_common.*
import liyihuan.app.android.androidpractice.R

/**
 * @ClassName: BaseCommonDialog
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/8/16 20:28
 */
open class BaseCommonDialog : DialogFragment() {

    private var clickListener: CommonDialogListener? = null
    private var coverLayout: Int = -1
    private var rootView: View? = null
    var childView: View? = null

    private var coverClickListener: CoverLayoutListener? = null


    companion object {
        private fun newInstance(content: String, btnConfirm: String, btnCancel: String): BaseCommonDialog {
            val args = Bundle()
            val fragment = BaseCommonDialog()
            args.putString("content", content)
            args.putString("btnConfirm", content)
            args.putString("btnCancel", content)
            fragment.arguments = args
            return fragment
        }
    }

    private fun setOnClickListener(clickListener: CommonDialogListener?) {
        this.clickListener = clickListener
    }

    private fun setCoverLayout(coverLayout: Int) {
        this.coverLayout = coverLayout
    }


    private fun setCoverClickLayout(coverClickListener: CoverLayoutListener?) {
        this.coverClickListener = coverClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //取消系统对dialog样式上的干扰，防止dialog宽度无法全屏
        setStyle(STYLE_NO_FRAME, R.style.dialogFullScreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.dialog_base_common, container, false)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments.let {
            tvDialogContent.text = it?.getString("content")
            btnCancel.text = it?.getString("btnCancel")
            btnConfirm.text = it?.getString("btnConfirm")
        }

        if (coverLayout != -1 && flCoverLayout.getChildAt(0) == null) {
            childView = LayoutInflater.from(context).inflate(coverLayout, flCoverLayout, false)
//            (childView as ViewGroup).forEach {
//                it.setOnClickListener {
//                    coverClickListener.onCoverItemClick(this)
//                }
//            }
            flCoverLayout.addView(childView, 0)
            flCoverLayout.visibility = View.VISIBLE
        }


        btnCancel.setOnClickListener {
            clickListener?.onCancel(this)
        }

        btnConfirm.setOnClickListener {
            clickListener?.onConfirm(this)
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        clickListener?.onDismiss(this)
    }


    object Builder {
        private var content: String = "默认内容"
        private var btnConfirm: String = "确定"
        private var btnCancel: String = "取消"
        private var clickListener: CommonDialogListener? = null
        private var coverClickListener: CoverLayoutListener? = null

        // 布局
        private var coverLayout = -1


        fun setContent(content: String): Builder {
            this.content = content
            return this
        }

        fun setBtnConfirm(btnConfirm: String) = this.also {
            it.btnConfirm = btnConfirm
        }

        fun setBtnCancel(btnCancel: String) = this.also {
            it.btnCancel = btnCancel
        }

        fun setListener(clickListener: CommonDialogListener) = this.also {
            it.clickListener = clickListener
        }

        fun setCoverLayout(layoutId: Int) = this.also {
            it.coverLayout = layoutId
        }

        fun setCoverLListener(coverClickListener: CoverLayoutListener) = this.also {
            it.coverClickListener = coverClickListener
        }

        fun build() = newInstance(content, btnConfirm, btnCancel).also {
            it.setOnClickListener(this.clickListener)
            it.setCoverLayout(this.coverLayout)
            it.setCoverClickLayout(this.coverClickListener)
        }

    }


}

open interface CommonDialogListener {
    fun onConfirm(dialog: DialogFragment, any: Any = Any()) {}
    fun onCancel(dialog: DialogFragment, any: Any = Any()) {}
    fun onDismiss(dialog: DialogFragment, any: Any = Any()) {}
}

open interface CoverLayoutListener {
    fun onCoverItemClick(dialog: DialogFragment, any: Any = Any()) {}

}

