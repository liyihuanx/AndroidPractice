package liyihuan.app.android.androidpractice.imdemo;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMCustomElem;
import com.tencent.imsdk.v2.V2TIMDownloadCallback;
import com.tencent.imsdk.v2.V2TIMElem;
import com.tencent.imsdk.v2.V2TIMFaceElem;
import com.tencent.imsdk.v2.V2TIMFileElem;
import com.tencent.imsdk.v2.V2TIMGroupChangeInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberChangeInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMGroupTipsElem;
import com.tencent.imsdk.v2.V2TIMImageElem;
import com.tencent.imsdk.v2.V2TIMLocationElem;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMSendCallback;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.imsdk.v2.V2TIMSoundElem;
import com.tencent.imsdk.v2.V2TIMTextElem;
import com.tencent.imsdk.v2.V2TIMUserInfo;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.imsdk.v2.V2TIMVideoElem;

import java.io.File;
import java.util.List;

/**
 * @author created by liyihuanx
 * @date 2021/1/13
 * description: 保存一些IM消息发送的方法
 */
public class SaveSomething {
    /**
     * 发送图片消息
     * @param path
     * @param userid
     */
    private void createImgMsg(String path, String userid) {
        // 创建图片消息
        V2TIMMessage v2TIMMessage = V2TIMManager.getMessageManager().createImageMessage(path);
        // 发送图片消息
        V2TIMManager.getMessageManager().sendMessage(v2TIMMessage, userid, null,
                V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null,  new V2TIMSendCallback<V2TIMMessage>() {
                    @Override
                    public void onError(int code, String desc) {
                        // 图片消息发送失败
                        Log.d("QWER", "ImgMsg-onError: " + code + "----" + desc);

                    }
                    @Override
                    public void onSuccess(V2TIMMessage v2TIMMessage) {
                        // 图片消息发送成功
                        Log.d("QWER", "ImgMsg-onSuccess: " + new Gson().toJson(v2TIMMessage));

                    }
                    @Override
                    public void onProgress(int progress) {
                        // 图片上传进度（0-100）

                    }
                });
    }

    /**
     * 发送和接收C2C消息
     * @param msg
     * @param receiver
     */
    private void sendC2CTextMsg(String msg,String receiver){
        V2TIMManager.getInstance().sendC2CTextMessage(msg, receiver, new V2TIMValueCallback<V2TIMMessage>() {
            @Override
            public void onError(int code, String desc) {
                Log.d("QWER", "sendC2CTextMsg-onError: " + code + "----" + desc);
            }

            @Override
            public void onSuccess(V2TIMMessage v2TIMMessage) {
                Log.d("QWER", "sendC2CTextMsg-onSuccess: "+v2TIMMessage.getUserID());
            }
        });
    }

