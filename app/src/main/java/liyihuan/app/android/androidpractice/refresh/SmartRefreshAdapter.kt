package liyihuan.app.android.androidpractice.refresh

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import liyihuan.app.android.androidpractice.R

/**
 * @author created by liyihuanx
 * @date 2021/5/10
 * description: 类的描述
 */

class SmartRefreshAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_smart_refresh) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tvItem, item)
    }
}