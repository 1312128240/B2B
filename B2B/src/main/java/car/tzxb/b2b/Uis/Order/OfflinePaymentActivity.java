package car.tzxb.b2b.Uis.Order;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.SpaceItemDecoration;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.MainActivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class OfflinePaymentActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.recy_guess)
    RecyclerView recyclerView;
    private List<BaseDataListBean.DataBean> beanList=new ArrayList<>();
    private CommonAdapter<BaseDataListBean.DataBean> adapter;
    private String orderid;

    @Override
    public void initParms(Bundle parms) {
        orderid = getIntent().getStringExtra("orderid");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_offline_payment;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("提交成功");
        initRecy();
    }

    @OnClick(R.id.tv_back_main)
    public void main(){
        startActivity(MainActivity.class);
    }

    @OnClick(R.id.tv_look_order)
    public void order(){
        Intent intent=new Intent(this,OrderXqActivity.class);
        intent.putExtra("orderid",orderid);
        startActivity(intent);
    }

    private void initRecy() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10, 2));
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(), R.layout.recommend_layout, beanList) {
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
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BaseDataListBean.DataBean bean = beanList.get(position);
                Intent intent = new Intent(OfflinePaymentActivity.this, GoodsXqActivity.class);
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
    public void back() {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Guess();
    }

    /**
     * 猜你在找
     */
    public void Guess(){
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("猜你在找", Constant.baseUrl+"item/index.php?c=Goods&m=UserLike&pagesize=10&page=0&user_id="+userId);
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
                        adapter.add(beanList,true);
                    }
                });
    }
}
