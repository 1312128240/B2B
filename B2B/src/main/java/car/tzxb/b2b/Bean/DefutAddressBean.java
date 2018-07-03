package car.tzxb.b2b.Bean;

/**
 * Created by Administrator on 2018/7/3 0003.
 */

public class DefutAddressBean {


    private String status;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private AddressBean address;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public static class AddressBean {

            private String ID;
            private String user_id;
            private String user_name;
            private String province;
            private String city;
            private String area;
            private String address;
            private String mobile;
            private Object tel;
            private Object zip;
            private Object email;
            private String status;
            private Object sort_id;
            private String is_del;
            private String add_time;
            private Object update_time;
            private String street;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public Object getTel() {
                return tel;
            }

            public void setTel(Object tel) {
                this.tel = tel;
            }

            public Object getZip() {
                return zip;
            }

            public void setZip(Object zip) {
                this.zip = zip;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Object getSort_id() {
                return sort_id;
            }

            public void setSort_id(Object sort_id) {
                this.sort_id = sort_id;
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

            public Object getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(Object update_time) {
                this.update_time = update_time;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }
        }
    }
}
