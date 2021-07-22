package liyihuan.app.android.androidpractice.recyclerview;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.ArrayList;

/**
 * @author created by liyihuanx
 * @date 2021/7/21
 * description: 类的描述
 */
public class SectionBean extends SectionEntity<SectionBean.UserList.UserBean> {


    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionBean(UserList.UserBean userBean) {
        super(userBean);
    }

    public static class UserList {
        public UserList(ArrayList<UserBean> list) {
            this.list = list;
        }

        private ArrayList<UserBean> list;

        public ArrayList<UserBean> getList() {
            return list;
        }

        public void setList(ArrayList<UserBean> list) {
            this.list = list;
        }

        public static class UserBean {
            private String username;
            private String userID;

            public UserBean(String userID, String username) {
                this.username = username;
                this.userID = userID;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getUserID() {
                return userID;
            }

            public void setUserID(String userID) {
                this.userID = userID;
            }
        }
    }


}
