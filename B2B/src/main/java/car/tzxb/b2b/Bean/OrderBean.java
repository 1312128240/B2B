package car.tzxb.b2b.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/27 0027.
 */

public class OrderBean implements Serializable{

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

    public static class DataBean implements Serializable{

        private int goods_kind_number;
        private double special_money;
        private int offset;
        private int all_offset;
        private double amount_price;
        private double discount_amount;
        private double amount_pay;
        private List<GoodsBean> goods;

        public int getGoods_kind_number() {
            return goods_kind_number;
        }

        public void setGoods_kind_number(int goods_kind_number) {
            this.goods_kind_number = goods_kind_number;
        }

        public double getSpecial_money() {
            return special_money;
        }

        public void setSpecial_money(double special_money) {
            this.special_money = special_money;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getAll_offset() {
            return all_offset;
        }

        public void setAll_offset(int all_offset) {
            this.all_offset = all_offset;
        }

        public double getAmount_price() {
            return amount_price;
        }

        public void setAmount_price(int amount_price) {
            this.amount_price = amount_price;
        }

        public double getDiscount_amount() {
            return discount_amount;
        }

        public void setDiscount_amount(int discount_amount) {
            this.discount_amount = discount_amount;
        }

        public double getAmount_pay() {
            return amount_pay;
        }

        public void setAmount_pay(int amount_pay) {
            this.amount_pay = amount_pay;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean implements Serializable{

            private String shop_id;
            private String shop_name;
            private int shop_disc;
            private double shop_amount;
            private String special_promotion;
            private List<DataChildBean> data_child;
            private String Mesg;

            public String getMesg() {
                return Mesg;
            }

            public void setMesg(String mesg) {
                Mesg = mesg;
            }


            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public int getShop_disc() {
                return shop_disc;
            }

            public void setShop_disc(int shop_disc) {
                this.shop_disc = shop_disc;
            }

            public double getShop_amount() {
                return shop_amount;
            }

            public void setShop_amount(int shop_amount) {
                this.shop_amount = shop_amount;
            }

            public String getSpecial_promotion() {
                return special_promotion;
            }

            public void setSpecial_promotion(String special_promotion) {
                this.special_promotion = special_promotion;
            }

            public List<DataChildBean> getData_child() {
                return data_child;
            }

            public void setData_child(List<DataChildBean> data_child) {
                this.data_child = data_child;
            }

            public static class DataChildBean implements Serializable {
                private String id;
                private String user_id;
                private String goods_id;
                private String number;
                private String is_del;
                private String add_time;
                private String type;
                private String shop_id;
                private Object seck_amount;
                private Object disc_id;
                private String is_seck;
                private String motion_id;
                private String motion_type;
                private String motion_zpid;
                private String pro_id;
                private String bgoods_id;
                private String bis_del;
                private String title;
                private String market_price;
                private String seal_price;
                private String pic;
                private String point;
                private String cost_point;
                private String offset;
                private String img_url;
                private String child_title;
                private String child_type;
                private int discount_amount;
                private List<?> gift;

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

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
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

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getShop_id() {
                    return shop_id;
                }

                public void setShop_id(String shop_id) {
                    this.shop_id = shop_id;
                }

                public Object getSeck_amount() {
                    return seck_amount;
                }

                public void setSeck_amount(Object seck_amount) {
                    this.seck_amount = seck_amount;
                }

                public Object getDisc_id() {
                    return disc_id;
                }

                public void setDisc_id(Object disc_id) {
                    this.disc_id = disc_id;
                }

                public String getIs_seck() {
                    return is_seck;
                }

                public void setIs_seck(String is_seck) {
                    this.is_seck = is_seck;
                }

                public String getMotion_id() {
                    return motion_id;
                }

                public void setMotion_id(String motion_id) {
                    this.motion_id = motion_id;
                }

                public String getMotion_type() {
                    return motion_type;
                }

                public void setMotion_type(String motion_type) {
                    this.motion_type = motion_type;
                }

                public String getMotion_zpid() {
                    return motion_zpid;
                }

                public void setMotion_zpid(String motion_zpid) {
                    this.motion_zpid = motion_zpid;
                }

                public String getPro_id() {
                    return pro_id;
                }

                public void setPro_id(String pro_id) {
                    this.pro_id = pro_id;
                }

                public String getBgoods_id() {
                    return bgoods_id;
                }

                public void setBgoods_id(String bgoods_id) {
                    this.bgoods_id = bgoods_id;
                }

                public String getBis_del() {
                    return bis_del;
                }

                public void setBis_del(String bis_del) {
                    this.bis_del = bis_del;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
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

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getPoint() {
                    return point;
                }

                public void setPoint(String point) {
                    this.point = point;
                }

                public String getCost_point() {
                    return cost_point;
                }

                public void setCost_point(String cost_point) {
                    this.cost_point = cost_point;
                }

                public String getOffset() {
                    return offset;
                }

                public void setOffset(String offset) {
                    this.offset = offset;
                }

                public String getImg_url() {
                    return img_url;
                }

                public void setImg_url(String img_url) {
                    this.img_url = img_url;
                }

                public String getChild_title() {
                    return child_title;
                }

                public void setChild_title(String child_title) {
                    this.child_title = child_title;
                }

                public String getChild_type() {
                    return child_type;
                }

                public void setChild_type(String child_type) {
                    this.child_type = child_type;
                }

                public int getDiscount_amount() {
                    return discount_amount;
                }

                public void setDiscount_amount(int discount_amount) {
                    this.discount_amount = discount_amount;
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
