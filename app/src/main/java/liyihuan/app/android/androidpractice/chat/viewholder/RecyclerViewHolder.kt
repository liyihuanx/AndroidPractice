package liyihuan.app.android.androidpractice.chat.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @ClassName: RecyclerViewHolder
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:02
 */

abstract class RecyclerViewHolder<T : RecyclerView.Adapter<*>, V : BaseViewHolder?, K>(val adapter: T? = null) {

    abstract fun convert(holder: V, data: K, position: Int)

}