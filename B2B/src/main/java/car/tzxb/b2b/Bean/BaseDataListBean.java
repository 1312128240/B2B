package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/25 0025.
 */

public class BaseDataListBean {


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
        private String shop_name;
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
}
