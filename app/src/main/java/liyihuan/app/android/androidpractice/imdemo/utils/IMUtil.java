package liyihuan.app.android.androidpractice.imdemo.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMConversationListener;
import com.tencent.imsdk.v2.V2TIMConversationResult;
import com.tencent.imsdk.v2.V2TIMCreateGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMGroupInfo;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMMessageManager;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;
import com.tencent.imsdk.v2.V2TIMSendCallback;
import com.tencent.imsdk.v2.V2TIMTextElem;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import liyihuan.app.android.androidpractice.imdemo.GenerateTestUserSig;
import liyihuan.app.android.androidpractice.imdemo.conversation.ConversationCallback;
import liyihuan.app.android.androidpractice.imdemo.imchat.IMChatCallback;

/**
 * @author created by liyihuanx
 * @date 2021/1/13
 * description: 类的描述
 */
public class IMUtil {
    private static V2TIMMessageManager msgManager = V2TIMManager.getMessageManager();
    public static String username = "";
    public static String to = "";

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


    public static void getHistoryPersonalMsg(String userID, IMChatCallback callback) {
        // 第一次拉取 lastMsg 传 null，表示从最新的消息开始拉取 20 条消息
        V2TIMManager.getMessageManager().getC2CHistoryMessageList(userID, 200, null, new V2TIMValueCallback<List<V2TIMMessage>>() {
            @Override
            public void onError(int code, String desc) {
                // 拉取失败
            }

            @Override
            public void onSuccess(List<V2TIMMessage> v2TIMMessages) {
                // 分页拉取返回的消息默认是按照从新到旧排列
                if (v2TIMMessages.size() > 0) {
                    callback.getC2CHistory(v2TIMMessages);
                    // 获取下一次分页拉取的起始消息
                    V2TIMMessage lastMsg = v2TIMMessages.get(v2TIMMessages.size() - 1);
                    // 拉取剩下的20条消息
                    V2TIMManager.getMessageManager().getC2CHistoryMessageList(userID, 200, lastMsg, new V2TIMValueCallback<List<V2TIMMessage>>() {
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
     * 拉取群聊历史消息
     */
    public static void HistoryGroupMsg() {
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


    public static void getConversationHistory(ConversationCallback callback){
        // 1. 设置会话监听
        V2TIMManager.getConversationManager().setConversationListener(new V2TIMConversationListener() {
            // 3.1 收到会话新增的回调
            @Override
            public void onNewConversation(List<V2TIMConversation> conversationList) {
                updateConversation(conversationList, true,callback);
            }

            // 3.2 收到会话更新的回调
            @Override
            public void onConversationChanged(List<V2TIMConversation> conversationList) {
                updateConversation(conversationList, true,callback);
            }
        });
        // 2. 先拉取50个本地会话做 UI 展示，nextSeq 第一次拉取传0
        V2TIMManager.getConversationManager().getConversationList(0, 20,
                new V2TIMValueCallback<V2TIMConversationResult>() {
                    @Override
                    public void onError(int code, String desc) {
                        // 拉取会话列表失败
                    }

                    @Override
                    public void onSuccess(V2TIMConversationResult v2TIMConversationResult) {
                        Log.d("QWER", "onSuccess: " + new Gson().toJson(v2TIMConversationResult.getConversationList()));

                        // 拉取成功，更新 UI 会话列表
                        updateConversation(v2TIMConversationResult.getConversationList(), false,callback);
                        if (!v2TIMConversationResult.isFinished()) {
                            V2TIMManager.getConversationManager().getConversationList(v2TIMConversationResult.getNextSeq(), 20, new V2TIMValueCallback<V2TIMConversationResult>() {
                                @Override
                                public void onError(int code, String desc) {
                                }

                                @Override
                                public void onSuccess(V2TIMConversationResult v2TIMConversationResult) {
                                    // 拉取成功，更新 UI 会话列表
                                    updateConversation(v2TIMConversationResult.getConversationList(), false,callback);
                                }
                            });
                        }
                    }
                });
    }


    private static void updateConversation(List<V2TIMConversation> convList, boolean needSort,ConversationCallback callback) {
        for (int i = 0; i < convList.size(); i++) {
            V2TIMConversation conv = convList.get(i);
            boolean isExit = false;
            for (int j = 0; j < convList.size(); j++) {
                V2TIMConversation uiConv = convList.get(j);
                // UI 会话列表存在该会话，则替换
                if (uiConv.getConversationID().equals(conv.getConversationID())) {
                    convList.set(j, conv);
                    isExit = true;
                    break;
                }
            }
            // UI 会话列表没有该会话，则新增
            if (!isExit) {
                convList.add(conv);
            }
        }
        // 4. 按照会话 lastMessage 的 timestamp 对 UI 会话列表做排序并更新界面
        if (needSort) {
            Collections.sort(convList, new Comparator<V2TIMConversation>() {
                @Override
                public int compare(V2TIMConversation o1, V2TIMConversation o2) {
                    if (o1.getLastMessage().getTimestamp() > o2.getLastMessage().getTimestamp()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
        }
        callback.getConversation(convList);
        Log.d("QWER", "onSuccess: " + new Gson().toJson(convList));

    }

    /**
     * 创建群
     * @param GroupName
     * @param GroupType
     * @param memberInfoList
     */
    public static void createGroup(String GroupName, String GroupType, List<V2TIMCreateGroupMemberInfo> memberInfoList) {
        V2TIMGroupInfo v2TIMGroupInfo = new V2TIMGroupInfo();
        v2TIMGroupInfo.setGroupName(GroupName);
        v2TIMGroupInfo.setGroupType(GroupType);
        v2TIMGroupInfo.setIntroduction("this is a test Work group");
        // TODO 加入成员
//        List<V2TIMCreateGroupMemberInfo> memberInfoList = new ArrayList<>();
//        V2TIMCreateGroupMemberInfo memberA = new V2TIMCreateGroupMemberInfo();
//        memberA.setUserID("vinson");
//        V2TIMCreateGroupMemberInfo memberB = new V2TIMCreateGroupMemberInfo();
//        memberB.setUserID("park");
//        memberInfoList.add(memberA);
//        memberInfoList.add(memberB);
        V2TIMManager.getGroupManager().createGroup(
                v2TIMGroupInfo, memberInfoList, new V2TIMValueCallback<String>() {
                    @Override
                    public void onError(int code, String desc) {
                        // 创建失败
                    }
                    @Override
                    public void onSuccess(String groupID) {
                        // 创建成功
                    }
                });
    }

    /**
     * 禁言某人
     * @param GroupName
     * @param memberID
     * @param second
     * @param callback
     */
    public static void muteGroupMember(String GroupName,String memberID,int second,ConversationCallback callback){
        V2TIMManager.getGroupManager().muteGroupMember(GroupName, memberID, second, new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {

            }

            @Override
            public void onSuccess() {

            }
        });
    }
}
