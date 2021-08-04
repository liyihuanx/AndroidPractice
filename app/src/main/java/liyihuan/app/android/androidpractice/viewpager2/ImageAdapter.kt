package liyihuan.app.android.androidpractice.viewpager2

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import liyihuan.app.android.androidpractice.R

/**
 * @author created by liyihuanx
 * @date 2021/8/3
 * @description: 类的描述
 */
class ImageAdapter : BaseQuickAdapter<Int, BaseViewHolder>(R.layout.item_image){
    override fun convert(helper: BaseViewHolder, item: Int) {
        helper.setImageResource(R.id.ivViewpager2, item)
    }

}
