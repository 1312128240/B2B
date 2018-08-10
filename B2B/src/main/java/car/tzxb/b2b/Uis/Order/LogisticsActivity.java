package car.tzxb.b2b.Uis.Order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.SpaceItemDecoration;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.logisticsView.Trace;
import car.myview.logisticsView.TraceAdapter;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.LogisticsData;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class LogisticsActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.traceRv)
    RecyclerView recyclerview;
    @BindView(R.id.recy_guess)
    RecyclerView recy_guess;
    private String orderId;
    private  int type;
    private List<BaseDataListBean.DataBean> beanList=new ArrayList<>();
    private CommonAdapter<BaseDataListBean.DataBean> guessAdapter;

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
        initGuess();
    }

    private void initGuess() {
        recy_guess.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recy_guess.addItemDecoration(new SpaceItemDecoration(10, 2));
        recy_guess.setNestedScrollingEnabled(false);
        guessAdapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(), R.layout.recommend_layout, beanList) {
              @Override
              protected void convert(ViewHolder holder, BaseDataListBean.DataBean bean, int position) {
                  //图片
                  ImageView iv = holder.getView(R.id.iv_recommend);
                  Glide.with(MyApp.getContext()).load(bean.getImg_url()).override(256, 256).into(iv);
                  holder.setText(R.id.tv_recommend_title, bean.getShop_name());
                  //名字
                  holder.setText(R.id.tv_recommend_title,bean.getGoods_name());
                  //价格
                  TextView tv_price=holder.getView(R.id.tv_recommend_price);
                  tv_price.setText(Html.fromHtml("¥ <big>" + bean.getPrice() + "</big>"));
                  ;           //销量
                  TextView tv_sales=holder.getView(R.id.tv_recomment_sales);
                  tv_sales.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                  tv_sales.setText("销量 "+bean.getSales());
              }
          };
        recy_guess.setAdapter(guessAdapter);
        guessAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BaseDataListBean.DataBean bean = beanList.get(position);
                Intent intent = new Intent(LogisticsActivity.this, GoodsXqActivity.class);
                intent.putExtra("mainId", bean.getId());
                startActivity(intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
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

    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
        getGuess();
    }

    private void getGuess() {
        String userId=SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        Log.i("猜你在找",Constant.baseUrl+"item/index.php?c=Goods&m=UserLike&pagesize=10&page=0&user_id="+userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"item/index.php?c=Goods&m=UserLike&pagesize=10&page=0")
                .addParams("user_id",userId)
                .addParams("sales","desc")
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {



                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        beanList = response.getData();
                        guessAdapter.add(beanList,true);
                    }
                });
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
        Collections.reverse(logisticsBeanList);
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
