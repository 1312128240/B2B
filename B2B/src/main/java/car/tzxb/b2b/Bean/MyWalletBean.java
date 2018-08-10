package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/10 0010.
 */

public class MyWalletBean {

    private DataBean data;
    private String status;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private String b2b_balance;
        private List<DetailBean> Detail;

        public String getB2b_balance() {
            return b2b_balance;
        }

        public void setB2b_balance(String b2b_balance) {
            this.b2b_balance = b2b_balance;
        }

        public List<DetailBean> getDetail() {
            return Detail;
        }

        public void setDetail(List<DetailBean> Detail) {
            this.Detail = Detail;
        }

        public static class DetailBean {

            private String id;
            private String user_id;
            private String user_name;
            private String title;
            private String add_time;
            private String is_del;
            private Object order_id;
            private String total_fee;
            private String titles;

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

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public Object getOrder_id() {
                return order_id;
            }

            public void setOrder_id(Object order_id) {
                this.order_id = order_id;
            }

            public String getTotal_fee() {
                return total_fee;
            }

            public void setTotal_fee(String total_fee) {
                this.total_fee = total_fee;
            }

            public String getTitles() {
                return titles;
            }

            public void setTitles(String titles) {
                this.titles = titles;
            }
        }
    }
}
