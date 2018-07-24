package car.tzxb.b2b.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/7/23 0023.
 */

public class MyCenterBean implements Serializable{

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

    public static class DataBean implements Serializable {

        private UserInfoBean UserInfo;
        public   List<Integer> UserCollect;
        private List<Integer> OrderNumber;
        private List<Integer> MyProperty;

        public UserInfoBean getUserInfo() {
            return UserInfo;
        }

        public  List<Integer> getUserCollect() {
            return UserCollect;
        }

        public List<Integer> getOrderNumber() {
            return OrderNumber;
        }


        public List<Integer> getMyProperty() {
            return MyProperty;
        }



        public static class UserInfoBean implements Serializable{
            private String ID;
            private String Username;
            private String recommend;
            private String parent_name;
            private String parent_id;
            private String Group_id;
            private String Salt;
            private Object Name;
            private String Tel;
            private String mobile;
            private String QQ;
            private String birthday;
            private String address;
            private String Safe_answer;
            private String Safe_question;
            private Object amount;
            private Object amount_consumption;
            private Object point;
            private Object exp;
            private Object status;
            private Object reg_ip;
            private Object erwm;
            private String is_del;
            private String sort_id;
            private String add_time;
            private String province;
            private String city;
            private String area;
            private String reg_time;
            private String user_type;
            private Object level_id;
            private Object amount_lock;
            private Object amount_withdrawal;
            private String shop_name;
            private String shop_address;
            private String user_zhizhao_no;
            private Object user_zhizhao;
            private Object user_idcard_positive;
            private Object user_idcard_back;
            private String email;
            private String parent_user_name;
            private String sex;
            private Object tjr;
            private String coordinate_Longitude;
            private String coordinate_Latitude;
            private String shop_img;
            private String skill_type_ids;
            private String shop_type;
            private String nackname;
            private String head_img;
            private Object licence_img;
            private String sales_count;
            private String shop_tj;
            private String shop_pf;
            private Object start_time;
            private String goods_visit;
            private Object Service_manager;
            private Object public_account_name;
            private Object opening_bank;
            private Object rate;
            private Object public_account_number;
            private String is_shop_close;
            private String open_list;
            private Object source;
            private String shop_explain;
            private Object activity_user;
            private String balance;
            private String balance_amount;
            private String is_subaccount;
            private Object carded;
            private String openid;
            private String session_key;
            private String formid;
            private String unionid;
            private Object IOS_push;
            private Object ANDROID_push;
            private String is_region;
            private String is_subsission;
            private String mark;
            private Object activity_ticket;
            private Object activity_ticket_total;
            private String gold;
            private String sign_in;
            private String sign_in_time;
            private Object warehouse_id;
            private String paypassword;
            private String subscriber_id;

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

