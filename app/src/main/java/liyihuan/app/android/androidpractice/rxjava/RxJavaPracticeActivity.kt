package liyihuan.app.android.androidpractice.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_java_practice.*
import liyihuan.app.android.androidpractice.R
import java.util.concurrent.TimeUnit

class RxJavaPracticeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java_practice)
        BtnTimeCount.setOnClickListener {
//            RxJavaHelper.switchMap()
//            search()
        }
        checkInput()
    }

    @SuppressLint("CheckResult")
    fun search() {
        RxTextView.textChanges(etSearch)
                .debounce(1000, TimeUnit.MILLISECONDS)
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .subscribe {
//                    Log.d("QWER", "onCreate: $it")
                    Log.d("QWER", "onCreate: ${Gson().toJson(it)}")
                }
    }

    @SuppressLint("CheckResult")
    fun checkInput() {
        // 跳过刚进入时的一次检测
        val skip = RxTextView.textChanges(etPhone).skip(1)
        val skip2 = RxTextView.textChanges(etPwd).skip(1)

        Observable.combineLatest(skip, skip2, BiFunction<CharSequence, CharSequence, Boolean> { t1, t2 ->
            t1.trim().isNotEmpty() && t2.trim().isNotEmpty()
        })
                .subscribe {
                    Log.d("QWER", "checkInput: $it")
                }

    }
}