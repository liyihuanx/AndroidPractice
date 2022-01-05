package liyihuan.app.android.androidpractice.appbarlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.layout_bar_fragment.*
import liyihuan.app.android.androidpractice.R

/**
 * @author liyihuan
 * @date 2021/12/08
 * @Description
 */
class BarFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view: View = inflater.inflate(R.layout.layout_bar_fragment, container, false)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {

        val list = arrayListOf<String>()

        val adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_app_bar) {
            override fun convert(helper: BaseViewHolder, item: String) {
                helper.setText(R.id.tvAppBarItem, item)
            }

        }
        adapter.setNewData(list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

    }
}