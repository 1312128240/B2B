package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6 0006.
 */

public class LogisticsData {

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

        private String payment_status;
        private String express_status;
        private String status;
        private String user_id;
        private List<LogisticsBean> logistics;

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        public String getExpress_status() {
            return express_status;
        }

        public void setExpress_status(String express_status) {
            this.express_status = express_status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public List<LogisticsBean> getLogistics() {
            return logistics;
        }

        public void setLogistics(List<LogisticsBean> logistics) {
            this.logistics = logistics;
        }

        public static class LogisticsBean {

            private String title;
            private String time;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
