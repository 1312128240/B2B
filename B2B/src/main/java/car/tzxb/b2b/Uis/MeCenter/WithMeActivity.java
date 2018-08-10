package car.tzxb.b2b.Uis.MeCenter;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.config.Constant;

public class WithMeActivity extends MyBaseAcitivity {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_version_name)
    TextView tv_vn;
    private int count;
    private boolean status;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_with_me;
    }

    @Override
    public void doBusiness(Context mContext) {
         tv_title.setText("关于我们");
         tv_vn.setText("版本"+getResources().getString(R.string.version));
         status = Constant.baseUrl.contains("wxb2b.aiucar.com");
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }

    @OnClick(R.id.tv_version_name)
        public void network(){
        count++;
        if(count>=7){
            if(status){
                Constant.baseUrl="http://39.108.98.157/mobile_api/";
                Snackbar.make(tv_title,"内网模式",Snackbar.LENGTH_SHORT).show();
            }else{
                Constant.baseUrl="https://wxb2b.aiucar.com/mobile_api/";
                Snackbar.make(tv_title,"外网模式",Snackbar.LENGTH_SHORT).show();
            }
        }
    }

}
