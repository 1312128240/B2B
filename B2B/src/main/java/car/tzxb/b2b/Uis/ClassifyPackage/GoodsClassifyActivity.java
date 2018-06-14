package car.tzxb.b2b.Uis.ClassifyPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.BageView.BadgeView;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.Presenter.GoodsClassifyPresenterIml;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Util.AnimationUtil;
import car.tzxb.b2b.Views.PopWindow.AddShoppingCarPop;
import car.tzxb.b2b.config.Constant;


public class GoodsClassifyActivity extends MyBaseAcitivity implements MvpViewInterface {
    @BindView(R.id.bv_classify)
    BadgeView badgeView;
    @BindView(R.id.recy_goods_classify)
    RecyclerView recyclerview;
    @BindView(R.id.tv_filter_zh)
    TextView tv_zh;
    @BindView(R.id.tv_filter_xl)
    TextView tv_xl;
    @BindView(R.id.tv_filter_jg)
    TextView tv_jg;
    @BindView(R.id.tv_filter_sx)
    TextView tv_sx;
    @BindView(R.id.goods_classify_drawerlayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.et_classify)
    EditText et_seach;
    MvpContact.Presenter presenter = new GoodsClassifyPresenterIml(this);
    private CommonAdapter<BaseDataListBean.DataBean> adapter;
    private boolean b1, b2;
    private List<BaseDataListBean.DataBean> beanList=new ArrayList<>();
    private String brand;
    private String cate;
    private String seach;
    private String price;
    private String sales;
    private String network_ids;
    private String url=Constant.baseUrl+"item/index.php?c=Goods&m=ProductList";

    @Override
    public void initParms(Bundle parms) {
        brand = getIntent().getStringExtra("brand");
        cate = getIntent().getStringExtra("cate");

        Log.i("传商品分类id", brand +"____"+ cate);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_goods_classify;
    }

    @Override
    public void doBusiness(Context mContext) {
        badgeView.setText("88");

        Map<String,String> parms=CreateMap(cate,brand,null,null,null,null);
        presenter.PresenterGetData(url, parms);

        initRecy();
        et_seach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    seach=et_seach.getText().toString();

                    Map<String,String> parms=CreateMap(cate,brand,seach,null,null,null);
                    presenter.PresenterGetData(url, parms);
                    hideSoftInput();

                    return true;
                }
                return false;
            }
        });
    }




    @Override
    protected BasePresenter bindPresenter() {
        return presenter;
    }


    @Override
    public void showData(Object o) {
        BaseDataListBean bean= (BaseDataListBean) o;
        beanList = bean.getData();
        adapter.add(beanList,true);
        Log.i("分类筛选数据",beanList.size()+"");
    }

    private void initRecy() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(), R.layout.commn_item, beanList) {
            @Override
            protected void convert(ViewHolder holder, BaseDataListBean.DataBean bean, int position) {
                //图片
                ImageView iv = holder.getView(R.id.iv_category);
                Glide.with(MyApp.getContext()).load(bean.getImg_url()).into(iv);
                //名字
                holder.setText(R.id.tv_catagroy_name,"\t\t\t"+bean.getProduct_title());
                //价钱
                TextView tv_price=holder.getView(R.id.tv_category_pice);
                tv_price.setText(Html.fromHtml("¥"+"<big>"+bean.getSeal_price()+"</big>"));
                //销量
                holder.setText(R.id.tv_maker_price,"月销量: "+bean.getSales());
                //商品类型
                TextView tv=holder.getView(R.id.tv_goods_type);
                GradientDrawable gd=new GradientDrawable();
                gd.setCornerRadius(5);
                gd.setStroke(1,Color.parseColor("#ff0000"));
                tv.setBackground(gd);
                tv.setText(bean.getDealer());
                //加入购物车
                ImageView iv_gwc = holder.getView(R.id.iv_gwc_icon);
                iv_gwc.setVisibility(View.VISIBLE);
                iv_gwc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       showPop();
                    }


                });
            }
        };
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent=new Intent(GoodsClassifyActivity.this, GoodsXqActivity.class);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void showPop() {
        AddShoppingCarPop window=new AddShoppingCarPop(this);
        window.show(drawerLayout);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @OnClick(R.id.tv_filter_zh)
    public void zh() {
        tv_zh.setTextColor(getResources().getColor(R.color.red1));
        MyToast.makeTextAnim(this, "综合", 0, Gravity.CENTER, 0, 0).show();
        tv_xl.setTextColor(getResources().getColor(R.color.black));
        tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(0), null);
        tv_jg.setTextColor(getResources().getColor(R.color.black));
        tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(0), null);
        tv_sx.setTextColor(getResources().getColor(R.color.black));

        Map<String,String> map=CreateMap(cate,brand,seach,null,null,network_ids);
        presenter.PresenterGetData(url, map);
    }

    @OnClick(R.id.tv_filter_xl)
    public void xl() {
        if (!b1) {
            tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(2), null);
            b1 = true;
            sales="asc";

        } else {
            tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(1), null);
            b1 = false;
            sales="desc";

        }

        tv_xl.setTextColor(getResources().getColor(R.color.red1));
        tv_zh.setTextColor(getResources().getColor(R.color.black));
        tv_sx.setTextColor(getResources().getColor(R.color.black));
        tv_jg.setTextColor(getResources().getColor(R.color.black));
        tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(0), null);

        Map<String,String> map=CreateMap(cate,brand,seach,null,sales,null);
        presenter.PresenterGetData(url, map);

    }

    @OnClick(R.id.tv_filter_jg)
    public void jg() {
        if (!b2) {
            tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(2), null);
            b2 = true;
            price="asc";
        } else {
            tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(1), null);
            b2 = false;
            price="desc";

        }

        tv_jg.setTextColor(getResources().getColor(R.color.red1));
        tv_zh.setTextColor(getResources().getColor(R.color.black));
        tv_sx.setTextColor(getResources().getColor(R.color.black));
        tv_xl.setTextColor(getResources().getColor(R.color.black));
        tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(0), null);

        Map<String,String> map=CreateMap(cate,brand,seach,price,null,null);
        presenter.PresenterGetData(url, map);
    }

    @OnClick(R.id.tv_filter_sx)
    public void sx() {
        tv_sx.setTextColor(getResources().getColor(R.color.red1));
        tv_zh.setTextColor(getResources().getColor(R.color.black));
        tv_xl.setTextColor(getResources().getColor(R.color.black));
        tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(0), null);
        tv_jg.setTextColor(getResources().getColor(R.color.black));
        tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(0), null);

       drawerLayout.openDrawer(Gravity.END);

    }

    @OnClick(R.id.iv_search_bar_left)
    public void back() {
        onBackPressed();
    }

    /**
     * 图片资源
     * @param i
     * @return
     */
    public Drawable getDraw(int i) {
        List<Drawable> list = new ArrayList<>();
        Drawable d = null;
        Drawable d1 = this.getResources().getDrawable(R.drawable.list_icon_sort_default);
        Drawable d2 = this.getResources().getDrawable(R.drawable.list_icon_sort_low);
        Drawable d3 = this.getResources().getDrawable(R.drawable.list_icon_sort_up);
        list.add(d1);
        list.add(d2);
        list.add(d3);
        d = list.get(i);
        return d;
    }

    public Map CreateMap(String cate,String brand,String search,String price,String sales,String network_ids){
        Map<String,String> map=new HashMap<>();
        map.put("cate",cate);
        map.put("brand",brand);
        map.put("search",search);
        map.put("price",price);
        map.put("sales",sales);
        map.put("network_ids",network_ids);
        return map;
    }

}
