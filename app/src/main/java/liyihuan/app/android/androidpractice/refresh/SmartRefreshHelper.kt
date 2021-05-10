package liyihuan.app.android.androidpractice.refresh

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import liyihuan.app.android.androidpractice.refresh.IEmptyView.*

/**
 * 上拉下拉帮助类
 *
 *
 */
class SmartRefreshHelper<T>(val adapter: BaseQuickAdapter<T, *>,
                            private val recycler_view: RecyclerView,
                            val refresh_layout: SmartRefreshLayout,
                            private val emptyCustomView: IEmptyView?,
                            private var eachPageSize: Int,  // 每次加载的的个数，小于这个数不会加载？
                            private val preLoadNumber: Int, // 倒数第几个item开始LoadMore
                            private val isNeedLoadMore: Boolean,
                            private val fetcherFuc: (page: Int) -> Unit) {

    var isLoadMoreing: Boolean = false
        private set
    var isRefreshing: Boolean = false
        private set
    var startPage = 0
        set(value) {
            field = value
        }
    private var currentPage = startPage

    init {
        adapter.setEnableLoadMore(isNeedLoadMore)
        if (isNeedLoadMore) {
            adapter.setPreLoadNumber(preLoadNumber)
            val simpleLoadMoreView = SimpleLoadMoreView()
            adapter.setLoadMoreView(simpleLoadMoreView)
            simpleLoadMoreView.setLoadMoreEndGone(true)
            adapter.setOnLoadMoreListener({
                loadMore()
            }, recycler_view)
        }
        refresh_layout.setEnableLoadMore(false)

        refresh_layout.setOnRefreshListener {
            // refresh()
            fetcherFuc(startPage)
        }
    }

    fun onFetchDataFinish(data: List<T>?) {
        onFetchDataFinish(data, false)
    }

    /**
     * 获取到分页数据 设置下拉刷新和上拉的状态
     */
    fun onFetchDataFinish(data: List<T>?, isNeedLoadMore: Boolean) {
        refresh_layout.finishRefresh(true)  // 关闭刷新
        if (data != null) {
            if (data.size > eachPageSize) { // if size = 10, eachPageSize = 9
                eachPageSize = data.size   // 赋值
            }
            if (isLoadMoreing) {  // 下拉加载
                if (data.isNotEmpty()) {  // 不等于空
                    currentPage++  // 下一页
                    adapter.addData(data)  // 加载数据
                }

            } else {
                currentPage = startPage  // 上拉刷新，重置当前页
                adapter.setNewData(data) // 加载数据
            }

            if (isNeedLoadMore) {   // 需要加载更多？
                adapter.loadMoreComplete()
            } else {
                if (data.size < eachPageSize || (data.isEmpty())) {  //  进入了 data 就不会为empty啊
                    adapter.loadMoreEnd(isRefreshing)     // refresh 时候赋值true,
                } else {
                    adapter.loadMoreComplete()  // 完成加载更多
                }
            }
        }
        refreshEmptyView(NODATA)
        isLoadMoreing = false
        isRefreshing = false
    }

    /**
     * 加载数据失败
     */
    fun onFetchDataError() {
        if (isRefreshing) {
            refresh_layout.finishRefresh(false)
        } else {
            adapter.loadMoreFail()
        }
        val disconnected = false
        if (disconnected) {
            refreshEmptyView(NETWORK_ERROR)
        } else {
            refreshEmptyView(NODATA)
        }
        isLoadMoreing = false
        isRefreshing = false
    }


    fun refreshEmptyView(type: Int) {
        if (adapter.data.isEmpty() && adapter.itemCount == 0) {
            emptyCustomView?.setErrorType(type)
        } else {
            emptyCustomView?.setErrorType(HIDE_LAYOUT)
        }
    }

    private fun loadMore() {
        if (isRefreshing || isLoadMoreing) {
            return
        }
        isLoadMoreing = true
        fetcherFuc(currentPage + 1)
        Log.d("QWER", "loadMore: ")
    }


    fun refresh() {
        if (isRefreshing || isLoadMoreing) {
            refresh_layout.finishRefresh(true)
            return
        }
        isRefreshing = true
        refresh_layout.autoRefresh()
    }


}