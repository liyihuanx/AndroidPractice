package liyihuan.app.android.androidpractice.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import liyihuan.app.android.androidpractice.R


class RxJavaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java)


        val observable = Observable.create<String> {
            it.onNext("1")
            it.onNext("2")
            it.onNext("3")
            it.onComplete()
        }.subscribeOn(Schedulers.io()) // 切到IO
        .observeOn(AndroidSchedulers.mainThread()) // 回到主线程

//        .subscribe({
//
//        }, {
//
//        })

        // 观察者
        val observer = object : Observer<String> {
            override fun onComplete() {
                Log.d("QWER", "onComplete: ")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d("QWER", "onSubscribe: ")
            }

            override fun onNext(t: String) {
                Log.d("QWER", "onNext: $t")
            }

            override fun onError(e: Throwable) {
                Log.d("QWER", "onError: ")
            }
        }

        observable.subscribe(observer)
    }
}