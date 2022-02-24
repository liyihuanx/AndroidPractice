package liyihuan.app.android.androidpractice.recyclerviewbug

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import liyihuan.app.android.androidpractice.R

/**
 * @author liyihuan
 * @date 2022/01/19
 * @Description
 */
class RvBugAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_bad) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.item_context, item)
    }
}