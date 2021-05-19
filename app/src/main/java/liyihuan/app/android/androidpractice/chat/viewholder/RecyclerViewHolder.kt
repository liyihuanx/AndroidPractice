package liyihuan.app.android.androidpractice.chat.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import liyihuan.app.android.androidpractice.chat.bean.IMMessage

/**
 * @ClassName: RecyclerViewHolder
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:02
 */

abstract class RecyclerViewHolder<K>(val adapter:  RecyclerView.Adapter<*>? = null) {
    abstract fun convert(holder: BaseViewHolder, data: K, position: Int)

}