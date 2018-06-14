package car.tzxb.b2b.Uis.MeCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;

public class MyAddressActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_address;
    }

    @Override
    public void doBusiness(Context mContext) {
      tv_title.setText("收货地址管理");
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
    @OnClick(R.id.btn_add_address)
    public void add(){
        Intent intent=new Intent(this,EditAddressActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.tv_actionbar_back)
    public void bcak(){
        onBackPressed();
    }
}
