package liyihuan.app.android.androidpractice.rxjava

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import liyihuan.app.android.androidpractice.diffType.UserBean
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

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
    fun delayDefer() {
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
    fun rxJavaFor() {
        val list1 = arrayListOf("1", "2", "3", "4")
        val list2 = arrayListOf(1, 2, 3, 4)
        val list3 = arrayListOf("5", "6", "7")
        Observable.just(list1, list2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }

        Observable.fromArray(list1, list2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }

        // 为什么不直接遍历？
        Observable.fromIterable(list1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                }

        Observable.range(1, 10)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }
    }

    //  TODO  -----------------------------   变换操作符 ------------------------------------
    /**
     * map 和 flatMap 和 concatMap
     */
    @SuppressLint("CheckResult")
    fun changeData() {
        val list1 = arrayListOf("1", "2", "3", "4")
        val list2 = arrayListOf(5, 6, 7, 8)
        val list3 = arrayListOf("9", "10", "11")

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
        // concat 只能接4个
        // concatArray 能接很多个
//        Observable.concatArray(
//                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
//                Observable.intervalRange(2, 3, 1, 1, TimeUnit.SECONDS))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    Log.d("QWER", "bindData: $it") // 0,1,2 -> 2,3,4 按对象顺序
//                }

//        Observable.mergeArray(
//                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS), // 从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
//                Observable.intervalRange(2, 3, 1, 1, TimeUnit.SECONDS)) // 从2开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    Log.d("QWER", "bindData: $it") // 0,2 -> 1,3 -> 2,4 一人一次，时间顺序
//                }


        Observable.concatArrayDelayError( // 会把第二个Observable也发送出去
                Observable.create<Int> {
                    it.onNext(1)
                    it.onNext(2)
                    it.onError(NullPointerException("我抛出的错误"))
                    it.onNext(3)
                    it.onComplete()
                },
                Observable.just(5, 6))
                .subscribe({
                    Log.d("QWER", "bindData: $it")
                }, {
                    Log.d("QWER", "error: ${it.message}")
                })
    }

    @SuppressLint("CheckResult")
    fun bindData2() {
        val list1 = Observable.create<UserBean> {
            it.onNext(UserBean("liyihuan", -1))
            it.onNext(UserBean("chenyalun", -1))
            it.onComplete()
        }

        val list2 = Observable.create<Int> {
            it.onNext(1)
            it.onComplete()
        }

        val network = Observable.just("网络")
        val file = Observable.just("本地文件")

        // 会对应数量
        Observable.zip(list1, list2, BiFunction<UserBean, Int, UserBean> { t1, t2 ->
            Log.d("QWER", "UserBean: ${Gson().toJson(t1)}")
            Log.d("QWER", "type: $t2")
            t1.type = t2
            t1
        }).subscribe({
            Log.d("QWER", "bindData2: ${Gson().toJson(it)}")
        }, {
            error(it)
        })
        // zip 永远按顺序计算
        // combineLatest 每个流只要发生了变化，就会拿这个流的最新值和另一个流的最新值进行运算。？有啥实际作用呢
        val just1 = Observable.just(1)
        val just2 = Observable.just(2)
        // 如果return t1 + t2 --> 1+2=3
        // 当just1改变为 Observable.just(1，2) --> return 2+2 = 4
    }

    @SuppressLint("CheckResult")
    fun bindData3(){
        // scan和reduce都是把操作的观察序列的上一次操作的结果做为第二次的参数传递给第二次Observable使用，但两者有区别:
        //        scan:每次操作之后先把数据输出，然后在调用scan的回调函数进行第二次操作
        //        reduce：把所有的操作都操作完成之后在调用一次观察者，把数据一次性输出

//        Observable.just(1,2,3,4)
////                .startWithArray(2) // 2,1
//
//                .reduce { t1, t2 ->
//                    Log.d("QWER", "t1: $t1")  // 1 -> 3(1+2) -> 6 -> 10
//                    Log.d("QWER", "t2: $t2")  // 2 -> 3 -> 4
//
//                    t1 + t2
//                }
//                .subscribe {
//                    Log.d("QWER", "bindData3: $it")  // scan会输出1,3,6,10， reduce输出10
//                }

        val list = ArrayList<Any>()
        Observable.just(1, 2, UserBean("liyihuan", -1), "4")
                .collect({
                    list
                }, { t1, t2 ->
                    t1.add(t2)
                })
                .subscribe({
                    Log.d("QWER", "fun: $it")
                }, {
                    error(it)
                })
    }


    //  TODO  -----------------------------   过滤操作符 ------------------------------------
    @SuppressLint("CheckResult")
    fun filterFun() {

        val userBean = UserBean()
        val javaClass = userBean.javaClass
        val java = userBean::class.java

        val a1 = "1".javaClass
        val a2 = String.javaClass
        val java1 = String::class.java



//        Observable.just(1,2,3,4)
////                .ofType(String::class.java) // 过滤指定类型 --> 只获得该类型的
////                .filter { it is String } // 过滤指定条件 --> 获得满足条件的
////                .skip(2) // 跳过
////                .distinct()  // 过滤重复元素
////                .distinctUntilChanged() // 过滤与前一项相同的值 ???????
//                .subscribe {
//                    Log.d("QWER", "filterFun: $it")
//                }

        Observable.interval(0,20,TimeUnit.SECONDS)
                .take(10) // 过滤指定长度或者时长
                .subscribe {
                    Log.d("QWER", "filterFun: $it")
                }
    }


    //  TODO  -----------------------------   条件操作符 ------------------------------------
    @SuppressLint("CheckResult")
    fun conditionFun() {
        // list.contains(1) 有区别 ????????
        Observable.just(UserBean("1",1),UserBean("2",2))
//                .all {
//                    it.type == 1
//                }
//                .contains(1)

                .subscribe({
                    Log.d("QWER", "filterFun: $it")
                }, {

                })
    }

    //  TODO  -----------------------------   其他操作符 ------------------------------------
    @SuppressLint("CheckResult")
    fun otherFun() {
//        Observable.concat(
//                Observable.interval(100, TimeUnit.MILLISECONDS).take(3),
//                Observable.interval(500, TimeUnit.MILLISECONDS).take(3),
//                Observable.interval(100, TimeUnit.MILLISECONDS).take(3),
//                Observable.just(1)
//        )
//                .timeout(200, TimeUnit.MILLISECONDS, Observable.just(-1))
//                .subscribe({
//                    Log.d("QWER", "otherFun: $it")
//                }, {
//                    Log.d("QWER", "otherFun: ${it.message}")
//                })

        Observable.just(5)
                .doOnSubscribe {

                }
                .doOnNext {
                    Log.d("QWER", "doOnNext: $it")
                }
                .doOnComplete {

                }
                .doOnError {

                }
                .doOnEach {

                }
//                .repeatWhen {
//                    it.delay(1000,TimeUnit.MILLISECONDS)
//                }
                .subscribe {
                    Log.d("QWER", "otherFun: $it")
                }

    }

    @SuppressLint("CheckResult")
    fun switchMap(){
        Observable.just(1,2,3)
                .switchMap {
                    var delay = if (it == 2) {
                        200L
                    } else {
                        200L
                    }
                    Observable.just(it)
                }
                .subscribe {
                    Log.d("QWER", "switchMap: $it")
                }
    }
}