package car.tzxb.b2b.Uis.GoodsXqPackage;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CustomToast.MyToast;
import car.myview.Loading.LoadingView;
import car.myview.NoScollViewPager;
import car.tzxb.b2b.Adapter.XqPagerAdapter;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.GoodsXqBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.Interface.ScollListener;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.Presenter.GoodsXqPresenterIml;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.LoginActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.PopWindow.AddShoppingCarPop;
import car.tzxb.b2b.config.Constant;
import car.tzxb.b2b.fragments.EvaluateFragment;
import car.tzxb.b2b.fragments.GoodsFragment;
import okhttp3.Call;

public class GoodsXqActivity extends MyBaseAcitivity implements RadioGroup.OnCheckedChangeListener,MvpViewInterface,GoodsFragment.ClickListener{

    @BindView(R.id.xq_tab)
    TabLayout tabLayout;
    @BindView(R.id.iv_xq_sc)
    ImageView iv_sc;
    @BindView(R.id.xq_vp)
    NoScollViewPager vp;
    @BindView(R.id.rg_xq)
    RadioGroup rg;
    @BindView(R.id.goods_xq_parent)
    View parent;
    @BindView(R.id.ll_xq_content)
    LinearLayout ll_content;
    @BindView(R.id.loadingview)
    LoadingView loadingView;
    private MvpContact.Presenter presenter = new GoodsXqPresenterIml(this);
    private String mainId;
    private GoodsXqBean goodsXqBean;
    private int index;
    private String from;

    public GoodsXqBean.DataBean ResultBean;

    ScollListener scollListener;

    public void SetScollTo(ScollListener listener) {
        this.scollListener = listener;
    }



    @Override
    public void initParms(Bundle parms) {
        mainId = getIntent().getStringExtra("mainId");
        from = getIntent().getStringExtra("from");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_goods_xq;
    }

    @Override
    public void doBusiness(Context mContext) {
        inittab();
        getData();
        rg.setOnCheckedChangeListener(this);

    }
    @Override
    protected BasePresenter bindPresenter() {
        return presenter;
    }


    private void getData() {
        String url = Constant.baseUrl + "item/index.php?c=Goods&m=GetGoodsInfo";
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Map<String, String> map = new HashMap<>();
        map.put("id", mainId);
        map.put("user_id", userId);
        presenter.PresenterGetData(url, map);
    }

    public void inittab() {
        String[] str = {"商品", "详情", "评价"};
        for (int i = 0; i <str.length ; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(str[i]));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index=tab.getPosition();
                switch (index){
                    case 0:
                        vp.setCurrentItem(0);
                        scollListener.scollTop();
                        break;
                    case 1:
                        vp.setCurrentItem(0);
                        scollListener.scollLv();
                        break;
                    case 2:
                        vp.setCurrentItem(1);
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        List<Fragment> fragments = new ArrayList<>();
        GoodsFragment goods = new GoodsFragment();
        EvaluateFragment ev = new EvaluateFragment();
        fragments.add(goods);
        fragments.add(ev);
        vp.isCanScoll(true);
        XqPagerAdapter pagerAdapter = new XqPagerAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(pagerAdapter);
        vp.setCurrentItem(0);
        vp.setOffscreenPageLimit(2);
    }


    public void collect(String b) {

        if ("0".equals(b)) {
            iv_sc.setImageResource(R.drawable.navbar_icon_nc);
        } else {
            iv_sc.setImageResource(R.drawable.navbar_icon_yc);
        }
    }

    @OnClick(R.id.iv_xq_sc)
    public void scGoods() {

        if (isFastClick()) {
            String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
            if (userId == null) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                return;
            }

            OkHttpUtils
                    .get()
                    .tag(this)
                    .url(Constant.baseUrl + "item/index.php?c=Goods&m=UserGoodsCollect")
                    .addParams("user_id", userId)
                    .addParams("goods_id", mainId)
                    .build()
                    .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(BaseStringBean response, int id) {
                            MyToast.makeTextAnim(MyApp.getContext(), response.getMsg(), 0, Gravity.CENTER, 0, 0).show();
                            boolean b=response.isFlag();
                            if(b==true){
                                iv_sc.setImageResource(R.drawable.navbar_icon_yc);
                            }else {
                                iv_sc.setImageResource(R.drawable.navbar_icon_nc);
                            }
                        }
                    });
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        RadioButton rb = radioGroup.findViewById(i);
        rb.setChecked(false);
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        if (userId == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            return;
        }
        if(isFastClick()){
            switch (i) {
                case R.id.rb_xq_shop:
                    MyToast.makeTextAnim(MyApp.getContext(), "卖家", 0, Gravity.CENTER, 0, 0).show();
                    break;
                case R.id.rb_xq_sc:
                    MyToast.makeTextAnim(MyApp.getContext(), "店铺", 0, Gravity.CENTER, 0, 0).show();
                    break;
                case R.id.rb_to_gwc:
                  //  startActivity(new Intent(this, ShoppingCarActivity.class));
                    startActivity(new Intent(this,ShoppingCartActivity.class));
                    if("fragment".equals(from)){
                        finish();
                    }
                    break;
                case R.id.rb_add_shoppingcar:
                    showShoppingCarWindow();
                    break;
        }

        }
    }

    private void showShoppingCarWindow() {

        List<GoodsXqBean.DataBean.ProductBean> productBeanList=goodsXqBean.getData().getProduct();
        AddShoppingCarPop window = new AddShoppingCarPop(MyApp.getContext(), productBeanList,index);
        DeviceUtils.showPopWindow(parent,window);
        window.setAddShoppingCar(new AddShoppingCarPop.AddShoppingCarListener() {
            @Override
            public void Click(int number, String pro_id, String shop_id,String discountsId) {
                putIn(number, pro_id, shop_id,discountsId);
            }
        });
    }

    public void putIn(int number, String pro_id, String shop_id,String discountsId) {

        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        if (userId == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            return;
        }
        Log.i("添加购物车路径", Constant.baseUrl + "orders/shopping_cars_moblie.php?m=add_shoppingcar" + "&number=" + number +
                "&pro_id=" + pro_id + "&shop_id=" + shop_id + "&type=0" + "&motion_id="+discountsId + "&user_id=" + userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/shopping_cars_moblie.php?m=add_shoppingcar")
                .addParams("number", String.valueOf(number))
                .addParams("pro_id", pro_id)
                .addParams("shop_id", shop_id)
                .addParams("type","0")
                .addParams("motion_id", discountsId)
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        MyToast.makeTextAnim(MyApp.getContext(), e.toString(), 0, Gravity.CENTER, 0, 0).show();
                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {

                        if (response.getStatus() == 1) {
                            MyToast.makeTextAnim(MyApp.getContext(), "加入购物车成功", 0, Gravity.CENTER, 0, 0).show();
                        }

                    }
                });
    }


    @Override
    public void showData(Object o) {
        goodsXqBean = (GoodsXqBean) o;

        this.ResultBean=goodsXqBean.getData();
    }






    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {
        loadingView.setVisibility(View.GONE);
        ll_content.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErro() {

    }
    @OnClick(R.id.iv_xq_back)
    public void back(){
        onBackPressed();
    }

    public void setTabSelect(int i){
        tabLayout.getTabAt(i).select();
    }


    public GoodsXqBean.DataBean getResultBean() {
        return ResultBean;
    }

    @Override
    public void checkId(int position) {
       this.index=position;
    }

}
