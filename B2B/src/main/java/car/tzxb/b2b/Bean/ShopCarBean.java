package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class ShopCarBean {


    private String msg;
    private String status;
    private double totals;
    private String number;
    private List<DataBean> data;
    private double offset;
    private double amount_pay;

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public double getAmount_pay() {
        return amount_pay;
    }

    public void setAmount_pay(double amount_pay) {
        this.amount_pay = amount_pay;
    }



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

    public double getTotals() {
        return totals;
    }

    public void setTotals(double totals) {
        this.totals = totals;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {


        private String shops_name;
        private String types;
        private String shop_id;
        private int coun;
        private List<DataChildBean> data_child;
        private boolean isCheck;

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getShops_name() {
            return shops_name;
        }

        public void setShops_name(String shops_name) {
            this.shops_name = shops_name;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public int getCoun() {
            return coun;
        }

        public void setCoun(int coun) {
            this.coun = coun;
        }

        public List<DataChildBean> getData_child() {
            return data_child;
        }

        public void setData_child(List<DataChildBean> data_child) {
            this.data_child = data_child;
        }

        public static class DataChildBean {


            private String shop_name;
            private String aid;
            private String type;
            private String shop_id;
            private String user_id;
            private int number;
            private String pro_id;
            private String b_id;
            private String title;
            private String name;
            private String market_price;
            private double seal_price;
            private String discount_price;
            private String motion_type;
            private int point;
            private Object cost_point;
            private String pic;
            private String id;
            private String category_id;
            private String brand_id;
            private String color_ids;
            private String packing_ids;
            private String network_ids;
            private Object capacity_ids;
            private Object memorysize_ids;
            private String flag;
            private String img_url;
            private String contents;
            private String seo_title;
            private String seo_keywords;
            private String seo_description;
            private String zhaiyao;
            private Object buy_type;
            private String is_del;
            private String sort_id;
            private String add_time;
            private String is_point_product;
            private Object is_nfc_product;
            private Object is_wholesale;
            private Object bak_int1;
            private Object bak_int2;
            private Object bak_str1;
            private Object bak_str2;
            private Object is_fanli;
            private String category_ids;
            private String is_yushou;
            private String is_weixiu;
            private String addres;
            private String obj_id;
            private String capacity_custom_name;
            private String user_type;
            private String user_name;
            private String offset;
            private String serve;
            private String prefecture;
            private double total;
            private boolean isChecked;
            private String child_title;
            private double discount_amount;
            private int  minimum_order_quantity;
            private String motion_id;

            public String getMotion_id() {
                return motion_id;
            }

            public void setMotion_id(String motion_id) {
                this.motion_id = motion_id;
            }


            public int getMinimum_order_quantity() {
                return minimum_order_quantity;
            }

            public void setMinimum_order_quantity(int minimum_order_quantity) {
                this.minimum_order_quantity = minimum_order_quantity;
            }


            public String getMotion_type() {
                return motion_type;
            }

            public void setMotion_type(String motion_type) {
                this.motion_type = motion_type;
            }
            public double getDiscount_amount() {
                return discount_amount;
            }

            public void setDiscount_amount(double discount_amount) {
                this.discount_amount = discount_amount;
            }


            private List<ShopCarBean.DataBean.DataChildBean.GiftBean> gift;

            public String getChild_title() {
                return child_title;
            }

            public void setChild_title(String child_title) {
                this.child_title = child_title;
            }


            public List<ShopCarBean.DataBean.DataChildBean.GiftBean> getGift() {
                return gift;
            }

            public void setGift(List<ShopCarBean.DataBean.DataChildBean.GiftBean> gift) {
                this.gift = gift;
            }


            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }


            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
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

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getPro_id() {
                return pro_id;
            }

            public void setPro_id(String pro_id) {
                this.pro_id = pro_id;
            }

            public String getB_id() {
                return b_id;
            }

            public void setB_id(String b_id) {
                this.b_id = b_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public double getSeal_price() {
                return seal_price;
            }

            public void setSeal_price(double seal_price) {
                this.seal_price = seal_price;
            }

            public String getDiscount_price() {
                return discount_price;
            }

            public void setDiscount_price(String discount_price) {
                this.discount_price = discount_price;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public Object getCost_point() {
                return cost_point;
            }

            public void setCost_point(Object cost_point) {
                this.cost_point = cost_point;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public String getColor_ids() {
                return color_ids;
            }

            public void setColor_ids(String color_ids) {
                this.color_ids = color_ids;
            }

            public String getPacking_ids() {
                return packing_ids;
            }

            public void setPacking_ids(String packing_ids) {
                this.packing_ids = packing_ids;
            }

            public String getNetwork_ids() {
                return network_ids;
            }

            public void setNetwork_ids(String network_ids) {
                this.network_ids = network_ids;
            }

            public Object getCapacity_ids() {
                return capacity_ids;
            }

            public void setCapacity_ids(Object capacity_ids) {
                this.capacity_ids = capacity_ids;
            }

            public Object getMemorysize_ids() {
                return memorysize_ids;
            }

            public void setMemorysize_ids(Object memorysize_ids) {
                this.memorysize_ids = memorysize_ids;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getContents() {
                return contents;
            }

            public void setContents(String contents) {
                this.contents = contents;
            }

            public String getSeo_title() {
                return seo_title;
            }

            public void setSeo_title(String seo_title) {
                this.seo_title = seo_title;
            }

            public String getSeo_keywords() {
                return seo_keywords;
            }

            public void setSeo_keywords(String seo_keywords) {
                this.seo_keywords = seo_keywords;
            }

            public String getSeo_description() {
                return seo_description;
            }

            public void setSeo_description(String seo_description) {
                this.seo_description = seo_description;
            }

            public String getZhaiyao() {
                return zhaiyao;
            }

            public void setZhaiyao(String zhaiyao) {
                this.zhaiyao = zhaiyao;
            }

            public Object getBuy_type() {
                return buy_type;
            }

            public void setBuy_type(Object buy_type) {
                this.buy_type = buy_type;
            }

            public String getIs_del() {
                return is_del;
            }

            public void setIs_del(String is_del) {
                this.is_del = is_del;
            }

            public String getSort_id() {
                return sort_id;
            }

            public void setSort_id(String sort_id) {
                this.sort_id = sort_id;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getIs_point_product() {
                return is_point_product;
            }

            public void setIs_point_product(String is_point_product) {
                this.is_point_product = is_point_product;
            }

            public Object getIs_nfc_product() {
                return is_nfc_product;
            }

            public void setIs_nfc_product(Object is_nfc_product) {
                this.is_nfc_product = is_nfc_product;
            }

            public Object getIs_wholesale() {
                return is_wholesale;
            }

            public void setIs_wholesale(Object is_wholesale) {
                this.is_wholesale = is_wholesale;
            }

            public Object getBak_int1() {
                return bak_int1;
            }

            public void setBak_int1(Object bak_int1) {
                this.bak_int1 = bak_int1;
            }

            public Object getBak_int2() {
                return bak_int2;
            }

            public void setBak_int2(Object bak_int2) {
                this.bak_int2 = bak_int2;
            }

            public Object getBak_str1() {
                return bak_str1;
            }

            public void setBak_str1(Object bak_str1) {
                this.bak_str1 = bak_str1;
            }

            public Object getBak_str2() {
                return bak_str2;
            }

            public void setBak_str2(Object bak_str2) {
                this.bak_str2 = bak_str2;
            }

            public Object getIs_fanli() {
                return is_fanli;
            }

            public void setIs_fanli(Object is_fanli) {
                this.is_fanli = is_fanli;
            }

            public String getCategory_ids() {
                return category_ids;
            }

            public void setCategory_ids(String category_ids) {
                this.category_ids = category_ids;
            }

            public String getIs_yushou() {
                return is_yushou;
            }

            public void setIs_yushou(String is_yushou) {
                this.is_yushou = is_yushou;
            }

            public String getIs_weixiu() {
                return is_weixiu;
            }

            public void setIs_weixiu(String is_weixiu) {
                this.is_weixiu = is_weixiu;
            }

            public String getAddres() {
                return addres;
            }

            public void setAddres(String addres) {
                this.addres = addres;
            }

            public String getObj_id() {
                return obj_id;
            }

            public void setObj_id(String obj_id) {
                this.obj_id = obj_id;
            }

            public String getCapacity_custom_name() {
                return capacity_custom_name;
            }

            public void setCapacity_custom_name(String capacity_custom_name) {
                this.capacity_custom_name = capacity_custom_name;
            }

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getOffset() {
                return offset;
            }

            public void setOffset(String offset) {
                this.offset = offset;
            }

            public String getServe() {
                return serve;
            }

            public void setServe(String serve) {
                this.serve = serve;
            }

            public String getPrefecture() {
                return prefecture;
            }

            public void setPrefecture(String prefecture) {
                this.prefecture = prefecture;
            }

            public double getTotal() {
                return total;
            }

            public void setTotal(double total) {
                this.total = total;
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
                private String goods_id;
                private int zp_numbers;

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

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public int getZp_numbers() {
                    return zp_numbers;
                }

                public void setZp_numbers(int zp_numbers) {
                    this.zp_numbers = zp_numbers;
                }
            }
        }
    }
}
