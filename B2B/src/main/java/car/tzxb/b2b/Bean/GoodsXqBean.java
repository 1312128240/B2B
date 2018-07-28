package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/15 0015.
 */

public class GoodsXqBean {


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


        private GoodsBean goods;
        private CommentBean comment;
        private ShopBean shop;
        private List<ProductBean> product;
        private List<HotBean> hot;

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public ShopBean getShop() {
            return shop;
        }

        public void setShop(ShopBean shop) {
            this.shop = shop;
        }

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public List<HotBean> getHot() {
            return hot;
        }

        public void setHot(List<HotBean> hot) {
            this.hot = hot;
        }

        public static class GoodsBean {

            private String goods_id;
            private String title;
            private String flag;
            private String is_del;
            private String dealer;
            private String sales;
            private String img_url;
            private String user_id;
            private String user_type;
            private String max_market_price;
            private String min_market_price;
            private String m_price;
            private String price;
            private String max_seal_price;
            private String min_seal_price;
            private String stock_distributor_all;
            private String minimum_order_quantity;
            private String province;
            private String city;
            private String area;
            private String collect;
            private List<String> banner;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public String getIs_del() {
                return is_del;
            }

            public void setIs_del(String is_del) {
                this.is_del = is_del;
            }

            public String getDealer() {
                return dealer;
            }

            public void setDealer(String dealer) {
                this.dealer = dealer;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
            }

            public String getMax_market_price() {
                return max_market_price;
            }

            public void setMax_market_price(String max_market_price) {
                this.max_market_price = max_market_price;
            }

            public String getMin_market_price() {
                return min_market_price;
            }

            public void setMin_market_price(String min_market_price) {
                this.min_market_price = min_market_price;
            }

            public String getM_price() {
                return m_price;
            }

            public void setM_price(String m_price) {
                this.m_price = m_price;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getCollect() {
                return collect;
            }

            public void setCollect(String collect) {
                this.collect = collect;
            }

            public List<String> getBanner() {
                return banner;
            }

            public void setBanner(List<String> banner) {
                this.banner = banner;
            }
        }

        public static class CommentBean {

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

        public static class ShopBean {

            private String ID;
            private String shop_name;
            private String shop_img;
            private String shop_type;
            private String mobile;
            private Object Tel;
            private String shop_address;
            private String coordinate_Latitude;
            private String coordinate_Longitude;
            private String sales_count;
            private String province;
            private String city;
            private String area;
            private String shop_tj;
            private String shop_pf;
            private String collect;
            private String tag;
            private String img;
            private List<String> imgs;

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

            public String getShop_type() {
                return shop_type;
            }

            public void setShop_type(String shop_type) {
                this.shop_type = shop_type;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public Object getTel() {
                return Tel;
            }

            public void setTel(Object Tel) {
                this.Tel = Tel;
            }

            public String getShop_address() {
                return shop_address;
            }

            public void setShop_address(String shop_address) {
                this.shop_address = shop_address;
            }

            public String getCoordinate_Latitude() {
                return coordinate_Latitude;
            }

            public void setCoordinate_Latitude(String coordinate_Latitude) {
                this.coordinate_Latitude = coordinate_Latitude;
            }

            public String getCoordinate_Longitude() {
                return coordinate_Longitude;
            }

            public void setCoordinate_Longitude(String coordinate_Longitude) {
                this.coordinate_Longitude = coordinate_Longitude;
            }

            public String getSales_count() {
                return sales_count;
            }

            public void setSales_count(String sales_count) {
                this.sales_count = sales_count;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getShop_tj() {
                return shop_tj;
            }

            public void setShop_tj(String shop_tj) {
                this.shop_tj = shop_tj;
            }

            public String getShop_pf() {
                return shop_pf;
            }

            public void setShop_pf(String shop_pf) {
                this.shop_pf = shop_pf;
            }

            public String getCollect() {
                return collect;
            }

            public void setCollect(String collect) {
                this.collect = collect;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
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
        }

        public static class ProductBean {

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
            private String seal_price;
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
            private String pics;
            private String p_title;
            private String dealer;
            private String stock_distributor_all;
            private int minimum_order_quantity;
            private List<String> contents_mobi;
            private List<String> imgs;
            private String color_name;
            private String network_name;
            private List<GoodsXqBean.DataBean.ProductBean.PromotionBean> promotion;

            public List<GoodsXqBean.DataBean.ProductBean.PromotionBean> getPromotion() {
                return promotion;
            }

            public void setPromotion(List<GoodsXqBean.DataBean.ProductBean.PromotionBean> promotion) {
                this.promotion = promotion;
            }

            public String getColor_name() {
                return color_name;
            }

            public void setColor_name(String color_name) {
                this.color_name = color_name;
            }

            public String getNetwork_name() {
                return network_name;
            }

            public void setNetwork_name(String network_name) {
                this.network_name = network_name;
            }

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

            public String getSeal_price() {
                return seal_price;
            }

            public void setSeal_price(String seal_price) {
                this.seal_price = seal_price;
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

            public String getPics() {
                return pics;
            }

            public void setPics(String pics) {
                this.pics = pics;
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

            public String getStock_distributor_all() {
                return stock_distributor_all;
            }

            public void setStock_distributor_all(String stock_distributor_all) {
                this.stock_distributor_all = stock_distributor_all;
            }

            public int getMinimum_order_quantity() {
                return minimum_order_quantity;
            }

            public void setMinimum_order_quantity(int minimum_order_quantity) {
                this.minimum_order_quantity = minimum_order_quantity;
            }

            public List<String> getContents_mobi() {
                return contents_mobi;
            }

            public void setContents_mobi(List<String> contents_mobi) {
                this.contents_mobi = contents_mobi;
            }

            public List<String> getImgs() {
                return imgs;
            }

            public void setImgs(List<String> imgs) {
                this.imgs = imgs;
            }

            public static class PromotionBean {

                private String id;
                private String start_time;
                private String end_time;
                private Object full_amount;
                private Object reduce_amount;
                private String type;
                private String zp_type;
                private String title;
                private List<GoodsXqBean.DataBean.ProductBean.PromotionBean.GiftBean> gift;

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

                public List<GoodsXqBean.DataBean.ProductBean.PromotionBean.GiftBean> getGift() {
                    return gift;
                }

                public void setGift(List<GoodsXqBean.DataBean.ProductBean.PromotionBean.GiftBean> gift) {
                    this.gift = gift;
                }

                public static class GiftBean {

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

        public static class HotBean {

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
            private String stock_distributor_all;
            private String minimum_order_quantity;
            private String b2bs;
            private List<String> contents_mobi;

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

            public List<String> getContents_mobi() {
                return contents_mobi;
            }

            public void setContents_mobi(List<String> contents_mobi) {
                this.contents_mobi = contents_mobi;
            }
        }
    }
}