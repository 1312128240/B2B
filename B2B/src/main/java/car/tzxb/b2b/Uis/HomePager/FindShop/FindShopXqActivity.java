package car.tzxb.b2b.Uis.HomePager.FindShop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.SpaceItemDecoration;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myrecyclerviewadapter.wrapper.LoadMoreWrapper;
import car.myview.CircleImageView.CircleImageView;
import car.myview.CustomToast.MyToast;
import car.myview.TagTextViewItem;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.Bean.FindShopXqBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.LoadingDialog;
import car.tzxb.b2b.Views.PopWindow.FindShopPop;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class FindShopXqActivity extends MyBaseAcitivity {
    @BindView(R.id.recy_find_shop_xq)
    RecyclerView recy;
    @BindView(R.id.recy_find_shop_drawer)
    RecyclerView recy_drawer;
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
    @BindView(R.id.tv_find_shop_zh)
    TextView tv_zh;
    @BindView(R.id.tv_find_shop_jg)
    TextView tv_jg;
    @BindView(R.id.tv_find_shop_xl)
    TextView tv_xl;
    @BindView(R.id.tv_find_shop_sx)
    TextView tv_sx;
    @BindDrawable(R.mipmap.shop_icon_collection)
    Drawable d_c1;
    @BindDrawable(R.mipmap.shop_icon_collection2)
    Drawable d_c2;
    @BindDrawable(R.drawable.shop_icon_sort)
    Drawable d_s1;
    @BindDrawable(R.drawable.shop_icon_sort2)
    Drawable d_s2;
    @BindDrawable(R.drawable.shop_icon_sort3)
    Drawable d_s3;
    @BindView(R.id.et_classify)
    EditText et_seach;
    @BindView(R.id.tv_empty)
    TextView tv_empty;
    private boolean flag1, flag2,b;
    private LoadingDialog loadingDialog;
    private int page;
    private List<FindShopXqBean.DataBean.GoodsBean> goodsBeanList = new ArrayList<>();
    private LoadMoreWrapper<FindShopXqBean.DataBean.GoodsBean> loadMoreWrapper;
    private CommonAdapter<FindShopXqBean.DataBean.GoodsBean> adapter;
    private String param1, param2,cate,search,userId,shop_id,mobile;
    private View Loadview;



    @Override
    public void initParms(Bundle parms) {
        shop_id = getIntent().getStringExtra("shop_id");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_find_shop_xq;
    }

    @Override
    public void doBusiness(Context mContext) {
        userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        iniAdapter();
        Refesh();
        getCate();

        et_seach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    search=et_seach.getText().toString().trim();
                    page=0;
                    Refesh();
                }
                return false;

            }
        });

        recy.addOnScrollListener(new RecyclerView.OnScrollListener() {
        });
    }
    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    private void iniAdapter() {
        recy.setLayoutManager(new GridLayoutManager(this, 2));
        recy.addItemDecoration(new SpaceItemDecoration(10, 2));

        adapter = new CommonAdapter<FindShopXqBean.DataBean.GoodsBean>(MyApp.getContext(), R.layout.recommend_layout, goodsBeanList) {
            @Override
            protected void convert(ViewHolder holder, FindShopXqBean.DataBean.GoodsBean goodsBean, int position) {
                //图片
                int i = DeviceUtils.dip2px(MyApp.getContext(), 178);
                ImageView iv = holder.getView(R.id.iv_recommend);
                Glide.with(MyApp.getContext()).load(goodsBean.getImg_url()).override(i, i).into(iv);
                //名字
                TextView tv_title = holder.getView(R.id.tv_recommend_title);
                tv_title.setText(goodsBean.getGoods_name());
                tv_title.setMaxLines(2);
                //现价
                TextView tv_price = holder.getView(R.id.tv_recomment_sales);
                tv_price.setTextColor(Color.parseColor("#FA482E"));
                tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tv_price.setText(Html.fromHtml("¥" + "<big>" + goodsBean.getSeal_price() + "</big>"));
                //原价
                TextView tv_original_prices = holder.getView(R.id.tv_recommend_original_prices);
                tv_original_prices.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                tv_original_prices.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
                tv_original_prices.setPadding(5, 5, 0, 0);
                tv_original_prices.setText("¥" + goodsBean.getMax_seal_price());
                //销量
                holder.getView(R.id.iv_gwc_icon).setVisibility(View.GONE);
                TextView tv_sales2 = holder.getView(R.id.tv_recommend_sales2);
                tv_sales2.setText("销量: " + goodsBean.getSales());
                tv_sales2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tv_sales2.setPadding(0, 0, 0, 5);
            }
        };

        loadMoreWrapper = new LoadMoreWrapper<>(adapter);
        loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
               LoadMore();
            }
        });


        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String mainId = goodsBeanList.get(position).getId();
                Intent intent = new Intent(FindShopXqActivity.this, GoodsXqActivity.class);
                intent.putExtra("mainId", mainId);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recy.setAdapter(loadMoreWrapper);
    }


    //门店信息
    private void initShopInfor( FindShopXqBean.DataBean.InfoBean inforBean) {
        //门店图片
        if (inforBean != null)
            Glide.with(MyApp.getContext()).load(inforBean.getImg()).asBitmap().error(R.drawable.bucket_no_picture).into(civ_shop);
        //电话号码
        mobile = inforBean.getMobile();
        //店名
        tv_shop_name.setText(inforBean.getShop_name());
        //销量
        tv_find_shop_saleas.setText("销量 " + inforBean.getSales_count());
        //收藏数量
        tv_find_shop_sc.setText("收藏 " + inforBean.getCollect());
        //收藏状态
        if ("0".equals(inforBean.getCollect())) {  //未收藏
            b = false;
            tv_sc_status.setText("收藏");
            tv_sc_status.setTextColor(Color.WHITE);
            tv_sc_status.setCompoundDrawablesRelativeWithIntrinsicBounds(d_c1, null, null, null);
            tv_sc_status.setBackgroundColor(Color.parseColor("#F4143F"));
        } else {
            tv_sc_status.setText("已收藏");     //已收藏
            b = true;
            tv_sc_status.setCompoundDrawablesRelativeWithIntrinsicBounds(d_c2, null, null, null);
            tv_sc_status.setTextColor(Color.parseColor("#F4143F"));
            tv_sc_status.setBackgroundColor(Color.WHITE);
        }
        //评分图片
        double pf=inforBean.getShop_pf();
        if(pf==5){
            iv_pf.setImageResource(R.drawable.shop_icon_score9);
        }else if(pf>4&&pf<5){
            iv_pf.setImageResource(R.drawable.shop_icon_score8);
        }else if(pf==4){
            iv_pf.setImageResource(R.drawable.shop_icon_score7);
        }else if(pf>3&&pf<4){
            iv_pf.setImageResource(R.drawable.shop_icon_score6);
        }else if(pf==3){
            iv_pf.setImageResource(R.drawable.shop_icon_score5);
        }else if(pf>2&&pf<3){
            iv_pf.setImageResource(R.drawable.shop_icon_score4);
        }else if(pf==2){
            iv_pf.setImageResource(R.drawable.shop_icon_score3);
        }else if(pf>1&&pf<2){
            iv_pf.setImageResource(R.drawable.shop_icon_score2);
        }else {
            iv_pf.setImageResource(R.drawable.shop_icon_score1);
        }
    }

    @OnClick(R.id.tv_find_shop_fl)
    public void fl() {
        drawerLayout.openDrawer(Gravity.END);
    }

    @OnClick(R.id.tv_find_shop_call)
    public void call() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" +mobile);
        intent.setData(data);
        startActivity(intent);
    }

    @OnClick(R.id.tv_find_shop_zh)
    public void zh() {
        tv_zh.setTextColor(Color.parseColor("#F93214"));
        tv_sx.setTextColor(Color.parseColor("#303030"));
        tv_jg.setTextColor(Color.parseColor("#303030"));
        tv_xl.setTextColor(Color.parseColor("#303030"));
        tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, d_s1, null);
        tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, d_s1, null);
        page=0;
        param1 = "";
        param2 = "";
        Refesh();
    }

    @OnClick(R.id.tv_find_shop_jg)
    public void jg() {
        tv_jg.setTextColor(Color.parseColor("#F93214"));
        tv_zh.setTextColor(Color.parseColor("#303030"));
        tv_xl.setTextColor(Color.parseColor("#303030"));
        tv_sx.setTextColor(Color.parseColor("#303030"));
        tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, d_s1, null);
        if (!flag1) {                                                     //上升
            tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, d_s2, null);
            flag1 = true;
            param1 = "asc";
        } else {                                                          //下降
            tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, d_s3, null);
            flag1 = false;
            param1 = "desc";
        }
        page=0;
        param2 = "";
        Refesh();
    }

    @OnClick(R.id.tv_find_shop_xl)
    public void xl() {
        tv_xl.setTextColor(Color.parseColor("#F93214"));
        tv_zh.setTextColor(Color.parseColor("#303030"));
        tv_jg.setTextColor(Color.parseColor("#303030"));
        tv_sx.setTextColor(Color.parseColor("#303030"));
        tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, d_s1, null);

        if (!flag2) {
            tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, d_s2, null);
            flag2 = true;
            param2 = "asc";
        } else {
            tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, d_s3, null);
            flag2 = false;
            param2 = "desc";
        }
        page=0;
        param1 = "";
        Refesh();
    }

    @OnClick(R.id.tv_find_shop_sx)
    public void sx() {
        tv_sx.setTextColor(Color.parseColor("#F93214"));
        tv_zh.setTextColor(Color.parseColor("#303030"));
        tv_jg.setTextColor(Color.parseColor("#303030"));
        tv_xl.setTextColor(Color.parseColor("#303030"));

        tv_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, d_s1, null);
        tv_jg.setCompoundDrawablesWithIntrinsicBounds(null, null, d_s1, null);
        page=0;
        param2 = "";
        param1 = "";
        Refesh();
    }


    @OnClick(R.id.tv_find_shop_sc_status)
    public void sc_status() {
        if (b == true) {
            tv_sc_status.setText("收藏");
            tv_sc_status.setCompoundDrawablesRelativeWithIntrinsicBounds(d_c1, null, null, null);
            tv_sc_status.setBackgroundColor(Color.parseColor("#F41640"));
            tv_sc_status.setTextColor(Color.WHITE);
            b = false;
            MyToast.makeTextAnim(MyApp.getContext(),"已取消",0,Gravity.CENTER,0,0).show();
        } else {
            tv_sc_status.setText("已收藏");
            tv_sc_status.setCompoundDrawablesRelativeWithIntrinsicBounds(d_c2, null, null, null);
            tv_sc_status.setBackgroundColor(Color.WHITE);
            tv_sc_status.setTextColor(Color.parseColor("#F41640"));
            b = true;
            MyToast.makeTextAnim(MyApp.getContext(),"已收藏",0,Gravity.CENTER,0,0).show();
        }
    }


    @OnClick(R.id.iv_search_bar_left)
    public void back() {
        onBackPressed();
    }
    //获取商品数据
    private void Refesh() {
        Log.i("门店详情数据",Constant.baseUrl+"item/index.php?c=Home&m=ShopIndexPage"+ "&shop_id="+shop_id+"&pagesize=20" +
                "&page="+page+"&price=" + param1 + "&sales=" + param2+"&user_id="+userId+"&cate="+cate+"&search="+search);
        showLoadingDialog();
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Home&m=ShopIndexPage")
                .addParams("user_id",userId)
                .addParams("shop_id", shop_id)
                .addParams("pagesize", "10")
                .addParams("page",String.valueOf(page))
                .addParams("price", param1)
                .addParams("sales", param2)
                .addParams("cate",cate)
                .addParams("search",search)
                .build()
                .execute(new GenericsCallback<FindShopXqBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                           closeLoadingDialog();
                    }

                    @Override
                    public void onResponse(FindShopXqBean response, int id) {
                        closeLoadingDialog();
                        //门店信息
                         FindShopXqBean.DataBean.InfoBean inforBean = response.getData().getInfo();
                         initShopInfor(inforBean);
                        //商品数据
                         goodsBeanList = response.getData().getGoods();
                         adapter.add(goodsBeanList, true);
                         Loadview = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.default_footer,null);
                         loadMoreWrapper.setLoadMoreView(Loadview);
                         loadMoreWrapper.notifyDataSetChanged();

                        if(goodsBeanList.size()>0){
                            recy.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                            page++;
                        }else {
                            recy.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


   //加载更多
    private void LoadMore() {

        Log.i("门店商品加载更多",Constant.baseUrl+"item/index.php?c=Home&m=ShopIndexPage"+
                "&shop_id="+shop_id+"&pagesize=20&page="+page+"&price=" + param1 + "&sales=" + param2+"&user_id="+userId+"&cate="+cate+"&search="+search);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Home&m=ShopIndexPage")
                .addParams("user_id",userId)
                .addParams("shop_id", shop_id)
                .addParams("pagesize", "20")
                .addParams("page", String.valueOf(page))
                .addParams("price", param1)
                .addParams("sales", param2)
                .addParams("cate",cate)
                .addParams("search",search)
                .build()
                .execute(new GenericsCallback<FindShopXqBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {


                    }

                    @Override
                    public void onResponse(FindShopXqBean response, int id) {

                        List<FindShopXqBean.DataBean.GoodsBean> tempList=response.getData().getGoods();
                        goodsBeanList.addAll(tempList);
                        adapter.add(goodsBeanList,true);
                        if(tempList.size()>0){
                            page++;
                        }else {
                            Loadview=null;
                            loadMoreWrapper.setLoadMoreView(Loadview);
                            MyToast.makeTextAnim(MyApp.getContext(),"已全部为您加载完毕",0,Gravity.CENTER,0,0).show();
                        }
                        loadMoreWrapper.notifyDataSetChanged();

                    }
                });
    }




    //获取品牌分类数据
    private void getCate() {
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"item/index.php?c=Goods&m=Category")
                .addParams("user_id",userId)
                .build()
                .execute(new GenericsCallback<BaseDataBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataBean response, int id) {
                      List<BaseDataBean.DataBean.CategoryBean> cateList=response.getData().getCategory();
                      initDrawerRecy(cateList);
                    }
                });

    }

    private void initDrawerRecy(final List<BaseDataBean.DataBean.CategoryBean> cateList) {
        recy_drawer.setLayoutManager(new LinearLayoutManager(this));
        BaseDataBean.DataBean.CategoryBean tempBean=new BaseDataBean.DataBean.CategoryBean();
        tempBean.setCategory_name("全部分类");
        tempBean.setId("");
        cateList.add(0,tempBean);
        final CommonAdapter<BaseDataBean.DataBean.CategoryBean> adapter=new CommonAdapter<BaseDataBean.DataBean.CategoryBean>(MyApp.getContext(),R.layout.tv_item,cateList) {
            @Override
            protected void convert(ViewHolder holder, BaseDataBean.DataBean.CategoryBean categoryBean, int position) {
                TagTextViewItem tv=holder.getView(R.id.tv_item);
                int l=DeviceUtils.dip2px(MyApp.getContext(),20);
                tv.setCheckTextColor(Color.parseColor("#FA3314"));
                tv.setUncheckTextColor(Color.parseColor("#303030"));
                tv.setPadding(l,l,0,0);
                tv.setText(categoryBean.getCategory_name());
                tv.setChecked(categoryBean.isCheck());
            }
        };
        recy_drawer.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                for (int i = 0; i <cateList.size(); i++) {
                    if (i == position) {
                        cateList.get(i).setCheck(true);
                    } else {
                        cateList.get(i).setCheck(false);
                    }
                }
                adapter.notifyDataSetChanged();
                drawerLayout.closeDrawer(Gravity.END);
                cate=cateList.get(position).getId();
                Refesh();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    //显示对话框
    public void showLoadingDialog() {
        loadingDialog = new LoadingDialog();
        loadingDialog.setCancelable(false);
        loadingDialog.show(getSupportFragmentManager(), "aa");
    }

    //关闭对话框
    public void closeLoadingDialog(){
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

}
