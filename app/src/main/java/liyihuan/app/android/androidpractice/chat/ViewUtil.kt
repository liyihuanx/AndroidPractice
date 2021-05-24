package com.example.module_fangroup.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Rect
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * @Author Yu
 * @Date 2021/3/25 17:28
 * @Description TODO
 */
/**
 *Create time 2020/9/11
 *Create Yu
 *description:
 * 1. dip 2 px
 * 2. px 2 dip
 */
object ViewUtil {


    /**
     * 描述：dip转换为px
     */
    fun dip2px(dipValue: Float): Int {
        val scale =
                Resources.getSystem().displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * 描述：px转换为dip
     */
    fun px2dip(pxValue: Float): Int {
        val scale =
                Resources.getSystem().displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        return getSizeByReflection(context, "status_bar_height")
    }

    /**
     * 获取状态栏高度 方式2
     *
     * @return
     */
    fun getStatusBarHeight(view: View): Int {
        val outRect = Rect()
        view.getWindowVisibleDisplayFrame(outRect)
        return outRect.top
    }

    /**
     * 获取ToolBar高度（包含状态栏高度）
     *
     * @return
     */
    fun getTitleBarHeight(view: View, mHasStatusBar: Boolean): Int {
        val rootView: View = view.rootView
        if (rootView != null) {
            val window = rootView.findViewById<View>(Window.ID_ANDROID_CONTENT)
            if (window != null) {
                return window.top + if (mHasStatusBar) getStatusBarHeight(view) else 0
            }
        }
        return if (mHasStatusBar) getStatusBarHeight(view) else 0
    }

    /**
     * 获取窗口可见区域高度
     */
    fun getVisibleHeight(view: View): Int {
        var outRect = Rect()
        view.getWindowVisibleDisplayFrame(outRect)
        return outRect.bottom
    }

    /**
     * 判断当前软键盘是否显示
     */
    public fun isSoftShowing(view: View): Boolean {
        return getScreenHeight(view.context) - getVisibleHeight(view) != 0
    }

    /**
     * 获取屏幕尺寸与密度.
     *
     * @return mDisplayMetrics
     */
    fun getDisplayMetrics(): DisplayMetrics? {
        return Resources.getSystem().displayMetrics
    }

    /**
     * 获取屏幕尺寸
     *
     * @return 数组 0：宽度， 1：高度
     */
    fun getScreenSize(): IntArray? {
        val displayMetrics = getDisplayMetrics()
        return intArrayOf(displayMetrics!!.widthPixels, displayMetrics.heightPixels)
    }

    /**
     * 得到设备的密度
     */
    fun getScreenDensity(): Float {
        return getDisplayMetrics()!!.density
    }




    /**
     * 是否为横屏状态
     */
    fun isHorScreen(): Boolean {
        return Resources.getSystem().configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    /**
     * 判断是否为全屏
     *
     * @param activity
     * @return
     */
    fun isFullScreen(activity: Activity): Boolean {
        val flags: Int = activity.window.attributes.flags
        return flags and WindowManager.LayoutParams.FLAG_FULLSCREEN == WindowManager.LayoutParams.FLAG_FULLSCREEN
    }

    fun getSizeByReflection(context: Context, field: String?): Int {
        var size = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = clazz.getField(field!!)[`object`].toString().toInt()
            size = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * 全屏切换
     */
    fun fullScreenChange(activity: Activity) {
        val mPreferences: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(activity)
        val fullScreen: Boolean = mPreferences.getBoolean("fullScreen", false)
        val attrs = activity.window.attributes
        if (fullScreen) {
            attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
            activity.window.attributes = attrs
            // 取消全屏设置
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            mPreferences.edit().putBoolean("fullScreen", false).commit()
        } else {
            attrs.flags = attrs.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
            activity.window.attributes = attrs
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            mPreferences.edit().putBoolean("fullScreen", true).commit()
        }
    }
}