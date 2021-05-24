package liyihuan.app.android.androidpractice.chat.im

import android.app.Application
import android.util.Log

import com.tencent.imsdk.*

object ImHelper {


    var isSetIm = false
    fun init() {

        ImMsgDispatcher.c2cMsgParsers.add(C2cMessageParser())

    }

    //可能崩溃im loss
    fun initIm(applicationContext: Application) {
        isSetIm = true
        //初始化 IM SDK 基本配置
        val timSdkConfig = TIMSdkConfig(EnvironmentConfig.IMSDK_APPID) // 禁止掉im日志显示
                .setLogLevel(TIMLogLevel.VERBOSE)
                .enableLogPrint(false)
        timSdkConfig.logListener = TIMLogListener { level, tag, msg ->
            //可以通过此回调将 sdk 的 log 输出到自己的日志系统中
            if (BuildConfig.DEBUG) {
            }
        }
        //基本用户配置
        val userConfig = TIMUserConfig()
        //初始化群设置
        userConfig.groupSettings = TIMGroupSettings()
        //禁止服务器自动代替上报已读
        userConfig.disableAutoReport(true)
        userConfig.isReadReceiptEnabled = true
        userConfig.userStatusListener = object : TIMUserStatusListener {
            override fun onForceOffline() {

            }

            override fun onUserSigExpired() {

            }
        }
        userConfig.isReadReceiptEnabled = false
        ImManager.init(applicationContext, userConfig, timSdkConfig)

    }

}