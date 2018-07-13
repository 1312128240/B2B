package car.tzxb.b2b.Uis.GoodsXqPackage;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import car.myview.NoScollViewPager;
import car.tzxb.b2b.Adapter.XqPagerAdapter;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.GoodsXqBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.Interface.GoodsXqInterface;
import car.tzxb.b2b.Interface.ScollListener;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.Presenter.GoodsXqPresenterIml;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.LoginActivity;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.PopWindow.AddShoppingCarPop;
import car.tzxb.b2b.config.Constant;
import car.tzxb.b2b.fragments.EvaluateFragment;
import car.tzxb.b2b.fragments.GoodsFragment;
import okhttp3.Call;

public class GoodsXqActivity extends MyBaseAcitivity implements RadioGroup.OnCheckedChangeListener, MvpViewInterface {


    @BindView(R.id.rg_tab)
    RadioGroup rg_tab;
    @BindView(R.id.iv_xq_sc)
    ImageView iv_sc;
    @BindView(R.id.xq_vp)
    NoScollViewPager vp;
    @BindView(R.id.rg_xq)
    RadioGroup rg;
    @BindView(R.id.goods_xq_parent)
    View parent;
    boolean sc;
    private MvpContact.Presenter presenter = new GoodsXqPresenterIml(this);
    private String mainId;
    private GoodsXqBean goodsXqBean;

    GoodsXqInterface goodsXqInterface;

    public void setDataSource(GoodsXqInterface ds) {
        this.goodsXqInterface = ds;
    }

    ScollListener scollListener;

    public void scoll(ScollListener listener) {
        this.scollListener = listener;
    }

    @Override
    public void initParms(Bundle parms) {
        mainId = getIntent().getStringExtra("mainId");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_goods_xq;
    }

    @Override
    public void doBusiness(Context mContext) {
        inittab();
        vp.isCanScoll(true);
        rg.setOnCheckedChangeListener(this);
        getData();
    }

    private void getData() {
        String url = Constant.baseUrl + "item/index.php?c=Goods&m=GetGoodsInfo";
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Map<String, String> map = new HashMap<>();
        map.put("id", "534");
        map.put("user_id", userId);
        presenter.PresenterGetData(url, map);
    }

    public void inittab() {
        final String[] str = {"商品", "详情", "评价"};
        for (int i = 0; i < str.length; i++) {
            RadioButton rb = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            rb.setLayoutParams(params);
            rb.setGravity(Gravity.CENTER_HORIZONTAL);
            rb.setText(str[i]);
            rb.setId(i);
            rb.setBackground(getDrawable(R.drawable.rb_line));
            rb.setTextColor(getResources().getColorStateList(R.color.tv_color2));
            rb.setButtonDrawable(null);
            rg_tab.addView(rb);
        }
        RadioButton rb1 = rg_tab.findViewById(0);
        rb1.setChecked(true);
        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
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
        });
        //添加fragment
        List<Fragment> fragments = new ArrayList<>();
        GoodsFragment goods = new GoodsFragment();
        EvaluateFragment ev = new EvaluateFragment();
        fragments.add(goods);
        fragments.add(ev);
        XqPagerAdapter pagerAdapter = new XqPagerAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(pagerAdapter);
        vp.setCurrentItem(0);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    RadioButton rb0 = rg_tab.findViewById(0);
                    rb0.setChecked(true);
                } else {
                    RadioButton rb2 = rg_tab.findViewById(2);
                    rb2.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    protected BasePresenter bindPresenter() {
        return presenter;
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

      /*  if(!sc){
            MyToast.makeTextAnim(MyApp.getContext(),"收藏成功",0,Gravity.CENTER,0,0).show();
            iv_sc.setImageResource(R.drawable.navbar_icon_yc);
            sc=true;
        }else {
            MyToast.makeTextAnim(MyApp.getContext(),"取消收藏",0,Gravity.CENTER,0,0).show();
            iv_sc.setImageResource(R.drawable.navbar_icon_nc);
            sc=false;
        }*/
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
                            boolean b=response.isData();
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
        switch (i) {
            case R.id.rb_xq_shop:
                MyToast.makeTextAnim(MyApp.getContext(), "卖家", 0, Gravity.CENTER, 0, 0).show();
                break;
            case R.id.rb_xq_sc:
                MyToast.makeTextAnim(MyApp.getContext(), "店铺", 0, Gravity.CENTER, 0, 0).show();
                break;
            case R.id.rb_to_gwc:

                startActivity(new Intent(this, ShoppingCarActivity.class));

                break;
            case R.id.rb_add_shoppingcar:

                if (isFastClick()) {
                    AddShoppingCar();
                }
                break;
        }
    }

    private void AddShoppingCar() {
        Log.i("查询的子商品", Constant.baseUrl + "item/index.php?c=Goods&m=GetProductsInfo" + "&id=" + mainId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Goods&m=GetProductsInfo")
                .addParams("id", mainId)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        if (response.getStatus() == 1) {
                            List<BaseDataListBean.DataBean> list = response.getData();
                            AddShoppingCarPop window = new AddShoppingCarPop(GoodsXqActivity.this, list);
                            window.show(parent);
                            window.setAddShoppingCar(new AddShoppingCarPop.AddShoppingCarListener() {
                                @Override
                                public void Click(int number, String pro_id, String shop_id, String type) {
                                    putIn(number, pro_id, shop_id, type);
                                }
                            });
                        }

                    }
                });
    }

    public void putIn(int number, String pro_id, String shop_id, String type) {

        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        if (userId == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            return;
        }
        Log.i("添加购物车路径", Constant.baseUrl + "orders/shopping_cars_moblie.php?m=add_shoppingcar" + "&number=" + number +
                "&pro_id=" + pro_id + "&shop_id=" + shop_id + "&type=" + type + "&motion_id=1" + "&user_id=" + userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/shopping_cars_moblie.php?m=add_shoppingcar")
                .addParams("number", String.valueOf(number))
                .addParams("pro_id", pro_id)
                .addParams("shop_id", shop_id)
                .addParams("type", type)
                .addParams("motion_id", "1")
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
        //传递到第一个fragment
        goodsXqInterface.getData(goodsXqBean);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showErro() {

    }

    public void setCurrent(int i) {
        vp.setCurrentItem(i);
    }

}
