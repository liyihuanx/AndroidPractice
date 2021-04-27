package liyihuan.app.android.androidpractice.datasource;

import android.app.Application;

/**
 * @author created by liyihuanx
 * @date 2021/4/27
 * description: 类的描述
 */
public class AppCache {
    private static Application context;

    public static Application getContext() {
        return context;
    }

    public static void setContext(Application app) {
        if (app == null) {
            AppCache.context = null;
            return;
        }
        AppCache.context = app;
    }
}
