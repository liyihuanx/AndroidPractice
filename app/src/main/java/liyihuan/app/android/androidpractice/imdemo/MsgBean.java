package liyihuan.app.android.androidpractice.imdemo;

import android.graphics.Bitmap;

/**
 * @author created by liyihuanx
 * @date 2020/11/30
 * description: 类的描述
 */
public class MsgBean {
    private String textContent;
    private Bitmap ImgContent;

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
}
