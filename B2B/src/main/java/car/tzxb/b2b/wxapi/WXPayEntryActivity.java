package car.tzxb.b2b.wxapi;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.PayResult;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class WXPayEntryActivity extends MyBaseAcitivity implements IWXAPIEventHandler {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_pay_money)
    TextView tv_money;
    @BindView(R.id.btn_start_pay)
    Button btn_pay;
    @BindView(R.id.cb_wx)
    CheckBox cb_wx;
    @BindView(R.id.cb_zfb)
    CheckBox cb_zfb;
    private double total;
    private IWXAPI api;
    private String order_seqnos;
    private final int SDK_PAY_FLAG = 1;
    //wx284ddc006d10854d
    @Override
    public void initParms(Bundle parms) {
        total = getIntent().getDoubleExtra("total",-1);
        order_seqnos = getIntent().getStringExtra("order_seqnos");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_wxpay_entry;
    }

    @Override
    public void doBusiness(Context mContext) {
        //注册微信支付
        api = WXAPIFactory.createWXAPI(this, Constant.AppID);
        api.handleIntent(getIntent(), this);

        initUi();

    }

    private void initUi() {
        tv_title.setText("收银台");
        tv_money.setText(Html.fromHtml("¥"+"<big>"+total+"</big>"));
        btn_pay.setText("使用支付宝支付 ¥"+total);
        cb_zfb.setChecked(true);
        cb_zfb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btn_pay.setText("使用支付宝支付 ¥"+total);
                    cb_wx.setChecked(false);
                    cb_zfb.setEnabled(false);
                }else {
                    cb_zfb.setEnabled(true);
                }
            }
        });
        cb_wx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btn_pay.setText("使用微信支付 ¥"+total);
                    cb_zfb.setChecked(false);
                    cb_wx.setEnabled(false);
                }else {
                    cb_wx.setEnabled(true);
                }
            }
        });
    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onResp, errCode = " + resp.errCode);
        String result = "";
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "微信支付成功";
                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    result = "微信支付失败：" + resp.errCode + "，" + resp.errStr;
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "微信支付取消：" + resp.errCode + "，" + resp.errStr;
                    break;
                default:
                    result = "微信支付未知异常：" + resp.errCode + "，" + resp.errStr;
                    break;
            }
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_start_pay)
    public void pay(){
        if(isFastClick()){

            if(cb_wx.isChecked()){
                WxPay();
            }else {
               ZfbPay();
            }
        }
    }

    private void ZfbPay() {
        String userId= SPUtil.getInstance(this).getUserId("UserId",null);
        Log.i("微信支付",Constant.baseUrl+"orders/orders_mobile.php?m=pay"+"&order_seqnos="+order_seqnos+"&pay_type=zfb"+"&pay_device=Android"+"&user_id="+userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"orders/orders_mobile.php?m=pay")
                .addParams("order_seqnos",order_seqnos)
                .addParams("pay_type","zfb")
                .addParams("pay_device","Android")
                .addParams("user_id",userId)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        final   String orderPaht=response.getOrder();
                        //调起支付宝支付
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(WXPayEntryActivity.this);
                                Map<String, String> result = alipay.payV2(orderPaht, true);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        }).start();
                    }
                });
    }

    private void WxPay() {
        String userId= SPUtil.getInstance(this).getUserId("UserId",null);
        Log.i("微信支付",Constant.baseUrl+"orders/orders_mobile.php?m=pay"+"&order_seqnos="+order_seqnos+"&pay_type=wx"+"&pay_device=Android"+"&user_id="+userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"orders/orders_mobile.php?m=pay")
                .addParams("order_seqnos",order_seqnos)
                .addParams("pay_type","wx")
                .addParams("pay_device","Android")
                .addParams("user_id",userId)
                .build()
                .execute(new GenericsCallback<BaseDataBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataBean response, int id) {
                        BaseDataBean.DataBean dataBean=response.getData();
                        PayReq request = new PayReq();

                        request.appId = dataBean.getAppId();

                        request.partnerId = dataBean.getPartnerid();

                        request.prepayId=dataBean.getPrepayid();

                        request.packageValue = "Sign=WXPay";

                        request.nonceStr= dataBean.getNonceStr();

                        request.timeStamp=dataBean.getTimeStamp();

                        request.sign= dataBean.getPaySign();
                        api.sendReq(request);
                    }
                });
    }

  /*  *//**
     * 发起支付宝支付
     *//*
    private void ZfbPay(BaseStringBean bean) {
      final   String orderPaht=bean.getOrder();
        //调起支付宝支付
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(WXPayEntryActivity.this);
                Map<String, String> result = alipay.payV2(orderPaht, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        }).start();
    }*/

  /*  *//**
     * 发起微信支付
     * @param bean
     *//*
    private void WxPay(BaseDataBean bean) {
        BaseDataBean.DataBean dataBean=bean.getData();
        PayReq request = new PayReq();

        request.appId = dataBean.getAppId();

        request.partnerId = dataBean.getPartnerid();

        request.prepayId=dataBean.getPrepayid();

        request.packageValue = "Sign=WXPay";

        request.nonceStr= dataBean.getNonceStr();

        request.timeStamp=dataBean.getTimeStamp();

        request.sign= dataBean.getPaySign();
        api.sendReq(request);
    }
*/
    /**
     * 支付宝结果回调
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            Log.i("支付宝回调地信息",msg+"");
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                    } else {
                        MyToast.makeTextAnim(MyApp.getContext(),"支付取消",0, Gravity.CENTER,0,0).show();

                    }

                    break;
                }
                default:
                    break;
            }
        }
    };
}
