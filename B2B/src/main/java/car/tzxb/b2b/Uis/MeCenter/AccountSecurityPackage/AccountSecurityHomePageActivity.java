package car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.SPUtil;

public class AccountSecurityHomePageActivity extends MyBaseAcitivity {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_bind_mobile)
    TextView tv_bind_mobile;
    public static AppCompatActivity sInstance = null;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_account_security_home_page;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("账户安全");
        sInstance=this;
        String mobile= SPUtil.getInstance(MyApp.getContext()).getMobile("Mobile",null);
        String maskNumber = mobile.substring(0,3)+"****"+mobile.substring(7,mobile.length());
        tv_bind_mobile.setText("已绑定:  "+maskNumber);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }
    @OnClick(R.id.tv_change_password)
    public void chang_password(){
        Intent intent=new Intent(this,AccountSecurityYzmActivity.class);
        intent.putExtra("index",1);
        intent.putExtra("title","验证手机");
        startActivity(intent);
    }
    @OnClick(R.id.tv_change_zf)
    public void chang_zf(){
        Intent intent=new Intent(this,AccountSecurityYzmActivity.class);
        intent.putExtra("index",2);
        intent.putExtra("title","身份验证");
        startActivity(intent);
    }
    @OnClick(R.id.ll_chang_bind_phone)
    public void chang_bind(){
        Intent intent=new Intent(this,AccountSecurityYzmActivity.class);
        intent.putExtra("index",3);
        intent.putExtra("title","换绑手机");
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        sInstance=null;
    }

}
