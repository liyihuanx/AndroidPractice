package liyihuan.app.android.androidpractice.datasource

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import liyihuan.app.android.androidpractice.R

class DataSoureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_soure)
    }


    @SuppressLint("CheckResult")
    fun realHttp(){
        fakeHttp().subscribe({
            // 接口调用成功
        },{
            // 报错
        })
    }


    // private var remoteQuest: () -> Observable<R>
    fun fakeHttp(): Observable<Unit> {
        return SimpleDataSource { // 对请求的接口进行配置，包装
            fakeApi() // 请求接口
        }.startFetchData()
    }


    // apiService里的接口
    fun fakeApi(): Observable<Unit>{
        return Observable.create<Unit> {
        }
    }
}