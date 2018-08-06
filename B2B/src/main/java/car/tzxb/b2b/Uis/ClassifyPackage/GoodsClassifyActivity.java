package car.tzxb.b2b.Uis.ClassifyPackage;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.umeng.commonsdk.debug.W;

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
import car.myview.FlexRadioGroupPackage.FlexRadioGroup;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.GoodsXqBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.Presenter.GoodsClassifyPresenterIml;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Uis.LoginActivity;
import car.tzxb.b2b.Util.AnimationUtil;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.LoadingDialog;
import car.tzxb.b2b.Views.PopWindow.AddShoppingCarPop;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;


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
    @BindView(R.id.iv_search_bar_right)
    ImageView iv_right;
    @BindView(R.id.drawerlayout_rg)
    FlexRadioGroup rg;
    @BindView(R.id.tv_reset)
    TextView tv_reset;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.tv_drawerlayout_title)
    TextView tv_drawerlayout_title;
    @BindView(R.id.et_max_price)
    EditText et_max_price;
    @BindView(R.id.et_min_price)
    EditText et_min_price;
    private String price_range="0";
    MvpContact.Presenter presenter = new GoodsClassifyPresenterIml(this);
    private CommonAdapter<BaseDataListBean.DataBean> adapter;
    private boolean b1, b2;
    private List<BaseDataListBean.DataBean> beanList = new ArrayList<>();
    private String brand;
    private String cate;
    private String seach;
    private String price;
    private String sales;
    private String url = Constant.baseUrl + "item/index.php?c=Goods&m=GoodsList";
    private LoadingDialog loadingDialog;
    private String from;


    @Override
    public void initParms(Bundle parms) {
        brand = getIntent().getStringExtra("brand");
        cate = getIntent().getStringExtra("cate");
        from = getIntent().getStringExtra("from");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_goods_classify;
    }

    @Override
    public void doBusiness(Context mContext) {
        initRecy();
        initUi();
    }
    @Override
    protected BasePresenter bindPresenter() {
        return presenter;
    }


    @Override
    public void showData(Object o) {
        BaseDataListBean bean = (BaseDataListBean) o;
        beanList = bean.getData();
        adapter.add(beanList, true);
    }

    private void initUi() {
        iv_right.setVisibility(View.INVISIBLE);
        tv_zh.setTextColor(getResources().getColor(R.color.red1));
        et_seach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    seach = et_seach.getText().toString();

                    Map<String, String> parms = CreateMap(cate, brand, seach, null, null,price_range);
                    presenter.PresenterGetData(url, parms);
                    hideSoftInput();

                    return true;
                }
                return false;
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //防止点击白色区域穿透到下层
                drawerView.setClickable(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        //第一屏数据
        Map<String, String> parms = CreateMap(cate, brand, null, null, null,price_range);
        presenter.PresenterGetData(url, parms);
        //侧滑数据
        String m=null;
        if("cate".equals(from)){
            tv_drawerlayout_title.setText("品牌");
            Screen(cate,"BrandList");
        }else {
            Screen(brand,"BrandCategory");
            tv_drawerlayout_title.setText("分类");
        }
    }

    private void Screen(String id,String m) {
        Log.i("品牌分类",Constant.baseUrl+"item/index.php?c=Goods&m="+m+"&id="+id);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"item/index.php?c=Goods")
                .addParams("m",m)
                .addParams("id",id)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {

                        initDrawerLayout(response);
                    }
                });
    }

     //侧滑布局
    private void initDrawerLayout(BaseDataListBean response) {

        final List<BaseDataListBean.DataBean> lists=response.getData();
        int width= DeviceUtils.dip2px(MyApp.getContext(),80);
        int height=DeviceUtils.dip2px(MyApp.getContext(),25);
        FlexRadioGroup.LayoutParams layoutParams=new FlexRadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,height);
        layoutParams.setMargins(0,0,20,20);
        for (int i = 0; i <lists.size() ; i++) {
            BaseDataListBean.DataBean bean=lists.get(i);
            RadioButton rb=new RadioButton(this);
            rb.setGravity(Gravity.CENTER);
            rb.setMinHeight(height);
            rb.setMinWidth(width);
            rb.setLayoutParams(layoutParams);
            rb.setButtonDrawable(null);
            rb.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            rb.setId(i);
            rb.setText(bean.getTitle());
            rb.setTextColor(getResources().getColorStateList(R.color.tv_color2));
            rb.setBackground(getResources().getDrawable(R.drawable.gg_select));
            rg.addView(rb);
        }

        rg.setOnCheckedChangeListener(new FlexRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@IdRes int checkedId) {
                BaseDataListBean.DataBean bean=lists.get(checkedId);
                String id=bean.getId();
                 if("cate".equals(from)){
                     brand=id;
                 }else {
                     cate=id;
                 }
            }
        });

        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i <lists.size() ; i++) {
                    RadioButton rb=rg.findViewById(i);
                    rb.setChecked(false);
                    if("cate".equals(from)){
                        brand="";
                    }else {
                        cate="";
                    }
                    et_max_price.setText("");
                    et_min_price.setText("");
                    price_range="0";
                }
            }
        });
    }

    @OnClick(R.id.tv_sure)
    public void sure()
    {

        //最高价
        String minPrice = et_min_price.getText().toString().trim();
        String maxPrice = et_max_price.getText().toString().trim();
        if(!TextUtils.isEmpty(minPrice)&&!TextUtils.isEmpty(maxPrice)){
            int min=Integer.valueOf(minPrice);
            int max=Integer.valueOf(maxPrice);
            if(min>max){
                MyToast.makeTextAnim(MyApp.getContext(),"最低价格不能高于最高价格",0,Gravity.CENTER,0,0).show();
                return;
            }
        }
       if(TextUtils.isEmpty(minPrice)){
           minPrice="0";
       }
        drawerLayout.closeDrawer(Gravity.END);
        price_range=minPrice+","+maxPrice;
        Map<String, String> parms = CreateMap(cate, brand, seach, price, sales,price_range);
        presenter.PresenterGetData(url, parms);
    }

    private void initRecy() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(), R.layout.commn_item, beanList) {
            @Override
            protected void convert(ViewHolder holder, final BaseDataListBean.DataBean bean, int position) {
                //图片
                ImageView iv = holder.getView(R.id.iv_category);
                Glide.with(MyApp.getContext()).load(bean.getImg_url()).into(iv);
                //名字
                holder.setText(R.id.tv_catagroy_name, "\u3000\u3000" + bean.getGoods_name());
                //价钱
                TextView tv_price = holder.getView(R.id.tv_category_pice);
                tv_price.setText(Html.fromHtml("¥" + "<big>" + bean.getPrice() + "</big>"));
                //商品类型
                holder.setText(R.id.tv_goods_type, bean.getDealer());
                //销量
                TextView tv_sales=holder.getView(R.id.tv_goods_count);
                tv_sales.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                tv_sales.setTextColor(Color.parseColor("#8D8D8A"));
                tv_sales.setText("月销量:  "+bean.getSales());
            }
        };
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BaseDataListBean.DataBean bean = beanList.get(position);
                Intent intent = new Intent(GoodsClassifyActivity.this, GoodsXqActivity.class);
                intent.putExtra("mainId", bean.getId());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    @Override
    public void showLoading() {
        loadingDialog = new LoadingDialog();
        loadingDialog.show(getSupportFragmentManager(), "class");
    }

    @Override
    public void closeLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void showErro() {

    }

    /**
     * 综合
     */
    @OnClick(R.id.tv_filter_zh)
    public void zh() {
        tv_zh.setTextColor(getResources().getColor(R.color.red1));
        tv_xl.setTextColor(getResources().getColor(R.color.black));
        tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(0), null);
        tv_jg.setTextColor(getResources().getColor(R.color.black));
        tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(0), null);
        tv_sx.setTextColor(getResources().getColor(R.color.black));

        Map<String, String> map = CreateMap(cate, brand, seach, null, null,price_range);
        presenter.PresenterGetData(url, map);
    }

    /**
     * 销量
     */
    @OnClick(R.id.tv_filter_xl)
    public void xl() {
        if (!b1) {
            tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(2), null);
            b1 = true;
            sales = "asc";

        } else {
            tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(1), null);
            b1 = false;
            sales = "desc";

        }

        tv_xl.setTextColor(getResources().getColor(R.color.red1));
        tv_zh.setTextColor(getResources().getColor(R.color.black));
        tv_sx.setTextColor(getResources().getColor(R.color.black));
        tv_jg.setTextColor(getResources().getColor(R.color.black));
        tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(0), null);

        Map<String, String> map = CreateMap(cate, brand, seach, null, sales,price_range);
        presenter.PresenterGetData(url, map);

    }

    /**
     * 价格
     */
    @OnClick(R.id.tv_filter_jg)
    public void jg() {
        if (!b2) {
            tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(2), null);
            b2 = true;
            price = "asc";
        } else {
            tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(1), null);
            b2 = false;
            price = "desc";

        }

        tv_jg.setTextColor(getResources().getColor(R.color.red1));
        tv_zh.setTextColor(getResources().getColor(R.color.black));
        tv_sx.setTextColor(getResources().getColor(R.color.black));
        tv_xl.setTextColor(getResources().getColor(R.color.black));
        tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, getDraw(0), null);

        Map<String, String> map = CreateMap(cate, brand, seach, price, null,price_range);
        presenter.PresenterGetData(url, map);
    }

    /**
     * 筛选
     */
    @OnClick(R.id.tv_filter_sx)
    public void sx() {
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

    public Map CreateMap(String cate, String brand, String search, String price, String sales,String price_range) {
        Map<String, String> map = new HashMap<>();
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        map.put("user_id", userId);
        map.put("cate", cate);
        map.put("brand", brand);
        map.put("search", search);
        map.put("price", price);
        map.put("sales", sales);
        map.put("price_range",price_range);
        return map;
    }

}
