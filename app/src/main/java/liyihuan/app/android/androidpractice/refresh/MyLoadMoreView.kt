package liyihuan.app.android.androidpractice.refresh

import com.chad.library.adapter.base.loadmore.LoadMoreView
import liyihuan.app.android.androidpractice.R

/**
 * @author created by liyihuanx
 * @date 2021/5/10
 * description: 类的描述
 */
class MyLoadMoreView : LoadMoreView() {
    override fun getLayoutId(): Int {
        return R.layout.view_load_more
    }

    override fun getLoadingViewId(): Int {
        return R.id.flLoading
    }

    override fun getLoadEndViewId(): Int {
        return R.id.flLoadEnd
    }

    override fun getLoadFailViewId(): Int {
        return R.id.flLoadFail
    }

}
