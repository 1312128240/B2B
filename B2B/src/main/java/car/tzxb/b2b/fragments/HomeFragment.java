package car.tzxb.b2b.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.SpaceItemDecoration;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CustomToast.MyToast;
import car.myview.StickyScrollView;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.HomeBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.Presenter.HomePresenterIml;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.ActiclePackage.ArticleActivity;
import car.tzxb.b2b.Uis.ActiclePackage.ArticleWebViewActivity;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Uis.HomePager.ActivityPackage.ActivityEntrance;
import car.tzxb.b2b.Uis.HomePager.FindShop.FindShopsActivity;
import car.tzxb.b2b.Uis.HomePager.SelfGoods.OemActivity;
import car.tzxb.b2b.Uis.HomePager.SelfGoods.SelfGoodsActivity;
import car.tzxb.b2b.Uis.HomePager.Vip.VipHomePagerActivity;
import car.tzxb.b2b.Uis.HomePager.Wallet.MyWalletActivity;
import car.tzxb.b2b.Uis.LoginActivity;
import car.tzxb.b2b.Uis.MeCenter.IntegralShop.IntegralOneActivity;
import car.tzxb.b2b.Uis.MeCenter.IntegralShop.IntegralXqActivity;
import car.tzxb.b2b.Uis.MeCenter.MyGoldActivity;
import car.tzxb.b2b.Uis.Order.LookOrderActivity;
import car.tzxb.b2b.Uis.SeachPackage.SeachActivity;
import car.tzxb.b2b.Util.BannerImageLoader;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.LoadingDialog;
import car.tzxb.b2b.Views.DialogFragments.SignDialogFragment;
import car.tzxb.b2b.Views.TextSwitchView;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class HomeFragment extends MyBaseFragment implements MvpViewInterface {
    @BindView(R.id.iv_search_bar_left)
    ImageView iv_left;
    @BindView(R.id.home_banner)
    Banner banner;
    @BindView(R.id.recy_track)
    RecyclerView recy_track;
    @BindView(R.id.activity_bg)
    ImageView iv_activity_bg;
    @BindView(R.id.iv_activity1)
    ImageView iv_activity1;
    @BindView(R.id.iv_activity2)
    ImageView iv_activity2;
    @BindView(R.id.iv_self_produc1)
    ImageView iv_self1;
    @BindView(R.id.iv_self_produc2)
    ImageView iv_self2;
    @BindView(R.id.classify_tablayout)
    TabLayout classify_tabLayout;
    @BindView(R.id.brand_tablayout)
    TabLayout brand_tablayout;
    @BindView(R.id.iv_find_shop1)
    ImageView iv_find_shop1;
    @BindView(R.id.iv_find_shop2)
    ImageView iv_find_shop2;
    @BindView(R.id.iv_find_shop_bg)
    ImageView iv_find_shop_bg;
    @BindView(R.id.recy_home_goods)
    RecyclerView recy_goods;
    @BindView(R.id.iv_app_notifi)
    ImageView iv_notifi;
    @BindView(R.id.iv_search_bar_right)
    ImageView iv_right;
    @BindView(R.id.et_classify)
    EditText et_classify;
    @BindView(R.id.textSwitcher)
    TextSwitchView textSwitcher;
    @BindView(R.id.iv_folatbutton)
    ImageView iv_floatingbutton;
    @BindView(R.id.sticky_scrollview)
    StickyScrollView scrollView;
    @BindView(R.id.seach_bar_framelayout)
    FrameLayout frameLayout;
    private String categoryId, brands;
    private int pager;
    private List<BaseDataListBean.DataBean> goodsList = new ArrayList<>();
    private MvpContact.Presenter presenter = new HomePresenterIml(this);
    private CommonAdapter<BaseDataListBean.DataBean> brandAndGoodsAdapter;
    private List<HomeBean.DataBean.CategoryBean> categoryBeanList;
    private List<BaseDataListBean.DataBean> tabList;
    private LoadingDialog loadingDialog;
    private String userId;
    private String saveUserId="00";
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
        iv_left.setImageResource(R.drawable.navbar_icon_scan);
        iv_right.setImageResource(R.drawable.navbar_icon_news);
        iv_right.setPadding(0, 0, 0, 5);
        recy_goods.addItemDecoration(new SpaceItemDecoration(10, 2));
        recy_track.setLayoutManager(new GridLayoutManager(getContext(), 5));

        iniEvent();
    }


    @Override
    protected BasePresenter bindPresenter() {
        return presenter;
    }

    @Override
    public void showData(Object o) {
        HomeBean bean = (HomeBean) o;
        initHeader(bean);
        initBottom(bean);
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", "-1");
        if("00".equals(saveUserId)){
            presenterGetData();
            Log.i("表示是第一次创建首页","aaa");
            return;
        }

        if(!saveUserId.equals(userId)){
            Log.i("首页用户id发生变化执行刷新", "aaa");
            pager=0;
            getBrandData();
        }else {
            Log.i("首页用户id没变化不用刷新","bbb");
        }
    }

    @Override
    protected void onHidden() {
        super.onHidden();
        saveUserId = userId;
        Log.i("首页fragment切换为不可见时保存数据",saveUserId);
    }


    @Override
    public void onPause() {
        super.onPause();
        saveUserId = userId;
    }

    /**
     * 获取数据
     */
    public void presenterGetData() {
        String url = Constant.baseUrl + "item/index.php?c=Home&m=Index&user_id=" + userId;
        presenter.PresenterGetData(url, null);
        Log.i("首页接口", url + "");
    }

    /**
     * tablayout点击事件
     */
    private void iniEvent() {

        classify_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                HomeBean.DataBean.CategoryBean categoryBean = categoryBeanList.get(tab.getPosition());
                //刷新品牌
                categoryId = categoryBean.getId();
                Log.i("根据上面点击id获取下面品牌", Constant.baseUrl + "item/index.php?c=Goods&m=BrandList" + "&id=" + categoryId);
                OkHttpUtils
                        .get()
                        .url(Constant.baseUrl + "item/index.php?c=Goods&m=BrandList")
                        .addParams("id", categoryId)
                        .tag(this)
                        .build()
                        .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(BaseDataListBean response, int id) {
                                tabList = response.getData();
                                setCustomTab(tabList);
                            }
                        });
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        brand_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                brands = tabList.get(tab.getPosition()).getId();
                pager = 0;
                showLoadingDialog();
                getBrandData();
                TextView tv = tab.getCustomView().findViewById(R.id.iv_layout_title);
                tv.setTextColor(Color.RED);

                Log.i("下面选中id", brands + "");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tv = tab.getCustomView().findViewById(R.id.iv_layout_title);
                tv.setTextColor(Color.BLACK);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        scrollView.setOnScrollListener(new StickyScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
             if (t > 1000) {
                    iv_floatingbutton.setVisibility(View.VISIBLE);
                } else {
                    iv_floatingbutton.setVisibility(View.INVISIBLE);
                }
             if(t==0){
                 iv_left.setVisibility(View.VISIBLE);
                 frameLayout.setVisibility(View.VISIBLE);
             }else {
                 iv_left.setVisibility(View.GONE);
                 frameLayout.setVisibility(View.GONE);
             }
            }
        });

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE: {
                        break;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        //当文本的measureheight 等于scroll滚动的长度+scroll的height
                        if (scrollView.getChildAt(0).getMeasuredHeight() <= scrollView.getScrollY() + scrollView.getHeight()) {
                            LoadMore();
                        }
                        break;
                    }
                }
                return false;
            }
        });


    }


    private void initBottom(HomeBean bean) {
        //自营
        List<HomeBean.DataBean.ProductTypeBean> selfBean = bean.getData().getProductType();
        Glide.with(getContext()).load(selfBean.get(0).getImg_url()).dontAnimate().into(iv_self1);
        Glide.with(getContext()).load(selfBean.get(1).getImg_url()).dontAnimate().into(iv_self2);
        //发现好店
        List<HomeBean.DataBean.FindShopBean> findShopBean = bean.getData().getFindShop();
        String img1 = findShopBean.get(0).getImg_url();
        String img2 = findShopBean.get(1).getImg_url();
        Glide.with(getContext()).load(img1).dontAnimate().into(iv_find_shop1);
        Glide.with(getContext()).load(img2).dontAnimate().into(iv_find_shop2);
        HomeBean.DataBean.FindShopBGBean bgBean = bean.getData().getFindShop_BG();
        Glide.with(getContext()).load(bgBean.getImg_url()).dontAnimate().into(iv_find_shop_bg);
        //商品tablayout
        categoryBeanList = bean.getData().getCategory();
        for (int i = 0; i < categoryBeanList.size(); i++) {
            String title = categoryBeanList.get(i).getCategory_name();
            classify_tabLayout.addTab(classify_tabLayout.newTab().setText(title));
        }
        //底部商品
        recy_goods.setLayoutManager(new GridLayoutManager(getContext(), 2));
        brandAndGoodsAdapter = new CommonAdapter<BaseDataListBean.DataBean>(getContext(), R.layout.recommend_layout, goodsList) {
            @Override
            protected void convert(ViewHolder holder, BaseDataListBean.DataBean dataBean, int position) {
                //图片
                int i = DeviceUtils.dip2px(MyApp.getContext(), 186);
                ImageView iv_brand = holder.getView(R.id.iv_recommend);
                Glide.with(MyApp.getContext()).load(dataBean.getImg_url()).override(i, i).into(iv_brand);
                //名字
                TextView tv_name = holder.getView(R.id.tv_recommend_title);
                tv_name.setMaxLines(3);
                tv_name.setLines(3);
                tv_name.setText(dataBean.getGoods_name());
                //价格
                TextView tv_price = holder.getView(R.id.tv_recommend_price);
                tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tv_price.setText(Html.fromHtml("¥ <big>" + dataBean.getPrice() + "</big>"));
                //销量
                TextView tv_sales = holder.getView(R.id.tv_recomment_sales);
                tv_sales.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tv_sales.setText("销量 " + dataBean.getSales());
            }
        };
        recy_goods.setAdapter(brandAndGoodsAdapter);
        brandAndGoodsAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String mainId = goodsList.get(position).getId();
                Intent intent = new Intent(getActivity(), GoodsXqActivity.class);
                intent.putExtra("mainId", mainId);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }
    //搜索
    @OnClick(R.id.et_classify)
    public void seach() {
        DeviceUtils.hideSystemSoftKeyBoard(getActivity(), et_classify);
        Intent intent=new Intent(getActivity(),SeachActivity.class);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), et_classify, "share");
        startActivity(intent, compat.toBundle());

    }
    //自营
    @OnClick(R.id.iv_self_produc1)
    public void self1() {
        Intent intent = new Intent(getActivity(), SelfGoodsActivity.class);
        intent.putExtra("list", (Serializable) categoryBeanList);
        startActivity(intent);
    }

    //贴牌
    @OnClick(R.id.iv_self_produc2)
    public void self2() {
        startActivity(new Intent(getActivity(), OemActivity.class));
    }

    //活动专区
    @OnClick(R.id.rl_home_pager_activity)
    public void activity() {
        startActivity(new Intent(getActivity(), ActivityEntrance.class));
    }

    //查看更多文章
    @OnClick(R.id.tv_more_article)
    public void more_article() {
        startActivity(new Intent(getActivity(), ArticleActivity.class));
    }

    //发现好店
    @OnClick(R.id.rl_home_find_shop)
    public void find() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        if (userId == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            return;
        }
        startActivity(new Intent(getActivity(), FindShopsActivity.class));
    }

    //快速回到顶部
    @OnClick(R.id.iv_folatbutton)
    public void goTop() {
        scrollView.fling(0);
        scrollView.smoothScrollTo(0, 0);
    }

    /**
     * 自定义tablayout
     *
     * @param beanList
     */
    private void setCustomTab(final List<BaseDataListBean.DataBean> beanList) {
        if (brand_tablayout.getTabCount() != 0) {
            brand_tablayout.removeAllTabs();
        }
        TabLayout.Tab tab = null;
        View view = null;
        int wh = DeviceUtils.dip2px(MyApp.getContext(), 45);
        for (int i = 0; i < beanList.size(); i++) {
            tab = brand_tablayout.newTab();
            view = LayoutInflater.from(getContext()).inflate(R.layout.iv_layout, null);
            BaseDataListBean.DataBean xBean = beanList.get(i);
            TextView tv = view.findViewById(R.id.iv_layout_title);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            ImageView iv = view.findViewById(R.id.iv_item);
            tv.setText(xBean.getTitle());
            Glide.with(MyApp.getContext()).load(xBean.getImg_url()).asBitmap().override(wh, wh).into(iv);
            tab.setCustomView(view);
            brand_tablayout.addTab(tab);
        }
    }

    /**
     * 首页底部商品
     */
    public void getBrandData() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("首页底部商品", Constant.baseUrl + "item/index.php?c=Goods&m=GoodsList" + "&cate=" + categoryId + "&brand=" + brands + "&page=" + pager + "&pagesize=10" + "&user_id=" + userId + "&sales=desc");
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Goods&m=GoodsList")
                .addParams("user_id", userId)
                .addParams("cate", categoryId)
                .addParams("page", String.valueOf(pager))
                .addParams("pagesize", "10")
                .addParams("brand", brands)
                .addParams("sales", "desc")
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        if (loadingDialog != null) {
                            loadingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        if (loadingDialog != null) {
                            loadingDialog.dismiss();
                        }
                        goodsList = response.getData();
                        brandAndGoodsAdapter.add(goodsList, true);

                        if (goodsList.size() > 0) {
                            pager++;
                        }

                    }
                });
    }


    private void initHeader(HomeBean bean) {
        //轮播图
        List<String> img = new ArrayList<>();
        final List<HomeBean.DataBean.IndexBannerBean> bannerBean = bean.getData().getIndexBanner();
        for (int i = 0; i < bannerBean.size(); i++) {
            String url = bannerBean.get(i).getImg_url();
            img.add(url);
        }
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置是否自动轮播（不设置则默认自动）
        banner.isAutoPlay(true);
        //设置轮播图片间隔时间（不设置默认为2000）
        banner.setDelayTime(3000);
        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        banner.setImageLoader(new BannerImageLoader());
        banner.setImages(img);
        banner.start();
        //5个按钮
        List<HomeBean.DataBean.BtnImgBean> buttonBeanList = bean.getData().getBtnImg();

        CommonAdapter<HomeBean.DataBean.BtnImgBean> ButtonAdapter = new CommonAdapter<HomeBean.DataBean.BtnImgBean>(MyApp.getContext(), R.layout.iv_layout, buttonBeanList) {
            @Override
            protected void convert(ViewHolder holder, HomeBean.DataBean.BtnImgBean btnImgBean, int position) {
                RelativeLayout parent = holder.getView(R.id.iv_layout_parent);
                int bt = DeviceUtils.dip2px(MyApp.getContext(), 15);
                int lr = DeviceUtils.dip2px(MyApp.getContext(), 10);
                parent.setPadding(lr, bt, lr, bt);
                //图片
                ImageView iv = holder.getView(R.id.iv_item);
                int width = DeviceUtils.dip2px(MyApp.getContext(), 45);
                int height = DeviceUtils.dip2px(MyApp.getContext(), 45);
                Glide.with(MyApp.getContext()).load(btnImgBean.getImg_url()).asBitmap().override(width, height).into(iv);
                //名字
                TextView tv = holder.getView(R.id.iv_layout_title);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                tv.setText(btnImgBean.getContent());
            }
        };
        recy_track.setAdapter(ButtonAdapter);
        ButtonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
                Intent intent = new Intent();
                if (userId == null) {
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    return;
                }
                switch (position) {
                    case 0:
                        sign();
                        break;
                    case 1:
                      /*  intent.setClass(getActivity(), VipHomePagerActivity.class);
                        startActivity(intent);*/
                        MyToast.makeTextAnim(MyApp.getContext(),"暂未开放",0,Gravity.CENTER,0,0).show();
                        break;
                    case 2:
                        minProgess();
                        break;
                    case 3:
                        intent.setClass(getActivity(), MyWalletActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent.setClass(getActivity(), LookOrderActivity.class);
                        intent.putExtra("type", "all");
                        intent.putExtra("index", 0);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        //活动背景
        List<HomeBean.DataBean.OnSaleBean> saleBean = bean.getData().getOnSale();
        Glide.with(getContext()).load(saleBean.get(0).getImg_url()).dontAnimate().into(iv_activity_bg);
        Glide.with(getContext()).load(saleBean.get(1).getImg_url()).dontAnimate().into(iv_activity1);
        Glide.with(getContext()).load(saleBean.get(2).getImg_url()).dontAnimate().into(iv_activity2);
        //头条内容
        HomeBean.DataBean.HotImageBean hotImageBean = bean.getData().getHotImage();
        Glide.with(getContext()).load(hotImageBean.getImg_url()).override(100, 350).into(iv_notifi);
        //跑马灯
        getTjArticle();
    }


    private void getTjArticle() {
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "messages/information.php?m=infolist")
                .addParams("title", "tj")
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, final int id) {
                        List<BaseDataListBean.DataBean> list = response.getData();
                        if (list.size() != 0) {
                            textSwitcher.setResources(list);
                            textSwitcher.setTextStillTime(3000);
                            textSwitcher.setListener(new TextSwitchView.clickListener() {
                                @Override
                                public void click(String title, String content) {
                                    Intent intent = new Intent(getActivity(), ArticleWebViewActivity.class);
                                    intent.putExtra("title", title);
                                    intent.putExtra("content", content);
                                    startActivity(intent);
                                }
                            });
                        }


                    }
                });
    }


    /**
     * 签到
     */
    private void sign() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        if (userId == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            return;
        }
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "item/index.php?c=Home&m=UserSignIn")
                .tag(this)
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        if (response.getStatus() == 0) {      //已签到
                            startActivity(new Intent(getActivity(), MyGoldActivity.class));
                        } else {                             //未签到
                            final SignDialogFragment sdf = new SignDialogFragment();
                            sdf.show(getFragmentManager(), "aaa");
                            sdf.setListener(new SignDialogFragment.Clicklistener() {
                                @Override
                                public void check() {
                                    startActivity(new Intent(getActivity(), MyGoldActivity.class));
                                    sdf.dismiss();
                                }
                            });
                        }
                    }
                });
    }

    /**
     * 跳转到小程序商家版
     */
    private void minProgess() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        IWXAPI api = WXAPIFactory.createWXAPI(MyApp.getContext(), Constant.AppID);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = "gh_2aa3f32c68d4"; // 填小程序原始id
        req.path = "/pages/Home/home?&platform=Android&user_id=" + userId;       //拉起小程序页面的可带参路径，不填默认拉起小程序首页
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
        api.sendReq(req);
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

    public void showLoadingDialog() {
        loadingDialog = new LoadingDialog();
        loadingDialog.setCancelable(false);
        loadingDialog.show(getFragmentManager(), "aaaa");
    }


    /**
     * 加载更多
     */
    private void LoadMore() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        showLoadingDialog();

        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("首页品牌商品加载更多", Constant.baseUrl + "item/index.php?c=Goods&m=GoodsList" + "&cate=" + categoryId +
                "&brand=" + brands + "&page=" + pager + "&pagesize=10" + "&sales=desc" + "&user_id=" + userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Goods&m=GoodsList")
                .addParams("user_id", userId)
                .addParams("cate", categoryId)
                .addParams("page", String.valueOf(pager))
                .addParams("pagesize", "10")
                .addParams("sales", "desc")
                .addParams("brand", brands)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (loadingDialog != null) {
                            loadingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        if (loadingDialog != null) {
                            loadingDialog.dismiss();
                        }
                        List<BaseDataListBean.DataBean> tempList = response.getData();
                        goodsList.addAll(tempList);
                        brandAndGoodsAdapter.add(goodsList, true);
                        if (tempList.size() != 0) {
                            pager++;
                        } else {
                            MyToast.makeTextAnim(MyApp.getContext(), "已全部加载完毕", 0, Gravity.CENTER, 0, 0).show();
                        }
                    }
                });
    }
}
