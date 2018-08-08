package car.tzxb.b2b.Uis.HomePager.Wallet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseStringBean;
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

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_wallet;
    }

    @Override
    public void doBusiness(Context mContext) {


    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userId= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        Log.i("是否设置过支付密码",Constant.baseUrl+"item/index.php?c=Home&m=UserIsSetPayPassWord"+"&user_id="+userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"item/index.php?c=Home&m=UserIsSetPayPassWord")
                .addParams("user_id",userId)
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

    @OnClick(R.id.tv_recharge)
    public void recharge(){
        if(pay==32){
            showDialogFragment();
        }else {
            startActivity(RechargeActivity.class);
        }

    }

    private void showDialogFragment() {
        final AlterDialogFragment adf=new AlterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "充值前请选设置支付密码");
        bundle.putString("ok", "去设置");
        bundle.putString("no", "以后再说");
        adf.setArguments(bundle);
        adf.show(getSupportFragmentManager(),"aa");
        adf.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                 adf.dismiss();
            }

            @Override
            public void sure() {
                Intent intent=new Intent(MyWalletActivity.this, AccountSecurityYzmActivity.class);
                intent.putExtra("index",2);
                intent.putExtra("title","身份验证");
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
