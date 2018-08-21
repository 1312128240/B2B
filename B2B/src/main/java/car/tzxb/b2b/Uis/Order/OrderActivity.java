package car.tzxb.b2b.Uis.Order;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.Bean.DefutAddressBean;
import car.tzxb.b2b.Bean.MyAddressBean;
import car.tzxb.b2b.Bean.OrderBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.MeCenter.MyAddressActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.AlterDialogFragment;
import car.tzxb.b2b.config.Constant;
import car.tzxb.b2b.wxapi.WXPayEntryActivity;
import okhttp3.Call;

public class OrderActivity extends MyBaseAcitivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.tv_goods_total_price)
    TextView tv_goods_total_price;
    @BindView(R.id.tv_goods_total_offset)
    TextView tv_goods_offset;
    @BindView(R.id.recy_order_goods)
    RecyclerView recy_goods;
    @BindView(R.id.tv_order_number)
    TextView tv_num;
    @BindView(R.id.rb_visit)
    RadioButton rb1;
    @BindView(R.id.rb_self)
    RadioButton rb2;
    @BindView(R.id.rg_order_swich)
    RadioGroup rg;
    @BindView(R.id.tv_distribution)
    TextView tv_distribution;
    @BindView(R.id.tv_consignee_name)
    TextView tv_consignee_name;
    @BindView(R.id.tv_consignee_mobile)
    TextView tv_consignee_mobile;
    @BindView(R.id.tv_consignee_address)
    TextView tv_consignee_address;
    @BindView(R.id.tv_finally_price)
    TextView tv_finally_price;
    @BindView(R.id.iv_fgx)
    ImageView iv_fgx;
    @BindView(R.id.tv_default_address)
    TextView tv_default_address;
    @BindView(R.id.ll_zf_type)
    LinearLayout ll_zf_type;
    @BindView(R.id.ll_order_address)
    LinearLayout addressLayout;
    @BindView(R.id.tv_goods_discounts)
    TextView tv_goods_discounts;
    private String shopId;
    private String carId;
    private String num;
    private String proId;
    private OrderBean.DataBean dataBean;
    private String mesg;
    private String special_id;
    private double offset1;
    private double offset2;
    private double special_money;
    private String dealer_name;
    private String dealer_mobile;
    private String dealer_address;


    @Override
    public void initParms(Bundle parms) {

        shopId = getIntent().getStringExtra("shopId");
        carId = getIntent().getStringExtra("carId");
        num = getIntent().getStringExtra("num");
        proId = getIntent().getStringExtra("proId");
        special_id = getIntent().getStringExtra("special_id");

        Log.i("购物车带过来信息", shopId + "_____" + carId + "____" + num + "_____" + proId + "______" + special_id);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order;
    }

    @Override
    public void doBusiness(Context mContext) {
        rb1.setChecked(true);
        rg.setOnCheckedChangeListener(this);
        rb1.setText("送货上门");
        rb2.setText("门店自取");
        iv_fgx.setVisibility(View.VISIBLE);
        tv_default_address.setVisibility(View.VISIBLE);
        ll_zf_type.setVisibility(View.GONE);
        getData();
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();
        getDefutAddress();
    }

    /**
     * 默认地址
     */
    private void getDefutAddress() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("我的默认地址", Constant.baseUrl + "orders/address.php?m=address" + "&user_id=" + userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/address.php?m=address")
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<DefutAddressBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        tv_consignee_name.setHint("收货人:");
                        tv_consignee_mobile.setHint("手机:");
                        tv_consignee_address.setHint("收货地址:");
                    }

                    @Override
                    public void onResponse(DefutAddressBean response, int id) {
                        // Log.i("我的默认地址",response.getData().getAddress().getUser_name()+"");
                        DefutAddressBean.DataBean.AddressBean bean = response.getData().getAddress();
                        if ("1".equals(response.getStatus())) {
                            tv_consignee_name.setText(bean.getUser_name());
                            tv_consignee_mobile.setText(bean.getMobile());
                            String address = bean.getProvince() + bean.getCity() + bean.getArea() + bean.getAddress();
                            tv_consignee_address.setText(address);
                        } else {
                            tv_consignee_name.setHint("收货人:");
                            tv_consignee_mobile.setHint("手机:");
                            tv_consignee_address.setHint("收货地址:");
                        }
                    }
                });

    }


    /**
     * 购物车过来
     */
    private void getData() {
        String userid = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("计算订单价格", Constant.baseUrl + "orders/shopping_cars_moblie.php?m=pay_list" + "&car_id=" + carId + "&pro_id=" +
                proId + "&num=" + num + "&shop_id=" + shopId + "&user_id=" + userid + "&motion_id=" + "&type=" + "&special_id=" + special_id);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "orders/shopping_cars_moblie.php?m=pay_list")
                .tag(this)
                .addParams("user_id", userid)
                .addParams("car_id", carId)
                .addParams("pro_id", proId)
                .addParams("num", num)
                .addParams("shop_id", shopId)
                .addParams("motion_id", "")
                .addParams("type", "")
                .addParams("special_id", special_id)
                .build()
                .execute(new GenericsCallback<OrderBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                       Log.i("走错误",e.toString());
                    }

                    @Override
                    public void onResponse(OrderBean response, int id) {
                        dataBean = response.getData();
                        initData();
                    }
                });
    }

    private void initData() {
        tv_goods_total_price.setText("¥" + dataBean.getAmount_price());
        //收取服务费还是优惠
        special_money = dataBean.getSpecial_money();
        //送货上门和门店自取的服务费
        offset1 = dataBean.getAll_offset();
        offset2 = dataBean.getOffset();
        tv_goods_offset.setText("¥" + offset1);
        //实付款
        tv_finally_price.setText(Html.fromHtml("实付款  " + "<font color='#FA3314'><big>" + "¥" + dataBean.getAmount_pay() + "</big></font>"));
        //总共商品数
        tv_num.setText(Html.fromHtml("<font color='#000000'>共" + dataBean.getGoods_kind_number() + "件</font>" + "<br>" + "(可留言)"));
        //优惠价钱
        tv_goods_discounts.setText("-¥" + dataBean.getDiscount_amount());
        //商品列表
        recy_goods.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //取出所有商品
        List<OrderBean.DataBean.GoodsBean> goodsBean = dataBean.getGoods();
        List<OrderBean.DataBean.GoodsBean.DataChildBean> lists = new ArrayList<>();
        for (int i = 0; i < goodsBean.size(); i++) {
            List<OrderBean.DataBean.GoodsBean.DataChildBean> childBeanList = goodsBean.get(i).getData_child();
            for (int j = 0; j < childBeanList.size(); j++) {
                OrderBean.DataBean.GoodsBean.DataChildBean childBean = childBeanList.get(j);
                lists.add(childBean);
            }
        }


        CommonAdapter<OrderBean.DataBean.GoodsBean.DataChildBean> adapter = new CommonAdapter<OrderBean.DataBean.GoodsBean.DataChildBean>(MyApp.getContext(), R.layout.iv_item, lists) {
            @Override
            protected void convert(ViewHolder holder, OrderBean.DataBean.GoodsBean.DataChildBean dataChildBean, int position) {
                int w = DeviceUtils.dip2px(MyApp.getContext(), 50);
                int h = DeviceUtils.dip2px(MyApp.getContext(), 70);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 20, 0);
                ImageView iv = holder.getView(R.id.iv_item);
                iv.setLayoutParams(params);
                Glide.with(MyApp.getContext()).load(dataChildBean.getImg_url()).override(w, h).into(iv);
            }
        };
        recy_goods.setAdapter(adapter);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_visit:
                tv_distribution.setText("送货上门");
                addressLayout.setVisibility(View.VISIBLE);
                tv_goods_offset.setText("¥" + offset1);
                tv_finally_price.setText(Html.fromHtml("实付款  " + "<font color='#FA3314'><big>" + "¥" + dataBean.getAmount_pay() + "</big></font>"));
                break;
            case R.id.rb_self:
                tv_distribution.setText("门店自取");
                addressLayout.setVisibility(View.GONE);
                tv_goods_offset.setText("¥" + offset2);
                double pay = dataBean.getAmount_pay() - dataBean.getAll_offset();
                Log.i("总价钱是",dataBean.getAmount_pay()+"");
                DecimalFormat df  = new DecimalFormat("######0.00");
                tv_finally_price.setText(Html.fromHtml("实付款  " + "<font color='#FA3314'><big>" + "¥" + df.format(pay)+ "</big></font>"));
                break;
        }

    }


    @OnClick(R.id.tv_submit_order)
    public void submit() {
        dealer_name = tv_consignee_name.getText().toString();
        dealer_mobile = tv_consignee_mobile.getText().toString();
        dealer_address = tv_consignee_address.getText().toString();
        if (rb1.isChecked()) {
            if (dealer_name.isEmpty() || dealer_mobile.isEmpty()) {
                MyToast.makeTextAnim(MyApp.getContext(), "请填写收货人信息", 0, Gravity.CENTER, 0, 0).show();
                return;
            }
        }

        if (isFastClick()) {
            //满足优惠条件
            if (rb2.isChecked()) {
                if (special_money > 0) {
                    //收取服务费
                    String hint = "尊敬的合作伙伴,根据协议,您本次订单将收取服务费" +"<font color='#FA3314'>"+"¥"+special_money +"</font>"+"元";
                    showHintDialog1(hint);
                } else {
                    //为您优惠
                    String hint = "尊敬的合作伙伴，根据协议，您本次订单达到优惠条件,优惠"+"<font color='#FA3314'>"+"¥"+special_money +"</font>"+"元";
                    showHintDialog1(hint);
                }
            } else {
                createOrder();
            }
        }
    }

    private void showHintDialog1(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage(Html.fromHtml(mess))
                .setPositiveButton("购买", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createOrder();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void showDialogFragment(final BaseDataBean response) {
        final AlterDialogFragment dialogFragment = new AlterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", response.getMsg());
        bundle.putString("ok", "立即付款");
        bundle.putString("no", "再想想");
        dialogFragment.setArguments(bundle);
        if (isStateEnable()) {
            dialogFragment.show(getSupportFragmentManager(), "order");
        }
        dialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                 onBackPressed();
            }

            @Override
            public void sure() {
                Intent intent = new Intent(OrderActivity.this, WXPayEntryActivity.class);
                double total = response.getData().getTotal_fee();
                intent.putExtra("total", String.valueOf(total));
                intent.putExtra("order_seqnos", response.getData().getCount_seqnos());
                intent.putExtra("orderid", response.getData().getOrder_id());
                startActivity(intent);
                if (isStateEnable()) {
                    dialogFragment.dismiss();
                }
            }
        });

    }

    //生成订单
    private void createOrder() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        String mobile = SPUtil.getInstance(MyApp.getContext()).getMobile("Mobile", null);
        String orderType = tv_distribution.getText().toString();
        Log.i("提交订单", Constant.baseUrl + "orders/orders_mobile.php?m=order_add" + "&user_id=" + userId + "&username=" + mobile +
                "&dealer_address=" + dealer_address + "&dealer_name=" + dealer_name + "&dealer_mobile=" + dealer_mobile + "&message=" + mesg
                + "&order_type=" + orderType + "&expect_time=" + "&pay_device=Android" + "&coupon_id=0" + "&is_car=0" + "&carid_proid=" + carId+"&special_id="+special_id);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "orders/orders_mobile.php?m=order_add")
                .tag(this)
                .addParams("user_id", userId)
                .addParams("username", mobile)
                .addParams("dealer_name", dealer_name)
                .addParams("dealer_mobile", dealer_mobile)
                .addParams("dealer_address", dealer_address)
                .addParams("message", mesg)
                .addParams("order_type", orderType)
                .addParams("expect_time", "")
                .addParams("pay_device", "Android")
                .addParams("coupon_id", "0")
                .addParams("is_car", "0")
                .addParams("carid_proid", carId)
                .addParams("special_id",special_id)
                .build()
                .execute(new GenericsCallback<BaseDataBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataBean response, int id) {
                        if (response.getStatus() == 1) {
                            showDialogFragment(response);
                        }
                    }
                });

    }

    @OnClick(R.id.rl_choice_address)
    public void chose() {
        Intent intent = new Intent(this, MyAddressActivity.class);
        intent.putExtra("from", "order");
        startActivityForResult(intent, 101);
    }

    @OnClick(R.id.tv_order_number)
    public void list() {
        Intent intent = new Intent(this, GoodsListActivity.class);
        intent.putExtra("from", "Order");
        intent.putExtra("bean", dataBean);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null) {
                mesg = data.getStringExtra("mesg");
            }
        } else if (requestCode == 101 && resultCode == RESULT_OK) {
            MyAddressBean.DataBean.AddressBean bean = (MyAddressBean.DataBean.AddressBean) data.getSerializableExtra("bean");
            if (bean != null) {
                tv_consignee_name.setText(bean.getUser_name());
                tv_consignee_mobile.setText(bean.getMobile());
                tv_consignee_address.setText(bean.getAddress());
            }
        }
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }

}
