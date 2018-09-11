package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/15 0015.
 */

public class FindShopsBenn {

    private String msg;
    private int status;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String ID;
        private String Username;
        private String shop_name;
        private String shop_img;
        private String day_click_num;
        private Object last_click_time;
        private String img;
        private List<String> imgs;
        private int day_click;
        private List<GoodsBean> goods;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getUsername() {
            return Username;
        }

        public void setUsername(String Username) {
            this.Username = Username;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_img() {
            return shop_img;
        }

        public void setShop_img(String shop_img) {
            this.shop_img = shop_img;
        }

        public String getDay_click_num() {
            return day_click_num;
        }

        public void setDay_click_num(String day_click_num) {
            this.day_click_num = day_click_num;
        }

        public Object getLast_click_time() {
            return last_click_time;
        }

        public void setLast_click_time(Object last_click_time) {
            this.last_click_time = last_click_time;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public int getDay_click() {
            return day_click;
        }

        public void setDay_click(int day_click) {
            this.day_click = day_click;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * ID : 735
             * img_url : https://img.aiucar.cn/goods_img/20171225/2017122591034949190.jpg
             * b2bs : 0
             */

            private String ID;
            private String img_url;
            private String b2bs;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getB2bs() {
                return b2bs;
            }

            public void setB2bs(String b2bs) {
                this.b2bs = b2bs;
            }
        }
    }
}
