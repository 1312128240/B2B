package car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.GatewayInfo;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.umeng.analytics.MobclickAgent;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.MeCenter.SettingsActivity;
import car.tzxb.b2b.Util.AnimationUtil;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Util.StringUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class ResetPasswordActivity extends MyBaseAcitivity {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.et_new_pass)
    EditText et;
    @BindView(R.id.btn_new_pass)
    Button btn;
    @BindView(R.id.iv_visible)
    ImageView iv_visible;
    private boolean flag1;
    private String mobile;
    private String yzm;

    @Override
    public void initParms(Bundle parms) {
        yzm = getIntent().getStringExtra("yzm");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_reset_password;
    }


    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("重置密码");
        mobile = SPUtil.getInstance(MyApp.getContext()).getMobile("Mobile",null);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                   if(s.length()>=6){
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

    @OnClick(R.id.iv_visible)
    public void visible(){
        if (flag1) {
            iv_visible.setImageResource(R.mipmap.login_icon_cansee);
            et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            flag1 = false;
        } else {
            iv_visible.setImageResource(R.mipmap.login_icon_nosee);
            et.setTransformationMethod(PasswordTransformationMethod.getInstance());
            flag1 = true;
        }
        et.setSelection(et.getText().length());
    }

  @OnClick(R.id.btn_new_pass)
  public void new_pass(){
      String newPass=et.getText().toString();
      if(TextUtils.isEmpty(newPass)||newPass.length()<6){
          AnimationUtil.Sharke(MyApp.getContext(),et);
          MyToast.makeTextAnim(MyApp.getContext(),"请输入6-20位的密码",0, Gravity.CENTER,0,0).show();
          return;
      }
      //密码加密
      String strA = "pwd=" + newPass;
      String strB = strA + "&key=!qJwHh!8Ln6ELn3rbFMk5c$vW#l13QLe";
      String pwdMd5 = StringUtil.stringToMD5(strB);
      StringBuilder sb_pasw = StringUtil.UpperLowerCase(pwdMd5);
      //生成签名
      final Long time = new Date().getTime() / 1000;
      String m = "retrievepwd";
      String stringA = "code=" + yzm + "&m=" + m + "&pwd=" + sb_pasw + "&timestamp=" + time + "&uname=" + mobile + "&key=6ljH6wpC4vDPy%Ruqlr4JJmG0kLo%^yN";
      final String sign = StringUtil.stringToMD5(stringA);
      StringBuilder sb_sign = StringUtil.UpperLowerCase(sign);
      OkHttpUtils
              .post()
              .url(Constant.baseUrl + "login/login.php?m=retrievepwd")
              .tag(this)
              .addParams("uname", mobile)
              .addParams("pwd", sb_pasw.toString())
              .addParams("timestamp", time.toString())
              .addParams("code", yzm)
              .addParams("sign", sb_sign.toString())
              .build()
              .execute(new GenericsCallback<BaseDataBean>(new JsonGenericsSerializator()) {
                  @Override
                  public void onError(Call call, Exception e, int id) {

                  }

                  @Override
                  public void onResponse(BaseDataBean response, int id) {
                      tLog("修改密码返回" + response.getStatus() + "_______" + response.getMsg());
                      if (response.getStatus() == 1) {
                          deleActivity();
                      } else {
                          Snackbar.make(tv_title, response.getMsg(), Snackbar.LENGTH_SHORT).show();
                      }
                  }
              });
  }

    private void deleActivity() {
        SettingsActivity a1= (SettingsActivity) SettingsActivity.sInstance;
        AccountSecurityHomePageActivity a2= (AccountSecurityHomePageActivity) AccountSecurityHomePageActivity.sInstance;
        AccountSecurityYzmActivity a3= (AccountSecurityYzmActivity) AccountSecurityYzmActivity.sInstance;
        a1.finish();
        a2.finish();
        a3.finish();
        this.finish();
        //退出重新登录
        SPUtil.getInstance(MyApp.getContext()).dele("UserId");
        MobclickAgent.onProfileSignOff();
        onBackPressed();
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }
}
