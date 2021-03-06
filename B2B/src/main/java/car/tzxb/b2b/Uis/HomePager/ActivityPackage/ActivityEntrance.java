package car.tzxb.b2b.Uis.HomePager.ActivityPackage;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import car.tzxb.b2b.Bean.ActivityDivisionBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class ActivityEntrance extends MyBaseAcitivity{
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.rg_activity)
    RadioGroup rg;
    @BindView(R.id.recy_activity)
    RecyclerView recyclerView;
    @BindView(R.id.iv_activity_bg)
    ImageView iv_bg;
    @BindView(R.id.coordinatorlayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.iv_empty_activity)
    TextView iv_empty;
    @BindView(R.id.iv_gift)
    ImageView iv_gift;
    private List<ActivityDivisionBean.DataBeanX.DataBean> beanList=new ArrayList<>();
    private CommonAdapter<ActivityDivisionBean.DataBeanX.DataBean> adapter;


    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_entrance;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("活动专区");
        int wh=DeviceUtils.dip2px(MyApp.getContext(),180);
        //diskCacheStrategy(DiskCacheStrategy.SOURCE)为其添加缓存策略,
        // 其中缓存策略可以为:Source及None,None为不缓存,Source缓存原型.如果为ALL和Result就不行
        Glide.with(mContext).load("file:///android_asset/loading.gif").asGif().override(wh,wh).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_gift);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_gift.setVisibility(View.GONE);
                initRecy();
                Refresh();
            }
        },1500);

    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }


    private void Refresh() {
        String userId=SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        Log.i("活动专区",Constant.baseUrl+"item/index.php?c=Goods&m=PromotionZone"+"&user_id="+userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"item/index.php?c=Goods&m=PromotionZone")
                .addParams("user_id",userId)
                .build()
                .execute(new GenericsCallback<ActivityDivisionBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(ActivityDivisionBean response, int id) {
                          List<ActivityDivisionBean.DataBeanX> xList=response.getData();
                          if(xList.size()!=0){
                              coordinatorLayout.setVisibility(View.VISIBLE);
                              iv_empty.setVisibility(View.GONE);
                              addRb(xList);
                          }else {
                              coordinatorLayout.setVisibility(View.GONE);
                              iv_empty.setVisibility(View.VISIBLE);
                          }
                    }
                });

    }

    private void addRb(final List<ActivityDivisionBean.DataBeanX> xList) {
        RadioButton rb=null;
        ActivityDivisionBean.DataBeanX xBean=null;
        RadioGroup.LayoutParams layoutParams=new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        layoutParams.setMargins(20,10,20,10);
        for (int i = 0; i <xList.size() ; i++) {
            rb = new RadioButton(this);
            xBean=xList.get(i);
            rb.setLayoutParams(layoutParams);
            rb.setGravity(Gravity.CENTER);
            rb.setButtonDrawable(null);
            rb.setId(i);
            rb.setText(xBean.getName());
            rb.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_yellow_select));
            rb.setTextColor(getResources().getColorStateList(R.color.tv_color4));
            rg.addView(rb);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
             List<ActivityDivisionBean.DataBeanX.DataBean> beanList=xList.get(checkedId).getData();
                adapter.add(beanList,true);
            }
        });
        RadioButton rb1=rg.findViewById(0);
        rb1.setChecked(true);
    }

    private void initRecy() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.addItemDecoration(new SpaceItemDecoration(8, 2));
        adapter = new CommonAdapter<ActivityDivisionBean.DataBeanX.DataBean>(MyApp.getContext(),R.layout.recommend_layout,beanList) {
            @Override
            protected void convert(ViewHolder holder, ActivityDivisionBean.DataBeanX.DataBean dataBean, int position) {
                //图片
                int i = DeviceUtils.dip2px(MyApp.getContext(), 186);
                ImageView iv_brand = holder.getView(R.id.iv_recommend);
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
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ActivityDivisionBean.DataBeanX.DataBean bean=beanList.get(position);
                Intent intent=new Intent(ActivityEntrance.this, GoodsXqActivity.class);
                intent.putExtra("mainId",bean.getGoods_id());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }

}
