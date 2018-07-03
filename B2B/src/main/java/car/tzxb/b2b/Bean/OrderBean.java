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

    public static class DataBean implements Serializable {


        private int shop_car;
        private int offset;
        private int amount_pay;
        private int amount_cost_point;
        private int amount_point;
        private List<GoodsBean> goods;

        public int getShop_car() {
            return shop_car;
        }

        public void setShop_car(int shop_car) {
            this.shop_car = shop_car;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getAmount_pay() {
            return amount_pay;
        }

        public void setAmount_pay(int amount_pay) {
            this.amount_pay = amount_pay;
        }

        public int getAmount_cost_point() {
            return amount_cost_point;
        }

        public void setAmount_cost_point(int amount_cost_point) {
            this.amount_cost_point = amount_cost_point;
        }

        public int getAmount_point() {
            return amount_point;
        }

        public void setAmount_point(int amount_point) {
            this.amount_point = amount_point;
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
            private String mesg;
            public String getMesg() {
                return mesg;
            }

            public void setMesg(String mesg) {
                this.mesg = mesg;
            }


            private List<DataChildBean> data_child;

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
                private Object motion_type;
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

                public Object getMotion_type() {
                    return motion_type;
                }

                public void setMotion_type(Object motion_type) {
                    this.motion_type = motion_type;
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
            }
        }
    }
}
