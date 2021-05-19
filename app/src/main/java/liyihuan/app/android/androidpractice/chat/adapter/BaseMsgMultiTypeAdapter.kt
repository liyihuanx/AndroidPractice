package liyihuan.app.android.androidpractice.chat.adapter

import android.util.Log
import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.chat.bean.IMMessage
import liyihuan.app.android.androidpractice.chat.viewholder.BaseMsgViewHolder
import liyihuan.app.android.androidpractice.chat.viewholder.RecyclerViewHolder

/**
 * @ClassName: BaseMsgMultiTypeAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:14
 */
abstract class BaseMsgMultiTypeAdapter(data: List<IMMessage<*>>) : BaseQuickAdapter<IMMessage<*>, BaseViewHolder>(data) {


    // 存放<type,holder.class>
    private var holderClasses: SparseArray<Class<out RecyclerViewHolder<*>>> = SparseArray()

    // 存放<type,<msg的id,holder的实例>>  ---> holder不能复用
    private var multiTypeViewHolders: MutableMap<Int, MutableMap<String, RecyclerViewHolder<Any>>> = HashMap()

    // 存放<Msg消息类型，holder的实例>  ---> HolderHelper里面有？？


    /**
     * 创建布局
     */
    override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return createBaseViewHolder(parent, getLayoutId())
    }

    /**
     *
     */
    private fun getLayoutId(): Int {
        return R.layout.chat_message_item
    }

    /**
     * 把type,layout,holder.class 建立联系并且保存在数组里
     *
     */
    fun addHolderInfo(holderType: Int, holder: Class<out BaseMsgViewHolder<*>>) {

        holderClasses.put(holderType, holder)
        multiTypeViewHolders[holderType] = HashMap()
    }

    /**
     * 用做key
     */
    private fun getItemMsgType(item: IMMessage<*>): String {
        return "-1"
    }

    /**
     * 每个item执行一次
     */
    override fun convert(helper: BaseViewHolder, item: IMMessage<*>) {
        val itemMsgID = getItemMsgType(item)
        // item的类型 --> 自己设置的吗？？
        val itemViewType = helper.itemViewType
        Log.d("QWER", "BaseMsgMultiTypeAdapter: $itemViewType")
        var holder = multiTypeViewHolders[itemViewType]?.get(itemMsgID)
        // 如果holder的实例为null
        if (holder == null) {
            // 通过反射，初始化实例
            try {
                val cls = holderClasses[itemViewType]
                // 第一个显式的构造函数
                if (cls.declaredConstructors.isNotEmpty()){
                    val c = cls.declaredConstructors[0]
                    c.isAccessible = true
                    holder = c.newInstance() as RecyclerViewHolder<Any>?
                    multiTypeViewHolders[itemViewType]?.put(itemMsgID, holder!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        // convert
        holder?.convert(helper, item, helper.adapterPosition)
    }
}