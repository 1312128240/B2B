package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class BaseDataBean {

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

        private String ID;
        private String Username;
        private String Group_id;
        private String user_type;
        private String mobile;
        private String shop_name;
        private String is_del;
        private List<DataBean.CategoryBean> category;
        private List<DataBean.BrandBean> brand;

        public List<DataBean.CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<DataBean.CategoryBean> category) {
            this.category = category;
        }

        public List<DataBean.BrandBean> getBrand() {
            return brand;
        }

        public void setBrand(List<DataBean.BrandBean> brand) {
            this.brand = brand;
        }


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

        public String getGroup_id() {
            return Group_id;
        }

        public void setGroup_id(String Group_id) {
            this.Group_id = Group_id;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }
        public static class CategoryBean {

            private String id;
            private String title;
            private String sort_id;
            private String img_url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSort_id() {
                return sort_id;
            }

            public void setSort_id(String sort_id) {
                this.sort_id = sort_id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }

        public static class BrandBean {


            private String id;
            private String title;
            private String sort_id;
            private String img_url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSort_id() {
                return sort_id;
            }

            public void setSort_id(String sort_id) {
                this.sort_id = sort_id;
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
