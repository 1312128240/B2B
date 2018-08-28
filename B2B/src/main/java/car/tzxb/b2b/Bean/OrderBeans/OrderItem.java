package car.tzxb.b2b.Bean.OrderBeans;

/**
 * Created by admin on 2016/11/8.
 */

import java.util.List;

import car.tzxb.b2b.Bean.ShopCarBean;

/**
 * orderDetailList字段的每一项
 */
public class OrderItem {
    private String shop_name;
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
    private String express_fee;
    private String express_status;
    private String status;
    private String order_buy_type;
    private String is_del;
    private String franchisee_id;
    private String amount_offset;
    private String shop_id;
    private String expect_time;
    private String viewTyep;
    private String motion_id;
    private String motion_type;
    private String name;
    private boolean isChoosed;
    private String child_title;
    private double discount_amount;
    private List<ShopCarBean.DataBean.DataChildBean.GiftBean> gift;
    private String imageUrl;
    private double price;
    private int postion;
    private int count;
    private int goodsImg;

    public double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }


    public List<ShopCarBean.DataBean.DataChildBean.GiftBean> getGift() {
        return gift;
    }

    public void setGift(List<ShopCarBean.DataBean.DataChildBean.GiftBean> gift) {
        this.gift = gift;
    }



    public String getChild_title() {
        return child_title;
    }

    public void setChild_title(String child_title) {
        this.child_title = child_title;
    }

    public String getMotion_id() {
        return motion_id;
    }

    public void setMotion_id(String motion_id) {
        this.motion_id = motion_id;
    }

    public String getMotion_type() {
        return motion_type;
    }

    public void setMotion_type(String motion_type) {
        this.motion_type = motion_type;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoosed() {
        return isChoosed;
    }


    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(int goodsImg) {
        this.goodsImg = goodsImg;
    }




    private int i1;
    private int i2;

    public int getI1() {
        return i1;
    }

    public void setI1(int i1) {
        this.i1 = i1;
    }

    public int getI2() {
        return i2;
    }

    public void setI2(int i2) {
        this.i2 = i2;
    }


    public String getViewTyep() {
        return viewTyep;
    }

    public void setViewTyep(String viewTyep) {
        this.viewTyep = viewTyep;
    }


    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder_buy_type() {
        return order_buy_type;
    }

    public void setOrder_buy_type(String order_buy_type) {
        this.order_buy_type = order_buy_type;
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


    public String getExpect_time() {
        return expect_time;
    }

    public void setExpect_time(String expect_time) {
        this.expect_time = expect_time;
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
