package car.tzxb.b2b.Uis.Order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

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

public class OrderActivity extends MyBaseAcitivity implements RadioGroup.OnCheckedChangeListener{

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
    private String shopId;
    private String carId;
    private String num;
    private String proId;
    private OrderBean.DataBean dataBean;
    private String mesg;

    @Override
    public void initParms(Bundle parms) {

        shopId = getIntent().getStringExtra("shopId");
        carId = getIntent().getStringExtra("carId");
        num = getIntent().getStringExtra("num");
        proId = getIntent().getStringExtra("proId");
        Log.i("购物车带过来信息", shopId +"_____"+ carId +"____"+ num +"_____"+ proId);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order;
    }

    @Override
    public void doBusiness(Context mContext) {
        rg.setOnCheckedChangeListener(this);
        rb1.setChecked(true);
        rb1.setText("送货上门");
        rb2.setText("门店自取");
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDefutAddress();
    }


    private void getDefutAddress() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"orders/address.php?m=address")
                .addParams("user_id",userId)
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
                        DefutAddressBean.DataBean.AddressBean bean=response.getData().getAddress();
                        if("1".equals(response.getStatus())){
                             tv_consignee_name.setText(bean.getUser_name());
                             tv_consignee_mobile.setText(bean.getMobile());
                             tv_consignee_address.setText(bean.getAddress());
                        }else {
                             tv_consignee_name.setHint("收货人:");
                             tv_consignee_mobile.setHint("手机:");
                             tv_consignee_address.setHint("收货地址:");
                        }
                    }
                });

    }


    private void getData() {
        String userid= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        Log.i("查看订单",Constant.baseUrl+"orders/shopping_cars_moblie.php?m=pay_list"+"&car_id="+carId+"&pro_id="+proId+"&num="+num+"&shop_id="+shopId
                +"&user_id="+userid+"&motion_id="+"&type=");
        OkHttpUtils
                .get()
                .url(Constant.baseUrl+"orders/shopping_cars_moblie.php?m=pay_list")
                .tag(this)
                .addParams("car_id",carId)
                .addParams("pro_id",proId)
                .addParams("num",num)
                .addParams("shop_id",shopId)
                .addParams("user_id",userid)
                .addParams("motion_id","")
                .addParams("type","")
                .build()
                .execute(new GenericsCallback<OrderBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(OrderBean response, int id) {
                        dataBean = response.getData();
                        initData();
                    }
                });
    }

    private void initData() {
        tv_goods_total_price.setText("¥"+dataBean.getAmount_pay());
        tv_goods_offset.setText("¥"+dataBean.getOffset());
        tv_num.setText("共"+dataBean.getGoods_kind_number()+"件"+"\n"+"(可留言)");
        //实际付款为商品总价+服务费-折扣费
      double finaprice= dataBean.getAmount_pay()+dataBean.getOffset();
       tv_finally_price.setText(Html.fromHtml("实付款 "+"<big>"+"¥"+finaprice+"</big>"));
        recy_goods.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //取出所有商品
        List<OrderBean.DataBean.GoodsBean> goodsBean=dataBean.getGoods();
        List<OrderBean.DataBean.GoodsBean.DataChildBean> lists=new ArrayList<>();
        for (int i = 0; i <goodsBean.size() ; i++) {
            List<OrderBean.DataBean.GoodsBean.DataChildBean> childBeanList=goodsBean.get(i).getData_child();
            for (int j = 0; j <childBeanList.size() ; j++) {
                OrderBean.DataBean.GoodsBean.DataChildBean childBean=childBeanList.get(j);
                lists.add(childBean);
            }
        }



      CommonAdapter<OrderBean.DataBean.GoodsBean.DataChildBean> adapter= new CommonAdapter<OrderBean.DataBean.GoodsBean.DataChildBean>(MyApp.getContext(),R.layout.iv_item,lists) {
            @Override
            protected void convert(ViewHolder holder, OrderBean.DataBean.GoodsBean.DataChildBean dataChildBean, int position) {
                 int w=DeviceUtils.dip2px(MyApp.getContext(),50);
                 int h=DeviceUtils.dip2px(MyApp.getContext(),70);
                 RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                 params.setMargins(0,0,20,0);

                 ImageView iv=holder.getView(R.id.iv_item);

                 iv.setLayoutParams(params);
                 Glide.with(MyApp.getContext()).load(dataChildBean.getImg_url()).override(w,h).into(iv);
            }
        };
        recy_goods.setAdapter(adapter);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            RadioButton rb=group.findViewById(checkedId);
            String s=rb.getText().toString();
            if(!TextUtils.isEmpty(s)){
                tv_distribution.setText(s);
            }

    }



    @OnClick(R.id.tv_submit_order)
    public void submit(){
        String dealer_name=tv_consignee_name.getText().toString();
        String dealer_mobile=tv_consignee_mobile.getText().toString();
        String dealer_address=tv_consignee_address.getText().toString();
        if(dealer_name.isEmpty()||dealer_address.isEmpty()||dealer_mobile.isEmpty()){
            MyToast.makeTextAnim(MyApp.getContext(),"请填写收货人信息",0, Gravity.CENTER,0,0).show();
            return;
        }
        String userId=  SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        String mobile=SPUtil.getInstance(MyApp.getContext()).getMobile("Mobile",null);
        String orderType=tv_distribution.getText().toString();

        Log.i("提交订单",Constant.baseUrl + "orders/orders_mobile.php?m=order_add"+"&user_id="+userId+"&username="+mobile+"&dealer_address="+dealer_address+
                mobile+"&dealer_name="+dealer_name+"&dealer_mobile=" +dealer_mobile +"&message="+"&order_type="+orderType+
                "&expect_time="+"&pay_device=Android"+"&coupon_id=0"+"&is_car=0"+"&carid_proid="+carId);

       if(isFastClick()) {
           OkHttpUtils
                   .get()
                   .url(Constant.baseUrl + "orders/orders_mobile.php?m=order_add")
                   .tag(this)
                   .addParams("user_id", userId)
                   .addParams("username", mobile)
                   .addParams("dealer_name", dealer_name)
                   .addParams("dealer_mobile", dealer_mobile)
                   .addParams("dealer_address",dealer_address)
                   .addParams("message", mesg)
                   .addParams("order_type", orderType)
                   .addParams("expect_time", "")
                   .addParams("pay_device", "Android")
                   .addParams("coupon_id", "0")
                   .addParams("is_car", "0")
                   .addParams("carid_proid", carId)
                   .build()
                   .execute(new GenericsCallback<BaseDataBean>(new JsonGenericsSerializator()) {
                       @Override
                       public void onError(Call call, Exception e, int id) {

                       }

                       @Override
                       public void onResponse(BaseDataBean response, int id) {
                           //  Log.i("能生成订单吗",response.getMsg()+"");
                           if (response.getStatus() == 1) {
                               showDialogFragment(response);
                           }
                       }
                   });

       }
    }

    private void showDialogFragment(final BaseDataBean response) {

        AlterDialogFragment dialogFragment=new AlterDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title",response.getMsg());
        bundle.putString("ok","立即付款");
        bundle.putString("no","再想想");
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(),"order");
        final double total=response.getData().getTotal_fee();
        dialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                 Intent intent=new Intent(OrderActivity.this,OrderXqActivity.class);
                 intent.putExtra("orderid",response.getData().getOrder_id());
                 startActivity(intent);
            }

            @Override
            public void sure() {
                Intent intent=new Intent(OrderActivity.this, WXPayEntryActivity.class);
                intent.putExtra("total",total);
                intent.putExtra("order_seqnos",response.getData().getCount_seqnos());
                startActivity(intent);
            }
        });
    }

   @OnClick(R.id.rl_choice_address)
   public void chose(){
       Intent intent=new Intent(this, MyAddressActivity.class);
       intent.putExtra("from","order");
       startActivityForResult(intent,101);
   }
    @OnClick(R.id.tv_order_number)
    public void list(){
        Intent intent=new Intent(this,GoodsListActivity.class);
        intent.putExtra("bean",dataBean);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==100&&resultCode==RESULT_OK){
            if(data!=null){
                mesg = data.getStringExtra("mesg");
            }
        }else if(requestCode==101&&resultCode==RESULT_OK){
            MyAddressBean.DataBean.AddressBean bean= (MyAddressBean.DataBean.AddressBean) data.getSerializableExtra("bean");
            if(bean!=null){
                tv_consignee_name.setText(bean.getUser_name());
                tv_consignee_mobile.setText(bean.getMobile());
                tv_consignee_address.setText(bean.getAddress());
            }
        }
    }

    @OnClick(R.id.iv_back)
    public void back(){
       onBackPressed();
   }
}
