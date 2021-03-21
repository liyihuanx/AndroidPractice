package liyihuan.app.android.androidpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author created by liyihuanx
 * @date 2021/3/19
 * description: 类的描述
 */
class MainAdapter(mList: ArrayList<String>?) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    public var itemClickListener: OnRecyclerItemClickListener? = null

    interface OnRecyclerItemClickListener {
        //RecyclerView的点击事件，将信息回调给view
        fun onItemClick(Position: Int);
    }

    private var mList: ArrayList<String>? = null

    init {
        this.mList = mList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bad, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.item_context.text = mList?.get(position) ?: "777"
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(position)
        }
    }

    class ViewHolder : RecyclerView.ViewHolder {
        var item_context: TextView

        constructor(itemView: View) : super(itemView) {
            item_context = itemView.findViewById(R.id.item_context)

        }
    }


}


