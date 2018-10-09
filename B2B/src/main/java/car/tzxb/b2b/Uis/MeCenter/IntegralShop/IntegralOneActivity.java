package car.tzxb.b2b.Uis.MeCenter.IntegralShop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import car.myrecyclerviewadapter.SpaceItemDecoration;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class IntegralOneActivity extends MyBaseAcitivity implements RadioGroup.OnCheckedChangeListener{

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.rg_ingegral)
    RadioGroup rg;
    @BindView(R.id.tv_jb)
    TextView tv_jb;
    @BindView(R.id.tv_jf)
    TextView tv_jf;
    @BindView(R.id.recy_jf)
    RecyclerView recy;
    private String userid;
    private List<BaseDataListBean.DataBean> lists=new ArrayList<>();
    private CommonAdapter<BaseDataListBean.DataBean> adapter;
    List<BaseDataListBean.DataBean> goodsList = new ArrayList<>();

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_integral_one;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("积分商城");
        userid = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        rg.setOnCheckedChangeListener(this);
        initAdapter();


      //  getNotice();

    }
    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getShop();
    }



    private void getNotice() {
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "goods/integralgoods.php?m=report")
                .build()
                .execute(new GenericsCallback(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Object response, int id) {

                    }
                });

    }




    private void initAdapter() {
        recy.setLayoutManager(new GridLayoutManager(this, 2));
        recy.addItemDecoration(new SpaceItemDecoration(10, 2));
        final int wh = DeviceUtils.dip2px(MyApp.getContext(), 178);
        adapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(), R.layout.recommend_layout, goodsList) {
            @Override
            protected void convert(ViewHolder holder, final BaseDataListBean.DataBean bean, int position) {
                //图片
                final ImageView iv = holder.getView(R.id.iv_recommend);
                Glide.with(MyApp.getContext()).load(bean.getPic()).override(wh, wh).into(iv);
                //名字
                holder.setText(R.id.tv_recommend_title, bean.getTitle());
                //价钱
                TextView tv_price = holder.getView(R.id.tv_recommend_original_prices);
                tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                tv_price.setText("原价" + bean.getMarket_price());
                //隐藏控件
                holder.getView(R.id.iv_gwc_icon).setVisibility(View.GONE);

                //跳转
                View parent=holder.getView(R.id.parent);
                parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(IntegralOneActivity.this,IntegralXqActivity.class);
                        intent.putExtra("product_id", bean.getId());
                        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(IntegralOneActivity.this,iv, "share");
                        startActivity(intent, compat.toBundle());
                    }
                });
            }
        };
        recy.setAdapter(adapter);
    }


    private void getShop() {
        Log.i("积分商城门店", Constant.baseUrl + "goods/integralgoods.php?m=store_list" + "&userid=" + userid);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "goods/integralgoods.php?m=store_list")
                .addParams("userid", userid)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        if (response.getData().size() != 0) {
                            lists = response.getData();
                            initUi(response);
                        }
                    }
                });
    }


    private void initUi(BaseDataListBean response) {
        RadioButton rb = null;
        int i1 = DeviceUtils.dip2px(MyApp.getContext(), 6);
        int i2 = DeviceUtils.dip2px(MyApp.getContext(), 12);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(i1, 0, i1, 0);
        rg.removeAllViews();
        List<BaseDataListBean.DataBean> list1 = response.getData();
        for (int i = 0; i <list1.size(); i++) {
            rb = new RadioButton(this);
            BaseDataListBean.DataBean bean = list1.get(i);
            rb.setText(bean.getShop_name());
            rb.setId(i);
            rb.setLayoutParams(params);
            rb.setButtonDrawable(null);
            rb.setPadding(i2, 0, i2, 0);
            rb.setBackground(getResources().getDrawable(R.drawable.red_white_select));
            rb.setTextColor(getResources().getColorStateList(R.color.tv_color4));
            rg.addView(rb);
        }

        RadioButton rb1 = rg.findViewById(0);
        rb1.setChecked(true);

        //积分信息
        BaseDataListBean.InfoBean inforBean = response.getInfo().get(0);
        String jf = inforBean.getB2b_point();
        String jb = inforBean.getGold();
        if (jf != null) {
            tv_jf.setText(jf);
        } else {
            tv_jf.setText("0");
        }
        if (jb != null || !"".equals(jb)) {
            tv_jb.setText(jb);
        } else {
            tv_jb.setText("0");
        }

    }


    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.tv_actionbar_title)
    public void test(){
       startActivity(IntegralXqActivity.class);
    }

    //兑换记录
    @OnClick(R.id.tv_dh_jf)
    public void dh() {
        startActivity(IntegralExchangeActivity.class);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

       String id=lists.get(checkedId).getID();

       getGoods(id);

    }

    private void getGoods(String id){
        Log.i("积分商品", Constant.baseUrl + "goods/integralgoods.php?m=integralshop_list&id="+id);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "goods/integralgoods.php?m=integralshop_list")
                .addParams("id",id)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        goodsList = response.getData();
                        adapter.add(goodsList, true);
                    }
                });
    }
}
