package car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CountDown.CountDownTextView;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.OpenShopPackage.OpenShopEntranceActivity;
import car.tzxb.b2b.Util.AnimationUtil;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Util.StringUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class AccountSecurityYzmActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.textView3)
    TextView tv_phone;
    @BindView(R.id.et_accountSecurity)
    EditText et;
    @BindView(R.id.btn_verification_next)
    Button btn;
    @BindView(R.id.tv_get_yzm)
    CountDownTextView tv_get_yzm;
    private int index;
    private String title;
    private String mobile;
    public static AppCompatActivity sInstance = null;
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
        sInstance=this;
        tv_title.setText(title);
        mobile = SPUtil.getInstance(MyApp.getContext()).getMobile("Mobile",null);
        String maskNumber = mobile.substring(0,3)+"****"+ mobile.substring(7, mobile.length());
        tv_phone.setText(Html.fromHtml("短信验证码将会发送至:  "+"<font color='#000000'>" +maskNumber+ "</font>"));

        initEvent();

    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    private void initEvent() {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>=4){
                    btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg1));
                }else {
                    btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg10));
                }
            }
        });
    }

    @OnClick(R.id.tv_get_yzm)
    public void get_yzm(){
           hideSoftInput();
           switch (index){
               case 1:
                   yzm("retrievepwd");
                   break;
               case 2:
                   yzm("UserSetPayPassWord");
                   break;
               case 3:
                   yzm("ChangeUserMobileVerify");
                   break;
           }
    }

    /**
     * 获取验证码
     */
    private void yzm(String m) {
        //生成签名才能获取验证码
        Long time = new Date().getTime() / 1000;
        String stringA = "m=" + m + "&mobile=" + mobile + "&timestamp=" + time + "&key=6ljH6wpC4vDPy%Ruqlr4JJmG0kLo%^yN";
        String sign = StringUtil.stringToMD5(stringA);
        //签名转大写
        StringBuilder sb_sign = StringUtil.UpperLowerCase(sign);
        Log.i("获取修改密码的验证码", Constant.baseUrl + "messages/send.php?" + "&m=" + m + "&mobile=" + mobile + "&timestamp=" + time + "&sign=" + sb_sign);
        OkHttpUtils
                .post()
                .tag(this)
                .url(Constant.baseUrl + "messages/send.php?")
                .addParams("m", m)
                .addParams("mobile", mobile)
                .addParams("timestamp", String.valueOf(time))
                .addParams("sign", sb_sign.toString())
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        String code = response.getCode();
                        if ("OK".equals(code)) {
                            Snackbar.make(tv_title, "验证码已发送至您的手机,请查收!", Snackbar.LENGTH_SHORT).show();
                            tv_get_yzm.start();
                        } else if ("isv.OUT_OF_SERVICE".equals(code)) {
                            Snackbar.make(tv_title, "此号码已停机", Snackbar.LENGTH_SHORT).show();
                        } else if ("isv.MOBILE_NUMBER_ILLEGAL".equals(code)) {
                            Snackbar.make(tv_title, "手机号码不正确", Snackbar.LENGTH_SHORT).show();
                        } else if ("isv.BUSINESS_LIMIT_CONTROL".equals(code)) {
                            Snackbar.make(tv_title, "此手机号因发送频繁,被限制获取", Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });
    }



    @OnClick(R.id.btn_verification_next)
    public void next(){
        String yzm=et.getText().toString();
        hideSoftInput();
        if(TextUtils.isEmpty(yzm)||yzm.length()<4){
            MyToast.makeTextAnim(MyApp.getContext(),"请输入4位验证码",0, Gravity.CENTER,0,0).show();
            return;
        }

        if(isFastClick()){
            switch (index){
                case 1:
                    comparisonYzm(yzm,"retrievepwd");
                    break;
                case 2:
                    comparisonYzm(yzm,"UserSetPayPassWord");
                    break;
                case 3:
                    comparisonYzm(yzm,"ChangeUserMobileVerify");
                    break;
            }
        }
    }

    /**
     * 比对验证码是否正确
     * @param yzm
     * @param m
     */
    private void comparisonYzm(final String yzm, String m) {
        Log.i("验证码比对",Constant.baseUrl+"messages/verify.php?m=SendVerify"+"&mobile="+mobile+"&type="+m+"&code="+yzm);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"messages/verify.php?m=SendVerify")
                .addParams("mobile",mobile)
                .addParams("type",m)
                .addParams("code",yzm)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                    // startActivity(new Intent(AccountSecurityYzmActivity.this,ResetPayPasswordActivity.class));

                  if (response.isFlag() == true) {
                            Intent intent=new Intent();
                            switch (index){
                                case 1:
                                    intent.setClass(AccountSecurityYzmActivity.this,ResetPasswordActivity.class);
                                    intent.putExtra("yzm",yzm);
                                    break;
                                case 2:
                                    intent.setClass(AccountSecurityYzmActivity.this,ResetPayPasswordActivity.class);
                                    break;
                                case 3:
                                    intent.setClass(AccountSecurityYzmActivity.this,ResetPhoneActivity.class);
                                    break;
                            }

                            startActivity(intent);

                        } else {
                            Snackbar.make(tv_title, response.getMsg(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }




    @OnClick(R.id.tv_actionbar_back)
    public void bcak() {
        onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sInstance=null;
    }
}
