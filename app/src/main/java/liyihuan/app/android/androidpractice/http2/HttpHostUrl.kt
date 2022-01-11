package liyihuan.app.android.androidpractice.http2

/**
 * @author liyihuan
 * @date 2022/01/10
 * @Description
 */
object HttpHostUrl {
    const val httpUrl = "https://www.wanandroid.com/"
    private const val httpUrl1 = "https://www.wanandroid1.com/"
    private const val httpUrl2 = "https://www.wanandroid2.com/"
    private const val httpUrl3 = "https://www.wanandroid3.com/"


    val HttpList = arrayListOf<String>(httpUrl1, httpUrl2, httpUrl3, httpUrl)


    fun getNextHttpUtl(index: Int): String {
        return if (index >= HttpList.size) {
            HttpList[0]
        } else {
            HttpList[index]
        }
    }
}