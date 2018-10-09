package car.tzxb.b2b.Uis.MeCenter.IntegralShop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.request.GetRequest;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.MyAddressBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.ActivityManagerUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.AlterDialogFragment;
import car.tzxb.b2b.Views.PopWindow.AddressPop;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class IntegralXqActivity extends MyBaseAcitivity {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_jf_goods_name)
    TextView tv_jf_name;
    @BindView(R.id.tv_jf_gold)
    TextView tv_gold;
    @BindView(R.id.tv_jf_original_prices)
    TextView tv_original_prices;
    @BindView(R.id.tv_jf_content)
    TextView tv_content;
    @BindView(R.id.iv_jf_xq)
    ImageView iv_jf;
    @BindView(R.id.tv_show_number)
    TextView tv_show;
    @BindView(R.id.parent)
    View parent;
    private String product_id;
    public int count = 1;

    @Override
    public void initParms(Bundle parms) {
        product_id = getIntent().getStringExtra("product_id");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_integral_xq;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("积分商城");
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        Log.i("积分商品详情", Constant.baseUrl + "goods/integralgoods.php?m=integralshop_list" + "&product_id=" + product_id);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "goods/integralgoods.php?m=integralshop_list")
                .addParams("product_id", product_id)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        if (response.getData().size() != 0) {
                            initUi(response);
                        }

                    }
                });
    }

    private void initUi(BaseDataListBean response) {
        BaseDataListBean.DataBean bean = response.getData().get(0);
        Glide.with(MyApp.getContext()).load(bean.getPic()).into(iv_jf);

        tv_jf_name.setText(bean.getTitle());
        tv_gold.setText(bean.getCost_point() + "积分");

        tv_original_prices.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        tv_original_prices.setText("原价: " + bean.getMarket_price());

        tv_content.setText(bean.getContents());
    }

    @OnClick(R.id.iv_plus)
    public void plus() {
        count++;
        tv_show.setText(count + "");
    }

    @OnClick(R.id.iv_subtract)
    public void subtract() {
        if (count == 1) {
            return;
        }
        count--;
        tv_show.setText(count + "");
    }

    @OnClick(R.id.btn_dh)
    public void dh(){
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/address.php?m=address_list")
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<MyAddressBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(MyAddressBean response, int id) {
                        List<MyAddressBean.DataBean.AddressBean> tempList = response.getData().getAddress();
                        AddressPop ap=new AddressPop(MyApp.getContext(),tempList);
                        ap.show(parent);
                        ap.setClickAddress(new AddressPop.ClickAddress() {
                            @Override
                            public void click(MyAddressBean.DataBean.AddressBean bean) {
                                 ShowHintDialogFragment(bean);
                            }
                        });
                    }
                });

    }

    private void ShowHintDialogFragment(final MyAddressBean.DataBean.AddressBean xBean) {
        final AlterDialogFragment alterDialogFragment = new AlterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "收货地址已选择,是否立即兑换?");
        bundle.putString("ok", "立即兑换");
        bundle.putString("no", "再考虑下");
        alterDialogFragment.setArguments(bundle);
        alterDialogFragment.show(getSupportFragmentManager(), "aaa");
        alterDialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                alterDialogFragment.dismiss();
            }

            @Override
            public void sure() {
                alterDialogFragment.dismiss();
                conversion(xBean);
            }
        });
    }

    private void conversion(MyAddressBean.DataBean.AddressBean xBean) {
        String userName=SPUtil.getInstance(MyApp.getContext()).getUserId("Mobile",null);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"goods/integralgoods.php?m=exchange_order")
                .addParams("user_id",xBean.getUser_id())
                .addParams("user_name",userName)
                .addParams("pay_device","Android")
                .addParams("pro_id",product_id)
                .addParams("dealer_name",xBean.getUser_name())
                .addParams("dealer_mobile",xBean.getMobile())
                .addParams("dealer_address",xBean.getAddress())
                .addParams("shop_id","12")
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.makeTextAnim(MyApp.getContext(),"兑换失败",0, Gravity.CENTER,0,0).show();
                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                           if(response.getStatus()==1){
                               MyToast.makeTextAnim(MyApp.getContext(),"恭喜您兑换成功",0, Gravity.CENTER,0,0).show();
                               new Handler().postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       ActivityManagerUtils.getInstance().finishActivity(IntegralXqActivity.this);
                                   }
                               },800);

                           }else {
                               MyToast.makeTextAnim(MyApp.getContext(),"兑换失败",0, Gravity.CENTER,0,0).show();
                           }
                    }
                });
        Log.i("积分兑换", GetRequest.Path);
    }

}
