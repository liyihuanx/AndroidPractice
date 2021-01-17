package liyihuan.app.android.androidpractice.imdemo.imchat;

import android.graphics.Bitmap;

/**
 * @author created by liyihuanx
 * @date 2020/11/30
 * description: 类的描述
 */
public class MsgBean {
    private String textContent;
    private String UserName;
    private Bitmap ImgContent;
    private int type;
    private String msgId;

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Bitmap getImgContent() {
        return ImgContent;
    }

    public void setImgContent(Bitmap imgContent) {
        ImgContent = imgContent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
