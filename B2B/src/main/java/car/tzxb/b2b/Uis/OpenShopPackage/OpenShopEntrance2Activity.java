package car.tzxb.b2b.Uis.OpenShopPackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;

public class OpenShopEntrance2Activity extends MyBaseAcitivity {


    @BindView(R.id.tv_submit_open_shop)
    TextView  tv_submit;
    @BindView(R.id.tv_upload_sign)
    TextView tv_sign;
    @BindView(R.id.tv_upload_shop_photo)
    TextView tv_shop;
    @BindView(R.id.tv_upload_business_license)
    TextView tv_license;
    @BindView(R.id.iv_upload1)
    ImageView iv1;
    @BindView(R.id.iv_upload2)
    ImageView iv2;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    private String mobile;
    public static OpenShopEntrance2Activity instance;
    private String password;
    private String from;

    @Override
    public void initParms(Bundle parms) {
        mobile = getIntent().getStringExtra("mobile");
        password = getIntent().getStringExtra("password");
        from = getIntent().getStringExtra("from");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_open_shop_entrance2;
    }

    @Override
    public void doBusiness(Context mContext) {
        instance=this;
        tv_title.setText("申请开店");
        tv_submit.setVisibility(View.GONE);
        tv_license.setVisibility(View.INVISIBLE);
        tv_shop.setVisibility(View.INVISIBLE);
        tv_sign.setVisibility(View.INVISIBLE);
        iv1.setImageResource(R.mipmap.apply_bg_5);
        iv2.setImageResource(R.mipmap.apply_bg_6);


    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }
    @OnClick(R.id.btn_start_open_shop)
    public void start(){
        if(isFastClick()){
            Intent intent=new Intent(this,OpenShopActivity.class);
            intent.putExtra("mobile",mobile);
            intent.putExtra("password",password);
            intent.putExtra("from",from);
            startActivity(intent);
        }

    }
}
