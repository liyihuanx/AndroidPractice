package liyihuan.app.android.androidpractice.rxjava

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author created by liyihuanx
 * @date 2021/4/28
 * description: 类的描述
 */

object RxJavaHelper {

    //  TODO subscribeOn()改变调用它之前代码的线程
    //  TODO observeOn()改变调用它之后代码的线程

    //  TODO  -----------------------------   创建操作符 ------------------------------------
    /**
     * 倒计时
     */
    @SuppressLint("CheckResult")
    fun timeCount() {
        var stop = false
        val subscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .takeWhile { // 发送的事件判断条件不满足时，就会终止后续事件接收
                    !stop
                }
                .subscribe({
                    Log.d("QWER", "timeCount: $it")
                    if (it.toInt() == 60) {
                        stop = true
                    }
                }, {

                })
    }

    @SuppressLint("CheckResult")
    fun timeCountRange() {
        val subscribe = Observable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .takeWhile {
                    if (it.toInt() == 10) {
                        // TODO do something
                        Log.d("QWER", "takeWhile: $it")
                    }
                    true
                }
                .subscribe({
                    Log.d("QWER", "timeCountRange: $it")
                }, {

                })
    }

    /**
     * 延迟任务
     */
    @SuppressLint("CheckResult")
    fun delayTimer() {
        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                })
    }

    @SuppressLint("CheckResult")
    fun delayDefer(){
        // TODO 有啥作用呢？
        var num = "1234"
        val observable: Observable<String> = Observable.defer<String> {
            Observable.create {
                it.onNext(num)
            }
        }
        num = "5678"

        observable.subscribe {
            Log.d("QWER", "delayDefer: $it")
        }
    }

    /**
     *  遍历
     */
    @SuppressLint("CheckResult")
    fun rxJavaFor(){
        val list1 = arrayListOf("1","2","3","4")
        val list2 = arrayListOf(1,2,3,4)
        val list3 = arrayListOf("5","6","7")
        Observable.just(list1,list2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }

        Observable.fromArray(list1,list2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }

        // 为什么不直接遍历？
        Observable.fromIterable(list1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                }

        Observable.range(1,10)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }
    }

    //  TODO  -----------------------------   变换操作符 ------------------------------------
    /**
     * map 和 flatMap 和 concatMap
     */
    @SuppressLint("CheckResult")
    fun changeData(){
        val list1 = arrayListOf("1","2","3","4")
        val list2 = arrayListOf(5,6,7,8)
        val list3 = arrayListOf("9","10","11")

        Observable.fromIterable(list2)
                .map {
                    it.toString()
                }
                .subscribe {

                }


        Observable.fromIterable(list2)
                .flatMap { // 无序
                    // 取出数据，做出转换后，继续发送
                    Observable.just(it.toString())
                }
                .subscribe {

                }

        Observable.fromIterable(list2)
                .concatMap { // 有序输出
                    Observable.just(it.toString())
                }
                .subscribe {

                }

    }

    //  TODO  -----------------------------   合并操作符 ------------------------------------
    /**
     *
     */
    @SuppressLint("CheckResult")
    fun bindData() {
        Observable.concatArray(Observable.just(1, 2), Observable.just(3, 4))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("QWER", "bindData: $it")
                }
    }

}