package car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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
    @BindView(R.id.et_accountSecurity)
    EditText et;
    @BindView(R.id.btn_verification_next)
    Button btn;
    private int index;
    private String title;

    @Override
    public void initParms(Bundle parms) {
        index = parms.getInt("index",-1);
        title = getIntent().getStringExtra("title");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_account_security_yzm;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText(title);
        String mobile= SPUtil.getInstance(MyApp.getContext()).getMobile("Mobile",null);
        String maskNumber = mobile.substring(0,3)+"****"+mobile.substring(7,mobile.length());
        tv_phone.setText(Html.fromHtml("短信验证码将会发送至:  "+"<font color='#000000'>" +maskNumber+ "</font>"));

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                      if(s.length()!=0){
                          btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg1));
                      }else {
                          btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg10));
                      }
            }
        });
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.btn_verification_next)
    public void next(){
        if(isFastClick()){
            Intent intent=new Intent();
            switch (index){
                case 1:
                    intent.setClass(this,ResetPasswordActivity.class);
                    break;
                case 2:
                    intent.setClass(this,ResetPayPasswordActivity.class);
                    break;
                case 3:
                    intent.setClass(this,ResetPhoneActivity.class);
                    break;
            }
            startActivity(intent);
        }

    }

    @OnClick(R.id.tv_actionbar_back)
    public void bcak() {
        onBackPressed();
    }
}
