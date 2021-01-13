package liyihuan.app.android.androidpractice.imdemo.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMAdvancedMsgListener;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMMessageManager;
import com.tencent.imsdk.v2.V2TIMMessageReceipt;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;
import com.tencent.imsdk.v2.V2TIMSendCallback;
import com.tencent.imsdk.v2.V2TIMTextElem;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import java.util.List;

import liyihuan.app.android.androidpractice.imdemo.GenerateTestUserSig;
import liyihuan.app.android.androidpractice.imdemo.MsgBean;

/**
 * @author created by liyihuanx
 * @date 2021/1/13
 * description: 类的描述
 */
public class IMUtil {
    private static V2TIMMessageManager msgManager = V2TIMManager.getMessageManager();

    /**
     * IM 初始化
     */
    public static void initIM(Context context){
        // 1. 从 IM 控制台获取应用 SDKAppID，详情请参考 SDKAppID。
        // 2. 初始化 config 对象
        V2TIMSDKConfig config = new V2TIMSDKConfig();
        // 3. 指定 log 输出级别，详情请参考 SDKConfig。
        config.setLogLevel(V2TIMSDKConfig.V2TIM_LOG_DEBUG);
        // 4. 初始化 SDK 并设置 V2TIMSDKListener 的监听对象。
        // initSDK 后 SDK 会自动连接网络，网络连接状态可以在 V2TIMSDKListener 回调里面监听。
        V2TIMManager.getInstance().initSDK(context, 1400453102, config, new V2TIMSDKListener() {
            // 5. 监听 V2TIMSDKListener 回调
            @Override
            public void onConnecting() {
                // 正在连接到腾讯云服务器
                Log.d("QWER", "正在连接到腾讯云服务器");
            }

            @Override
            public void onConnectSuccess() {
                // 已经成功连接到腾讯云服务器
                Log.d("QWER", "已经成功连接到腾讯云服务器");

            }

            @Override
            public void onConnectFailed(int code, String error) {
                // 连接腾讯云服务器失败
                Log.d("QWER", "连接腾讯云服务器失败");
            }
        });
    }

    /**
     * IM登录
     * @param userId
     */
    public static void loginIM(final String userId){
        V2TIMManager.getInstance().login(userId, GenerateTestUserSig.genTestUserSig(userId), new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                Log.d("QWER", "loginIM-onError: " + code + "----" + desc);
            }

            @Override
            public void onSuccess() {
                Log.d("QWER", userId+"loginIM-onSuccess: ");

            }
        });
    }




    /**
     * 创建富文本消息
     * @param msg
     * @param receiver
     */
    public static V2TIMMessage createAdvancedMsg(String msg, String receiver) {
        V2TIMMessage v2TIMMessage = msgManager.createTextMessage(msg);
        msgManager.sendMessage(v2TIMMessage, receiver, null,
                V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null, new V2TIMSendCallback<V2TIMMessage>() {
                    @Override
                    public void onProgress(int progress) {
                        Log.d("QWER", "createAdvancedMsg-onProgress: " + progress);
                    }

                    @Override
                    public void onError(int code, String desc) {
                        Log.d("QWER", "createAdvancedMsg-onError: " + code + "----" + desc);

                    }

                    @Override
                    public void onSuccess(V2TIMMessage v2TIMMessage) {
                        Log.d("QWER", "createAdvancedMsg-onSuccess: " + new Gson().toJson(v2TIMMessage));
                    }
                });
        return v2TIMMessage;
    }

    /**
     * 解析消息
     * @param msg
     */
    public static void decodeMsg(V2TIMMessage msg) {
        int elemType = msg.getElemType();
        switch (elemType){
            case V2TIMMessage.V2TIM_ELEM_TYPE_TEXT :
                // 文本消息
                V2TIMTextElem v2TIMTextElem = msg.getTextElem();
                String text = v2TIMTextElem.getText();
//

                break;
            default:
                break;
        }
    }

    /**
     *  撤回消息
     */
    public static void revokeMessage(V2TIMMessage v2TIMMessage) {
        V2TIMManager.getMessageManager().revokeMessage(v2TIMMessage, new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                // 撤回消息失败
            }

            @Override
            public void onSuccess() {
                // 撤回消息成功
                Log.d("QWER", "onSuccess: ");
            }
        });
    }

    /**
     * 拉取历史消息
     */
    public static void HistoryMsg() {
        // 第一次拉取 lastMsg 传 null，表示从最新的消息开始拉取 20 条消息
        V2TIMManager.getMessageManager().getGroupHistoryMessageList("groupA", 20, null, new V2TIMValueCallback<List<V2TIMMessage>>() {
            @Override
            public void onError(int code, String desc) {
                // 拉取失败
            }

            @Override
            public void onSuccess(List<V2TIMMessage> v2TIMMessages) {
                // 分页拉取返回的消息默认是按照从新到旧排列
                if (v2TIMMessages.size() > 0) {
                    // 获取下一次分页拉取的起始消息
                    V2TIMMessage lastMsg = v2TIMMessages.get(v2TIMMessages.size() - 1);
                    // 拉取剩下的20条消息
                    V2TIMManager.getMessageManager().getGroupHistoryMessageList("groupA", 20, lastMsg, new V2TIMValueCallback<List<V2TIMMessage>>() {
                        @Override
                        public void onError(int code, String desc) {
                            // 拉取消息失败
                        }

                        @Override
                        public void onSuccess(List<V2TIMMessage> v2TIMMessages) {
                            // 拉取消息结束
                        }
                    });
                }
            }
        });
    }

    /**
     * 接收方标记消息已读
     * @param userId
     */
    public static void sendHasRead(String userId){
        //将来自 haven 的消息均标记为已读
        V2TIMManager.getMessageManager().markC2CMessageAsRead(userId, new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                // 设置消息已读失败
            }
            @Override
            public void onSuccess() {
                // 设置消息已读成功
                Log.d("QWER", "消息已读 ");
            }
        });
    }


}
