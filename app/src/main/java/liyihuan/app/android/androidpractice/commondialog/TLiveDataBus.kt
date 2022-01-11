package liyihuan.app.android.androidpractice.commondialog

import android.util.Log
import androidx.lifecycle.*
import java.util.concurrent.ConcurrentHashMap

/**
 * @author created by liyihuanx
 * @date 2021/9/15
 * @description: 类的描述
 */
object TLiveDataBus {

//    LiveDataBus.with<String>("TestLiveDataBus").postStickyData("测试！")
//    LiveDataBus.with<String>("TestLiveDataBus") .observerSticky(this, false) {
//
//    }

    private val mStickyMap = ConcurrentHashMap<String, StickyLiveData<*>>()

    fun <T> with(eventName: String): StickyLiveData<T> {
        var stickyLiveData = mStickyMap[eventName]
        if (stickyLiveData == null) {
            stickyLiveData = StickyLiveData<T>(eventName)
            mStickyMap[eventName] = stickyLiveData
        }

        return stickyLiveData as StickyLiveData<T>
    }


    /**
     * 将发射出去的LiveData包装一下，再做一些数据保存
     */
    class StickyLiveData<T>(private var eventName: String) : LiveData<T>() {

        var mLiveDataVersion = 0
        var mStickyData: T? = null

        fun setStickyData(stickyData: T) {
            mStickyData = stickyData
            setValue(stickyData)
        }

        fun postStickyData(stickyData: T) {
            mStickyData = stickyData
            postValue(stickyData)
        }

        override fun setValue(value: T) {
            mLiveDataVersion++
            super.setValue(value)
        }

        override fun postValue(value: T) {
            mLiveDataVersion++
            super.postValue(value)
        }

        fun observerSticky(owner: LifecycleOwner, sticky: Boolean, observer: Observer<in T>) {
            // 移除自己保存的StickyLiveData
            owner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    mStickyMap.remove(eventName)
                }
            })

//            super.observe(owner, StickyObserver(this, sticky, observer))
        }

        /**
         * 重写LiveData的observer，把传入的observer包装一下
         */
        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            observerSticky(owner, false, observer)
        }
    }

    class StickyObserver<T>(
            // 为了拿到StickyLiveData的mLiveDataVersion字段，只好传入
            private val stickyLiveData: LiveDataBus.StickyLiveData<T>,
            private val sticky: Boolean,
            private val observer: Observer<in T>
    ) : Observer<T> {

        // 创建出来的时候就做一个对齐
        private var mLastVersion = stickyLiveData.mLiveDataVersion

        override fun onChanged(t: T) {

            // 所以有黏性事件时 就一定会进入，因为他们是相等的
            if (mLastVersion >= stickyLiveData.mLiveDataVersion) {
                // 接着再判断你要不要黏性事件，要的话发送，不要的话return
                if (sticky && stickyLiveData.mStickyData != null) {
                    observer.onChanged(stickyLiveData.mStickyData)
                }
                return
            }
            // 普通事件的发送
            observer.onChanged(t)
        }
    }


}