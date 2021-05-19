package liyihuan.app.android.androidpractice.chat.im
import android.util.Log
import com.google.gson.Gson


import com.tencent.imsdk.TIMElemType
import com.tencent.imsdk.TIMMessage
import com.tencent.imsdk.TIMTextElem
import liyihuan.app.android.androidpractice.chat.bean.IMMessage
import liyihuan.app.android.androidpractice.chat.bean.TextMsgBean
import liyihuan.app.android.androidpractice.imdemo.imchat.MsgBean
import org.json.JSONObject

class C2cMessageParser : C2cMsgParser {

    override fun parse(msg: TIMMessage): IMMessage<*>? {

        var been: IMMessage<*>? = null
        val ele = msg.getElement(0)
        if (ele.type == TIMElemType.Custom) {

        }

        if(ele.type == TIMElemType.Text){
            var e: TIMTextElem? = null // 文本元素
            e = ele as TIMTextElem?
            if (e == null) {
                return null
            }
            try {
                val dataJson = e.text // parseIMMessage --> this.text = elem.getText();
                val jb: JSONObject = JSONObject(dataJson)
                val userAction = jb.opt("userAction").toString()
                Log.d("QWER", "parse: $dataJson")
                val classType = TextMsgBean::class.java

                been = Gson().fromJson(dataJson, classType) // 转换成对应的Bean类
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        been?.setTxMsg(msg) // msg 消息体
        return been // 把MsgBean扔出去
    }
}