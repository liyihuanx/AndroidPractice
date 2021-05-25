package liyihuan.app.android.androidpractice.diffType

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.android.synthetic.main.item_type_one.view.*
import liyihuan.app.android.androidpractice.R
import java.io.Serializable
import java.util.ArrayList

/**
 * @author created by liyihuanx
 * @date 2021/3/15
 * description: 类的描述
 */

class DiffAdapter(data: MutableList<CommonItemModel<Any>>) : BaseMultiItemQuickAdapter<CommonItemModel<Any>, BaseViewHolder>(data) {


    companion object {
        const val TYPE_BIG = 0
        const val TYPE_MIDDLE = 1
        const val TYPE_SMALL = 2
    }

    init {
        addItemType(TYPE_BIG, R.layout.item_type_one)
        addItemType(TYPE_MIDDLE, R.layout.item_type_one)
        addItemType(TYPE_SMALL, R.layout.item_type_two)
    }

    override fun convert(helper: BaseViewHolder, item: CommonItemModel<Any>) {
        when (item.itemType) {
            TYPE_BIG -> {
                helper.setText(R.id.name, item.data.toString())
            }
            TYPE_SMALL -> {
                if (item.data is ArrayList<*>) {
                    item.data.forEachIndexed { index, item ->
                        if (index == 0) {
                            helper.setText(R.id.name, item.toString())
                        } else {
                            helper.setText(R.id.name2, item.toString())
                        }
                    }
                }
            }
        }

    }
}


class CommonItemModel<T>(val data: T, private val itemViewType: Int = 1) : Serializable, MultiItemEntity {

    override fun getItemType(): Int {
        return itemViewType
    }
}