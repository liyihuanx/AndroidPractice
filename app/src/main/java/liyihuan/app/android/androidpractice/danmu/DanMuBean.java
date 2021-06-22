package liyihuan.app.android.androidpractice.danmu;

/**
 * @author created by liyihuanx
 * @date 2021/6/16
 * description: 类的描述
 */
public class DanMuBean {
    private String name;
    private String content;

    public DanMuBean(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
