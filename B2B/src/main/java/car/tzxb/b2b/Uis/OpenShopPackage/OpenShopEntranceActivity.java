package car.tzxb.b2b.Uis.OpenShopPackage;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CountDown.CountDownTextView;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.AnimationUtil;
import car.tzxb.b2b.Util.StringUtil;
import car.tzxb.b2b.Views.DialogFragments.AlterDialogFragment;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;


public class OpenShopEntranceActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.et_open_shop_phone)
    EditText et_phone;
    @BindView(R.id.et_open_shop_yzm)
    EditText et_yzm;
    @BindView(R.id.tv_open_shop_get_yzm)
    CountDownTextView tv_get_yzm;
    @BindView(R.id.btn_open_shop_next)
    Button btn_next;
    private String from;
    private String mobile;
    public static OpenShopEntranceActivity instance1;
    public static OpenShopEntranceActivity instance2;

    @Override
    public void initParms(Bundle parms) {
        from = getIntent().getStringExtra("from");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_open_shop_entrance;
    }

    @Override
    public void doBusiness(Context mContext) {

        tv_title.setText("申请开店");
        if ("login".equals(from)) {
            instance1 = this;
            et_phone.setHint(getString(R.string.login_phone));
            et_yzm.setHint(getString(R.string.yzm_pass));
            et_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
            et_yzm.setInputType(InputType.TYPE_CLASS_NUMBER);
            initTextWatch1();

        } else {
            instance2 = this;
            mobile = getIntent().getStringExtra("mobile");
            et_phone.setHint(getString(R.string.input_pass));
            et_yzm.setHint(getString(R.string.agin_input_pass));
            tv_get_yzm.setVisibility(View.INVISIBLE);
            initTextWatch2();
        }

    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.btn_open_shop_next)
    public void next() {
        if (isFastClick()) {
            if ("login".equals(from)) {
                next1();
            } else {
                next2();
            }
        }

    }

    private void initTextWatch1() {
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
                //不能超进11位
                if (s.length() > 4) {
                    String str = et_yzm.getText().toString();
                    String body = str.substring(0, str.length() - 1);
                    et_yzm.setText(body);
                    et_yzm.setSelection(body.length());
                } else if (s.length() == 4) {
                    hideSoftInput();
                    btn_next.setBackground(getResources().getDrawable(R.drawable.bg1));
                } else if (s.length() < 4) {
                    btn_next.setBackground(getResources().getDrawable(R.drawable.bg3));
                }
            }
        });
    }

    private void initTextWatch2() {
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String editable = et_phone.getText().toString();
                String regEx = "[^a-zA-Z0-9]";  //只能输入字母或数字
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(editable);
                String str = m.replaceAll("").trim();    //删掉不是字母或数字的字符
                if (!editable.equals(str)) {
                    et_phone.setText(str);
                    et_phone.setSelection(str.length());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //不能超进11位
                if (s.length() > 16) {
                    String str = et_phone.getText().toString();
                    String body = str.substring(0, str.length() - 1);
                    et_phone.setText(body);
                    et_phone.setSelection(body.length());
                } else if (s.length() == 16) {
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
                String editable = et_yzm.getText().toString();
                String regEx = "[^a-zA-Z0-9]";  //只能输入字母或数字
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(editable);
                String str = m.replaceAll("").trim();    //删掉不是字母或数字的字符
                if (!editable.equals(str)) {
                    et_yzm.setText(str);
                    et_yzm.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //不能超进16位
                if (s.length() > 16) {
                    String str = et_yzm.getText().toString();
                    String body = str.substring(0, str.length() - 1);
                    et_yzm.setText(body);
                    et_yzm.setSelection(body.length());
                } else if (s.length() == 16) {
                    hideSoftInput();
                } else if (s.length() < 6) {
                    btn_next.setBackground(getDrawable(R.drawable.bg3));
                } else if (s.length() > 5) {
                    btn_next.setBackground(getDrawable(R.drawable.bg1));
                }
            }
        });
    }


    public void next1() {
        final String phone = et_phone.getText().toString();
        String yzm = et_yzm.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Snackbar.make(tv_title, "请输入手机号", Snackbar.LENGTH_SHORT).show();
            AnimationUtil.Sharke(MyApp.getContext(), et_phone);
            return;
        }
        if (TextUtils.isEmpty(yzm)) {
            Snackbar.make(tv_title, "请输入验证码", Snackbar.LENGTH_SHORT).show();
            AnimationUtil.Sharke(MyApp.getContext(), et_yzm);
            return;
        }

        isRegister(phone,yzm);
    }

    private void next2() {
        String pass = et_phone.getText().toString();
        String agin_pass = et_yzm.getText().toString();

        if (TextUtils.isEmpty(pass)) {
            Snackbar.make(tv_title, "请输入密码", Snackbar.LENGTH_SHORT).show();
            AnimationUtil.Sharke(MyApp.getContext(), et_phone);
            return;
        }

        if (pass.length() < 6) {
            Snackbar.make(tv_title, "密码不能小位6位数", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(agin_pass)) {
            Snackbar.make(tv_title, "请再次输入密码", Snackbar.LENGTH_SHORT).show();
            AnimationUtil.Sharke(MyApp.getContext(), et_yzm);
            return;
        }
        if (!pass.equals(agin_pass)) {
            Snackbar.make(tv_title, "前后两次密码不一致", Snackbar.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, OpenShopEntrance2Activity.class);
        intent.putExtra("mobile", mobile);
        intent.putExtra("password", agin_pass);
        intent.putExtra("from","apply");
        startActivity(intent);
    }

    @OnClick(R.id.tv_open_shop_get_yzm)
    public void getYzm() {
        String mobile = et_phone.getText().toString();
     if (TextUtils.isEmpty(mobile)) {
            AnimationUtil.Sharke(MyApp.getContext(), et_phone);
            Snackbar.make(tv_title, "请输入手机号", Snackbar.LENGTH_SHORT).show();
            return;
        }

        String m = "register";
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
                            Snackbar.make(tv_get_yzm, "验证码已发送至您的手机,请查收!", Snackbar.LENGTH_SHORT).show();
                            tv_get_yzm.start();
                        } else if ("isv.OUT_OF_SERVICE".equals(code)) {
                            Snackbar.make(tv_get_yzm, "此号码已停机", Snackbar.LENGTH_SHORT).show();
                        } else if ("isv.MOBILE_NUMBER_ILLEGAL".equals(code)) {
                            Snackbar.make(tv_get_yzm, "手机号码不正确", Snackbar.LENGTH_SHORT).show();
                        } else if ("isv.BUSINESS_LIMIT_CONTROL".equals(code)) {
                            Snackbar.make(tv_get_yzm, "此手机号因发送频繁,被限制获取", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * 判断此手机号是否已经注册过
     * @param phone
     * @return
     */
    private void isRegister(final String phone, final String yzm) {

        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "login/login.php?m=is_register")
                .addParams("mobile", phone)
                .build()
                .execute(new GenericsCallback<BaseDataBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataBean response, int id) {
                       // apply(phone,yzm);
                        if(response.getStatus()==1){
                            //手机号没有申请过走正常申请流程
                            apply(phone,yzm);
                        }else {
                            showDialgoFragment(response.getMsg());
                        }
                       // int status =Integer.parseInt(et_phone.getText().toString());
                      /*  int status=Integer.valueOf(et_phone.getText().toString());
                        switch (status) {
                            case 0:   //账号已经审核通过
                                showPassDialogFragment();
                                break;
                            case 1:  //手机号没有申请过走正常申请流程
                                apply(phone,yzm);
                                break;
                            case 2:  //账号注册过,但可以编缉资料
                                showEditInfor(phone);
                                break;
                            case 3:    //审核未通过
                                break;
                        }*/
                    }
                });

    }

    private void showDialgoFragment(String msg) {
        AlterDialogFragment dialogFragment=new AlterDialogFragment();
        dialogFragment.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putString("title", msg);
        bundle.putString("ok","知道了");
        bundle.putString("no","好的");
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(),"pass");
        dialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                onBackPressed();
            }

            @Override
            public void sure() {
                onBackPressed();
            }
        });
    }

 /*   private void showEditInfor(final String phone) {
        final AlterDialogFragment dialogFragment=new AlterDialogFragment();
        dialogFragment.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putString("title", "此账号已注册,是否继续编缉资料");
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(),"pass");
        dialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                dialogFragment.dismiss();
            }

            @Override
            public void sure() {
                Intent intent=new Intent(OpenShopEntranceActivity.this,OpenShopEntrance2Activity.class);
                intent.putExtra("from","edit");
                intent.putExtra("mobile",phone);
                startActivity(intent);
            }
        });
    }

    private void showPassDialogFragment() {
        AlterDialogFragment dialogFragment=new AlterDialogFragment();
        dialogFragment.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putString("title", "此账号已通过审核,可直接登录");
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(),"pass");
        dialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                onBackPressed();
            }

            @Override
            public void sure() {
               onBackPressed();
            }
        });
    }*/

    public void apply(final String phone, String yzm){

          tLog("登录比对" + Constant.baseUrl + "messages/verify.php?m=SendVerify" + "&mobile=" + phone + "&type=register" + "&code=" + yzm);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "messages/verify.php?m=SendVerify")
                .addParams("mobile", phone)
                .addParams("type", "register")
                .addParams("code", yzm)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        if (response.isData() == true) {
                            Intent intent = new Intent(OpenShopEntranceActivity.this, OpenShopEntranceActivity.class);
                            intent.putExtra("mobile", phone);
                            startActivity(intent);

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
