package liyihuan.app.android.androidpractice.chat.viewholder

import liyihuan.app.android.androidpractice.chat.bean.IMMessage
import liyihuan.app.android.androidpractice.chat.bean.TextMsgBean
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.ArrayList

/**
 * @ClassName: ViewHolderHelper
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:28
 */
object ViewHolderHelper {


    private val viewHolderFactory: HashMap<Class<out IMMessage>, Class<out BaseMsgViewHolder<*>>> = HashMap<Class<out IMMessage?>, Class<out BaseMsgViewHolder<*>>>()

    // 注册所有类型的viewHolder
    init {
        registerViewHolder(TextMsgBean::class.java, TextViewHolder::class.java)
    }

    /**
     * 存放<IMessage的子类,BaseMsgViewHolder的子类>
     *     TextMsgBean.class,TextMsgViewHolder.class
     *
     */
    private fun registerViewHolder(bean: Class<out IMMessage>, viewHolder: Class<out BaseMsgViewHolder<*>>) {
        viewHolderFactory[bean] = viewHolder
    }


    /**
     * 根据类型返回
     */
    @JvmStatic
    fun getViewHolder(bean: Class<out IMMessage>): Class<out BaseMsgViewHolder<*>>? {
        return viewHolderFactory[bean]
    }


    /**
     * 获取所有类型的ViewHolder
     */
    @JvmStatic
    fun getAllViewHolderType(): ArrayList<Class<out BaseMsgViewHolder<*>>> {
        val holderList = ArrayList<Class<out BaseMsgViewHolder<*>>>()
        for (item in viewHolderFactory){
            holderList.add(item.value)
        }
        return holderList
    }

}