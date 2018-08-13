package car.tzxb.b2b.Uis.HomePager.FindShop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import car.myview.CircleImageView.CircleImageView;
import car.myview.MyNestScollview;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.FindShopXqBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class FindShopXqActivity extends MyBaseAcitivity implements MyNestScollview.OnScrollviewListener {

    private List<FindShopXqBean.DataBean.GoodsBean> goodsBeanList = new ArrayList<>();
    @BindView(R.id.recy_find_shop_xq)
    RecyclerView recy;
    @BindView(R.id.my_nestcscollview)
    MyNestScollview scollview;
    @BindView(R.id.ll_suspension)
    LinearLayout ll_suspension;
    @BindView(R.id.rl_find_shop)
    RelativeLayout rl_bottom;
    @BindView(R.id.drawerlayout_find_shop)
    DrawerLayout drawerLayout;
    @BindView(R.id.civ_shop)
    CircleImageView civ_shop;
    @BindView(R.id.tv_find_shop_name)
    TextView tv_shop_name;
    @BindView(R.id.iv_find_shop_pf)
    ImageView iv_pf;
    @BindView(R.id.tv_find_shop_sc)
    TextView tv_find_shop_sc;
    @BindView(R.id.tv_find_shop_sc_saleas)
    TextView tv_find_shop_saleas;
    @BindView(R.id.tv_find_shop_sc_status)
    TextView tv_sc_status;
    private CommonAdapter<FindShopXqBean.DataBean.GoodsBean> adapter;
    private int bottom;
    private FindShopXqBean.DataBean.InfoBean inforBean;
    private boolean b;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_find_shop_xq;
    }

    @Override
    public void doBusiness(Context mContext) {
        initEvent();
        iniAdapter();

    }


    private void initEvent() {
        //来获得宽度或者高度。这是获得一个view的宽度和高度的方法之一。
        // 这是一个注册监听视图树的观察者(observer)，在视图树的全局事件改变时得到通知。
        // ViewTreeObserver不能直接实例化，而是通过getViewTreeObserver()获得。
        scollview.setOnScrolInterface(this);
        //实现windowChange监听
        scollview.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                onScroll(scollview.getScrollY());
            }
        });
    }

    private void iniAdapter() {
        recy.setLayoutManager(new GridLayoutManager(this, 2));
        recy.addItemDecoration(new SpaceItemDecoration(10, 2));
        adapter = new CommonAdapter<FindShopXqBean.DataBean.GoodsBean>(MyApp.getContext(), R.layout.recommend_layout, goodsBeanList) {
            @Override
            protected void convert(ViewHolder holder, FindShopXqBean.DataBean.GoodsBean goodsBean, int position) {
                //图片
                ImageView iv = holder.getView(R.id.iv_recommend);
                Glide.with(MyApp.getContext()).load(goodsBean.getImg_url()).override(256, 256).into(iv);
                //名字
                holder.setText(R.id.tv_recommend_title, goodsBean.getGoods_name());
            }
        };
        recy.setAdapter(adapter);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Refesh();
    }

    private void Refesh() {
        Log.i("门店详情数据", Constant.baseUrl + "item/index.php?c=Home&m=ShopIndexPage&user_id=1&shop_id=12&pagesize=20&page=0&sales=&price=desc");
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Home&m=ShopIndexPage")
                .addParams("user_id", "1")
                .addParams("shop_id", "12")
                .addParams("pagesize", "20")
                .addParams("page", "0")
                .addParams("sales", "")
                .addParams("price", "desc")
                .build()
                .execute(new GenericsCallback<FindShopXqBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(FindShopXqBean response, int id) {
                        //商品数据
                        goodsBeanList = response.getData().getGoods();
                        adapter.add(goodsBeanList, true);
                        //门店信息
                        inforBean = response.getData().getInfo();
                        initShopInfor();
                    }
                });

    }

    @Override
    public void onScroll(int scrollY) {
        int top = Math.max(scrollY, bottom);
        ll_suspension.layout(0, top, ll_suspension.getWidth(), top + ll_suspension.getHeight());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            bottom = rl_bottom.getBottom();
        }
    }

    //门店信息
    private void initShopInfor() {
        //门店图片
        if (inforBean != null)
            Glide.with(MyApp.getContext()).load(inforBean.getImg()).asBitmap().error(R.drawable.bucket_no_picture).into(civ_shop);
        //店名
        tv_shop_name.setText(inforBean.getShop_name());
        //销量
        tv_find_shop_saleas.setText("销量 " + inforBean.getSales_count());
        //收藏数量
        tv_find_shop_sc.setText("收藏 " + inforBean.getCollect());
        //收藏状态
        if("0".equals(inforBean.getCollect())){  //未收藏
            tv_sc_status.setText("收藏");
            tv_sc_status.setTextColor(Color.parseColor("#FFFFFF"));
            tv_sc_status.setBackgroundColor(Color.parseColor("#F4143F"));
        }else {
            tv_sc_status.setText("已收藏");     //已收藏
            tv_sc_status.setTextColor(Color.parseColor("#F4143F"));
            tv_sc_status.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @OnClick(R.id.tv_find_shop_fl)
    public void fl() {
        drawerLayout.openDrawer(Gravity.END);
    }

    @OnClick(R.id.tv_find_shop_call)
    public void call() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + inforBean.getMobile());
        intent.setData(data);
        startActivity(intent);
    }
    @OnClick(R.id.tv_find_shop_sc)
    public void sc_status(){
      /*  if (b == false) {
            tv_collect.setText("收藏");
            ll_shine.setBackgroundColor(getResources().getColor(R.color.collect));
            tv_collect.setTextColor(Color.WHITE);
            sb.setChecked(false);
        } else {
            tv_collect.setText("已收藏");
            ll_shine.setBackgroundColor(Color.WHITE);
            tv_collect.setTextColor(getResources().getColor(R.color.collect));
            sb.setChecked(true);
        }*/
    }


    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }
}
