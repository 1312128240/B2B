package car.tzxb.b2b.Uis.HomePager.SelfGoods;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class OemActivity extends MyBaseAcitivity {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.recy_oem)
    RecyclerView recyclerview;
    private List<BaseDataListBean.DataBean> beanList=new ArrayList<>();
    private CommonAdapter<BaseDataListBean.DataBean> adapter;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_oem2;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("贴牌商品");
        initRecy();
        getData();
    }

    private void initRecy() {
        recyclerview.setLayoutManager(new GridLayoutManager(MyApp.getContext(),2));
        recyclerview.addItemDecoration(new SpaceItemDecoration(12, 2));
        adapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(),R.layout.recommend_layout,beanList) {
            @Override
            protected void convert(ViewHolder holder, final BaseDataListBean.DataBean dataBean, int position) {
                //图片
                int i = DeviceUtils.dip2px(MyApp.getContext(), 186);
                final ImageView iv_brand = holder.getView(R.id.iv_recommend);
                Glide.with(MyApp.getContext()).load(dataBean.getImg_url()).override(i, i).into(iv_brand);
                //名字
                TextView tv_name = holder.getView(R.id.tv_recommend_title);
                tv_name.setMaxLines(2);
                tv_name.setText(dataBean.getGoods_name());
                //价格
                TextView tv_sales=holder.getView(R.id.tv_recomment_sales);
                tv_sales.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                tv_sales.setTextColor(Color.parseColor("#FA3314"));
                tv_sales.setText("¥"+dataBean.getSeal_price());
                //隐藏
                holder.getView(R.id.tv_recommend_price).setVisibility(View.GONE);
                holder.getView(R.id.iv_gwc_icon).setVisibility(View.INVISIBLE);

                //去详情
                iv_brand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(OemActivity.this, GoodsXqActivity.class);
                        intent.putExtra("mainId",dataBean.getGoods_id());
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(OemActivity.this,iv_brand,"share").toBundle());
                    }
                });
            }
        };
        recyclerview.setAdapter(adapter);
    }

    private void getData() {
        String usreId= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        Log.i("贴牌商品", Constant.baseUrl+"item/index.php?c=Goods&m=TZXBGoodsList&user_id="+usreId+"&sales=desc");
        OkHttpUtils
                .get()
                .tag(this)
                .url( Constant.baseUrl+"item/index.php?c=Goods&m=TZXBGoodsList")
                .addParams("user_id",usreId)
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

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }
}
