package liyihuan.app.android.androidpractice.http2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_http2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.fragment.FragmentActivity
import liyihuan.app.android.androidpractice.http2.RepositoryManager.getRepo
import retrofit2.HttpException
import java.net.UnknownHostException

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
            flow {
                Log.d("QWER", "getHttpTest: ")
                val httpResult = getRepo(ConfigRepository::class.java).config()
                emit(httpResult)
            }.flowOn(Dispatchers.IO)
                .retryWhen { cause, attempt ->
                    Log.d("QWER", "重试次数: $attempt")
                    attempt < 1
                }
                .catch { ex ->
                    Log.d("QWER", "error :${ex.javaClass.simpleName} : ${ex.message} ")
                }
                .collect {

                }
        }
    }



}