package car.tzxb.b2b.Uis.Order;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CustomToast.MyToast;
import car.myview.Loading.ShapeLoadingDialog;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.OrderBean;
import car.tzxb.b2b.Bean.OrderXqBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.AlterDialogFragment;
import car.tzxb.b2b.Views.DialogFragments.LoadingDialog;
import car.tzxb.b2b.Views.PopWindow.CancelOrderPop;
import car.tzxb.b2b.config.Constant;
import car.tzxb.b2b.wxapi.WXPayEntryActivity;
import okhttp3.Call;

public class OrderXqActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.ll_order_status)
    LinearLayout status;
    @BindView(R.id.recy_order_goods)
    RecyclerView recy_img;
    @BindView(R.id.tv_order_number)
    TextView tv_number;
    @BindView(R.id.tv_pay_type)
    TextView tv_pay_type;
    @BindView(R.id.tv_order_time)
    TextView tv_time;
    @BindView(R.id.tv_order_no)
    TextView tv_no;
    @BindView(R.id.tv_goods_total)
    TextView tv_goods_total;
    @BindView(R.id.tv_order_xq_offset)
    TextView tv_offset;
    @BindView(R.id.tv_actual_total)
    TextView tv_actual_total;
    @BindView(R.id.tv_order_xq_yh)
    TextView tv_yh;
    @BindView(R.id.tv_ts)
    TextView tv_ts;
    @BindView(R.id.tv_consignee_name)
    TextView tv_consignee_name;
    @BindView(R.id.tv_consignee_mobile)
    TextView tv_consignee_mobile;
    @BindView(R.id.tv_consignee_address)
    TextView tv_consignee_address;
    @BindView(R.id.iv_chose_address)
    ImageView iv_chose;
    @BindView(R.id.tv_view1)
    TextView tv1;
    @BindView(R.id.tv_view2)
    TextView tv2;
    @BindView(R.id.tv_view3)
    TextView tv3;
    @BindView(R.id.orderXq_parent)
    View parent;
    @BindView(R.id.tv_order_hint)
    TextView tv_hint;
    @BindView(R.id.tv_order_status)
    TextView tv_status;
    private String orderid;
    private OrderXqBean.DataBean.OrderDetailsBean bean;

    @Override
    public void initParms(Bundle parms) {
        orderid = getIntent().getStringExtra("orderid");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order_xq;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("订单详情");
        status.setVisibility(View.VISIBLE);
        iv_chose.setVisibility(View.INVISIBLE);
        getData();
    }

    private void initRecy() {

        recy_img.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        List<OrderXqBean.DataBean.OrderDetailsBean.ChildDataBean> list=bean.getChild_data();
        tv_number.setText("共"+list.size()+"件");
        CommonAdapter<OrderXqBean.DataBean.OrderDetailsBean.ChildDataBean> adapter= new CommonAdapter<OrderXqBean.DataBean.OrderDetailsBean.ChildDataBean>(MyApp.getContext(),R.layout.iv_item,list) {
            @Override
            protected void convert(ViewHolder holder, OrderXqBean.DataBean.OrderDetailsBean.ChildDataBean dataChildBean, int position) {

                RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(0,0,20,0);
                int i= DeviceUtils.dip2px(MyApp.getContext(),50);
                ImageView iv=holder.getView(R.id.iv_item);

                iv.setLayoutParams(params);
                Glide.with(MyApp.getContext()).load(dataChildBean.getImg_url()).override(i,i).into(iv);
            }
        };
        recy_img.setAdapter(adapter);

    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
    private void getData() {
        String userid= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        Log.i("订单详情",Constant.baseUrl+"orders/order_list_mobile.php?m=order_details"+"&user_id="+userid+"&order_id="+orderid);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"orders/order_list_mobile.php?m=order_details")
                .addParams("user_id",userid)
                .addParams("order_id",orderid)
                .build()
                .execute(new GenericsCallback<OrderXqBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(OrderXqBean response, int id) {
                        bean = response.getData().getOrder_details();
                        initUi();
                    }
                });
    }

    private void initUi() {
      /*  //订单付款状态
        tv_status.setText(bean.getStatus());*/
        //商品图片和数量
        initRecy();
        //支付方式
        tv_pay_type.setText(bean.getPayment_type());
        //订单编号
        tv_no.setText(bean.getOrder_seqno());
        //下单时间
        tv_time.setText(bean.getAdd_time());
        //商品总价
        tv_goods_total.setText("¥"+bean.getAmount_goods());
        //服务费
        tv_offset.setText("¥"+bean.getAmount_offset());
        //实际支付
        tv_actual_total.setText("¥"+bean.getAmount_pay_able());
        //优惠信息
        tv_yh.setText("-¥"+bean.getAmount_coupon());
        //收货人信息
        tv_consignee_name.setText(bean.getAccept_name());
        tv_consignee_mobile.setText(bean.getMobile());
        tv_consignee_address.setText(bean.getAddress());
        //隐藏投诉商家
        tv_ts.setVisibility(View.INVISIBLE);

        if ("待付款".equals(bean.getStatus())) {         //待付款
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv1.setText("付款");
            tv2.setText("取消订单");
            tv_status.setText("等待买家付款");
            tv_hint.setText("23:59:59自动关闭");
        } else if ("待发货".equals(bean.getStatus())) { //待发货
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv1.setText("提醒发货");
            tv2.setText("查看物流");
            tv_status.setText("买家已付款");
            tv_hint.setText("商家正在准备发货");
        } else if ("待收货".equals(bean.getStatus())) { //待收货
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv1.setText("确认收货");
            tv2.setText("物流详情");
            tv_status.setText("商家已发货");
            tv_hint.setText("请注意保持联络通畅");
        } else if ("待评价".equals(bean.getStatus())) { //待评价
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
            tv1.setText("晒单评价");
            tv2.setText("物流详情");
            tv_status.setText("交易已完成");
            tv_hint.setText("如有问题请及时联系商家");

        }else if("已取消".equals(bean.getStatus())){
            tv3.setVisibility(View.VISIBLE);
            tv_status.setText("交易已关闭");
            tv_hint.setText("我不想买了");
        }

    }

    @OnClick(R.id.tv_view1)
    public void view1(){
         if("待付款".equals(bean.getStatus())){
             Intent intent=new Intent(this, WXPayEntryActivity.class);
             startActivity(intent);
         }else if("待发货".equals(bean.getStatus())) {
             Reminder();
         }else if("待收货".equals(bean.getStatus())){
             Confirm();
         }else if("待评价".equals(bean.getStatus())){
             MyToast.makeTextAnim(MyApp.getContext(),"晒单评价",0,Gravity.CENTER,0,0).show();
         }

    }


    @OnClick(R.id.tv_view2)
    public void view2(){
        if("待付款".equals(bean.getStatus())){
            cancleOrder();
        }else if("待发货".equals(bean.getStatus())) {
            //查看物流
            Intent intent=new Intent(OrderXqActivity.this,LogisticsActivity.class);
            startActivity(intent);
        }else if("待收货".equals(bean.getStatus())){
            Intent intent=new Intent(OrderXqActivity.this,LogisticsActivity.class);
            startActivity(intent);
        }else if("待评价".equals(bean.getStatus())){
            Intent intent=new Intent(OrderXqActivity.this,LogisticsActivity.class);
            startActivity(intent);
        }

    }



    @OnClick(R.id.tv_view3)
    public void view3(){
        final AlterDialogFragment alterDialogFragment=new AlterDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title","确定删除订单吗");
        bundle.putString("ok","确定");
        bundle.putString("no","再想想");
        alterDialogFragment.setArguments(bundle);
        alterDialogFragment.show(getSupportFragmentManager(),"del");
        alterDialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
               alterDialogFragment.dismiss();
            }

            @Override
            public void sure() {
                alterDialogFragment.dismiss();
                del();
            }
        });

    }

    /**
     * 提醒发货
     * @param
     */
    private void Reminder() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"orders/order_list_mobile.php?m=order_remind")
                .addParams("user_id",userId)
                .addParams("order_id",bean.getAid())
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0,Gravity.CENTER,0,0).show();
                    }
                });
    }


    /**
     * 确定删除
     */
    private void del() {
        String userId=SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"orders/order_list_mobile.php?m=order_del")
                .addParams("user_id",userId)
                .addParams("order_id",bean.getAid())
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                             if(response.getStatus()==1){
                                 onBackPressed();
                             }else {
                                 MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0,Gravity.CENTER,0,0).show();
                             }
                    }
                });
    }

    /**
     * 取消订单
     */
    private void cancleOrder() {
        final CancelOrderPop cop=new CancelOrderPop(MyApp.getContext());
        cop.show(parent);
        cop.setOnClickCancle(new CancelOrderPop.onClickCancleOrder() {
            @Override
            public void cancle(final String s) {
                String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
                //  Log.i("取消的", Constant.baseUrl + "orders/order_list_mobile.php?m=order_cancel" + "&user_id=" + userId + "&order_id=" + orderId + "&return_reason=s");
                OkHttpUtils
                        .get()
                        .url(Constant.baseUrl + "orders/order_list_mobile.php?m=order_cancel")
                        .tag(this)
                        .addParams("user_id", userId)
                        .addParams("order_id", bean.getAid())
                        .addParams("return_reason", s)
                        .build()
                        .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(BaseStringBean response, int id) {

                                if("1".equals(String.valueOf(response.getStatus()))){
                                    tv1.setVisibility(View.GONE);
                                    tv2.setVisibility(View.GONE);
                                    tv3.setVisibility(View.VISIBLE);

                                    tv_status.setText("交易已关闭");
                                    tv_hint.setText(s);
                                }else {
                                    MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0,Gravity.CENTER,0,0).show();
                                }
                                cop.dismiss();
                            }
                        });
            }
        });
    }

    /**
     * 确认收货
     */
    private void Confirm() {
        //确认收货
        final AlterDialogFragment alterDialogFragment=new AlterDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title","确定完成订单吗");
        bundle.putString("ok","确定");
        bundle.putString("no","再想想");
        alterDialogFragment.setArguments(bundle);
        alterDialogFragment.show(getSupportFragmentManager(),"del");
        alterDialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                alterDialogFragment.dismiss();
            }

            @Override
            public void sure() {
                alterDialogFragment.dismiss();
                Confirm2();
            }

            private void Confirm2() {
                //http://172.20.10.142/mobile_api/orders/order_list_mobile.php?m=order_confirm&user_id=446&order_id=93
                String userId=SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
                OkHttpUtils
                        .get()
                        .tag(this)
                        .url(Constant.baseUrl+"orders/order_list_mobile.php?m=order_confirm")
                        .addParams("user_id",userId)
                        .addParams("order_id",bean.getAid())
                        .build()
                        .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(BaseStringBean response, int id) {
                                if(response.getStatus()==1){
                                    tv1.setText("晒单评价");
                                    tv2.setText("物流详情");
                                    tv3.setVisibility(View.VISIBLE);
                                    tv_status.setText("交易已完成");
                                    tv_hint.setText("如有问题请及时联系商家");
                                    bean.setStatus("已评价");
                                }else {
                                    MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0,Gravity.CENTER,0,0).show();
                                }
                            }
                        });

            }
        });
    }


    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }

}
