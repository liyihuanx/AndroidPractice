package liyihuan.app.android.androidpractice.chat.adapter

import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import liyihuan.app.android.androidpractice.chat.bean.IMMessage

/**
 * @ClassName: BaseMsgMultiTypeAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:14
 */
abstract class BaseMsgMultiTypeAdapter(data: List<IMMessage>) : BaseQuickAdapter<IMMessage, BaseViewHolder>(data) {

    override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return createBaseViewHolder(parent, getLayoutId(viewType))
    }

    private fun getLayoutId(viewType: Int): Int {
        return -1
    }


    override fun convert(helper: BaseViewHolder, item: IMMessage?) {

    }

}