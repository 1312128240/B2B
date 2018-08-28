package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/13 0013.
 */

public class BrowhistoryBean {

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


        private String add_time;
        private List<ChildDataBean> child_data;

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public List<ChildDataBean> getChild_data() {
            return child_data;
        }

        public void setChild_data(List<ChildDataBean> child_data) {
            this.child_data = child_data;
        }

        public static class ChildDataBean {
            private int Groupindex;
            private int Childindex;
            private String history_id;
            private String id;
            private String title;
            private String isset;
            private String msg;
            private String img_url;
            private String shop_id;
            private Object shop_name;
            private Object shop_address;
            private Object is_del;
            private String add_time;
            private String seal_price;
            private String max_seal_price;
            private String min_seal_price;
            private String price;

            public int getChildindex() {
                return Childindex;
            }

            public void setChildindex(int childindex) {
                Childindex = childindex;
            }

            public int getGroupindex() {
                return Groupindex;
            }

            public void setGroupindex(int groupindex) {
                Groupindex = groupindex;
            }


            public String getHistory_id() {
                return history_id;
            }

            public void setHistory_id(String history_id) {
                this.history_id = history_id;
            }

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

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public Object getShop_name() {
                return shop_name;
            }

            public void setShop_name(Object shop_name) {
                this.shop_name = shop_name;
            }

            public Object getShop_address() {
                return shop_address;
            }

            public void setShop_address(Object shop_address) {
                this.shop_address = shop_address;
            }

            public Object getIs_del() {
                return is_del;
            }

            public void setIs_del(Object is_del) {
                this.is_del = is_del;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
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
        }
    }
}
