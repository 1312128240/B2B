package car.tzxb.b2b.Uis.HomePager.Wallet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.MyWalletBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage.AccountSecurityYzmActivity;
import car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage.ResetPayPasswordActivity;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.AlterDialogFragment;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class MyWalletActivity extends MyBaseAcitivity {

    private int pay;
    @BindView(R.id.tv_my_wallet_price)
    TextView tv_my_balance;
    @BindView(R.id.recy_my_wallet)
    RecyclerView recyclerView;
    private String userId;
    private List<MyWalletBean.DataBean.DetailBean> beanList = new ArrayList<>();
    private CommonAdapter<MyWalletBean.DataBean.DetailBean> adapter;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_wallet;
    }

    @Override
    public void doBusiness(Context mContext) {

        initAdapter();
    }


    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<MyWalletBean.DataBean.DetailBean>(MyApp.getContext(), R.layout.my_gold_sign_item, beanList) {
            @Override
            protected void convert(ViewHolder holder, MyWalletBean.DataBean.DetailBean dataBean, int position) {
                RelativeLayout parent= holder.getView(R.id.my_gold_sign_parent);
                parent.setPadding(20,10,20,10);
                parent.setBackgroundColor(Color.WHITE);
                TextView tv1 = holder.getView(R.id.tv_offline_payment);
                tv1.setVisibility(View.VISIBLE);
                tv1.setPadding(0,0,0,10);
                tv1.setTextColor(Color.BLACK);
                tv1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv1.setText(dataBean.getTitle());
                holder.getView(R.id.ll_gold_sign).setVisibility(View.GONE);
                LinearLayout ll_discoutn=holder.getView(R.id.ll_dicounts);
                ll_discoutn.setVisibility(View.VISIBLE);
                ll_discoutn.setBackgroundColor(Color.WHITE);

                holder.setText(R.id.tv_discounts_content,dataBean.getAdd_time());
                holder.setText(R.id.tv_discounts_num,dataBean.getTotal_fee());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        getPassword();
        getData();
    }

    private void getPassword() {
        Log.i("是否设置过支付密码", Constant.baseUrl + "item/index.php?c=Home&m=UserIsSetPayPassWord" + "&user_id=" + userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Home&m=UserIsSetPayPassWord")
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        pay = response.getStrLen();
                    }
                });
    }

    private void getData() {
        Log.i("我的充值明细", Constant.baseUrl + "orders/user_wallet.php?m=user_details" + "&user_id=" + userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/user_wallet.php?m=user_details")
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<MyWalletBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(MyWalletBean response, int id) {
                        MyWalletBean.DataBean xBean=response.getData();
                        beanList = xBean.getDetail();
                        adapter.add(beanList, true);
                        //可用余额
                        String balance=xBean.getB2b_balance();
                        if(balance==null||"".equals(balance)){
                            tv_my_balance.setText(Html.fromHtml("¥ "+"<big>"+0.00+"</big>"));
                        }else {
                            tv_my_balance.setText(Html.fromHtml("¥ "+"<big>"+xBean.getB2b_balance()+"</big>"));
                        }

                    }
                });

    }

    @OnClick(R.id.tv_recharge)
    public void recharge() {
        if (pay != 32) {
            showDialogFragment();
        } else {
            startActivity(RechargeActivity.class);
        }

    }

    private void showDialogFragment() {
        final AlterDialogFragment adf = new AlterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "充值前请选设置支付密码");
        bundle.putString("ok", "去设置");
        bundle.putString("no", "以后再说");
        adf.setArguments(bundle);
        adf.show(getSupportFragmentManager(), "aa");
        adf.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                adf.dismiss();
            }

            @Override
            public void sure() {
                Intent intent = new Intent(MyWalletActivity.this, AccountSecurityYzmActivity.class);
                intent.putExtra("index", 2);
                intent.putExtra("title", "身份验证");
                startActivity(intent);
                adf.dismiss();
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }
}
