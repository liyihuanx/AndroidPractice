package liyihuan.app.android.androidpractice.datasource.api

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_data_soure.*
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.datasource.SimpleDataSource
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class HttpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_soure)

        btnDataSource.setOnClickListener {
//            okhttp3()
//            retrofitRequest()
//            rxjava()
//            realHttp()
//            rxJavaConcat()
            retrofitPostRequest()
        }

    }

    /**
     * 缓存-本地-网络
     * 谁有取谁，缓存策略
     */
    @SuppressLint("CheckResult")
    fun rxJavaConcat() {
        val memory = Observable.create<String> {
//            it.onNext("memory")
            it.onComplete()
        }.subscribeOn(Schedulers.io())


        val diskSource = Observable.create<String> {
            it.onNext("diskSource")
            it.onComplete()
        }.subscribeOn(Schedulers.io())

        val network = Observable.create<String> {
            it.onNext("network")
            it.onComplete()
        }.subscribeOn(Schedulers.io())

        Observable.concat(memory, diskSource, network)
                .firstElement()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("QWER", "rxJavaConcat: $it")
                }
    }


    /**
     * okhttp简单实用
     */
    fun okhttp3() {
        val url = "http://wwww.baidu.com"
        val request = Request.Builder()
                .url(url)
                .get()
                .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("QWER", "onFailure: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("QWER", "response: ${Gson().toJson(response)}")

            }

        })
    }


    /**
     * retrofit的简单使用
     */
    fun retrofitRequest() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        val apiService = retrofit.create(ApiService::class.java)

        val chapters = apiService.getChapters()

        chapters.enqueue(object : retrofit2.Callback<ChapterBean> {
            override fun onFailure(call: retrofit2.Call<ChapterBean>, t: Throwable) {
                Log.d("QWER", "onFailure: ${t.message}")
            }

            override fun onResponse(call: retrofit2.Call<ChapterBean>, response: retrofit2.Response<ChapterBean>) {
                Log.d("QWER", "response: ${Gson().toJson(response)}")

            }

        })

    }




    /**
     * rxjava 简单使用
     */
    @SuppressLint("CheckResult")
    fun rxjava(): ApiService {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(ApiService::class.java)
//        val apiService = retrofit.create(ApiService::class.java)
//
//        val chapters = apiService.getChapters2()
//
//
//        chapters.subscribeOn(Schedulers.io()) // 切到IO
//                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程
//                .subscribe({
//            Log.d("QWER", "response: ${Gson().toJson(it)}")
//
//        },{
//            Log.d("QWER", "onFailure: ${it.message}")
//
//        })

    }


    @SuppressLint("CheckResult")
    fun realHttp() {
        fakeHttp()
                .subscribeOn(Schedulers.io()) // 切到IO
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程
                .subscribe({
                    // 接口调用成功
                    Log.d("QWER", "realHttp: ${Gson().toJson(it)} ")
                }, {
                    // 报错
                    Log.d("QWER", "onFailure: ${it.message}")
                })
    }

    // private var remoteQuest: () -> Observable<R>
    fun fakeHttp(): Observable<ChapterBean> {
        return SimpleDataSource { // 对请求的接口进行配置，包装
            rxjava().getChapters2() // 请求接口
        }.startFetchData()
    }


    @SuppressLint("CheckResult")
    fun retrofitPostRequest() {
        fakePostHttp()
                .subscribeOn(Schedulers.io()) // 切到IO
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程
                .subscribe({
                    Log.d("QWER", "retrofitPostRequest: ${Gson().toJson(it)} ")
                }, {
                    Log.d("QWER", "onFailure: ${it.message}")

                })
    }


    fun fakePostHttp(): Observable<Any> {
        return SimpleDataSource { // 对请求的接口进行配置，包装
            rxjava().register("liyihuan","12345678","12345678") // 请求接口
        }.startFetchData()
    }

}