package liyihuan.app.android.androidpractice.dialogfragment

import kotlinx.android.synthetic.main.dialog_fragment_test.*
import liyihuan.app.android.androidpractice.R

/**
 * @author liyihuan
 * @date 2022/01/19
 * @Description
 */
class TestDialogFragment : BaseDialogFragment() {
    override fun getViewLayoutId(): Int {
        return R.layout.dialog_fragment_test
    }

    override fun initView() {
        tvDialogFragment.text = "TestDialogFragment2"
        btncancel.setOnClickListener {
            dismiss()
        }

        btncomfirm.setOnClickListener {
            DialogHelper.dialog2.show(parentFragmentManager)
        }
    }
}