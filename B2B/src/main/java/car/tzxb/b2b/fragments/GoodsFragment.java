package car.tzxb.b2b.fragments;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CircleImageView.XCRoundRectImageView;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.Adapter.DetailsAdapter;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.Bean.GoodsXqBean;
import car.tzxb.b2b.Interface.GoodsXqInterface;
import car.tzxb.b2b.Interface.ScollListener;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Uis.LoginActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.PopWindow.ExplainPop;

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
    @BindView(R.id.tv_more_pj)
    TextView tv_more_pj;
    private String flag;
    private int y;


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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        goodsActivity = (GoodsXqActivity) activity;
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
            tv_collect.setBackground(getResources().getDrawable(R.drawable.bg1));
            Drawable drawable = getResources().getDrawable(R.drawable.list_icon_collection);
            tv_collect.setText("收藏");
            tv_collect.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            flag = "0";
        } else {
            tv_collect.setBackground(getResources().getDrawable(R.drawable.bg3));
            tv_collect.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            tv_collect.setText("已收藏");
            flag = "1";
        }

    }


    @Override
    public void getData(GoodsXqBean bean) {
        //商品详情头部
        initInfor(bean.getData().getGoods());
        //评价
        initEvaluate(bean.getData().getComment());
        //店铺信息
        initShop(bean.getData().getShop(), bean.getData().getHot());
        //底部详情图片
        initdetails(bean.getData().getProduct());

        //详情详情列表的高度
        int[] location = new int[2];
        tv_img.getLocationOnScreen(location);
        int x = location[0];
        y = location[1];

    }

    /**
     * 底部图片
     *
     * @param product
     */
    private void initdetails(List<GoodsXqBean.DataBean.ProductBean> product) {

        List<String> imgList = new ArrayList<>();
        for (int i = 0; i < product.size(); i++) {
            imgList.addAll(product.get(i).getContents_mobi());
        }
        Log.i("接收到数据了吗", imgList.size() + "");
        DetailsAdapter detailsAdapter = new DetailsAdapter(getContext(), imgList);

        lv_details.setAdapter(detailsAdapter);

    }

    public void changStatus(String s) {
        //是否收藏
        if ("0".equals(s)) {
            tv_collect.setBackground(getResources().getDrawable(R.drawable.bg1));
            Drawable drawable = getResources().getDrawable(R.drawable.list_icon_collection);
            tv_collect.setText("收藏");
            tv_collect.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        } else {
            tv_collect.setBackground(getResources().getDrawable(R.drawable.bg3));
            tv_collect.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            tv_collect.setText("已收藏");
        }
    }

    /**
     * 店铺信息
     *
     * @param shop
     */
    private void initShop(GoodsXqBean.DataBean.ShopBean shop, List<GoodsXqBean.DataBean.HotBean> hotBeanList) {
        Glide.with(this).load(shop.getShop_img()).asBitmap().into(xriv);
        tv_shop_name.setText(shop.getShop_name());
        tv_shop_city.setText(shop.getProvince() + shop.getCity() + shop.getArea());
        //门店是否收藏
        flag = shop.getCollect();
        changStatus(flag);
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
        tv_ev_num.setText("客户评价   (" + comment.getWhole() + ")");
        int hpl= comment.getGood()/comment.getGood()*100;
        tv_hpl.setText(Html.fromHtml("好评率 " + "<font color='#ff0000'>" + hpl+"%" + "</font>"));
        recy_ev.setLayoutManager(new LinearLayoutManager(getContext()));
        final List<GoodsXqBean.DataBean.CommentBean.EvaluteBean> commentBeanList = comment.getEvalute();
        if (comment.getWhole() > 1) {
            tv_more_pj.setVisibility(View.VISIBLE);
        } else {
            tv_more_pj.setVisibility(View.GONE);
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
        tv_more_pj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsActivity.setCurrent(1);
            }
        });


    }

    /**
     * 商品规格信息
     *
     * @param goods
     */
    private void initInfor(GoodsXqBean.DataBean.GoodsBean goods) {
        //-------------轮播图---------------------
        List<String> images = goods.getBanner();
        banner.setBannerStyle(Banner.NUM_INDICATOR);
        banner.setIndicatorGravity(Banner.RIGHT);
        //设置是否自动轮播（不设置则默认自动）
        banner.isAutoPlay(true);
        //设置轮播图片间隔时间（不设置默认为2000）
        banner.setDelayTime(3000);
        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        banner.setImages(images);
        //-----------价钱销量信息-------------------
        tv_sales.setText("月销量: " + goods.getSales());
        tv_type.setText(goods.getDealer());
        tv_goods_name.setText("\u3000\u3000" + goods.getTitle());
        tv_current_price.setText(Html.fromHtml("¥ " + "<big>" + goods.getPrice() + "</big>"));
        tv_cost_price.setText(Html.fromHtml("原价:¥" + goods.getM_price()));
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
