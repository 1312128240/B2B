package car.tzxb.b2b.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/7/2 0002.
 */

public class OrderXqBean implements Serializable {

    private DataBean data;
    private int status;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean implements Serializable {

        private OrderDetailsBean order_details;

        public OrderDetailsBean getOrder_details() {
            return order_details;
        }

        public void setOrder_details(OrderDetailsBean order_details) {
            this.order_details = order_details;
        }

        public static class OrderDetailsBean implements Serializable {

            private String shop_name;
            private String shop_mobile;
            private String order_seqno;
            private String amount_goods;
            private Object amount_coupon;
            private Object amount_offset;
            private String amount_pay_able;
            private Object amount_real;
            private String payment_status;
            private String status;
            private String express_status;
            private String is_refund;
            private Object is_return;
            private Object return_status;
            private String refund_status;
            private String order_buy_type;
            private Object refund_cancel_time;
            private String add_time;
            private String name;
            private String mobile;
            private String expect_time;
            private String message;
            private String shop_id;
            private Object is_shop;
            private String accept_name;
            private String coupon_type;
            private String coupon_id;
            private String aid;
            private String payment_time;
            private String order_type;
            private String user_name;
            private Object refund_start_time;
            private Object refund_end_time;
            private Object receive_time;
            private Object refund_content;
            private Object return_reason;
            private String amount_total;
            private String order_source;
            private Object payment_id;
            private String payment_type;
            private String address;
            private int numbers;
            private String express_start_time;

            public String getOrder_type() {
                return order_type;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
            }

            public String getPayment_time() {
                return payment_time;
            }

            public void setPayment_time(String payment_time) {
                this.payment_time = payment_time;
            }

            public String getExpress_start_time() {
                return express_start_time;
            }

            public void setExpress_start_time(String express_start_time) {
                this.express_start_time = express_start_time;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }


            private List<ChildDataBean> child_data;

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public String getShop_mobile() {
                return shop_mobile;
            }

            public void setShop_mobile(String shop_mobile) {
                this.shop_mobile = shop_mobile;
            }

            public String getOrder_seqno() {
                return order_seqno;
            }

            public void setOrder_seqno(String order_seqno) {
                this.order_seqno = order_seqno;
            }

            public String getAmount_goods() {
                return amount_goods;
            }

            public void setAmount_goods(String amount_goods) {
                this.amount_goods = amount_goods;
            }

            public Object getAmount_coupon() {
                return amount_coupon;
            }

            public void setAmount_coupon(Object amount_coupon) {
                this.amount_coupon = amount_coupon;
            }

            public Object getAmount_offset() {
                return amount_offset;
            }

            public void setAmount_offset(Object amount_offset) {
                this.amount_offset = amount_offset;
            }

            public String getAmount_pay_able() {
                return amount_pay_able;
            }

            public void setAmount_pay_able(String amount_pay_able) {
                this.amount_pay_able = amount_pay_able;
            }

            public Object getAmount_real() {
                return amount_real;
            }

            public void setAmount_real(Object amount_real) {
                this.amount_real = amount_real;
            }

            public String getPayment_status() {
                return payment_status;
            }

            public void setPayment_status(String payment_status) {
                this.payment_status = payment_status;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getExpress_status() {
                return express_status;
            }

            public void setExpress_status(String express_status) {
                this.express_status = express_status;
            }

            public String getIs_refund() {
                return is_refund;
            }

            public void setIs_refund(String is_refund) {
                this.is_refund = is_refund;
            }

            public Object getIs_return() {
                return is_return;
            }

            public void setIs_return(Object is_return) {
                this.is_return = is_return;
            }

            public Object getReturn_status() {
                return return_status;
            }

            public void setReturn_status(Object return_status) {
                this.return_status = return_status;
            }

            public String getRefund_status() {
                return refund_status;
            }

            public void setRefund_status(String refund_status) {
                this.refund_status = refund_status;
            }

            public String getOrder_buy_type() {
                return order_buy_type;
            }

            public void setOrder_buy_type(String order_buy_type) {
                this.order_buy_type = order_buy_type;
            }

            public Object getRefund_cancel_time() {
                return refund_cancel_time;
            }

            public void setRefund_cancel_time(Object refund_cancel_time) {
                this.refund_cancel_time = refund_cancel_time;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getExpect_time() {
                return expect_time;
            }

            public void setExpect_time(String expect_time) {
                this.expect_time = expect_time;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public Object getIs_shop() {
                return is_shop;
            }

            public void setIs_shop(Object is_shop) {
                this.is_shop = is_shop;
            }

            public String getAccept_name() {
                return accept_name;
            }

            public void setAccept_name(String accept_name) {
                this.accept_name = accept_name;
            }

            public String getCoupon_type() {
                return coupon_type;
            }

            public void setCoupon_type(String coupon_type) {
                this.coupon_type = coupon_type;
            }

            public String getCoupon_id() {
                return coupon_id;
            }

            public void setCoupon_id(String coupon_id) {
                this.coupon_id = coupon_id;
            }

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public Object getRefund_start_time() {
                return refund_start_time;
            }

            public void setRefund_start_time(Object refund_start_time) {
                this.refund_start_time = refund_start_time;
            }

            public Object getRefund_end_time() {
                return refund_end_time;
            }

            public void setRefund_end_time(Object refund_end_time) {
                this.refund_end_time = refund_end_time;
            }

            public Object getReceive_time() {
                return receive_time;
            }

            public void setReceive_time(Object receive_time) {
                this.receive_time = receive_time;
            }

            public Object getRefund_content() {
                return refund_content;
            }

            public void setRefund_content(Object refund_content) {
                this.refund_content = refund_content;
            }

            public Object getReturn_reason() {
                return return_reason;
            }

            public void setReturn_reason(Object return_reason) {
                this.return_reason = return_reason;
            }

            public String getAmount_total() {
                return amount_total;
            }

            public void setAmount_total(String amount_total) {
                this.amount_total = amount_total;
            }

            public String getOrder_source() {
                return order_source;
            }

            public void setOrder_source(String order_source) {
                this.order_source = order_source;
            }

            public Object getPayment_id() {
                return payment_id;
            }

            public void setPayment_id(Object payment_id) {
                this.payment_id = payment_id;
            }

            public String getPayment_type() {
                return payment_type;
            }

            public void setPayment_type(String payment_type) {
                this.payment_type = payment_type;
            }

            public int getNumbers() {
                return numbers;
            }

            public void setNumbers(int numbers) {
                this.numbers = numbers;
            }

            public List<ChildDataBean> getChild_data() {
                return child_data;
            }

            public void setChild_data(List<ChildDataBean> child_data) {
                this.child_data = child_data;
            }

            public static class ChildDataBean implements Serializable{

                private Object shop_name;
                private String address;
                private String add_time;
                private String order_seqno;
                private String amount_goods;
                private String amount_pay_able;
                private String aid;
                private String id;
                private String order_id;
                private String category_id;
                private String product_id;
                private String goods_id;
                private String product_title;
                private String real_price;
                private String quantity;
                private String point;
                private String img_url;
                private String cost_point;
                private String recargo;
                private String payment_status;
                private Object payment_time;
                private Object express_id;
                private String express_fee;
                private String express_status;
                private Object express_start_time;
                private String status;
                private Object order_type;
                private String order_buy_type;
                private Object is_return;
                private Object return_status;
                private Object return_start_time;
                private Object return_end_time;
                private String is_refund;
                private String refund_status;
                private Object refund_start_time;
                private Object refund_end_time;
                private String is_del;
                private String franchisee_id;
                private Object fw_img;
                private Object bespeak_time;
                private String amount_offset;
                private String shop_id;
                private Object franchisee_tech;
                private String expect_time;
                private Object prepay_id;
                private Object totals;
                private Object confirm_time;
                private String coupon_type;
                private Object activity_id;
                private String coupon_id;

                public Object getShop_name() {
                    return shop_name;
                }

                public void setShop_name(Object shop_name) {
                    this.shop_name = shop_name;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getAdd_time() {
                    return add_time;
                }

                public void setAdd_time(String add_time) {
                    this.add_time = add_time;
                }

                public String getOrder_seqno() {
                    return order_seqno;
                }

                public void setOrder_seqno(String order_seqno) {
                    this.order_seqno = order_seqno;
                }

                public String getAmount_goods() {
                    return amount_goods;
                }

                public void setAmount_goods(String amount_goods) {
                    this.amount_goods = amount_goods;
                }

                public String getAmount_pay_able() {
                    return amount_pay_able;
                }

                public void setAmount_pay_able(String amount_pay_able) {
                    this.amount_pay_able = amount_pay_able;
                }

                public String getAid() {
                    return aid;
                }

                public void setAid(String aid) {
                    this.aid = aid;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getOrder_id() {
                    return order_id;
                }

                public void setOrder_id(String order_id) {
                    this.order_id = order_id;
                }

                public String getCategory_id() {
                    return category_id;
                }

                public void setCategory_id(String category_id) {
                    this.category_id = category_id;
                }

                public String getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(String product_id) {
                    this.product_id = product_id;
                }

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public String getProduct_title() {
                    return product_title;
                }

                public void setProduct_title(String product_title) {
                    this.product_title = product_title;
                }

                public String getReal_price() {
                    return real_price;
                }

                public void setReal_price(String real_price) {
                    this.real_price = real_price;
                }

                public String getQuantity() {
                    return quantity;
                }

                public void setQuantity(String quantity) {
                    this.quantity = quantity;
                }

                public String getPoint() {
                    return point;
                }

                public void setPoint(String point) {
                    this.point = point;
                }

                public String getImg_url() {
                    return img_url;
                }

                public void setImg_url(String img_url) {
                    this.img_url = img_url;
                }

                public String getCost_point() {
                    return cost_point;
                }

                public void setCost_point(String cost_point) {
                    this.cost_point = cost_point;
                }

                public String getRecargo() {
                    return recargo;
                }

                public void setRecargo(String recargo) {
                    this.recargo = recargo;
                }

                public String getPayment_status() {
                    return payment_status;
                }

                public void setPayment_status(String payment_status) {
                    this.payment_status = payment_status;
                }

                public Object getPayment_time() {
                    return payment_time;
                }

                public void setPayment_time(Object payment_time) {
                    this.payment_time = payment_time;
                }

                public Object getExpress_id() {
                    return express_id;
                }

                public void setExpress_id(Object express_id) {
                    this.express_id = express_id;
                }

                public String getExpress_fee() {
                    return express_fee;
                }

                public void setExpress_fee(String express_fee) {
                    this.express_fee = express_fee;
                }

                public String getExpress_status() {
                    return express_status;
                }

                public void setExpress_status(String express_status) {
                    this.express_status = express_status;
                }

                public Object getExpress_start_time() {
                    return express_start_time;
                }

                public void setExpress_start_time(Object express_start_time) {
                    this.express_start_time = express_start_time;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public Object getOrder_type() {
                    return order_type;
                }

                public void setOrder_type(Object order_type) {
                    this.order_type = order_type;
                }

                public String getOrder_buy_type() {
                    return order_buy_type;
                }

                public void setOrder_buy_type(String order_buy_type) {
                    this.order_buy_type = order_buy_type;
                }

                public Object getIs_return() {
                    return is_return;
                }

                public void setIs_return(Object is_return) {
                    this.is_return = is_return;
                }

                public Object getReturn_status() {
                    return return_status;
                }

                public void setReturn_status(Object return_status) {
                    this.return_status = return_status;
                }

                public Object getReturn_start_time() {
                    return return_start_time;
                }

                public void setReturn_start_time(Object return_start_time) {
                    this.return_start_time = return_start_time;
                }

                public Object getReturn_end_time() {
                    return return_end_time;
                }

                public void setReturn_end_time(Object return_end_time) {
                    this.return_end_time = return_end_time;
                }

                public String getIs_refund() {
                    return is_refund;
                }

                public void setIs_refund(String is_refund) {
                    this.is_refund = is_refund;
                }

                public String getRefund_status() {
                    return refund_status;
                }

                public void setRefund_status(String refund_status) {
                    this.refund_status = refund_status;
                }

                public Object getRefund_start_time() {
                    return refund_start_time;
                }

                public void setRefund_start_time(Object refund_start_time) {
                    this.refund_start_time = refund_start_time;
                }

                public Object getRefund_end_time() {
                    return refund_end_time;
                }

                public void setRefund_end_time(Object refund_end_time) {
                    this.refund_end_time = refund_end_time;
                }

                public String getIs_del() {
                    return is_del;
                }

                public void setIs_del(String is_del) {
                    this.is_del = is_del;
                }

                public String getFranchisee_id() {
                    return franchisee_id;
                }

                public void setFranchisee_id(String franchisee_id) {
                    this.franchisee_id = franchisee_id;
                }

                public Object getFw_img() {
                    return fw_img;
                }

                public void setFw_img(Object fw_img) {
                    this.fw_img = fw_img;
                }

                public Object getBespeak_time() {
                    return bespeak_time;
                }

                public void setBespeak_time(Object bespeak_time) {
                    this.bespeak_time = bespeak_time;
                }

                public String getAmount_offset() {
                    return amount_offset;
                }

                public void setAmount_offset(String amount_offset) {
                    this.amount_offset = amount_offset;
                }

                public String getShop_id() {
                    return shop_id;
                }

                public void setShop_id(String shop_id) {
                    this.shop_id = shop_id;
                }

                public Object getFranchisee_tech() {
                    return franchisee_tech;
                }

                public void setFranchisee_tech(Object franchisee_tech) {
                    this.franchisee_tech = franchisee_tech;
                }

                public String getExpect_time() {
                    return expect_time;
                }

                public void setExpect_time(String expect_time) {
                    this.expect_time = expect_time;
                }

                public Object getPrepay_id() {
                    return prepay_id;
                }

                public void setPrepay_id(Object prepay_id) {
                    this.prepay_id = prepay_id;
                }

                public Object getTotals() {
                    return totals;
                }

                public void setTotals(Object totals) {
                    this.totals = totals;
                }

                public Object getConfirm_time() {
                    return confirm_time;
                }

                public void setConfirm_time(Object confirm_time) {
                    this.confirm_time = confirm_time;
                }

                public String getCoupon_type() {
                    return coupon_type;
                }

                public void setCoupon_type(String coupon_type) {
                    this.coupon_type = coupon_type;
                }

                public Object getActivity_id() {
                    return activity_id;
                }

                public void setActivity_id(Object activity_id) {
                    this.activity_id = activity_id;
                }

                public String getCoupon_id() {
                    return coupon_id;
                }

                public void setCoupon_id(String coupon_id) {
                    this.coupon_id = coupon_id;
                }
            }
        }
    }
}
