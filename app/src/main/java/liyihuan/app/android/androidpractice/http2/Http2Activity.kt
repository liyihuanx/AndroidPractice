package liyihuan.app.android.androidpractice.http2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_http2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.fragment.FragmentActivity
import liyihuan.app.android.androidpractice.http2.RepositoryManager.getRepo

class Http2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http2)

        btnHttp.setOnClickListener {
            getHttpTest()
        }
    }


    private fun getHttpTest(){
        lifecycleScope.launch {
//            flow {
//                val httpResult = getRepo(ConfigRepository::class.java).config()
//                emit(httpResult)
//            }.flowOn(Dispatchers.IO)
//                .catch { ex ->
//                    Log.d("QWER", "error :${ex.javaClass.simpleName} : ${ex.message} ")
//                }
//                .collect {
//                    Log.d("QWER", "数据: ${Gson().toJson(it)}")
//                }
//
            getRepo(ConfigRepository::class.java).config()
                .asFlow()
                .flowOn(Dispatchers.IO)
                .catch { ex ->
                    Log.d("QWER", "error :${ex.javaClass.simpleName} : ${ex.message} ")
                }
                .collect {

                }
        }
    }



}