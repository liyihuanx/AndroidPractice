package liyihuan.app.android.androidpractice.chat.viewholder

import liyihuan.app.android.androidpractice.chat.bean.IMMessage
import liyihuan.app.android.androidpractice.chat.bean.TextMsgBean

/**
 * @ClassName: ViewHolderHelper
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:28
 */
object ViewHolderHelper {



    // 注册所有类型的viewHolder
    init {
        registerViewHolder(TextMsgBean::class.java, TextViewHolder::class.java)

    }

    private fun registerViewHolder(bean: Class<out IMMessage>, viewHolder: Class<out BaseMsgViewHolder<*>>) {

    }


    @JvmStatic
    fun getViewHolder() {

    }

    @JvmStatic
    fun getAllViewHolderType() {

    }

}