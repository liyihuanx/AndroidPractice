package liyihuan.app.android.androidpractice.diffType;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import liyihuan.app.android.androidpractice.R;

/**
 * @author created by liyihuanx
 * @date 2020/9/22
 * description: 类的描述
 */
public class mAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;

    public mAdapter() {
        super(null);

        setMultiTypeDelegate(new MultiTypeDelegate<UserBean>() {
            @Override
            protected int getItemType(UserBean userBean) {
                return userBean.getType();
            }
        });

        getMultiTypeDelegate().registerItemType(TYPE_ONE, R.layout.item_type_one)
                .registerItemType(TYPE_TWO, R.layout.item_type_two);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        helper.setText(R.id.name, item.getUsername());
        switch (helper.getItemViewType()){
            case TYPE_ONE:

                break;
            case TYPE_TWO:

                break;
        }
    }
}
