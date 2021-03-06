package car.tzxb.b2b.wxapi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.alipay.sdk.app.PayTask;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.Adapter.CashAdapter;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.PayResult;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.HomePager.Wallet.MyWalletActivity;
import car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage.ResetPayPasswordActivity;
import car.tzxb.b2b.Uis.Order.LookOrderActivity;
import car.tzxb.b2b.Uis.Order.OfflinePaymentActivity;
import car.tzxb.b2b.Uis.Order.OrderActivity;
import car.tzxb.b2b.Util.ActivityManagerUtils;
import car.tzxb.b2b.Util.RsaUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.AlterDialogFragment;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class WXPayEntryActivity extends MyBaseAcitivity implements IWXAPIEventHandler {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_pay_money)
    TextView tv_money;
    @BindView(R.id.btn_start_pay)
    Button btn_pay;
    @BindView(R.id.lv_zf)
    ListView lv;
    @BindDrawable(R.mipmap.pyt_icon_ye)
    Drawable d1;
    @BindDrawable(R.mipmap.pyt_icon_zfb)
    Drawable d2;
    @BindDrawable(R.mipmap.pyt_icon_wx)
    Drawable d3;
    @BindDrawable(R.mipmap.pyt_icon_yl)
    Drawable d4;
    @BindDrawable(R.mipmap.pyt_icon_xj)
    Drawable d5;
    String[] str1 = {"支付宝", "微信", "银联支付"};
    String[] str2 = {"余额支付", "支付宝", "微信", "银联支付", "线下支付"};
    private String total;
    private IWXAPI api;
    private String order_seqnos;
    private int position;
    private String orderid;
    private final String mMode = "00";  //"00" - 启动银联正式环境 ,"01" - 连接银联测试环境
    private final int SDK_PAY_FLAG = 1;
    private String from;
    private boolean isShow;

    @Override
    public void initParms(Bundle parms) {
        total = getIntent().getStringExtra("total");
        from = getIntent().getStringExtra("from");
        order_seqnos = getIntent().getStringExtra("order_seqnos");
        orderid = getIntent().getStringExtra("orderid");
    }

    @Override
    public int bindLayout() {
        //注册微信支付
        api = WXAPIFactory.createWXAPI(MyApp.getContext(), null);
        api.registerApp(Constant.AppID);
        return R.layout.activity_wxpay_entry;
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }


    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("收银台");
        tv_money.setText(Html.fromHtml("¥" + "<big>" + total + "</big>"));
        if ("Recharge".equals(from)) {
            btn_pay.setText("使用支付宝支付 ¥" + total);
            initLv1();
        } else {
            btn_pay.setText("使用余额支付 ¥" + total);
            queryBalance();
        }
    }

    private void initLv1() {
        Drawable[] img1 = {d2, d3, d4};
        List<String> stringList = Arrays.asList(str1);
        CashAdapter cashAdapter = new CashAdapter(MyApp.getContext(), stringList, img1, isShow);
        lv.setAdapter(cashAdapter);
        cashAdapter.setClickPosition(new CashAdapter.ClickPosition() {
            @Override
            public void onClick(int index, String name) {
                position = index;
                if ("线下支付".equals(name)) {
                    btn_pay.setText("收货时您需要付款 ¥" + total);
                } else {
                    btn_pay.setText("使用" + name + "支付 ¥" + total);
                }
            }
        });
    }

    public void queryBalance() {
        String userId = SPUtil.getInstance(this).getUserId("UserId", null);
        Log.i("我的余额", Constant.baseUrl + "orders/orders_mobile.php?m=user_balance" + "&user_id=" + userId);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "orders/orders_mobile.php?m=user_balance")
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        double balance = response.getBalance();
                        initLv2(balance);
                    }
                });
    }

    private void initLv2(double balance) {
        Double t1 = Double.valueOf(total);
        if (balance < t1) {
            Log.i("余额不足", "aa");
            isShow = true;
        } else {
            Log.i("余额充足", "bb");
            isShow = false;
        }
        Drawable[] img2 = {d1, d2, d3, d4, d5};
        List<String> stringList = Arrays.asList(str2);
        CashAdapter cashAdapter = new CashAdapter(MyApp.getContext(), stringList, img2, isShow);
        lv.setAdapter(cashAdapter);
        cashAdapter.setClickPosition(new CashAdapter.ClickPosition() {
            @Override
            public void onClick(int index, String name) {
                position = index;
                if ("线下支付".equals(name)) {
                    btn_pay.setText("收货时您需要付款 ¥" + total);
                } else {
                    btn_pay.setText("使用" + name + "支付 ¥" + total);
                }
            }
        });
    }


    @OnClick(R.id.tv_actionbar_back)
    public void back() {
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
        String result = "";
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "微信支付成功";
                    if ("Recharge".equals(from)) {
                        Intent intent = new Intent(WXPayEntryActivity.this, RechargeSuccessActivity.class);
                        intent.putExtra("total", total);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(WXPayEntryActivity.this, LookOrderActivity.class);
                        intent.putExtra("index", 0);
                        startActivity(intent);
                    }
                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    result = "调试版：" + resp.errCode + "，" + resp.errStr;
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "微信支付取消：" + resp.errCode + "，" + resp.errStr;
                    break;
                default:
                    result = "微信支付未知异常：" + resp.errCode + "，" + resp.errStr;
                    break;
            }
        }
        MyToast.makeTextAnim(MyApp.getContext(), result, 0, Gravity.CENTER, 0, 0).show();
    }

    @OnClick(R.id.btn_start_pay)
    public void pay() {
        if (isFastClick()) {
            if ("Recharge".equals(from)) {
                Log.i("余额充值",from+"");
                RechargePay();
            } else {
                switch (position) {
                    case 0:
                        BalancePay();
                        break;
                    case 1:
                        ZfbPay();
                        break;
                    case 2:
                        WxPay();
                        break;
                    case 3:
                        getTn();
                        break;
                    case 4:
                        offlinePayment();
                        break;
                }
            }

        }
    }

    private void BalancePay() {
        if (isShow) {
            showDialogFragment();
        } else {
            Intent intent = new Intent(WXPayEntryActivity.this, ResetPayPasswordActivity.class);
            intent.putExtra("from", "WxPay");
            intent.putExtra("orderSeqnos", order_seqnos);
            startActivity(intent);
        }
    }

    //是否去充值余额
    private void showDialogFragment() {
        final AlterDialogFragment dialogFragment = new AlterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "余额不足");
        bundle.putString("ok", "确认");
        bundle.putString("no", "取消");
        dialogFragment.setArguments(bundle);

        dialogFragment.show(getSupportFragmentManager(), "order");

        dialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {

                dialogFragment.dismiss();


            }

            @Override
            public void sure() {
                dialogFragment.dismiss();
                if ("order".equals(from)) {
                    ActivityManagerUtils.getInstance().finishActivityclass(OrderActivity.class);
                }
                startActivity(new Intent(WXPayEntryActivity.this, MyWalletActivity.class));
                WXPayEntryActivity.this.finish();
            }
        });
    }

    /**
     * 充值
     */
    private void RechargePay() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        String username = SPUtil.getInstance(MyApp.getContext()).getMobile("Mobile", null);

        String stringC = "username="+username+"&total_fee=" +total + "&user_id=" + userId;

        Log.i("原内容",stringC);

        String result= RsaUtils.base64Encrypted(stringC,RsaUtils.PUblicKey);

        Log.i("Rsa加密",result);

        Log.i("余额充值",Constant.baseUrl + "orders/user_wallet.php?m=wallet_order"+"&sign="+result+"&pay_device=Android");
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/user_wallet.php?m=wallet_order")
                .addParams("sign", result)
                .addParams("pay_device", "Android")
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("充值返回错误", e.toString());
                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        if (response.getStatus() == 1) {
                            order_seqnos = response.getOrder_seqno();
                            if (order_seqnos != null) {
                                Log.i("走充值", order_seqnos + "___" + position);
                                switch (position) {
                                    case 0:
                                        ZfbPay();
                                        break;
                                    case 1:
                                        WxPay();
                                        break;
                                    case 2:
                                        getTn();
                                        break;
                                }
                            }
                        }
                    }
                });

    }

    private void getTn() {
        String userId = SPUtil.getInstance(this).getUserId("UserId", null);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "orders/orders_mobile.php?m=pay")
                .addParams("order_seqnos", order_seqnos)
                .addParams("pay_type", "UnionPay")
                .addParams("pay_device", "Android")
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("银联支付错误", e.toString());
                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        Log.i("返回tn号", response.getTn());
                        if (response.getStatus() == 1) {
                            String tn = response.getTn();
                            if (tn != null) {
                                UPPayAssistEx.startPayByJAR(WXPayEntryActivity.this, PayActivity.class, null, null, tn, mMode);
                            }
                        }
                    }
                });
    }

    /**
     * 线下支付
     */
    private void offlinePayment() {
        String userId = SPUtil.getInstance(this).getUserId("UserId", null);
        Log.i("线下支付", Constant.baseUrl + "orders/orders_mobile.php?m=pay" + "&order_seqnos=" + order_seqnos + "&pay_type=offlinepayment" + "&pay_device=Android" + "&user_id=" + userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/orders_mobile.php?m=pay")
                .addParams("order_seqnos", order_seqnos)
                .addParams("pay_type", "offlinepayment")
                .addParams("pay_device", "Android")
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("线下支付错误", e.toString());
                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        if (response.getStatus() == 1) {
                            Intent intent = new Intent(WXPayEntryActivity.this, OfflinePaymentActivity.class);
                            intent.putExtra("orderid", orderid);
                            startActivity(intent);
                        }
                    }
                });
    }

    private void ZfbPay() {
        String userId = SPUtil.getInstance(this).getUserId("UserId", null);
        Log.i("支付宝支付", Constant.baseUrl + "orders/orders_mobile.php?m=pay" + "&order_seqnos=" + order_seqnos + "&pay_type=zfb" + "&pay_device=Android" + "&user_id=" + userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/orders_mobile.php?m=pay")
                .addParams("order_seqnos", order_seqnos)
                .addParams("pay_type", "zfb")
                .addParams("pay_device", "Android")
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        final String orderPaht = response.getOrder();
                        if (orderPaht != null) {
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

                    }
                });
    }

    private void WxPay() {
        String userId = SPUtil.getInstance(this).getUserId("UserId", null);
        Log.i("微信支付", Constant.baseUrl + "orders/orders_mobile.php?m=pay" + "&order_seqnos=" + order_seqnos + "&pay_type=wx" + "&pay_device=Android" + "&user_id=" + userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/orders_mobile.php?m=pay")
                .addParams("order_seqnos", order_seqnos)
                .addParams("pay_type", "wx")
                .addParams("pay_device", "Android")
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BaseDataBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("微信支付走错误", e.toString());
                    }

                    @Override
                    public void onResponse(BaseDataBean response, int id) {
                        BaseDataBean.DataBean dataBean = response.getData();
                        if (dataBean != null) {
                            PayReq request = new PayReq();
                            request.appId = dataBean.getAppid();
                            request.partnerId = dataBean.getPartnerid();
                            request.prepayId = dataBean.getPrepayid();
                            request.packageValue = "Sign=WXPay";
                            request.nonceStr = dataBean.getNoncestr();
                            request.timeStamp = dataBean.getTimestamp();
                            request.sign = dataBean.getPaySign();
                            api.sendReq(request);
                        }

                    }
                });
    }

    /**
     * 支付宝结果回调
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            Log.i("支付宝回调地信息", msg + "");
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        if ("Recharge".equals(from)) {
                            Intent intent = new Intent(WXPayEntryActivity.this, RechargeSuccessActivity.class);
                            intent.putExtra("total", total);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(WXPayEntryActivity.this, LookOrderActivity.class);
                            intent.putExtra("index", 0);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        MyToast.makeTextAnim(MyApp.getContext(), "支付取消", 0, Gravity.CENTER, 0, 0).show();
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String msg = "";
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                msg = "支付成功！";
            } else if (str.equalsIgnoreCase("fail")) {
                msg = "支付失败！";
            } else if (str.equalsIgnoreCase("cancel")) {
                msg = "用户取消了支付";
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setInverseBackgroundForced(true);
            // builder.setCustomTitle();
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

}
