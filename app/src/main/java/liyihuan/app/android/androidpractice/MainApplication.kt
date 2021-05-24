package liyihuan.app.android.androidpractice

import android.app.Application
import liyihuan.app.android.androidpractice.chat.im.ImHelper
import liyihuan.app.android.androidpractice.datasource.AppCache

/**
 * @author created by liyihuanx
 * @date 2021/4/27
 * description: 类的描述
 */

class MainApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        AppCache.setContext(this)
        ImHelper.init()
    }
}