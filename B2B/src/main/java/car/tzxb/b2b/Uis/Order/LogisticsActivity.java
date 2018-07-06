package car.tzxb.b2b.Uis.Order;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.LogisticsData;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class LogisticsActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    private String orderId;


    @Override
    public void initParms(Bundle parms) {
        orderId = getIntent().getStringExtra("orderId");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_logistics;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("订单详情");
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
    protected void onResume() {
        super.onResume();
        Refresh();
    }

    private void Refresh() {
       String userId= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl+"orders/order_list_mobile.php?m=order_logistics")
                .tag(this)
                .addParams("user_id",userId)
                .addParams("order_id",orderId)
                .build()
                .execute(new GenericsCallback<LogisticsData>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(LogisticsData response, int id) {
                            initLagistics(response);
                    }
                });
    }

    private void initLagistics(LogisticsData response) {
        List<String> list0 = new ArrayList<>();
        for (int i = 0; i <response.getData().getLogistics().size() ; i++) {
             String title=response.getData().getLogistics().get(i).getTitle();
             list0.add(title);
        }

    }


}
