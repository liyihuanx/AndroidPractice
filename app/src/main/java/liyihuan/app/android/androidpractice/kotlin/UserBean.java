package liyihuan.app.android.androidpractice.kotlin;

/**
 * @author created by liyihuanx
 * @date 2021/4/13
 * description: 类的描述
 */
public class UserBean {

    private String username;
    private int age;

    public UserBean() {

    }

    public UserBean(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
