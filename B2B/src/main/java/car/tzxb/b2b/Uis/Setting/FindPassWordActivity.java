package car.tzxb.b2b.Uis.Setting;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.url;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.LoginActivity;
import car.tzxb.b2b.Util.AnimationUtil;
import car.tzxb.b2b.Util.StringUtil;
import car.tzxb.b2b.Views.CountDown.CountDownTextView;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

import static com.example.mylibrary.HttpClient.url.baseUrl;

public class FindPassWordActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.et_find_phone)
    EditText et_phone;
    @BindView(R.id.et_find_pass)
    EditText et_yzm;
    @BindView(R.id.et_new_pass)
    EditText et_new_pass;
    @BindView(R.id.tv_find_yzm)
    CountDownTextView tv_yzm;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_find_pass_word;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("找回密码");
        initTextWatch();
    }

    private void initTextWatch() {
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //不能超进11位
                if (s.length() > 11) {
                    String str = et_phone.getText().toString();
                    String body = str.substring(0, str.length() - 1);
                    et_phone.setText(body);
                    et_phone.setSelection(body.length());
                } else if (s.length() == 11) {
                    hideSoftInput();
                }
            }
        });

        et_yzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //不能超进4位
                if (s.length() > 4) {
                    String str = et_yzm.getText().toString();
                    String body = str.substring(0, str.length() - 1);
                    et_yzm.setText(body);
                    et_yzm.setSelection(body.length());
                } else if (s.length() == 4) {
                    hideSoftInput();
                }
            }
        });

        et_new_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //不能超进4位
                if (s.length() > 16) {
                    String str = et_new_pass.getText().toString();
                    String body = str.substring(0, str.length() - 1);
                    et_new_pass.setText(body);
                    et_new_pass.setSelection(body.length());
                } else if (s.length() == 16) {
                    hideSoftInput();
                }
            }
        });
    }

    //获取验证码
    @OnClick(R.id.tv_find_yzm)
    public void yzm() {
        hideSoftInput();
        String mobile = et_phone.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            AnimationUtil.Sharke(MyApp.getContext(), et_phone);
            Snackbar.make(tv_title, "请输入手机号", Snackbar.LENGTH_SHORT).show();
            return;
        }
        //生成签名才能获取验证码
        Long time = new Date().getTime() / 1000;
        String m = "retrievepwd";

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
                            tv_yzm.start();
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

    //提交更改密码
    @OnClick(R.id.btn_submit_new_password)
    public void submit() {
        String mobile = et_phone.getText().toString();
        String yzm = et_yzm.getText().toString();
        String newPass = et_new_pass.getText().toString();

        if (TextUtils.isEmpty(mobile)) {
            AnimationUtil.Sharke(MyApp.getContext(), et_phone);
            Snackbar.make(tv_title, "请输入手机号", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(yzm)) {
            AnimationUtil.Sharke(MyApp.getContext(), et_yzm);
            Snackbar.make(tv_title, "请输入验证码", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(newPass)) {
            AnimationUtil.Sharke(MyApp.getContext(), et_new_pass);
            Snackbar.make(tv_title, "请输入新密码", Snackbar.LENGTH_SHORT).show();
            return;
        }
        //密码加密------------------------------------------------------------------------------------------
        String strA = "pwd=" + newPass;
        String strB = strA + "&key=!qJwHh!8Ln6ELn3rbFMk5c$vW#l13QLe";
        //md5加密
        String pwdMd5 = StringUtil.stringToMD5(strB);
        //将密码转大写
        StringBuilder sb_pasw = StringUtil.UpperLowerCase(pwdMd5);

        //生成签名-----------------------------------------------------------------------------------------------------
        final Long time = new Date().getTime() / 1000;
        String m = "retrievepwd";
        String stringA = "code=" + yzm + "&m=" + m + "&pwd=" + sb_pasw + "&timestamp=" + time + "&uname=" + mobile + "&key=6ljH6wpC4vDPy%Ruqlr4JJmG0kLo%^yN";
        final String sign = StringUtil.stringToMD5(stringA);
        //签名转大写
        StringBuilder sb_sign = StringUtil.UpperLowerCase(sign);

        Log.i("修改密码", Constant.baseUrl + "login/login.php?m=retrievepwd" + "&uname=" + mobile + "&pwd=" + sb_pasw + "&timestamp=" + time + "&code=" + yzm + "&sign=" + sb_sign);
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
                            onBackPressed();
                            finish();
                        } else {
                            Snackbar.make(tv_title, response.getMsg(), Snackbar.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }

}
