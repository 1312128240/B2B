package car.tzxb.b2b.wxapi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;

public class WXPayEntryActivity extends MyBaseAcitivity {


    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_wxpay_entry;
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
}
