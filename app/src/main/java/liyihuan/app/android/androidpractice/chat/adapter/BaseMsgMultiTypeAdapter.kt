package liyihuan.app.android.androidpractice.chat.adapter

import android.util.SparseArray
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import liyihuan.app.android.androidpractice.chat.bean.IMMessage
import liyihuan.app.android.androidpractice.chat.viewholder.BaseMsgViewHolder

/**
 * @ClassName: BaseMsgMultiTypeAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:14
 */
abstract class BaseMsgMultiTypeAdapter(data: List<IMMessage>) : BaseQuickAdapter<IMMessage, BaseViewHolder>(data) {

    // 存放布局<holderType,layoutID>
    private val layouts: SparseArray<Int> = SparseArray()

    // 存放<type,holder.class>

    // 存放<type,holder的实例>

    /**
     * 创建布局
     */
    override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return createBaseViewHolder(parent, getLayoutId(viewType))
    }

    /**
     * 根据ChatAdapter给Holder的类型 获取相应的布局
     */
    private fun getLayoutId(viewType: Int): Int {
        return layouts[viewType]
    }

    /**
     * 把type,layout,holder.class 建立联系并且保存在数组里
     *
     */
    fun addHolderInfo(holderType: Int, layout: Int, holder: Class<out BaseMsgViewHolder<*>>) {

        layouts.put(holderType,layout)
    }


    override fun convert(helper: BaseViewHolder, item: IMMessage?) {

    }

}