package liyihuan.app.android.mrouter_api;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @ClassName: BundleManager
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/3/22 21:04
 */
public class BundleManager {
    public Bundle bundle = new Bundle();


    public Bundle getBundle() {
        return this.bundle;
    }

    public BundleManager withString(@NonNull String key, @NonNull String value) {
        bundle.putString(key, value);
        return this;
    }

    public BundleManager withBoolean(@NonNull String key, @Nullable boolean value) {
        bundle.putBoolean(key, value);
        return this;
    }

    public BundleManager withInt(@NonNull String key, @Nullable int value) {
        bundle.putInt(key, value);
        return this;
    }


    public BundleManager withBundle(Bundle bundle) {
        this.bundle = bundle;
        return this;
    }


    // 直接完成跳转
    public Object navigation(Context context) {
        // 单一原则
        // 把自己所有行为 都交给了  路由管理器
        return RouterManager.getInstance().openUrl(context, this);
    }
}
