package car.tzxb.b2b.Uis.HomePager.Wallet;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage.ResetPayPasswordActivity;
import car.tzxb.b2b.Views.DialogFragments.AlterDialogFragment;

public class MyWalletActivity extends MyBaseAcitivity {

    private boolean b;
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
    @OnClick(R.id.tv_recharge)
    public void recharge(){
        if(b){
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
                startActivity(ResetPayPasswordActivity.class);
                adf.dismiss();
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }
}
