package liyihuan.app.android.androidpractice.chat.im


import com.tencent.imsdk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import liyihuan.app.android.androidpractice.chat.bean.IMMessage

/**
 * 消息在协成里解析完成 分发
 */
object ImMsgDispatcher {

    val c2cMsgParsers = ArrayList<C2cMsgParser>()

    private val c2CimMsgListener = ArrayList<ImMsgListener>()

    fun addC2CListener(imMsgListener: ImMsgListener){
        c2CimMsgListener.add(imMsgListener)
    }

    fun removeC2CListener(imMsgListener: ImMsgListener){
        c2CimMsgListener.remove(imMsgListener)
    }


    
    fun onNewMsg(msg: TIMMessage) {
        GlobalScope.launch(Dispatchers.Main) {
            when (msg.conversation?.type) {
                TIMConversationType.C2C -> {
                    val job = async {
                        var asyncBeen: IMMessage<*>? = null
                        c2cMsgParsers.forEach {
                            asyncBeen = it.parse(msg)
                            if (asyncBeen !== null) {
                                return@forEach
                            }
                        }
                        asyncBeen
                    }
                    val been = job.await() ?: return@launch
                    c2CimMsgListener.forEach {
                        it.onNewMsg(been)
                    }
                }
                else -> {

                }
            }
        }
    }

}





