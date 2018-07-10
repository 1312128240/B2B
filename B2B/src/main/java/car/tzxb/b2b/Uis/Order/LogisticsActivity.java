package car.tzxb.b2b.Uis.Order;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import car.myview.logisticsView.Trace;
import car.myview.logisticsView.TraceAdapter;
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
    @BindView(R.id.traceRv)
    RecyclerView recyclerview;
    private String orderId;
    private  int type;
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
        Log.i("物流信息",Constant.baseUrl+"orders/order_list_mobile.php?m=order_logistics"+"&user_id="+userId+"&order_id="+orderId);
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
         List<LogisticsData.DataBean.LogisticsBean> logisticsBeanList=response.getData().getLogistics();
         List<Trace> mTraceList=new ArrayList<>();
        for (int i = 0; i <logisticsBeanList.size() ; i++) {
            if(i==0){
                type=0;
            }else {
                type=1;
            }
            LogisticsData.DataBean.LogisticsBean bean=logisticsBeanList.get(i);
            mTraceList.add(new Trace(type, bean.getTime(), bean.getTitle()));
        }
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        TraceAdapter adapter=new TraceAdapter(MyApp.getContext(),mTraceList);
        recyclerview.setAdapter(adapter);

    }


}
