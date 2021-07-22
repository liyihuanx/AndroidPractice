package liyihuan.app.android.androidpractice.recyclerview

import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import liyihuan.app.android.androidpractice.R

/**
 * @author created by liyihuanx
 * @date 2021/7/21
 * description: 类的描述
 */
class SectionAdapter : BaseSectionQuickAdapter<SectionBean, BaseViewHolder>(R.layout.item_section, R.layout.header_section, ArrayList()) {
    override fun convert(helper: BaseViewHolder, item: SectionBean) {
        helper.setText(R.id.tvUserInfo, "${item.t.userID} + ${item.t.username}")
    }

    override fun convertHead(helper: BaseViewHolder, item: SectionBean) {
        helper.setText(R.id.tvHeader, item.header)
    }
}