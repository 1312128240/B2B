package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/30 0030.
 */

public class DiscountsBean {

    private String msg;
    private String status;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String id;
        private String product_id;
        private String product_title;
        private String product_img;
        private String product_number;
        private String point;
        private String type;
        private String zp_type;
        private String is_status;
        private Object full_amount;
        private Object reduce_amount;
        private String user_shopid;
        private String title;
        private List<TitlesBean> titles;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_title() {
            return product_title;
        }

        public void setProduct_title(String product_title) {
            this.product_title = product_title;
        }

        public String getProduct_img() {
            return product_img;
        }

        public void setProduct_img(String product_img) {
            this.product_img = product_img;
        }

        public String getProduct_number() {
            return product_number;
        }

        public void setProduct_number(String product_number) {
            this.product_number = product_number;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getZp_type() {
            return zp_type;
        }

        public void setZp_type(String zp_type) {
            this.zp_type = zp_type;
        }

        public String getIs_status() {
            return is_status;
        }

        public void setIs_status(String is_status) {
            this.is_status = is_status;
        }

        public Object getFull_amount() {
            return full_amount;
        }

        public void setFull_amount(Object full_amount) {
            this.full_amount = full_amount;
        }

        public Object getReduce_amount() {
            return reduce_amount;
        }

        public void setReduce_amount(Object reduce_amount) {
            this.reduce_amount = reduce_amount;
        }

        public String getUser_shopid() {
            return user_shopid;
        }

        public void setUser_shopid(String user_shopid) {
            this.user_shopid = user_shopid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<TitlesBean> getTitles() {
            return titles;
        }

        public void setTitles(List<TitlesBean> titles) {
            this.titles = titles;
        }

        public static class TitlesBean {

            private String id;
            private String full_product_id;
            private String zp_pro_id;
            private String zp_number;
            private String zp_pro_img;
            private String is_del;
            private String add_time;
            private String zp_title;
            private String promotion_id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFull_product_id() {
                return full_product_id;
            }

            public void setFull_product_id(String full_product_id) {
                this.full_product_id = full_product_id;
            }

            public String getZp_pro_id() {
                return zp_pro_id;
            }

            public void setZp_pro_id(String zp_pro_id) {
                this.zp_pro_id = zp_pro_id;
            }

            public String getZp_number() {
                return zp_number;
            }

            public void setZp_number(String zp_number) {
                this.zp_number = zp_number;
            }

            public String getZp_pro_img() {
                return zp_pro_img;
            }

            public void setZp_pro_img(String zp_pro_img) {
                this.zp_pro_img = zp_pro_img;
            }

            public String getIs_del() {
                return is_del;
            }

            public void setIs_del(String is_del) {
                this.is_del = is_del;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getZp_title() {
                return zp_title;
            }

            public void setZp_title(String zp_title) {
                this.zp_title = zp_title;
            }

            public String getPromotion_id() {
                return promotion_id;
            }

            public void setPromotion_id(String promotion_id) {
                this.promotion_id = promotion_id;
            }
        }
    }
}
