package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/10 0010.
 */

public class ActivityDivisionBean {
    private String msg;
    private int status;
    private List<DataBeanX> data;

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

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {

        private String name;
        private List<DataBean> data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            private String goods_id;
            private String product_id;
            private String img_url;
            private String goods_name;
            private String is_point_product;
            private String is_balance_product;
            private String shop_id;
            private String category_id;
            private String cate_title;
            private String shop_name;
            private String product_title;
            private String market_price;
            private String cost_point;
            private String discount_price;
            private String point;
            private String sales;
            private String color_id;
            private String network_id;
            private String name;
            private String miaoshu;
            private String brand_id;
            private String offset;
            private String pic;
            private String p_title;
            private String dealer;
            private String seal_price;
            private String stock_headquarters;
            private String stock_distributor_all;
            private String minimum_order_quantity;
            private String b2bs;
            private List<?> contents_mobi;
            private List<PromotionBean> promotion;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
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

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
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

            public String getProduct_title() {
                return product_title;
            }

            public void setProduct_title(String product_title) {
                this.product_title = product_title;
            }

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public String getCost_point() {
                return cost_point;
            }

            public void setCost_point(String cost_point) {
                this.cost_point = cost_point;
            }

            public String getDiscount_price() {
                return discount_price;
            }

            public void setDiscount_price(String discount_price) {
                this.discount_price = discount_price;
            }

            public String getPoint() {
                return point;
            }

            public void setPoint(String point) {
                this.point = point;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            public String getColor_id() {
                return color_id;
            }

            public void setColor_id(String color_id) {
                this.color_id = color_id;
            }

            public String getNetwork_id() {
                return network_id;
            }

            public void setNetwork_id(String network_id) {
                this.network_id = network_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMiaoshu() {
                return miaoshu;
            }

            public void setMiaoshu(String miaoshu) {
                this.miaoshu = miaoshu;
            }

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public String getOffset() {
                return offset;
            }

            public void setOffset(String offset) {
                this.offset = offset;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getP_title() {
                return p_title;
            }

            public void setP_title(String p_title) {
                this.p_title = p_title;
            }

            public String getDealer() {
                return dealer;
            }

            public void setDealer(String dealer) {
                this.dealer = dealer;
            }

            public String getSeal_price() {
                return seal_price;
            }

            public void setSeal_price(String seal_price) {
                this.seal_price = seal_price;
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

            public String getB2bs() {
                return b2bs;
            }

            public void setB2bs(String b2bs) {
                this.b2bs = b2bs;
            }

            public List<?> getContents_mobi() {
                return contents_mobi;
            }

            public void setContents_mobi(List<?> contents_mobi) {
                this.contents_mobi = contents_mobi;
            }

            public List<PromotionBean> getPromotion() {
                return promotion;
            }

            public void setPromotion(List<PromotionBean> promotion) {
                this.promotion = promotion;
            }

            public static class PromotionBean {

                private String id;
                private String start_time;
                private String end_time;
                private String full_amount;
                private String reduce_amount;
                private String type;
                private String zp_type;
                private String title;
                private List<?> gift;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getStart_time() {
                    return start_time;
                }

                public void setStart_time(String start_time) {
                    this.start_time = start_time;
                }

                public String getEnd_time() {
                    return end_time;
                }

                public void setEnd_time(String end_time) {
                    this.end_time = end_time;
                }

                public String getFull_amount() {
                    return full_amount;
                }

                public void setFull_amount(String full_amount) {
                    this.full_amount = full_amount;
                }

                public String getReduce_amount() {
                    return reduce_amount;
                }

                public void setReduce_amount(String reduce_amount) {
                    this.reduce_amount = reduce_amount;
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

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public List<?> getGift() {
                    return gift;
                }

                public void setGift(List<?> gift) {
                    this.gift = gift;
                }
            }
        }
    }
}
