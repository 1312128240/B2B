package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/23 0023.
 */

public class MyGoldBean {

    private String msg;
    private int status;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private UserBean user;
        private List<LogBean> log;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<LogBean> getLog() {
            return log;
        }

        public void setLog(List<LogBean> log) {
            this.log = log;
        }

        public static class UserBean {


            private String gold;
            private String sign_in;
            private String sign_in_time;
            private String is_sign_in;
            private String msg;

            public String getGold() {
                return gold;
            }

            public void setGold(String gold) {
                this.gold = gold;
            }

            public String getSign_in() {
                return sign_in;
            }

            public void setSign_in(String sign_in) {
                this.sign_in = sign_in;
            }

            public String getSign_in_time() {
                return sign_in_time;
            }

            public void setSign_in_time(String sign_in_time) {
                this.sign_in_time = sign_in_time;
            }

            public String getIs_sign_in() {
                return is_sign_in;
            }

            public void setIs_sign_in(String is_sign_in) {
                this.is_sign_in = is_sign_in;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }
        }

        public static class LogBean {


            private String id;
            private String user_id;
            private String type;
            private String number;
            private String add_time;
            private String is_del;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getIs_del() {
                return is_del;
            }

            public void setIs_del(String is_del) {
                this.is_del = is_del;
            }
        }
    }
}
