package car.tzxb.b2b.Uis.MeCenter.IntegralShop;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class IntegralExchangeActivity extends MyBaseAcitivity{
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_integral_exchange;
    }

    @Override
    public void doBusiness(Context mContext) {
         tv_title.setText("兑换记录");

    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

   @OnClick(R.id.tv_actionbar_back)
    public void back(){
       onBackPressed();
   }
}
