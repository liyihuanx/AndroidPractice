package liyihuan.app.android.androidpractice.rxjava

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.google.gson.Gson
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_java_practice.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.kotlin.CoroutineScopeWrap
import java.util.concurrent.TimeUnit

class RxJavaPracticeActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java_practice)
        BtnTimeCount.setOnClickListener {
            RxJavaHelper.switchMap()
        }
        RxTextView.textChanges(etSearch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(1000,TimeUnit.MILLISECONDS)
                .filter {
                    it.trim().isNotEmpty()
                }
                .switchMap {
                    val list = ArrayList<String>()
                    list.add("网络请求")
//                    if (it.toString().toInt() % 2 == 1) {
//                        list.add("网络请求")
//                    } else {
//                        list.add("又一次网络请求")
//                    }
                    Observable.just(list)
                }
                .distinctUntilChanged()
                .subscribe {
//                    Log.d("QWER", "onCreate: $it")
                    Log.d("QWER", "onCreate: ${Gson().toJson(it)}")
                }


    }
}