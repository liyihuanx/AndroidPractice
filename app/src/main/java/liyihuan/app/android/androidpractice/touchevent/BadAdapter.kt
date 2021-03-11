package liyihuan.app.android.androidpractice.touchevent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import liyihuan.app.android.androidpractice.R

/**
 * @ClassName: BadAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/3/11 21:26
 */

class BadAdapter(mNoteList: List<String>?) : RecyclerView.Adapter<BadAdapter.ViewHolder>() {


    private var mNoteList: List<String>? = null

    init {
        this.mNoteList = mNoteList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bad, parent, false);
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mNoteList!!.size
    }

    override fun onBindViewHolder(holder: BadAdapter.ViewHolder, position: Int) {
        holder.item_context.text = mNoteList?.get(position) ?: "777"
    }


    class ViewHolder : RecyclerView.ViewHolder {
        var item_context: TextView

        constructor(itemView: View) : super(itemView) {
            item_context = itemView.findViewById(R.id.item_context)
        }
    }

}