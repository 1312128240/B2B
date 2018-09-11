package car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import car.myview.Custkeyboard.KeyboardAdapter;
import car.myview.Custkeyboard.KeyboardView;
import car.myview.Custkeyboard.PasswordInputView;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.Order.LookOrderActivity;
import car.tzxb.b2b.Uis.Order.OrderActivity;
import car.tzxb.b2b.Util.ActivityManager;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.RsaUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Util.StringUtil;
import car.tzxb.b2b.config.Constant;
import car.tzxb.b2b.wxapi.WXPayEntryActivity;
import okhttp3.Call;

public class ResetPayPasswordActivity extends MyBaseAcitivity implements KeyboardAdapter.OnKeyboardClickListener, PasswordInputView.OnFinishListener {
    @BindView(R.id.password_view)
    PasswordInputView etInput;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.keyboard_view)
    KeyboardView keyboardView;
    private List<String> datas;
    public static AppCompatActivity sInstance = null;
    private String from;
    private String orderSeqnos;

    @Override
    public void initParms(Bundle parms) {
        from = getIntent().getStringExtra("from");
        orderSeqnos = getIntent().getStringExtra("orderSeqnos");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_reset_pay_password;
    }

    @Override
    public void doBusiness(Context mContext) {
        sInstance = this;
        tv_title.setText("支付密码");
        initCustKey();
    }

    private void initCustKey() {
        DeviceUtils.hideSystemSoftKeyBoard(this, etInput);
        datas = keyboardView.getDatas();
        etInput.setOnFinishListener(this);
        keyboardView.setOnKeyBoardClickListener(this);
        etInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!keyboardView.isVisible()) {
                    keyboardView.show();
                }
            }
        });

    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    public void onKeyClick(View view, RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 9: // 按下小数点
                String num = etInput.getText().toString().trim();
                if (!num.contains(datas.get(position))) {
                    num += datas.get(position);
                    etInput.setText(num);
                    etInput.setSelection(etInput.getText().length());
                }
                break;
            default: // 按下数字键
                if ("0".equals(etInput.getText().toString().trim())) { // 第一个数字按下0的话，第二个数字只能按小数点
                    break;
                }
                etInput.setText(etInput.getText().toString().trim() + datas.get(position));
                etInput.setSelection(etInput.getText().length());
                break;
        }
    }

    @Override
    public void onDeleteClick(View view, RecyclerView.ViewHolder holder, int position) {
        // 点击删除按钮
        String num = etInput.getText().toString().trim();
        if (num.length() > 0) {
            etInput.setText(num.substring(0, num.length() - 1));
            etInput.setSelection(etInput.getText().length());
        }
    }

    @Override
    public void setOnPasswordFinished() {
        if (etInput.getOriginText().length() == etInput.getMaxPasswordLength()) {

            if (!"WxPay".equals(from)) {
                Intent intent = new Intent(this, ResetPayPassword2Activity.class);
                intent.putExtra("pass", etInput.getOriginText());
                startActivity(intent);
            } else {
                //输入密码
                String pass = etInput.getOriginText();
                String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
                //支付密码加密
                String  stringA="pay_pwd="+pass+"&user_id="+userId;
                String  stringB = stringA + "&key=H1ereN3nfdF6wZcoeBdYUfQv7tq1Pq5t";
                String  stringMd5= StringUtil.stringToMD5(stringB);
                String  stringUpper=StringUtil.UpperLowerCase(stringMd5).toString();
                Log.i("密码Md5加密",stringUpper);

                String stringC = "order_seqnos=" + orderSeqnos + "&pwd=" + stringUpper + "&user_id=" + userId;

                Log.i("原内容",stringC);

                String result= RsaUtils.base64Encrypted(stringC,RsaUtils.PUblicKey);

                Log.i("Rsa加密",result);

                Log.i("余额支付", Constant.baseUrl + "orders/orders_mobile.php?m=pay" + "&pay_device=Android" + "&pay_type=Balance" + "&sign=" +result);
                OkHttpUtils
                        .get()
                        .tag(this)
                        .url(Constant.baseUrl + "orders/orders_mobile.php?m=pay")
                        .addParams("pay_device", "Android")
                        .addParams("pay_type", "Balance")
                        .addParams("sign", result)
                        .build()
                        .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.i("余额支付走失误", e.toString());

                            }

                            @Override
                            public void onResponse(BaseStringBean response, int id) {
                                Log.i("余额支付返回", response.getMsg() + "____" + response.getStatus());
                                int status = response.getStatus();
                                if (status == 1) {
                                    //成功去订单界面
                                    Intent intent = new Intent(ResetPayPasswordActivity.this, LookOrderActivity.class);
                                    intent.putExtra("index", 0);
                                    startActivity(intent);
                                    ResetPayPasswordActivity.this.finish();
                                    //关闭微信支付activity
                                    WXPayEntryActivity wxPayEntryActivity = (WXPayEntryActivity) WXPayEntryActivity.sInstance;
                                    ActivityManager.getInstance().deleteActivity(wxPayEntryActivity);
                                    //关闭订单办面
                                    OrderActivity orderActivity= (OrderActivity) OrderActivity.sInstance;
                                    if(orderActivity!=null){
                                        ActivityManager.getInstance().deleteActivity(orderActivity);
                                    }

                                } else {
                                    MyToast.makeTextAnim(MyApp.getContext(), response.getMsg(), 0, Gravity.CENTER, 0, 0).show();
                                }
                            }
                        });
            }

        }
    }

    @Override
    public void onBackPressed() {
        if (keyboardView.isVisible()) {
            keyboardView.dismiss();
            return;
        }
        super.onBackPressed();
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sInstance = null;
    }
}
