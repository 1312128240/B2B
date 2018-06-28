package car.tzxb.b2b.Uis.Order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
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
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;

import car.tzxb.b2b.Bean.OrderBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
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
    private String shopId;
    private String carId;
    private String num;
    private String proId;
    private OrderBean.DataBean dataBean;

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
          getData();
        rg.setOnCheckedChangeListener(this);
        rb1.setChecked(true);
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

        tv_num.setText("共"+lists.size()+"件"+"\n"+"(可留言)");

      CommonAdapter<OrderBean.DataBean.GoodsBean.DataChildBean> adapter=
                new CommonAdapter<OrderBean.DataBean.GoodsBean.DataChildBean>(MyApp.getContext(),R.layout.iv_item,lists) {
            @Override
            protected void convert(ViewHolder holder, OrderBean.DataBean.GoodsBean.DataChildBean dataChildBean, int position) {

                 RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                 params.setMargins(0,0,20,0);
                 int i=DeviceUtils.dip2px(MyApp.getContext(),50);
                 ImageView iv=holder.getView(R.id.iv_item);
                 iv.setLayoutParams(params);
                 Glide.with(MyApp.getContext()).load(dataChildBean.getImg_url()).override(i,i).into(iv);
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

    }
    @OnClick(R.id.tv_order_number)
    public void list(){
        Intent intent=new Intent(this,GoodsListActivity.class);
        intent.putExtra("bean",dataBean);
        startActivity(intent);
    }

    @OnClick(R.id.tv_submit_order)
    public void submit(){
        AlterDialogFragment dialogFragment=new AlterDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title","订单已生成,是否立即付款");
        bundle.putString("ok","立即付款");
        bundle.putString("no","再想想");
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(),"order");
        dialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {

            }

            @Override
            public void sure() {
             Intent intent=new Intent(OrderActivity.this, WXPayEntryActivity.class);
                startActivity(intent);
            }
        });
    }
}
