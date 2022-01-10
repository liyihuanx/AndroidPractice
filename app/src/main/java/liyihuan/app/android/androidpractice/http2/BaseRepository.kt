package liyihuan.app.android.androidpractice.http2

import liyihuan.app.android.androidpractice.http2.request.HttpProvider


/**
 * @author created by liyihuanx
 * @date 2021/9/7
 * @description: 类的描述
 */
abstract class BaseRepository<T> {

    var apiService: T = HttpProvider.defaultCreate(TypeUtils.findNeedType(javaClass) as Class<out T>)

}