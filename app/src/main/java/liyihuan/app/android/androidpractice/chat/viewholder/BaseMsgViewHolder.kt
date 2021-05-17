package liyihuan.app.android.androidpractice.chat.viewholder

import com.chad.library.adapter.base.BaseViewHolder
import liyihuan.app.android.androidpractice.chat.adapter.ChatAdapter
import liyihuan.app.android.androidpractice.chat.bean.IMMessage

/**
 * @ClassName: BaseMsgViewHolder
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:03
 */
abstract class BaseMsgViewHolder<K : IMMessage>(adapter: ChatAdapter? = null) : RecyclerViewHolder<ChatAdapter, BaseViewHolder, K>(adapter) {

    override fun convert(holder: BaseViewHolder, data: K, position: Int) {

    }

}