    private void receiveC2CTextMsg(){
        V2TIMManager.getInstance().addSimpleMsgListener(new V2TIMSimpleMsgListener() {
            @Override
            public void onRecvC2CTextMessage(String msgID, V2TIMUserInfo sender, String text) {
                super.onRecvC2CTextMessage(msgID, sender, text);
                Log.d("QWER", "onRecvC2CTextMessage: " + msgID + "---" + sender.getUserID() + "---" + text);
            }

            @Override
            public void onRecvC2CCustomMessage(String msgID, V2TIMUserInfo sender, byte[] customData) {
                super.onRecvC2CCustomMessage(msgID, sender, customData);
            }

            @Override
            public void onRecvGroupTextMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, String text) {
                super.onRecvGroupTextMessage(msgID, groupID, sender, text);
            }

            @Override
            public void onRecvGroupCustomMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, byte[] customData) {
                super.onRecvGroupCustomMessage(msgID, groupID, sender, customData);
            }
        });
    }



    /**
     * 解析消息
     * @param msg
     */
    private void decodeMsg(V2TIMMessage msg){
        int elemType = msg.getElemType();
        if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_TEXT) {
            // 文本消息
            V2TIMTextElem v2TIMTextElem = msg.getTextElem();
            String text = v2TIMTextElem.getText();

        } else if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM) {
            // 自定义消息
            V2TIMCustomElem v2TIMCustomElem = msg.getCustomElem();
            byte[] customData = v2TIMCustomElem.getData();
        } else if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE) {
            // 图片消息
            V2TIMImageElem v2TIMImageElem = msg.getImageElem();
            // 一个图片消息会包含三种格式大小的图片，分别为原图、大图、微缩图（SDK 会在发送图片消息的时候自动生成微缩图、大图，客户不需要关心）
            // 大图：是将原图等比压缩，压缩后宽、高中较小的一个等于720像素。
            // 缩略图：是将原图等比压缩，压缩后宽、高中较小的一个等于198像素。
            List<V2TIMImageElem.V2TIMImage> imageList = v2TIMImageElem.getImageList();
            for (V2TIMImageElem.V2TIMImage v2TIMImage : imageList) {
                // 图片 ID，内部标识，可用于外部缓存 key
                String uuid = v2TIMImage.getUUID();
                // 图片类型
                int imageType = v2TIMImage.getType();
                // 图片大小（字节）
                int size = v2TIMImage.getSize();
                // 图片宽度
                int width = v2TIMImage.getWidth();
                // 图片高度
                int height = v2TIMImage.getHeight();
                // 设置图片下载路径 imagePath，这里可以用 uuid 作为标识，避免重复下载
                String imagePath = "/sdcard/im/image/" + "myUserID" + uuid;
                File imageFile = new File(imagePath);
                // 判断 imagePath 下有没有已经下载过的图片文件
                if (!imageFile.exists()) {
                    // 下载图片
                    v2TIMImage.downloadImage(imagePath, new V2TIMDownloadCallback() {
                        @Override
                        public void onProgress(V2TIMElem.V2ProgressInfo progressInfo) {
                            // 下载进度回调：已下载大小 v2ProgressInfo.getCurrentSize()；总文件大小 v2ProgressInfo.getTotalSize()
                        }
                        @Override
                        public void onError(int code, String desc) {
                            // 下载失败
                            Log.d("QWER", "onError: ");

                        }
                        @Override
                        public void onSuccess() {
                            // 下载完成
                            Log.d("QWER", "onSuccess: ");
                        }
                    });
                    Log.d("QWER", "decodeMsg: " + imagePath);

                } else {
                    // 图片已存在
                    Log.d("QWER", "decodeMsg: 图片已存在");
                }
            }
        } else if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_SOUND) {
            // 语音消息
            V2TIMSoundElem v2TIMSoundElem = msg.getSoundElem();
            // 语音 ID,内部标识，可用于外部缓存 key
            String uuid = v2TIMSoundElem.getUUID();
            // 语音文件大小
            int dataSize = v2TIMSoundElem.getDataSize();
            // 语音时长
            int duration = v2TIMSoundElem.getDuration();
            // 设置语音文件路径 soundPath，这里可以用 uuid 作为标识，避免重复下载
            String soundPath = "/sdcard/im/sound/" + "myUserID" + uuid;
            File imageFile = new File(soundPath);
            // 判断 soundPath 下有没有已经下载过的语音文件
            if (!imageFile.exists()) {
                v2TIMSoundElem.downloadSound(soundPath, new V2TIMDownloadCallback() {
                    @Override
                    public void onProgress(V2TIMElem.V2ProgressInfo progressInfo) {
                        // 下载进度回调：已下载大小 v2ProgressInfo.getCurrentSize()；总文件大小 v2ProgressInfo.getTotalSize()
                    }
                    @Override
                    public void onError(int code, String desc) {
                        // 下载失败
                    }
                    @Override
                    public void onSuccess() {
                        // 下载完成
                    }
                });
            } else {
                // 文件已存在
            }
        } else if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO) {
            // 视频消息
            V2TIMVideoElem v2TIMVideoElem = msg.getVideoElem();
            // 视频截图 ID,内部标识，可用于外部缓存 key
            String snapshotUUID = v2TIMVideoElem.getSnapshotUUID();
            // 视频截图文件大小
            int snapshotSize = v2TIMVideoElem.getSnapshotSize();
            // 视频截图宽
            int snapshotWidth = v2TIMVideoElem.getSnapshotWidth();
            // 视频截图高
            int snapshotHeight = v2TIMVideoElem.getSnapshotHeight();
            // 视频 ID,内部标识，可用于外部缓存 key
            String videoUUID = v2TIMVideoElem.getVideoUUID();
            // 视频文件大小
            int videoSize = v2TIMVideoElem.getVideoSize();
            // 视频时长
            int duration = v2TIMVideoElem.getDuration();
            // 设置视频截图文件路径，这里可以用 uuid 作为标识，避免重复下载
            String snapshotPath = "/sdcard/im/snapshot/" + "myUserID" + snapshotUUID;
            File snapshotFile = new File(snapshotPath);
            if (!snapshotFile.exists()) {
                v2TIMVideoElem.downloadSnapshot(snapshotPath, new V2TIMDownloadCallback() {
                    @Override
                    public void onProgress(V2TIMElem.V2ProgressInfo progressInfo) {
                        // 下载进度回调：已下载大小 v2ProgressInfo.getCurrentSize()；总文件大小 v2ProgressInfo.getTotalSize()
                    }
                    @Override
                    public void onError(int code, String desc) {
                        // 下载失败
                    }
                    @Override
                    public void onSuccess() {
                        // 下载完成
                    }
                });
            } else {
                // 文件已存在
            }

            // 设置视频文件路径，这里可以用 uuid 作为标识，避免重复下载
            String videoPath = "/sdcard/im/video/" + "myUserID" + snapshotUUID;
            File videoFile = new File(videoPath);
            if (!snapshotFile.exists()) {
                v2TIMVideoElem.downloadSnapshot(videoPath, new V2TIMDownloadCallback() {
                    @Override
                    public void onProgress(V2TIMElem.V2ProgressInfo progressInfo) {
                        // 下载进度回调：已下载大小 v2ProgressInfo.getCurrentSize()；总文件大小 v2ProgressInfo.getTotalSize()
                    }
                    @Override
                    public void onError(int code, String desc) {
                        // 下载失败
                    }
                    @Override
                    public void onSuccess() {
                        // 下载完成
                    }
                });
            } else {
                // 文件已存在
            }
        } else if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_FILE) {
            // 文件消息
            V2TIMFileElem v2TIMFileElem = msg.getFileElem();
            // 文件 ID,内部标识，可用于外部缓存 key
            String uuid = v2TIMFileElem.getUUID();
            // 文件名称
            String fileName = v2TIMFileElem.getFileName();
            // 文件大小
            int fileSize = v2TIMFileElem.getFileSize();
            // 设置文件路径，这里可以用 uuid 作为标识，避免重复下载
            String filePath = "/sdcard/im/file/" + "myUserID" + uuid;
            File file = new File(filePath);
            if (!file.exists()) {
                v2TIMFileElem.downloadFile(filePath, new V2TIMDownloadCallback() {
                    @Override
                    public void onProgress(V2TIMElem.V2ProgressInfo progressInfo) {
                        // 下载进度回调：已下载大小 v2ProgressInfo.getCurrentSize()；总文件大小 v2ProgressInfo.getTotalSize()
                    }
                    @Override
                    public void onError(int code, String desc) {
                        // 下载失败
                    }
                    @Override
                    public void onSuccess() {
                        // 下载完成
                    }
                });
            } else {
                // 文件已存在
            }
        } else if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_LOCATION) {
            // 地理位置消息
            V2TIMLocationElem v2TIMLocationElem = msg.getLocationElem();
            // 地理位置信息描述
            String desc = v2TIMLocationElem.getDesc();
            // 经度
            double longitude = v2TIMLocationElem.getLongitude();
            // 纬度
            double latitude = v2TIMLocationElem.getLatitude();
        } else if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_FACE) {
            // 表情消息
            V2TIMFaceElem v2TIMFaceElem = msg.getFaceElem();
            // 表情所在的位置
            int index = v2TIMFaceElem.getIndex();
            // 表情自定义数据
            byte[] data = v2TIMFaceElem.getData();
        } else if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_GROUP_TIPS) {
            // 群 tips 消息
            V2TIMGroupTipsElem v2TIMGroupTipsElem = msg.getGroupTipsElem();
            // 所属群组
            String groupId = v2TIMGroupTipsElem.getGroupID();
            // 群Tips类型
            int type = v2TIMGroupTipsElem.getType();
            // 操作人资料
            V2TIMGroupMemberInfo opMember = v2TIMGroupTipsElem.getOpMember();
            // 被操作人资料
            List<V2TIMGroupMemberInfo> memberList = v2TIMGroupTipsElem.getMemberList();
            // 群信息变更详情
            List<V2TIMGroupChangeInfo> groupChangeInfoList = v2TIMGroupTipsElem.getGroupChangeInfoList();
            // 群成员变更信息
            List<V2TIMGroupMemberChangeInfo> memberChangeInfoList = v2TIMGroupTipsElem.getMemberChangeInfoList();
            // 当前群在线人数
            int memberCount = v2TIMGroupTipsElem.getMemberCount();
        }
    }

    /**
     * 打开相册
     */
    private static final int CHOOSE_PHOTO = 385;
    private void openAlbum() {
//        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        openAlbumIntent.setType("image/*");
//        startActivityForResult(openAlbumIntent, CHOOSE_PHOTO);//打开相册
    }

    /*相机或者相册返回来的数据*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case CHOOSE_PHOTO:
//                if (data == null) {//如果没有选取照片，则直接返回
//                    return;
//                }
//                Log.i("QWER", "onActivityResult: ImageUriFromAlbum: " + data.getData());
//                if (resultCode == RESULT_OK) {
//                    handleImageOnKitKat(data);//4.4之后图片解析
//                }
//                break;
//            default:
//                break;
//        }
    }


    /**
     * 4.4版本以上对返回的图片Uri的处理：
     * 返回的Uri是经过封装的，要进行处理才能得到真实路径
     * @param data 调用系统相册之后返回的Uri
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
//        String imagePath = null;
//        Uri uri = data.getData();
//        if (DocumentsContract.isDocumentUri(this, uri)) {
//            //如果是document类型的Uri，则提供document id处理
//            String docId = DocumentsContract.getDocumentId(uri);
//            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
//                String id = docId.split(":")[1];//解析出数字格式的id
//                String selection = MediaStore.Images.Media._ID + "=" + id;
//                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
//            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
//                imagePath = getImagePath(contentUri, null);
//            }
//        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            //如果是content类型的uri，则进行普通处理
//            imagePath = getImagePath(uri, null);
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            //如果是file类型的uri，则直接获取路径
//            imagePath = uri.getPath();
//        }
//        Log.d("QWER", "handleImageOnKitKat: " + imagePath);
//        // 发送文本消息
//        createImgMsg(imagePath,"liyihuanx");
    }

    /**
     * 将Uri转化为路径
     * @param uri 要转化的Uri
     * @param selection 4.4之后需要解析Uri，因此需要该参数
     * @return 转化之后的路径
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
//        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//            cursor.close();
//        }
        return path;
    }
}
