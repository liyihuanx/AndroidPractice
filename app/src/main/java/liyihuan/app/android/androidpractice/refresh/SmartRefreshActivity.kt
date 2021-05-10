package liyihuan.app.android.androidpractice.refresh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.mbms.StreamingServiceInfo
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_smart_refresh.*
import liyihuan.app.android.androidpractice.R

class SmartRefreshActivity : AppCompatActivity() {
    private val adapter = SmartRefreshAdapter()
    private var list = ArrayList<String>()
    private lateinit var smartRefreshHelper: SmartRefreshHelper<String>

    private var loadCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_refresh)

        repeat(11) {
            list.add(" -- 数据$it -- ")
        }

        rvSmartRefresh.layoutManager = LinearLayoutManager(this)
        rvSmartRefresh.adapter = adapter

        smartRefreshHelper = SmartRefreshHelper(adapter, rvSmartRefresh, smartRefreshLayout,
                emptyView, 10, 1, true) {
            loadData()
        }
        smartRefreshHelper.refresh()
    }


    private fun loadData(){
        smartRefreshHelper.onFetchDataFinish(list)
    }



    fun test(){
        repeat(15) {
            list.add("第 $it 条item")
        }
        adapter.setNewData(list)
        rvSmartRefresh.layoutManager = LinearLayoutManager(this)
        rvSmartRefresh.adapter = adapter
        val simpleLoadMoreView = MyLoadMoreView()
        simpleLoadMoreView.setLoadMoreEndGone(false)
        adapter.setLoadMoreView(simpleLoadMoreView)
        adapter.setOnLoadMoreListener({
            Log.d("QWER", "adapter-onLoadMoreRequested: ")
//            adapter.loadMoreEnd()
        }, rvSmartRefresh)
        // 上拉加载
        var canRefresh = true
        smartRefreshLayout.setOnLoadMoreListener {
            if (canRefresh) {
                repeat(10) {
                    list.add("添加的 $it 条item")
                }
                adapter.notifyDataSetChanged()
            }
            canRefresh = false
            adapter.loadMoreEnd()
            it.finishLoadMore()
        }
//
//        // 下拉刷新
//        smartRefreshLayout.setOnRefreshListener {
//            list.add(0, "刷新添加的item")
//            adapter.notifyDataSetChanged()
//            it.finishRefresh()
//        }
    }
}