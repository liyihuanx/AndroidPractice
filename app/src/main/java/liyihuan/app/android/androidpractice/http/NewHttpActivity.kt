package liyihuan.app.android.androidpractice.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_http.*
import liyihuan.app.android.androidpractice.R

class NewHttpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_http)

//        Renovace.init(HttpConfig::class.java)

        btnFakeHttp.setOnClickListener {
            RepositoryManager.getRepo(TestRepository::class.java)
                    .fakeHttp2()
                    .subscribeOn(Schedulers.io()) // 切到IO
                    .observeOn(AndroidSchedulers.mainThread()) // 回到主线程
                    .subscribe({
                        Log.d("QWER", "onCreate: ${Gson().toJson(it)}")
                    }, {
                        Log.d("QWER", "onCreate: ${it.message}")
                    })
        }

        btnFakeHttp2.setOnClickListener {
            RepositoryManager.getRepo(TestRepository2::class.java)
                    .fakeHttp3()
                    .subscribeOn(Schedulers.io()) // 切到IO
                    .observeOn(AndroidSchedulers.mainThread()) // 回到主线程
                    .subscribe({
                        Log.d("QWER", "onCreate: ${Gson().toJson(it)}")
                    }, {
                        Log.d("QWER", "onCreate: ${it.message}")
                    })
        }

    }
}