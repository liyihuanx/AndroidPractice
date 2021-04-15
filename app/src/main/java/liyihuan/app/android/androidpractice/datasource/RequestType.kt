package liyihuan.app.android.androidpractice.datasource

/**
 * @author created by liyihuanx
 * @date 2021/4/15
 * description: 类的描述
 */
enum class RequestType {
    /**
     * 只请求缓存
     */
    OnlyCache,
    /**
     * 只请求网络
     */
    OnlyRemote,
    /**
     * 优先缓存，缓存拿到就展示缓存，缓存没拿到就请求网络
     */
    CacheFirst,
    /**
     * 缓存和网络同时请求，数据会返回2次（如果都成功的话）
     */
    Both,
    /**
     * 首次启动app第一次网络请求成功后刷新缓存　以后都不刷新　app杀死重复这个过程
     */
    AppFirst


}