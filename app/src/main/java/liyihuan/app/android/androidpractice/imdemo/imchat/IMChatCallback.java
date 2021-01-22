package liyihuan.app.android.androidpractice.imdemo.imchat;

import com.tencent.imsdk.v2.V2TIMMessage;

import java.util.List;

/**
 * @ClassName: IMChatCallback
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/1/17 23:23
 */
public interface IMChatCallback {

    void getC2CHistory(List<V2TIMMessage> v2TIMMessages);
}
