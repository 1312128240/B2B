package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

public class EvBean {

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

        private int good;
        private int center;
        private int difference;
        private int amap;
        private int whole;
        private List<EvaluteBean> evalute;

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public int getCenter() {
            return center;
        }

        public void setCenter(int center) {
            this.center = center;
        }

        public int getDifference() {
            return difference;
        }

        public void setDifference(int difference) {
            this.difference = difference;
        }

        public int getAmap() {
            return amap;
        }

        public void setAmap(int amap) {
            this.amap = amap;
        }

        public int getWhole() {
            return whole;
        }

        public void setWhole(int whole) {
            this.whole = whole;
        }

        public List<EvaluteBean> getEvalute() {
            return evalute;
        }

        public void setEvalute(List<EvaluteBean> evalute) {
            this.evalute = evalute;
        }

        public static class EvaluteBean {

            private String head_img;
            private String number;
            private String shop_id;
            private String user_name;
            private String add_time;
            private String remark;
            private String shop_name;
            private String img_url;
            private List<String> img_urls;

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public List<String> getImg_urls() {
                return img_urls;
            }

            public void setImg_urls(List<String> img_urls) {
                this.img_urls = img_urls;
            }
        }
    }
}
