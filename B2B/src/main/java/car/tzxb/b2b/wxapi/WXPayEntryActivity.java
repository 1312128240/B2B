package car.tzxb.b2b.wxapi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;

public class WXPayEntryActivity extends MyBaseAcitivity {
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

    @Override
    public void initParms(Bundle parms) {
        total = getIntent().getDoubleExtra("total",-1);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_wxpay_entry;
    }

    @Override
    public void doBusiness(Context mContext) {
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

}
