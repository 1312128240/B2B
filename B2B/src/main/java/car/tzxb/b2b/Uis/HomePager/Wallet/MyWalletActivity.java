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

public class MyWalletActivity extends MyBaseAcitivity {


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
        startActivity(RechargeActivity.class);
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }
}
