package liyihuan.app.android.androidpractice.imdemo.conversation;

import com.tencent.imsdk.v2.V2TIMConversation;

import java.util.List;

/**
 * @ClassName: ConversationCallback
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/1/17 17:42
 */
public interface ConversationCallback {

    void getConversation(List<V2TIMConversation> convList);

}
