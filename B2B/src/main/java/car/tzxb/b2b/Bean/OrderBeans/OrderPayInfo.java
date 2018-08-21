package car.tzxb.b2b.Bean.OrderBeans;

/**
 * Created by admin on 2016/11/8.
 */

/**
 *  列表页中每一个大订单的金额信息View所用的数据实体
 */
public class OrderPayInfo {

    private String status;
    private int id;
    private int number;
    private int index;
    private String shop_name;
    private String order_seqno;
    private String amount_goods;
    private Object amount_coupon;
    private Object amount_offset;
    private double amount_pay_able;
    private Object amount_real;

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
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

    public Object getAmount_offset() {
        return amount_offset;
    }

    public void setAmount_offset(Object amount_offset) {
        this.amount_offset = amount_offset;
    }

    public double getAmount_pay_able() {
        return amount_pay_able;
    }

    public void setAmount_pay_able(double amount_pay_able) {
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

    private String payment_status;
    private String express_status;
    private String is_refund;
    private Object is_return;
    private Object return_status;
    private String refund_status;
    private String order_buy_type;
    private String add_time;
    private String name;
    private String mobile;
    private String expect_time;
    private String message;
    private String shop_id;
    private String accept_name;
    private String coupon_type;
    private String coupon_id;
    private String aid;
    private Object payment_id;
    private String payment_type;

    public Object getAmount_coupon() {
        return amount_coupon;
    }

    public void setAmount_coupon(Object amount_coupon) {
        this.amount_coupon = amount_coupon;
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



    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }



    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
