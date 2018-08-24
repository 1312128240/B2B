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
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.google.android.flexbox.FlexboxLayout;
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
import car.myview.FlexRadioGroupPackage.FlexRadioGroup;
import car.tzxb.b2b.Adapter.DetailsAdapter;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.GoodsXqBean;
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

public class GoodsFragment extends MyBaseFragment implements ScollListener {
    @BindView(R.id.xq_banner)
    Banner banner;
    @BindView(R.id.tv_xq_goods_shop_collect)
    TextView tv_collect;
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
    NestedScrollView scrollView;
    @BindView(R.id.lv_details)
    ListView lv_details;
    @BindView(R.id.tv_xq_img)
    TextView tv_img;
    @BindView(R.id.ll_all_pj)
    LinearLayout ll_all_pj;
    @BindView(R.id.rg_gg)
    FlexRadioGroup rg_gg;
    @BindView(R.id.rl_shop)
    RelativeLayout rl_shoplayout;
    @BindView(R.id.ll_discounts)
    LinearLayout ll_discounts;
    @BindView(R.id.recy_discounts)
    RecyclerView recyclerView_discounts;
    @BindView(R.id.tv_miaoshu)
    TextView tv_ms;
    public GoodsXqActivity goodsXqActivity;
    private String flag;
    private String shopId;
    private GoodsXqBean.DataBean xqBean;
    private   ClickListener listener;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_goods;
    }

    @Override
    public void initData() {
        //商品详情头部
        initInfor(xqBean.getGoods());
        //评价
        initEvaluate(xqBean.getComment());
        //店铺信息
        initShop(xqBean.getShop(), xqBean.getHot());
        //底部详情图片
        initdetails(xqBean.getProduct());
    }




    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        goodsXqActivity = (GoodsXqActivity) activity;

        xqBean = goodsXqActivity.getResultBean();

        goodsXqActivity.SetScollTo(this);

        listener= (ClickListener) getActivity();

    }


    @OnClick(R.id.tv_xq_goods_shop_collect)
    public void collect() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        if (userId == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
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


    /**
     * 底部图片和商品规格
     * @param product
     */
    private void initdetails(final List<GoodsXqBean.DataBean.ProductBean> product) {

        List<String> ggList=new ArrayList<>();

        if(product.size()!=0){

            for (int i = 0; i <product.size(); i++) {
                GoodsXqBean.DataBean.ProductBean productBean=product.get(i);
                //取出规格颜色
                ggList.add(productBean.getColor_name()+productBean.getNetwork_name());
            }
            Log.i("商品规格",ggList.size()+"");
            //规格信息自定义radiobutton
            custRadioButton(ggList,product);
            //适配底部图片
            List<String> imgUrl=product.get(0).getContents_mobi();
            DetailsAdapter detailsAdapter = new DetailsAdapter(MyApp.getContext(), imgUrl);
            lv_details.setAdapter(detailsAdapter);
        }


    }
    /**
     * 自定义规格radioButton
     */
    public void custRadioButton(List<String> ggList, final List<GoodsXqBean.DataBean.ProductBean> product){
         RadioButton rb=null;
         int minWidth=DeviceUtils.dip2px(MyApp.getContext(),80);
         int height=DeviceUtils.dip2px(MyApp.getContext(),25);
         FlexboxLayout.LayoutParams layoutParams=new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
        layoutParams.setMargins(0,0,15,15);
        for (int i = 0; i <ggList.size() ; i++) {
            rb=new RadioButton(MyApp.getContext());
            rb.setLayoutParams(layoutParams);
            rb.setGravity(Gravity.CENTER);
            rb.setMinWidth(minWidth);
            rb.setMinHeight(height);
            rb.setButtonDrawable(null);
            rb.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            String gg=ggList.get(i);
            if(!"".equals(gg)){
                rb.setText(gg);
            }else {
                rb.setText("默认");
            }
            rb.setId(i);
            rb.setBackground(MyApp.getContext().getResources().getDrawable(R.drawable.gg_select));
            rb.setTextColor(MyApp.getContext().getResources().getColorStateList(R.color.tv_color2));
            rg_gg.addView(rb);
        }
        Log.i("添加进去没有",rg_gg.getChildCount()+"");
        rg_gg.setOnCheckedChangeListener(new FlexRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@IdRes int checkedId) {
                //listterner.process(checkedId);
                listener.checkId(checkedId);
                GoodsXqBean.DataBean.ProductBean productBean=product.get(checkedId);
                //切换轮播图
                viewpager(productBean.getImgs());
                //价钱
                tv_current_price.setText(Html.fromHtml("¥ " + "<big>"+productBean.getSeal_price()+"</big>"));
                tv_cost_price.setText(Html.fromHtml("原价:¥" + productBean.getMarket_price()));
                tv_cost_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
                //优惠信息
                List<GoodsXqBean.DataBean.ProductBean.PromotionBean>  promotionBeanList = productBean.getPromotion();
                if(promotionBeanList.size()!=0){
                    ll_discounts.setVisibility(View.VISIBLE);
                    initDiscounts(promotionBeanList);
                }else {
                    ll_discounts.setVisibility(View.GONE);
                }
                //描述
                String ms=productBean.getMiaoshu();
                if(!"".equals(ms)&&ms!=null){
                    tv_ms.setVisibility(View.VISIBLE);
                    tv_ms.setText(ms);
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
                dp.showPow(scrollView);
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
                goodsXqActivity.setTabSelect(2);
            }
        });
    }

    /**
     * 商品参数信息
     * @param goods
     */
    private void initInfor(GoodsXqBean.DataBean.GoodsBean goods) {
        //价钱销量信息
        tv_sales.setText("月销量: " + goods.getSales());
        tv_type.setBackground(MyApp.getContext().getResources().getDrawable(R.drawable.bg7));
        tv_type.setText(goods.getDealer());
        tv_goods_name.setText("\u3000\u3000" + goods.getTitle());
        tv_city.setText(goods.getProvince() + goods.getCity() + goods.getArea());
        //商品收藏
        goodsXqActivity.collect(goods.getCollect());
    }



    //说明弹窗
    @OnClick(R.id.ll_goods_xq_explain)
    public void explain() {
        ExplainPop ep = ExplainPop.getmInstance(MyApp.getContext());
        DeviceUtils.showPopWindow(scrollView,ep);
    }

    @Override
    public void scollLv() {

        scrollView.fling(0);
        scrollView.smoothScrollTo(0,1600);
    }

    @Override
    public void scollTop() {
        scrollView.fling(0);
        scrollView.smoothScrollTo(0, 0);
    }




    public interface  ClickListener{
        void checkId(int position);
    }

}
