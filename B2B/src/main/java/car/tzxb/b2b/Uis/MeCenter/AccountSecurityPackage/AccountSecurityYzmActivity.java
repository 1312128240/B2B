package car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.SPUtil;

public class AccountSecurityYzmActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.textView3)
    TextView tv_phone;
    @Override
    public void initParms(Bundle parms) {
        String from = parms.getString("from");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_account_security_yzm;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("验证手机");
        String mobile= SPUtil.getInstance(MyApp.getContext()).getMobile("Mobile",null);
        String maskNumber = mobile.substring(0,3)+"****"+mobile.substring(7,mobile.length());
        tv_phone.setText(Html.fromHtml("短信验证码将会发送至:  "+"<font color='#000000'>" +maskNumber+ "</font>"));
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void bcak() {
        onBackPressed();
    }
}