            public String getRecommend() {
                return recommend;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public String getParent_name() {
                return parent_name;
            }

            public void setParent_name(String parent_name) {
                this.parent_name = parent_name;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public String getGroup_id() {
                return Group_id;
            }

            public void setGroup_id(String Group_id) {
                this.Group_id = Group_id;
            }

            public String getSalt() {
                return Salt;
            }

            public void setSalt(String Salt) {
                this.Salt = Salt;
            }

            public Object getName() {
                return Name;
            }

            public void setName(Object Name) {
                this.Name = Name;
            }

            public String getTel() {
                return Tel;
            }

            public void setTel(String Tel) {
                this.Tel = Tel;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getQQ() {
                return QQ;
            }

            public void setQQ(String QQ) {
                this.QQ = QQ;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getSafe_answer() {
                return Safe_answer;
            }

            public void setSafe_answer(String Safe_answer) {
                this.Safe_answer = Safe_answer;
            }

            public String getSafe_question() {
                return Safe_question;
            }

            public void setSafe_question(String Safe_question) {
                this.Safe_question = Safe_question;
            }

            public Object getAmount() {
                return amount;
            }

            public void setAmount(Object amount) {
                this.amount = amount;
            }

            public Object getAmount_consumption() {
                return amount_consumption;
            }

            public void setAmount_consumption(Object amount_consumption) {
                this.amount_consumption = amount_consumption;
            }

            public Object getPoint() {
                return point;
            }

            public void setPoint(Object point) {
                this.point = point;
            }

            public Object getExp() {
                return exp;
            }

            public void setExp(Object exp) {
                this.exp = exp;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getReg_ip() {
                return reg_ip;
            }

            public void setReg_ip(Object reg_ip) {
                this.reg_ip = reg_ip;
            }

            public Object getErwm() {
                return erwm;
            }

            public void setErwm(Object erwm) {
                this.erwm = erwm;
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

            public String getReg_time() {
                return reg_time;
            }

            public void setReg_time(String reg_time) {
                this.reg_time = reg_time;
            }

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
            }

            public Object getLevel_id() {
                return level_id;
            }

            public void setLevel_id(Object level_id) {
                this.level_id = level_id;
            }

            public Object getAmount_lock() {
                return amount_lock;
            }

            public void setAmount_lock(Object amount_lock) {
                this.amount_lock = amount_lock;
            }

            public Object getAmount_withdrawal() {
                return amount_withdrawal;
            }

            public void setAmount_withdrawal(Object amount_withdrawal) {
                this.amount_withdrawal = amount_withdrawal;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public String getShop_address() {
                return shop_address;
            }

            public void setShop_address(String shop_address) {
                this.shop_address = shop_address;
            }

            public String getUser_zhizhao_no() {
                return user_zhizhao_no;
            }

            public void setUser_zhizhao_no(String user_zhizhao_no) {
                this.user_zhizhao_no = user_zhizhao_no;
            }

            public Object getUser_zhizhao() {
                return user_zhizhao;
            }

            public void setUser_zhizhao(Object user_zhizhao) {
                this.user_zhizhao = user_zhizhao;
            }

            public Object getUser_idcard_positive() {
                return user_idcard_positive;
            }

            public void setUser_idcard_positive(Object user_idcard_positive) {
                this.user_idcard_positive = user_idcard_positive;
            }

            public Object getUser_idcard_back() {
                return user_idcard_back;
            }

            public void setUser_idcard_back(Object user_idcard_back) {
                this.user_idcard_back = user_idcard_back;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getParent_user_name() {
                return parent_user_name;
            }

            public void setParent_user_name(String parent_user_name) {
                this.parent_user_name = parent_user_name;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public Object getTjr() {
                return tjr;
            }

            public void setTjr(Object tjr) {
                this.tjr = tjr;
            }

            public String getCoordinate_Longitude() {
                return coordinate_Longitude;
            }

            public void setCoordinate_Longitude(String coordinate_Longitude) {
                this.coordinate_Longitude = coordinate_Longitude;
            }

            public String getCoordinate_Latitude() {
                return coordinate_Latitude;
            }

            public void setCoordinate_Latitude(String coordinate_Latitude) {
                this.coordinate_Latitude = coordinate_Latitude;
            }

            public String getShop_img() {
                return shop_img;
            }

            public void setShop_img(String shop_img) {
                this.shop_img = shop_img;
            }

            public String getSkill_type_ids() {
                return skill_type_ids;
            }

            public void setSkill_type_ids(String skill_type_ids) {
                this.skill_type_ids = skill_type_ids;
            }

            public String getShop_type() {
                return shop_type;
            }

            public void setShop_type(String shop_type) {
                this.shop_type = shop_type;
            }

            public String getNackname() {
                return nackname;
            }

            public void setNackname(String nackname) {
                this.nackname = nackname;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public Object getLicence_img() {
                return licence_img;
            }

            public void setLicence_img(Object licence_img) {
                this.licence_img = licence_img;
            }

            public String getSales_count() {
                return sales_count;
            }

            public void setSales_count(String sales_count) {
                this.sales_count = sales_count;
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

            public Object getStart_time() {
                return start_time;
            }

            public void setStart_time(Object start_time) {
                this.start_time = start_time;
            }

            public String getGoods_visit() {
                return goods_visit;
            }

            public void setGoods_visit(String goods_visit) {
                this.goods_visit = goods_visit;
            }

            public Object getService_manager() {
                return Service_manager;
            }

            public void setService_manager(Object Service_manager) {
                this.Service_manager = Service_manager;
            }

            public Object getPublic_account_name() {
                return public_account_name;
            }

            public void setPublic_account_name(Object public_account_name) {
                this.public_account_name = public_account_name;
            }

            public Object getOpening_bank() {
                return opening_bank;
            }

            public void setOpening_bank(Object opening_bank) {
                this.opening_bank = opening_bank;
            }

            public Object getRate() {
                return rate;
            }

            public void setRate(Object rate) {
                this.rate = rate;
            }

            public Object getPublic_account_number() {
                return public_account_number;
            }

            public void setPublic_account_number(Object public_account_number) {
                this.public_account_number = public_account_number;
            }

            public String getIs_shop_close() {
                return is_shop_close;
            }

            public void setIs_shop_close(String is_shop_close) {
                this.is_shop_close = is_shop_close;
            }

            public String getOpen_list() {
                return open_list;
            }

            public void setOpen_list(String open_list) {
                this.open_list = open_list;
            }

            public Object getSource() {
                return source;
            }

            public void setSource(Object source) {
                this.source = source;
            }

            public String getShop_explain() {
                return shop_explain;
            }

            public void setShop_explain(String shop_explain) {
                this.shop_explain = shop_explain;
            }

            public Object getActivity_user() {
                return activity_user;
            }

            public void setActivity_user(Object activity_user) {
                this.activity_user = activity_user;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getBalance_amount() {
                return balance_amount;
            }

            public void setBalance_amount(String balance_amount) {
                this.balance_amount = balance_amount;
            }

            public String getIs_subaccount() {
                return is_subaccount;
            }

            public void setIs_subaccount(String is_subaccount) {
                this.is_subaccount = is_subaccount;
            }

            public Object getCarded() {
                return carded;
            }

            public void setCarded(Object carded) {
                this.carded = carded;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getSession_key() {
                return session_key;
            }

            public void setSession_key(String session_key) {
                this.session_key = session_key;
            }

            public String getFormid() {
                return formid;
            }

            public void setFormid(String formid) {
                this.formid = formid;
            }

            public String getUnionid() {
                return unionid;
            }

            public void setUnionid(String unionid) {
                this.unionid = unionid;
            }

            public Object getIOS_push() {
                return IOS_push;
            }

            public void setIOS_push(Object IOS_push) {
                this.IOS_push = IOS_push;
            }

            public Object getANDROID_push() {
                return ANDROID_push;
            }

            public void setANDROID_push(Object ANDROID_push) {
                this.ANDROID_push = ANDROID_push;
            }

            public String getIs_region() {
                return is_region;
            }

            public void setIs_region(String is_region) {
                this.is_region = is_region;
            }

            public String getIs_subsission() {
                return is_subsission;
            }

            public void setIs_subsission(String is_subsission) {
                this.is_subsission = is_subsission;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public Object getActivity_ticket() {
                return activity_ticket;
            }

            public void setActivity_ticket(Object activity_ticket) {
                this.activity_ticket = activity_ticket;
            }

            public Object getActivity_ticket_total() {
                return activity_ticket_total;
            }

            public void setActivity_ticket_total(Object activity_ticket_total) {
                this.activity_ticket_total = activity_ticket_total;
            }

            public String getGold() {
                return gold;
            }

            public void setGold(String gold) {
                this.gold = gold;
            }

            public String getSign_in() {
                return sign_in;
            }

            public void setSign_in(String sign_in) {
                this.sign_in = sign_in;
            }

            public String getSign_in_time() {
                return sign_in_time;
            }

            public void setSign_in_time(String sign_in_time) {
                this.sign_in_time = sign_in_time;
            }

            public Object getWarehouse_id() {
                return warehouse_id;
            }

            public void setWarehouse_id(Object warehouse_id) {
                this.warehouse_id = warehouse_id;
            }

            public String getPaypassword() {
                return paypassword;
            }

            public void setPaypassword(String paypassword) {
                this.paypassword = paypassword;
            }

            public String getSubscriber_id() {
                return subscriber_id;
            }

            public void setSubscriber_id(String subscriber_id) {
                this.subscriber_id = subscriber_id;
            }
        }
    }
}