package liyihuan.app.android.androidpractice.kotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_xie_cheng.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import liyihuan.app.android.androidpractice.R

class XieChengActivity : AppCompatActivity() {

    var userList = ArrayList<UserBean>()
    var parserList = ArrayList<msgParser>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xie_cheng)
        parserList.add(C2CParser())
        parserList.add(C2CParser2())

        sendMsg.setOnClickListener {
            imXC(UserBean("1", 1))
            imXC(UserBean("2", 4))
//            imXC(UserBean("3", 2))
//            imXC(UserBean("4", 5))
//            imXC(UserBean("5", 3))
//            imXC(UserBean("6", 6))
        }
    }


    fun imXC(data: UserBean) {
        Log.d("QWER", "接收消息  ")
        GlobalScope.launch(Dispatchers.Main) {
            Log.d("QWER", "GlobalScope 启动 ")
            var userBean: UserBean? = null
            val job = async {
                Log.d("QWER", "job 运行中 ")

                parserList.forEach {
                    userBean = it.parser(data)
                    // 解析不出来
                    if (userBean !== null) {
                        return@forEach
                    }
                }

                Log.d("QWER", "job 返回的值: ${Gson().toJson(userBean)} ")
                userBean

            }
            val been = job.await() ?: return@launch
        }
    }
}


interface msgParser {
    fun parser(userbean: UserBean): UserBean?
}