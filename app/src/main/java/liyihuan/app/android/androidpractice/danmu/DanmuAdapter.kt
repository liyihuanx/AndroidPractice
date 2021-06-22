package liyihuan.app.android.androidpractice.danmu

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import liyihuan.app.android.androidpractice.R

/**
 * @author created by liyihuanx
 * @date 2021/6/16
 * description: 类的描述
 */
class DanmuAdapter : BaseQuickAdapter<DanMuBean, BaseViewHolder>(R.layout.item_splash) {

    override fun convert(helper: BaseViewHolder, item: DanMuBean) {
        val itemdata: DanMuBean = data[helper.position % data.size]

        helper.setText(R.id.tvItem, "${itemdata.name} -- ${itemdata.content}")
    }


    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

}