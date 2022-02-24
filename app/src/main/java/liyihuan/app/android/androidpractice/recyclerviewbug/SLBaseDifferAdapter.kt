package liyihuan.app.android.androidpractice.recyclerviewbug

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 *  on 2021/6/19
 *  function：
 */
abstract class BaseDifferAdapter<T, DB : ViewDataBinding>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BaseDifferViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseDifferViewHolder {
        val binding = DataBindingUtil.inflate<DB>(LayoutInflater.from(parent.context), getLayoutId(), parent, false)

        return BaseDifferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseDifferViewHolder, position: Int) {
        holder.item.setVariable(getItemId(), getItem(position))
    }

    abstract fun getLayoutId(): Int
    abstract fun getItemId(): Int

    open fun getViewModelId(): Int = 0
}

class BaseDifferViewHolder(val item: ViewDataBinding) : RecyclerView.ViewHolder(item.root)