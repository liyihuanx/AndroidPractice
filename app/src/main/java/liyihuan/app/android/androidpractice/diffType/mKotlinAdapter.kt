package liyihuan.app.android.androidpractice.diffType;

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import liyihuan.app.android.androidpractice.R

/**
 * @author created by liyihuanx
 * @date 2020/9/22
 * description: 类的描述
 */

public class mKotlinAdapter :BaseQuickAdapter<UserBean,BaseViewHolder>(null){
    private val TYPE_ONE = 1
    private val TYPE_TWO = 2

   init {
       multiTypeDelegate = object : MultiTypeDelegate<UserBean>() {
           override fun getItemType(userBean: UserBean): Int {
               return userBean.type
           }
       }

       multiTypeDelegate.registerItemType(TYPE_ONE, R.layout.item_type_one)
               .registerItemType(TYPE_TWO, R.layout.item_type_two)
   }

    override fun convert(helper: BaseViewHolder, item: UserBean?) {
        helper.setText(R.id.name, item!!.username)
        when (helper.itemViewType) {
            TYPE_ONE -> {
            }
            TYPE_TWO -> {
            }
        }
    }
}