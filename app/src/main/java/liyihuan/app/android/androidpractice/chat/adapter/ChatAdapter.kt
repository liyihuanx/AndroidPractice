package liyihuan.app.android.androidpractice.chat.adapter

import liyihuan.app.android.androidpractice.chat.viewholder.BaseMsgViewHolder
import liyihuan.app.android.androidpractice.chat.viewholder.ViewHolderHelper

/**
 * @ClassName: ChatAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:09
 */
class ChatAdapter : BaseMsgMultiTypeAdapter(arrayListOf()) {

    private var holder2ViewType: HashMap<Class<out BaseMsgViewHolder<*>>, Int> = HashMap()


    init {
        // 获取所有类型
        val allViewHolderType = ViewHolderHelper.getAllViewHolderType()
        // 给holder的记数用作类型
        var holderType = 0
        // 遍历
        for (holder in allViewHolderType) {
            holderType++
            holder2ViewType[holder] = holderType
            addHolderInfo(holderType,  holder)
        }
    }

    override fun getDefItemViewType(position: Int): Int {
        return holder2ViewType[ViewHolderHelper.getViewHolder(getItem(position))]!!
    }
}