package car.tzxb.b2b.wxapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.Order.LookOrderActivity;
import car.tzxb.b2b.Util.ActivityManagerUtils;


public class RechargeSuccessActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_recharge_success_price)
    TextView tv_price;
    private String total;

    @Override
    public void initParms(Bundle parms) {
        total = getIntent().getStringExtra("total");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_recharge_success;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("充值成功");
        tv_price.setText("¥" + total);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
         onBackPressed();
    }
    @OnClick(R.id.btn_recharge_success)
    public void next(){
        Intent intent=new Intent(this,LookOrderActivity.class);
        intent.putExtra("index", 0);
        startActivity(intent);
        RechargeSuccessActivity.this. finish();
        ActivityManagerUtils.getInstance().finishActivityclass(WXPayEntryActivity.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManagerUtils.getInstance().finishActivityclass(WXPayEntryActivity.class);
        RechargeSuccessActivity.this. finish();
    }
}
