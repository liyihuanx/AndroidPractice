package liyihuan.app.android.androidpractice.chat.bean

import com.tencent.imsdk.TIMTextElem
import liyihuan.app.android.androidpractice.chat.im.EnvironmentConfig.USER_ID

/**
 * @ClassName: TextMsgBean
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/17 22:35
 */

class TextMsgBean : IMMessage<TIMTextElem>(){


    private var textContent = ""

    fun createMsg(paramBeen: String) {
        userId = USER_ID
        userName = if (USER_ID == "liyihuan") "李逸欢" else "陈雅伦"
        userHeadImg = ""
        msgContent = paramBeen
    }

    override fun parseIMMessage(elem: TIMTextElem) {
        textContent = elem.text
    }

    fun getText(): String {
        return textContent
    }

}