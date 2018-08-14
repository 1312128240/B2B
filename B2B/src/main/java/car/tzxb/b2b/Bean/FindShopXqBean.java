package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/13 0013.
 */

public class FindShopXqBean {
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
        private InfoBean Info;
        private int IsCollect;
        private List<GoodsBean> Goods;

        public InfoBean getInfo() {
            return Info;
        }

        public void setInfo(InfoBean Info) {
            this.Info = Info;
        }

        public int getIsCollect() {
            return IsCollect;
        }

        public void setIsCollect(int IsCollect) {
            this.IsCollect = IsCollect;
        }

        public List<GoodsBean> getGoods() {
            return Goods;
        }

        public void setGoods(List<GoodsBean> Goods) {
            this.Goods = Goods;
        }

        public static class InfoBean {
            private String ID;
            private String shop_name;
            private String shop_img;
            private String sales_count;
            private String mobile;
            private String img;
            private String imgs;
            private String Collect;
            private double shop_pf;
            public double getShop_pf() {
                return shop_pf;
            }

            public void setShop_pf(double shop_pf) {
                this.shop_pf = shop_pf;
            }



            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
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

            public String getSales_count() {
                return sales_count;
            }

            public void setSales_count(String sales_count) {
                this.sales_count = sales_count;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getCollect() {
                return Collect;
            }

            public void setCollect(String Collect) {
                this.Collect = Collect;
            }
        }

        public static class GoodsBean {
            private String id;
            private String img_url;
            private String goods_name;
            private String is_point_product;
            private String is_balance_product;
            private String user_id;
            private String category_id;
            private String cate_title;
            private String shop_name;
            private String dealer;
            private String b2bs;
            private String seal_price;
            private String max_seal_price;
            private String min_seal_price;
            private String price;
            private String stock_headquarters;
            private String stock_distributor_all;
            private String minimum_order_quantity;
            private String sales;
            private String cost_point;
            private String min_market_price;
            private String max_market_price;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getIs_point_product() {
                return is_point_product;
            }

            public void setIs_point_product(String is_point_product) {
                this.is_point_product = is_point_product;
            }

            public String getIs_balance_product() {
                return is_balance_product;
            }

            public void setIs_balance_product(String is_balance_product) {
                this.is_balance_product = is_balance_product;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getCate_title() {
                return cate_title;
            }

            public void setCate_title(String cate_title) {
                this.cate_title = cate_title;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public String getDealer() {
                return dealer;
            }

            public void setDealer(String dealer) {
                this.dealer = dealer;
            }

            public String getB2bs() {
                return b2bs;
            }

            public void setB2bs(String b2bs) {
                this.b2bs = b2bs;
            }

            public String getSeal_price() {
                return seal_price;
            }

            public void setSeal_price(String seal_price) {
                this.seal_price = seal_price;
            }

            public String getMax_seal_price() {
                return max_seal_price;
            }

            public void setMax_seal_price(String max_seal_price) {
                this.max_seal_price = max_seal_price;
            }

            public String getMin_seal_price() {
                return min_seal_price;
            }

            public void setMin_seal_price(String min_seal_price) {
                this.min_seal_price = min_seal_price;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStock_headquarters() {
                return stock_headquarters;
            }

            public void setStock_headquarters(String stock_headquarters) {
                this.stock_headquarters = stock_headquarters;
            }

            public String getStock_distributor_all() {
                return stock_distributor_all;
            }

            public void setStock_distributor_all(String stock_distributor_all) {
                this.stock_distributor_all = stock_distributor_all;
            }

            public String getMinimum_order_quantity() {
                return minimum_order_quantity;
            }

            public void setMinimum_order_quantity(String minimum_order_quantity) {
                this.minimum_order_quantity = minimum_order_quantity;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            public String getCost_point() {
                return cost_point;
            }

            public void setCost_point(String cost_point) {
                this.cost_point = cost_point;
            }

            public String getMin_market_price() {
                return min_market_price;
            }

            public void setMin_market_price(String min_market_price) {
                this.min_market_price = min_market_price;
            }

            public String getMax_market_price() {
                return max_market_price;
            }

            public void setMax_market_price(String max_market_price) {
                this.max_market_price = max_market_price;
            }
        }
    }
}
