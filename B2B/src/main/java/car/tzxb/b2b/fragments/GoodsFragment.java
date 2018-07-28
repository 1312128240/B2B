package car.tzxb.b2b.fragments;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CircleImageView.XCRoundRectImageView;
import car.myview.CustomToast.MyToast;
import car.myview.RadioGroupEx;
import car.tzxb.b2b.Adapter.DetailsAdapter;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.GoodsXqBean;
import car.tzxb.b2b.Interface.GoodsXqInterface;
import car.tzxb.b2b.Interface.ScollListener;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Uis.LoginActivity;
import car.tzxb.b2b.Util.BannerImageLoader;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.PopWindow.DiscountsPop;
import car.tzxb.b2b.Views.PopWindow.ExplainPop;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class GoodsFragment extends MyBaseFragment implements GoodsXqInterface, ScollListener {
    @BindView(R.id.xq_banner)
    Banner banner;
    @BindView(R.id.tv_xq_goods_shop_collect)
    TextView tv_collect;
    GoodsXqActivity goodsActivity;
    @BindView(R.id.tv_xq_goods_name)
    TextView tv_goods_name;
    @BindView(R.id.tv_xq_goods_current_price)
    TextView tv_current_price;
    @BindView(R.id.tv_xq_goods_cost_price)
    TextView tv_cost_price;
    @BindView(R.id.tv_goods_xq_sales)
    TextView tv_sales;
    @BindView(R.id.tv_goods_xq_type)
    TextView tv_type;
    @BindView(R.id.tv_goods_xq_evaluate_hpl)
    TextView tv_hpl;
    @BindView(R.id.tv_goods_xq_evaluate_num)
    TextView tv_ev_num;
    @BindView(R.id.tv_goods_xq_city)
    TextView tv_city;
    @BindView(R.id.iv_goods_xq_shop)
    XCRoundRectImageView xriv;
    @BindView(R.id.tv_goods_xq_shop_city)
    TextView tv_shop_city;
    @BindView(R.id.tv_goods_xq_shop_name)
    TextView tv_shop_name;
    @BindView(R.id.recy_hot)
    RecyclerView recy_hot;
    @BindView(R.id.recy_ev)
    RecyclerView recy_ev;
    @BindView(R.id.goods_xq_parent)
    RelativeLayout parent;
    @BindView(R.id.lv_details)
    ListView lv_details;
    @BindView(R.id.xq_nestedview)
    NestedScrollView scrollView;
    @BindView(R.id.tv_xq_img)
    TextView tv_img;
    @BindView(R.id.ll_all_pj)
    LinearLayout ll_all_pj;
    @BindView(R.id.rg_gg)
    RadioGroupEx rg_gg;
    @BindView(R.id.rl_shop)
    RelativeLayout rl_shoplayout;
    @BindView(R.id.ll_discounts)
    LinearLayout ll_discounts;
    @BindView(R.id.recy_discounts)
    RecyclerView recyclerView_discounts;
    private String flag;
    private int y;
    private String shopId;



    @Override
    public int getLayoutResId() {
        return R.layout.fragment_goods;
    }

    @Override
    public void initData() {
        goodsActivity.setDataSource(this);
        goodsActivity.scoll(this);
    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
    // 2.1 定义用来与外部activity交互，获取到宿主activity
    private FragmentInteraction listterner;

    // 1 定义了所有activity必须实现的接口方法
    public interface FragmentInteraction {
        void process(int checkId);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        goodsActivity = (GoodsXqActivity) activity;

        if(activity instanceof FragmentInteraction) {
            listterner = (FragmentInteraction)activity; // 2.2 获取到宿主activity并赋值
        } else{
            throw new IllegalArgumentException("activity must implements FragmentInteraction");
        }
    }


    @OnClick(R.id.tv_xq_goods_shop_collect)
    public void collect() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        if (userId == null) {
            Intent intent = new Intent(goodsActivity, LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(goodsActivity).toBundle());
            return;
        }
        //是否收藏
        if (!"0".equals(flag)) {
            tv_collect.setBackground(MyApp.getContext().getResources().getDrawable(R.drawable.bg1));
            Drawable drawable =MyApp.getContext(). getResources().getDrawable(R.drawable.list_icon_collection);
            tv_collect.setText("收藏");
            tv_collect.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            flag = "0";
        } else {
            tv_collect.setBackground(MyApp.getContext().getResources().getDrawable(R.drawable.bg3));
            tv_collect.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            tv_collect.setText("已收藏");
            flag = "1";
        }
        OkHttpUtils
                .get()
                .url(Constant.baseUrl+"item/index.php?c=Home&m=CollectShopUser")
                .tag(this)
                .addParams("user_id",userId)
                .addParams("shop_id",shopId)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                       MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0,Gravity.CENTER,0,0).show();
                    }
                });

    }


    @Override
    public void getData(GoodsXqBean bean) {
       GoodsXqBean.DataBean xqBean = bean.getData();
        //商品详情头部
        initInfor(xqBean.getGoods());
        //评价
        initEvaluate(xqBean.getComment());
        //店铺信息
        initShop(xqBean.getShop(), xqBean.getHot());
        //底部详情图片
        initdetails(xqBean.getProduct());
        //详情详情列表的高度
        int[] location = new int[2];
        tv_img.getLocationOnScreen(location);
        int x = location[0];
        y = location[1];

    }

    /**
     * 底部图片和商品规格
     * @param product
     */
    private void initdetails(final List<GoodsXqBean.DataBean.ProductBean> product) {

        List<String> imgList = new ArrayList<>();
        List<String> ggList=new ArrayList<>();
        for (int i = 0; i < product.size(); i++) {
            GoodsXqBean.DataBean.ProductBean productBean=product.get(i);
            //取出图片
            imgList.addAll(productBean.getContents_mobi());
            //取出规格颜色
            ggList.add(productBean.getColor_name()+productBean.getNetwork_name());
        }
         //规格信息自定义radiobutton
        custRadioButton(ggList,product);
        //适配底部图片
        DetailsAdapter detailsAdapter = new DetailsAdapter(MyApp.getContext(), imgList);
        lv_details.setAdapter(detailsAdapter);
    }
    /**
     * 自定义规格radioButton
     */
    public void custRadioButton(List<String> ggList, final List<GoodsXqBean.DataBean.ProductBean> product){
        RadioButton rb=null;
        int minWidth=DeviceUtils.dip2px(MyApp.getContext(),80);
        int height=DeviceUtils.dip2px(MyApp.getContext(),25);
        RadioGroup.LayoutParams layoutParams=new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
        layoutParams.setMargins(0,0,20,20);
        for (int i = 0; i <ggList.size() ; i++) {
            rb=new RadioButton(getContext());
            rb.setLayoutParams(layoutParams);
            rb.setGravity(Gravity.CENTER);
            rb.setMinWidth(minWidth);
            rb.setMinHeight(height);
            rb.setButtonDrawable(null);
            rb.setPadding(30,0,30,0);
            rb.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            String gg=ggList.get(i);
            if(!"".equals(gg)){
                rb.setText(gg);
            }else {
                rb.setText("默认");
            }
            rb.setId(i);
            rb.setBackground(getContext().getResources().getDrawable(R.drawable.gg_select));
            rb.setTextColor(getContext().getResources().getColorStateList(R.color.tv_color2));
            rg_gg.addView(rb);
        }

        rg_gg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                listterner.process(checkedId);
                //切换轮播图
                List<String> imgList=product.get(checkedId).getImgs();
                viewpager(imgList);
                //优惠信息
               List<GoodsXqBean.DataBean.ProductBean.PromotionBean>  promotionBeanList = product.get(checkedId).getPromotion();
               if(promotionBeanList.size()!=0){
                    ll_discounts.setVisibility(View.VISIBLE);
                    initDiscounts(promotionBeanList);
                }else {
                    ll_discounts.setVisibility(View.GONE);
                }

            }
        });
        RadioButton firstButton= (RadioButton) rg_gg.getChildAt(0);
        firstButton.setChecked(true);
    }

    /**
     * 优惠信息
     * @param
     */
    private void initDiscounts(final List<GoodsXqBean.DataBean.ProductBean.PromotionBean>  promotionBeanList) {
        recyclerView_discounts.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> titlelist=new ArrayList<>();
        for (int i = 0; i <promotionBeanList.size() ; i++) {
            StringBuilder sb=new StringBuilder();
            GoodsXqBean.DataBean.ProductBean.PromotionBean promotionBan=promotionBeanList.get(i);
            String title=promotionBan.getTitle();
            List<GoodsXqBean.DataBean.ProductBean.PromotionBean.GiftBean> giftBeanList=promotionBan.getGift();
            for (int j = 0; j <giftBeanList.size() ; j++) {
                GoodsXqBean.DataBean.ProductBean.PromotionBean.GiftBean giftBean=giftBeanList.get(j);
                sb.append(giftBean.getZp_title());
            }
            String content=title+sb;
            titlelist.add(content);
        }
        CommonAdapter<String> promotionBeanCommonAdapter= new CommonAdapter<String>(MyApp.getContext(),R.layout.tv_item,titlelist) {
                    @Override
                    protected void convert(ViewHolder holder, String sb, int position) {
                        TextView tv=holder.getView(R.id.tv_item);
                        tv.setMaxLines(1);
                        tv.setEllipsize(TextUtils.TruncateAt.END);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                        tv.setTextColor(Color.parseColor("#000000"));
                        tv.setText(sb);
                    }
                };
        recyclerView_discounts.setAdapter(promotionBeanCommonAdapter);
        promotionBeanCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                DiscountsPop dp=new DiscountsPop(MyApp.getContext(),promotionBeanList);
                dp.showPow(parent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    /**
     * 轮播图
     */
    public void viewpager(List<String> imgList){
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置是否自动轮播（不设置则默认自动）
        banner.isAutoPlay(true);
        //设置轮播图片间隔时间（不设置默认为2000）
        banner.setDelayTime(3000);
        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        banner.setImageLoader(new BannerImageLoader());
        banner.setImages(imgList);
        banner.start();
    }

    /**
     * 切换收藏状态
     * @param s
     */
    public void changStatus(String s) {
        //是否收藏
        if ("0".equals(s)) {
            tv_collect.setBackground(MyApp.getContext().getResources().getDrawable(R.drawable.bg1));
            Drawable drawable = MyApp.getContext().getResources().getDrawable(R.drawable.list_icon_collection);
            tv_collect.setText("收藏");
            tv_collect.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        } else {
            tv_collect.setBackground(MyApp.getContext().getResources().getDrawable(R.drawable.bg3));
            tv_collect.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            tv_collect.setText("已收藏");
        }
    }

    /**
     * 店铺信息
     * 如果shopId为12则不显示
     * @param shop
     */
    private void initShop(GoodsXqBean.DataBean.ShopBean shop, List<GoodsXqBean.DataBean.HotBean> hotBeanList) {
        shopId = shop.getID();
        if("12".equals(shopId)){
            rl_shoplayout.setVisibility(View.GONE);
        }else {
            //门店是否收藏
            Glide.with(MyApp.getContext()).load(shop.getShop_img()).asBitmap().into(xriv);
            tv_shop_name.setText(shop.getShop_name());
            tv_shop_city.setText(shop.getProvince() + shop.getCity() + shop.getArea());
            flag = shop.getCollect();
            changStatus(flag);
        }

        recy_hot.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        CommonAdapter<GoodsXqBean.DataBean.HotBean> adapter = new CommonAdapter<GoodsXqBean.DataBean.HotBean>(MyApp.getContext(), R.layout.common_item2, hotBeanList) {
            @Override
            protected void convert(ViewHolder holder, GoodsXqBean.DataBean.HotBean hotBean, int position) {
                int width = DeviceUtils.dip2px(MyApp.getContext(), 110);
                int heigh = DeviceUtils.dip2px(MyApp.getContext(), 110);
                RelativeLayout parent = holder.getView(R.id.common_item2_parent);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 0, 0, 0);
                parent.setLayoutParams(params);
                //图片
                ImageView iv_hot = holder.getView(R.id.iv_goods);
                iv_hot.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getContext()).load(hotBean.getImg_url()).asBitmap().centerCrop().override(width, heigh).into(iv_hot);
                //商品名字
                holder.setText(R.id.tv_goods_item_title, hotBean.getGoods_name());
                //价格
                TextView tv_price = holder.getView(R.id.tv_goods_item_price);
                tv_price.setText(Html.fromHtml("¥" + "<big>" + hotBean.getSeal_price() + "</big>"));
            }
        };
        recy_hot.setAdapter(adapter);
    }

    /**
     * 评论部份
     *
     * @param comment
     */

    private void initEvaluate(final GoodsXqBean.DataBean.CommentBean comment) {
        //好评率
        tv_ev_num.setText("客户评价   (" + comment.getWhole() + ")");
        if(comment.getWhole()==0){
            tv_hpl.setText(Html.fromHtml("好评率   <font color='#ff0000'>" +"0%" + "</font>"));
        }else {
            int hpl= comment.getGood()/comment.getWhole()*100;
            tv_hpl.setText(Html.fromHtml("好评率 " + "<font color='#ff0000'>" + hpl+"%" + "</font>"));
        }
       //底部评论
        recy_ev.setLayoutManager(new LinearLayoutManager(getContext()));
        final List<GoodsXqBean.DataBean.CommentBean.EvaluteBean> commentBeanList = comment.getEvalute();
        if (comment.getWhole() > 1) {
            ll_all_pj.setVisibility(View.VISIBLE);
        } else {
            ll_all_pj.setVisibility(View.GONE);
        }

        CommonAdapter<GoodsXqBean.DataBean.CommentBean.EvaluteBean> commonAdapter = new CommonAdapter<GoodsXqBean.DataBean.CommentBean.EvaluteBean>(MyApp.getContext(), R.layout.ev_item, commentBeanList) {
            @Override
            protected void convert(ViewHolder holder, GoodsXqBean.DataBean.CommentBean.EvaluteBean commentBean, int position) {
                //头像
                final int i = DeviceUtils.dip2px(MyApp.getContext(), 25);
                ImageView iv = holder.getView(R.id.civ_ev);
                Glide.with(MyApp.getContext()).load(commentBean.getHead_img()).override(i, i).into(iv);
                //名字
                holder.setText(R.id.tv_ev_title, commentBean.getUser_name());
                //时间
                holder.setText(R.id.tv_ev_time, commentBean.getAdd_time());
                //评论内容
                holder.setText(R.id.tv_ev_content, commentBean.getRemark());
            }
        };
        recy_ev.setAdapter(commonAdapter);
        ll_all_pj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsActivity.setCurrent(1);
            }
        });


    }

    /**
     * 商品参数信息
     *
     * @param goods
     */
    private void initInfor(GoodsXqBean.DataBean.GoodsBean goods) {
        //轮播
     //   viewpager(goods.getBanner());
        //-----------价钱销量信息-------------------
        tv_sales.setText("月销量: " + goods.getSales());
        tv_type.setText(goods.getDealer());
        tv_goods_name.setText("\u3000\u3000" + goods.getTitle());
        //现价区间
        tv_current_price.setText(Html.fromHtml("¥ " + "<big>" +goods.getPrice()+"</big>"));
        //原价区间
        tv_cost_price.setText(Html.fromHtml("原价:¥" + goods.getMin_market_price()+"-"+goods.getMax_market_price()));
        tv_cost_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        tv_city.setText(goods.getProvince() + goods.getCity() + goods.getArea());
        //商品收藏
        goodsActivity.collect(goods.getCollect());
    }



    //说明弹窗
    @OnClick(R.id.ll_goods_xq_explain)
    public void explain() {
        ExplainPop ep = ExplainPop.getmInstance(MyApp.getContext());
        ep.show(parent);
    }

    @Override
    public void scollLv() {
        scrollView.fling(0);
        scrollView.smoothScrollTo(0, y);
    }

    @Override
    public void scollTop() {
        scrollView.fling(0);
        scrollView.smoothScrollTo(0, 0);
    }

}
