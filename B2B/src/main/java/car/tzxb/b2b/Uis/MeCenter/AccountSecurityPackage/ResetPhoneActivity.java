package car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
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
import com.umeng.analytics.MobclickAgent;

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
import car.tzxb.b2b.Uis.MeCenter.SettingsActivity;
import car.tzxb.b2b.Util.AnimationUtil;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Util.StringUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class ResetPhoneActivity extends MyBaseAcitivity {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.btn_new_phone_ok)
    Button btn;
    @BindView(R.id.et_new_phone)
    EditText et_phone;
    @BindView(R.id.et_new_phone_yzm)
    EditText et_yzm;
    @BindView(R.id.tv_get_yzm)
    CountDownTextView tv_get_yzm;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_reset_phone;
    }

    @Override
    public void doBusiness(Context mContext) {
          tv_title.setText("换绑手机");
          initEvent();
    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    private void initEvent() {
       et_yzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==4){
                    btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg1));
                }else {
                    btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg10));
                }
            }
        });
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }

    @OnClick(R.id.tv_get_yzm)
    public void get_yzm(){

        String mobile=et_phone.getText().toString();
        if(TextUtils.isEmpty(mobile)||mobile.length()<11){
            AnimationUtil.Sharke(MyApp.getContext(),et_phone);
            MyToast.makeTextAnim(MyApp.getContext(),"请输入11位手机号",0,Gravity.CENTER,0,0).show();
            return;
        }
        hideSoftInput();
        //生成签名才能获取验证码
        Long time = new Date().getTime() / 1000;
        String m = "ChangeMobile";
        String stringA = "m=" + m + "&mobile=" + mobile + "&timestamp=" + time + "&key=6ljH6wpC4vDPy%Ruqlr4JJmG0kLo%^yN";
        String sign = StringUtil.stringToMD5(stringA);
        //签名转大写
        StringBuilder sb_sign = StringUtil.UpperLowerCase(sign);
        Log.i("为新手机号获取验证码", Constant.baseUrl + "messages/send.php?" + "&m=" + m + "&mobile=" + mobile + "&timestamp=" + time + "&sign=" + sb_sign);
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

    @OnClick(R.id.btn_new_phone_ok)
    public void ok(){
        String new_phone=et_phone.getText().toString();
        String yzm=et_yzm.getText().toString();
        if(TextUtils.isEmpty(new_phone)||new_phone.length()<11){
            AnimationUtil.Sharke(MyApp.getContext(),et_phone);
            MyToast.makeTextAnim(MyApp.getContext(),"请输入11位手机号码",0, Gravity.CENTER,0,0).show();
            return;
        }

        if(TextUtils.isEmpty(yzm)||yzm.length()<4){
            AnimationUtil.Sharke(MyApp.getContext(),et_yzm);
            MyToast.makeTextAnim(MyApp.getContext(),"请输入4位验证码",0, Gravity.CENTER,0,0).show();
            return;
        }
        if(isFastClick()){
            ChangePhone(new_phone,yzm);
        }

    }

    private void ChangePhone(final String new_phone, String yzm) {
        String userId= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        Log.i("换绑手机",Constant.baseUrl+"item/index.php?c=Home&m=ChangeUserMobileVerify"+"&user_id="+userId+"&mobile="+new_phone+"&code="+yzm);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"item/index.php?c=Home&m=ChangeUserMobileVerify")
                .addParams("user_id",userId)
                .addParams("mobile",new_phone)
                .addParams("code",yzm)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {

                          if(response.getStatus()==1){
                              MyToast.makeTextAnim(MyApp.getContext(),"换绑成功,请您重新登录",0, Gravity.CENTER,0,0).show();
                              deleteActivity(new_phone);
                          }else {
                              MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0, Gravity.CENTER,0,0).show();
                          }
                    }
                });
    }

    private void deleteActivity(String new_phone) {
        SettingsActivity a1= (SettingsActivity) SettingsActivity.sInstance;
        AccountSecurityHomePageActivity a2= (AccountSecurityHomePageActivity) AccountSecurityHomePageActivity.sInstance;
        AccountSecurityYzmActivity a3= (AccountSecurityYzmActivity) AccountSecurityYzmActivity.sInstance;
        a1.finish();
        a2.finish();
        a3.finish();
        this.finish();
        //保存新手机号
        SPUtil.getInstance(MyApp.getContext()).putMobile("Mobile",new_phone);
        //删除用户id
        SPUtil.getInstance(MyApp.getContext()).dele("UserId");
        MobclickAgent.onProfileSignOff();           //友盟统计退出
        onBackPressed();
    }
}
