package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/25 0025.
 */

public class BaseDataListBean {


    private String msg;
    private int status;
    private List<DataBean> data;
    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

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
        private String shop_img;
        private String shop_type;
        private String mobile;
        private String Tel;
        private String shop_address;
        private String coordinate_Latitude;
        private String coordinate_Longitude;
        private Object sales_count;
        private String province;
        private String city;
        private String area;
        private String shop_tj;
        private String shop_pf;
        private String tag;
        private String img;
        private String Username;
        private boolean isCheck;
        private String price;
        private int minimum_order_quantity;
        private String color_name;
        private String network_name;
        private String title;
        private String isset;
        private String msg;
        private String is_del;
        private String add_time;
        private String shop_name;
        private String max_seal_price;
        private String min_seal_price;
        private String content;
        private String type;
        private String contents;
        private String orderstatus;
        private String id;
        private String Name;
        private String shop_id;
        private String product_title;
        private String market_price;
        private String discount_price;
        private String point;
        private String color_id;
        private String network_id;
        private String miaoshu;
        private String brand_id;
        private String offset;
        private String pic;
        private String p_title;
        private String goods_id;
        private String product_id;

        private String img_url;
        private String goods_name;
        private String is_point_product;
        private String is_balance_product;
        private String user_id;
        private String category_id;
        private String cate_title;
        private String dealer;
        private String seal_price;
        private String stock_distributor_all;
        private String sales;
        private String cost_point;

        public String getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(String orderstatus) {
            this.orderstatus = orderstatus;
        }




        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getMinimum_order_quantity() {
            return minimum_order_quantity;
        }

        public void setMinimum_order_quantity(int minimum_order_quantity) {
            this.minimum_order_quantity = minimum_order_quantity;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIsset() {
            return isset;
        }

        public void setIsset(String isset) {
            this.isset = isset;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
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

        public String getId() {
            return id;
        }


        public String getName() {
            return Name;
        }

        public String getDealer() {
            return dealer;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public String getImg_url() {
            return img_url;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public String getIs_point_product() {
            return is_point_product;
        }

        public String getIs_balance_product() {
            return is_balance_product;
        }

        public String getShop_id() {
            return shop_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public String getCate_title() {
            return cate_title;
        }

        public String getProduct_title() {
            return product_title;
        }

        public String getMarket_price() {
            return market_price;
        }

        public String getSeal_price() {
            return seal_price;
        }

        public String getCost_point() {
            return cost_point;
        }

        public String getDiscount_price() {
            return discount_price;
        }

        public String getPoint() {
            return point;
        }

        public String getSales() {
            return sales;
        }

        public String getColor_id() {
            return color_id;
        }

        public String getNetwork_id() {
            return network_id;
        }

        public String getMiaoshu() {
            return miaoshu;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public String getOffset() {
            return offset;
        }

        public String getPic() {
            return pic;
        }

        public String getP_title() {
            return p_title;
        }


        public String getUsername() {
            return Username;
        }

        public void setUsername(String username) {
            Username = username;
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

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
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

        public Object getSales_count() {
            return sales_count;
        }

        public void setSales_count(Object sales_count) {
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

    }

    public static class InfoBean {
        private String Group_id;
        private String gold;
        private String b2b_point;

        public String getGroup_id() {
            return Group_id;
        }

        public void setGroup_id(String Group_id) {
            this.Group_id = Group_id;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getB2b_point() {
            return b2b_point;
        }

        public void setB2b_point(String b2b_point) {
            this.b2b_point = b2b_point;
        }
    }
}

