package liyihuan.app.android.androidpractice.refresh

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import liyihuan.app.android.androidpractice.refresh.IEmptyView.*

/**
 * @author created by liyihuanx
 * @date 2021/5/10
 * description: 类的描述
 */
class CommonEmptyView : FrameLayout,IEmptyView{

    constructor(context: Context) : this(context, null) {
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    override fun setErrorType(type: Int) {
        when(type){
            HIDE_LAYOUT -> {

            }
            NETWORK_ERROR -> {

            }
            NODATA -> {

            }
            NODATA_ENABLE_CLICK -> {

            }
        }
    }
}