package liyihuan.app.android.androidpractice.chat

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author created by liyihuanx
 * @date 2021/5/13
 * description: 类的描述
 */
object KeyBoardUtil{
    // InputMethodManager 来完成


    @JvmStatic
    fun openKeyBoard(focusView: View) {
        val manager = focusView.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(focusView, 0)
    }


    /**
     * 通过控件View
     */
    @JvmStatic
    fun hideKeyBoardByView(focusView: View) {
        val manager = focusView.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(focusView.windowToken, 0)
    }


    /**
     * 通过activity
     */
    @JvmStatic
    fun hideKeyBoard(activity: Activity) {
        activity.currentFocus?.let { hideKeyBoardByView(it) }
    }

    /**
     * 测量键盘高度
     */
    fun getKeyBoardHeight() {

    }

    /**
     * 键盘显示状态，
     * 感觉不太行的样子
     */
    fun isKeyBoardShow(context: Context, v: View): Boolean {
        val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return if (manager.hideSoftInputFromWindow(v.windowToken, 0)) { // 隐藏软键盘成功 == 键盘之前是展示的
            manager.showSoftInput(v, 0) // 这里恢复一下
            true
        } else {
            false
        }
    }
}