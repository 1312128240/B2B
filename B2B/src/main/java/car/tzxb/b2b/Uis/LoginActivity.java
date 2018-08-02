package car.tzxb.b2b.Uis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.umeng.analytics.MobclickAgent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CountDown.CountDownTextView;
import car.myview.CustomToast.MyToast;
import car.myview.MorphButton.MorphingButton;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.OpenShopPackage.OpenShopActivity;
import car.tzxb.b2b.Uis.OpenShopPackage.OpenShopEntranceActivity;
import car.tzxb.b2b.Uis.Setting.FindPassWordActivity;
import car.tzxb.b2b.Util.AnimationUtil;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Util.StringUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;


public class LoginActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tab_login)
    XTabLayout tabLayout;
    @BindView(R.id.mor_button)
    MorphingButton morphingButton;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_pass)
    EditText etLoginPass;
    @BindView(R.id.iv_pass_visible)
    ImageView iv_pass_visiable;
    @BindView(R.id.tv_get_yzm)
    CountDownTextView tv_get_yzm;
    @BindView(R.id.tv_actionbar_back)
    TextView tv_back;
    @BindView(R.id.tv_agreement)
    TextView tv_agreement;
    private int index;
    private boolean flag1;

    @Override
    public void initParms(Bundle parms) {

    }


    @Override
    public int bindLayout() {
        getWindow().setEnterTransition(new Slide().setDuration(300));
        getWindow().setExitTransition(new Slide().setDuration(300));
        return R.layout.activity_login;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("同致相伴");
        tv_back.setVisibility(View.INVISIBLE);
        tv_agreement.setText(Html.fromHtml("登录即代表您已同意<font color='#F6A623'>《同致相伴平台服务协议》</font>"));
        initUi();
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    private void initUi() {
        tabLayout.addTab(tabLayout.newTab().setText("账号密码登录"));
        tabLayout.addTab(tabLayout.newTab().setText("手机验证码登录"));
        tabLayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                index = tab.getPosition();
                etLoginPass.setText("");
                etLoginPhone.setText("");
                if (index == 0) {
                    etLoginPhone.setHint(R.string.login_phone2);
                    etLoginPass.setHint(R.string.login_pass);
                    tv_get_yzm.setVisibility(View.INVISIBLE);
                    iv_pass_visiable.setImageResource(R.mipmap.login_icon_cansee);
                } else {
                    etLoginPhone.setHint(R.string.login_phone);
                    etLoginPass.setHint(R.string.yzm_pass);
                    etLoginPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_pass_visiable.setVisibility(View.INVISIBLE);
                    tv_get_yzm.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });
        //输入框的监听
        etLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(index==0){
                    String editable = etLoginPhone.getText().toString();
                    String str =StringUtil.stringFilter(editable.toString());
                    if(!editable.equals(str)){
                        etLoginPhone.setText(str);
                        //设置新的光标所在位置
                        etLoginPhone.setSelection(str.length());
                    }

                    etLoginPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                    if(s.length()==20){
                        hideSoftInput();
                    }

                }else {
                    //不能超进11位
                    etLoginPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                    String editable = etLoginPhone.getText().toString();
                    String str =StringUtil.numberFilter(editable.toString());
                    if(!editable.equals(str)){
                        etLoginPhone.setText(str);
                        //设置新的光标所在位置
                        etLoginPhone.setSelection(str.length());
                    }
                    if(s.length()==11){
                        hideSoftInput();
                    }
                }
            }
        });

        etLoginPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (index == 0) {
                    String editable = etLoginPass.getText().toString();
                    String regEx = "[^a-zA-Z0-9]";  //只能输入字母或数字
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(editable);
                    String str = m.replaceAll("").trim();    //删掉不是字母或数字的字符
                    if (!editable.equals(str)) {
                        etLoginPass.setText(str);
                        etLoginPass.setSelection(str.length());
                    }


                } else {

                    String editable = etLoginPass.getText().toString();
                    String regEx = "[^0-9]";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(editable);
                    String str = m.replaceAll("").trim();
                    if (!editable.equals(str)) {
                        etLoginPass.setText(str);
                        etLoginPass.setSelection(str.length());
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (index == 0) {     //密码
                    if (s.length() > 0) {
                        iv_pass_visiable.setVisibility(View.VISIBLE);
                    } else {
                        iv_pass_visiable.setVisibility(View.INVISIBLE);
                    }
                    if (s.length() > 16) {
                        String str = etLoginPass.getText().toString();
                        String body = str.substring(0, str.length() - 1);
                        etLoginPass.setText(body);
                        etLoginPass.setSelection(body.length());
                    }

                } else {
                    if (s.length() > 4) {
                        String str = etLoginPass.getText().toString();
                        String body = str.substring(0, str.length() - 1);
                        etLoginPass.setText(body);
                        etLoginPass.setSelection(body.length());
                    } else if (s.length() == 4) {
                        hideSoftInput();
                    }
                }

            }
        });

    }

    @OnClick(R.id.iv_pass_visible)
    public void visiable() {
        if (flag1) {
            iv_pass_visiable.setImageResource(R.mipmap.login_icon_cansee);
            etLoginPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            flag1 = false;
        } else {
            iv_pass_visiable.setImageResource(R.mipmap.login_icon_nosee);
            etLoginPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            flag1 = true;
        }
        etLoginPass.setSelection(etLoginPass.getText().length());
    }

    @OnClick(R.id.mor_button)
    public void login() {
        if (isFastClick()) {

            String phone = etLoginPhone.getText().toString();
            String pass = etLoginPass.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                AnimationUtil.Sharke(MyApp.getContext(), etLoginPhone);
                MyToast.makeTextAnim(MyApp.getContext(),"请输入手机号",0,Gravity.CENTER,0,0).show();
                return;
            }
            if (index == 0 && TextUtils.isEmpty(pass)) {
                AnimationUtil.Sharke(MyApp.getContext(), etLoginPass);
                MyToast.makeTextAnim(MyApp.getContext(),"请输入密码",0,Gravity.CENTER,0,0).show();
                return;
            }

            if (index == 1 && TextUtils.isEmpty(pass)) {
                AnimationUtil.Sharke(MyApp.getContext(), etLoginPass);
                MyToast.makeTextAnim(MyApp.getContext(),"请输入验证码",0,Gravity.CENTER,0,0).show();
                return;
            }
            if (index == 0) {
                passLogin(phone, pass);
            } else {
                yzmLogin(phone, pass);
            }
        }
    }

    private void yzmLogin(String phone, String yzm) {
        String login_type = "mobile";
        //生成签名才能获取验证码
        Long time = new Date().getTime() / 1000;
        String m = "login";
        String stringA = "login_type=" + login_type + "&m=" + m + "&pwd=" + yzm + "&timestamp=" + time + "&usern=" + phone + "&key=6ljH6wpC4vDPy%Ruqlr4JJmG0kLo%^yN";
        String sign = StringUtil.stringToMD5(stringA);
        StringBuilder Upsign = StringUtil.UpperLowerCase(sign);

        Log.i("登录", Constant.baseUrl + "login/login.php?&m=login" + "&usern=" + phone + "&pwd=" + yzm + "&timestamp=" + time + "&login_type=" + login_type + "&sign=" + Upsign);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "login/login.php?&m=login")
                .addParams("usern", phone)
                .addParams("pwd", yzm)
                .addParams("timestamp", String.valueOf(time))
                .addParams("login_type", login_type)
                .addParams("sign", Upsign.toString())
                .build()
                .execute(new GenericsCallback<BaseDataBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {


                    }

                    @Override
                    public void onResponse(BaseDataBean response, int id) {
                        if (response.getStatus() == 1) {
                            hideSoftInput();
                            startAnmi(response);
                        } else {
                            MyToast.makeTextAnim(MyApp.getContext(), response.getMsg(), 0, Gravity.CENTER, 0, 0).show();
                        }
                    }
                });
    }

    private void passLogin(String phone, String pass) {

        String login_type = "password";
        //密码加密------------------------------------------------------------------------
        String strA = "pwd=" + pass;
        String strB = strA + "&key=!qJwHh!8Ln6ELn3rbFMk5c$vW#l13QLe";
        String pwdMd5 = StringUtil.stringToMD5(strB);
        StringBuilder Uppass = StringUtil.UpperLowerCase(pwdMd5);
        //生成签名才能获取验证码
        Long time = new Date().getTime() / 1000;
        String m = "login";
        String stringA = "login_type=" + login_type + "&m=" + m + "&pwd=" + Uppass + "&timestamp=" + time + "&usern=" + phone + "&key=6ljH6wpC4vDPy%Ruqlr4JJmG0kLo%^yN";
        String sign = StringUtil.stringToMD5(stringA);
        StringBuilder Upsign = StringUtil.UpperLowerCase(sign);

        Log.i("登录", Constant.baseUrl + "login/login.php?&m=login" + "&usern=" + phone + "&pwd=" + Uppass + "&timestamp=" + time + "&login_type=" + login_type + "&sign=" + Upsign);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "login/login.php?&m=login")
                .addParams("usern", phone)
                .addParams("pwd", Uppass.toString())
                .addParams("timestamp", String.valueOf(time))
                .addParams("login_type", login_type)
                .addParams("sign", Upsign.toString())
                .build()
                .execute(new GenericsCallback<BaseDataBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {


                    }

                    @Override
                    public void onResponse(BaseDataBean response, int id) {
                        if (response.getStatus() == 1) {
                            hideSoftInput();
                            startAnmi(response);
                        } else {
                            MyToast.makeTextAnim(MyApp.getContext(), response.getMsg(), 0, Gravity.CENTER, 0, 0).show();
                        }
                    }
                });
    }

    public void startAnmi(BaseDataBean bean) {
        String userId = bean.getData().getID();
        String mobile = bean.getData().getMobile();
        SPUtil.getInstance(MyApp.getContext()).putUserId("UserId", userId);
        SPUtil.getInstance(MyApp.getContext()).putMobile("Mobile", mobile);
        MobclickAgent.onProfileSignIn(userId); //当用户使用自有账号登录时，可以这样统计：
        MorphingButton.Params params = MorphingButton.Params.create()
                .duration(500)
                .cornerRadius(56)
                .width(100)
                .height(100)
                .color(Color.parseColor("#FA3314"))
                .icon(R.mipmap.ic_done);
        morphingButton.morph(params);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        }, 1000);

    }


    @OnClick(R.id.tv_get_yzm)
    public void getYzm() {
        String mobile = etLoginPhone.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            AnimationUtil.Sharke(MyApp.getContext(), etLoginPhone);
            MyToast.makeTextAnim(MyApp.getContext(),"请输入手机号",0,Gravity.CENTER,0,0).show();
            return;
        }

        String m = "login";
        Long time = new Date().getTime() / 1000;
        String stringA = "m=" + m + "&mobile=" + mobile + "&timestamp=" + time + "&key=6ljH6wpC4vDPy%Ruqlr4JJmG0kLo%^yN";
        String sign = StringUtil.stringToMD5(stringA);
        StringBuilder Upsign = StringUtil.UpperLowerCase(sign);
        /**
         * 获取验证码
         */
        Log.i("好烦啊", Constant.baseUrl + "messages/send.php?" + "&m=" + m + "&mobile=" + mobile + "&sign=" + sign + "&timestamp=" + time);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "messages/send.php?")
                .addParams("m", m)
                .addParams("mobile", mobile)
                .addParams("sign", Upsign.toString())
                .addParams("timestamp", String.valueOf(time))
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        String code = response.getCode();
                        if ("OK".equals(code)) {
                            MyToast.makeTextAnim(MyApp.getContext(),"验证码已发送至您的手机,请查收!",0,Gravity.CENTER,0,0).show();
                            tv_get_yzm.start();
                        } else if ("isv.OUT_OF_SERVICE".equals(code)) {
                            MyToast.makeTextAnim(MyApp.getContext(),"此号码已停机",0,Gravity.CENTER,0,0).show();
                        } else if ("isv.MOBILE_NUMBER_ILLEGAL".equals(code)) {
                            MyToast.makeTextAnim(MyApp.getContext(),"手机号码不正确",0,Gravity.CENTER,0,0).show();
                        } else if ("isv.BUSINESS_LIMIT_CONTROL".equals(code)) {
                            MyToast.makeTextAnim(MyApp.getContext(),"此手机号因发送频繁,被限制获取",0,Gravity.CENTER,0,0).show();
                        }
                    }
                });

    }

    @OnClick(R.id.tv_find_password)
    public void find() {
        if (isFastClick()) {
            startActivity(FindPassWordActivity.class);
        }
    }

    @OnClick(R.id.tv_open_shop)
    public void open() {
        if (isFastClick()) {
            Intent intent = new Intent(this, OpenShopEntranceActivity.class);
            intent.putExtra("from", "login");
            startActivity(intent);
        }

    }

}
