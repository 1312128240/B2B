package car.tzxb.b2b.fragments;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.SpaceItemDecoration;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CustomToast.MyToast;
import car.myview.MyScrollView;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.HomeBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.Interface.WindowFocusChang;
import car.tzxb.b2b.MainActivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.Presenter.HomePresenterIml;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Uis.LoginActivity;
import car.tzxb.b2b.Uis.MeCenter.MyGoldActivity;
import car.tzxb.b2b.Uis.Order.OrderStatusActivity;
import car.tzxb.b2b.Uis.Vip.VipHomePagerActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.LoadingDialog;
import car.tzxb.b2b.Views.DialogFragments.SignDialogFragment;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class HomeFragment extends MyBaseFragment implements MvpViewInterface, MyScrollView.OnScrollListener,
        WindowFocusChang, ViewTreeObserver.OnGlobalFocusChangeListener, View.OnTouchListener {
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
    @BindView(R.id.tv_headline)
    TextView tv_headerline;
    @BindView(R.id.iv_self_produc1)
    ImageView iv_self1;
    @BindView(R.id.iv_self_produc2)
    ImageView iv_self2;
    @BindView(R.id.home_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.home_scollview)
    MyScrollView scrollView;
    @BindView(R.id.rl_home_find_shop)
    RelativeLayout rl_find_shop;
    @BindView(R.id.iv_find_shop1)
    ImageView iv_find_shop1;
    @BindView(R.id.iv_find_shop2)
    ImageView iv_find_shop2;
    @BindView(R.id.iv_find_shop_bg)
    ImageView iv_find_shop_bg;
    @BindView(R.id.recy_home_brand)
    RecyclerView recy_brand;
    @BindView(R.id.recy_home_goods)
    RecyclerView recy_goods;
    @BindView(R.id.iv_app_notifi)
    ImageView iv_notifi;
    @BindView(R.id.iv_search_bar_right)
    ImageView iv_right;
    private String category, brands;
    private int bottom, pager;
    List<BaseDataListBean.DataBean> brandList = new ArrayList<>();
    MvpContact.Presenter presenter = new HomePresenterIml(this);
    private CommonAdapter<BaseDataListBean.DataBean> brandAndGoodsAdapter;
    private LoadingDialog dialog;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
        iv_left.setImageResource(R.drawable.navbar_icon_scan);
        iv_right.setImageResource(R.drawable.navbar_icon_news);
        iv_right.setPadding(0, 0, 0, 5);
        //获取数据
        String url = Constant.baseUrl + "item/index.php?c=Home&m=Index&user_id=1";
        presenter.PresenterGetData(url, null);
        //来获得宽度或者高度。这是获得一个view的宽度和高度的方法之一。
        // 这是一个注册监听视图树的观察者(observer)，在视图树的全局事件改变时得到通知。
        // ViewTreeObserver不能直接实例化，而是通过getViewTreeObserver()获得。
        scrollView.setOnScrollListener(this);
        //实现windowChange监听
        scrollView.getViewTreeObserver().addOnGlobalFocusChangeListener(this);
        scrollView.setOnTouchListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MainActivity mainActivity = (MainActivity) activity;
        mainActivity.setFocusChanged(this);
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
        //商品筛选
        final List<HomeBean.DataBean.CategoryBean> categoryBeanList = bean.getData().getCategory();
        for (int i = 0; i < categoryBeanList.size(); i++) {
            category = categoryBeanList.get(0).getTitle();
            String title = categoryBeanList.get(i).getTitle();
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                pager = 0;
                category = categoryBeanList.get(index).getTitle();
                brands = "";
                getBrandData();
                showLoadingDialog();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //品牌筛选
        final List<HomeBean.DataBean.BrandBean> brandBeanList = bean.getData().getBrand();
        recy_brand.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        CommonAdapter<HomeBean.DataBean.BrandBean> brandAdapter = new CommonAdapter<HomeBean.DataBean.BrandBean>(getContext(), R.layout.iv_layout, brandBeanList) {
            @Override
            protected void convert(ViewHolder holder, HomeBean.DataBean.BrandBean brandBean, int position) {
                RelativeLayout parent = holder.getView(R.id.iv_layout_parent);
                int top = DeviceUtils.dip2px(MyApp.getContext(), 15);
                int i = DeviceUtils.dip2px(MyApp.getContext(), 50);
                parent.setPadding(top, top, top, top);
                //图片
                ImageView iv = holder.getView(R.id.iv_item);
                Glide.with(getContext()).load(brandBean.getImg_url()).asBitmap().override(i, i).into(iv);
                //标题
                holder.setText(R.id.iv_layout_title, brandBean.getTitle());
            }
        };
        recy_brand.setAdapter(brandAdapter);
        brandAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                brands = brandBeanList.get(position).getId();
                pager = 0;
                getBrandData();
                showLoadingDialog();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        //底部品牌商品
        getBrandData();
        recy_goods.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recy_goods.addItemDecoration(new SpaceItemDecoration(10, 2));
        brandAndGoodsAdapter = new CommonAdapter<BaseDataListBean.DataBean>(getContext(), R.layout.recommend_layout, brandList) {
            @Override
            protected void convert(ViewHolder holder, BaseDataListBean.DataBean dataBean, int position) {
                //图片
                int i = DeviceUtils.dip2px(MyApp.getContext(), 186);
                ImageView iv_brand = holder.getView(R.id.iv_recommend);
                Glide.with(getContext()).load(dataBean.getImg_url()).override(i, i).into(iv_brand);
                //名字
                TextView tv_name = holder.getView(R.id.tv_recommend_title);
                tv_name.setMaxLines(3);
                tv_name.setText(dataBean.getGoods_name());

                //价格
                TextView tv_price = holder.getView(R.id.tv_recommend_price);
                tv_price.setText(Html.fromHtml("¥" + "<big>" + dataBean.getSeal_price() + "</big>"));
                //销量
                holder.setText(R.id.tv_recomment_sales, "销量 " + dataBean.getSales());
            }
        };
        recy_goods.setAdapter(brandAndGoodsAdapter);
        brandAndGoodsAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String mainId = brandList.get(position).getId();
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

    public void getBrandData() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("首页品牌商品", Constant.baseUrl + "item/index.php?c=Goods&m=GoodsList" + "&cate=" + category + "&brand=" + brands + "&page=" + pager + "&pagesize=10" + "&user_id=" + userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Goods&m=GoodsList")
                .addParams("user_id", userId)
                .addParams("cate", category)
                .addParams("page", String.valueOf(pager))
                .addParams("pagesize", "10")
                .addParams("brand", brands)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {

                        brandList = response.getData();
                        brandAndGoodsAdapter.add(brandList, true);

                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        if (brandList.size() > 0) {
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
        banner.setBannerStyle(Banner.NUM_INDICATOR);
        banner.setIndicatorGravity(Banner.RIGHT);
        //设置是否自动轮播（不设置则默认自动）
        banner.isAutoPlay(true);
        //设置轮播图片间隔时间（不设置默认为2000）
        banner.setDelayTime(3000);
        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        banner.setImages(img);
        //5个按钮
        List<HomeBean.DataBean.BtnImgBean> buttonBeanList = bean.getData().getBtnImg();
        recy_track.setLayoutManager(new GridLayoutManager(getContext(), 5));
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
                        sign(false);
                        break;
                    case 1:
                        intent.setClass(getActivity(), VipHomePagerActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        minProgess();
                        break;
                    case 3:
                        break;
                    case 4:
                        intent.setClass(getActivity(), OrderStatusActivity.class);
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
        String title = bean.getData().getHotActicle();
        Glide.with(getContext()).load(hotImageBean.getImg_url()).override(100, 350).into(iv_notifi);
        tv_headerline.setText(title);
    }

    /**
     * 签到
     */
    private void sign(boolean b) {
        if (b) {

        } else {
            final SignDialogFragment sdf = new SignDialogFragment();
            sdf.show(getFragmentManager(), "aaa");
            sdf.setListener(new SignDialogFragment.Clicklistener() {
                @Override
                public void check() {
                    Intent intent = new Intent(getActivity(), MyGoldActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    sdf.dismiss();
                }
            });
        }
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


    @Override
    public void onScroll(int scrollY) {
        int top = Math.max(scrollY, bottom);

        tabLayout.layout(0, top, tabLayout.getWidth(), top + tabLayout.getHeight());

    }


    @Override
    public void focus(boolean b) {
        if (b) {
            bottom = rl_find_shop.getBottom();
        }
    }

    @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        onScroll(scrollView.getScrollY());
    }

    public void showLoadingDialog() {
        dialog = new LoadingDialog();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "aaa");
    }

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
                View view = ((ScrollView) v).getChildAt(0);
                if (view.getMeasuredHeight() <= v.getScrollY() + v.getHeight()) {
                    // getBrandData();
                    showLoadingDialog();
                    LoadMore();
                }
                break;
            }
        }
        return false;
    }

    /**
     * 加载更多
     */
    private void LoadMore() {
        Log.i("首页品牌商品加载更多", Constant.baseUrl + "item/index.php?c=Goods&m=GoodsList" + "&cate=" + category + "&brand=" + brands + "&page=" + pager + "&pagesize=10");
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Goods&m=GoodsList")
                .addParams("user_id", userId)
                .addParams("cate", category)
                .addParams("page", String.valueOf(pager))
                .addParams("pagesize", "10")
                .addParams("brand", brands)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        List<BaseDataListBean.DataBean> tempList = response.getData();
                        brandList.addAll(tempList);
                        brandAndGoodsAdapter.add(brandList, true);
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        if (tempList.size() > 0) {
                            pager++;
                        } else {
                            MyToast.makeTextAnim(MyApp.getContext(), "我也是有底线的", 0, Gravity.CENTER, 0, 0).show();
                        }
                    }
                });
    }

}
