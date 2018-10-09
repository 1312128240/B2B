package car.tzxb.b2b.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.SpaceItemDecoration;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.BageView.BadgeView;
import car.myview.CircleImageView.CircleImageView;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.MyCenterBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Uis.HomePager.Wallet.MyWalletActivity;
import car.tzxb.b2b.Uis.LoginActivity;
import car.tzxb.b2b.Uis.MeCenter.BrowhistoryActivity;
import car.tzxb.b2b.Uis.MeCenter.CollectActivity;
import car.tzxb.b2b.Uis.MeCenter.IntegralShop.IntegralOneActivity;
import car.tzxb.b2b.Uis.MeCenter.MyAddressActivity;
import car.tzxb.b2b.Uis.MeCenter.MyGoldActivity;
import car.tzxb.b2b.Uis.MeCenter.SettingsActivity;
import car.tzxb.b2b.Uis.Order.LookOrderActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;


public class MyFragment extends MyBaseFragment implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rg_my_service)
    RadioGroup rg_service;
    @BindView(R.id.recycler_recommend)
    RecyclerView recyclerView;
    @BindView(R.id.rg_collect)
    RadioGroup rg_collect;
    @BindView(R.id.txt_user_name)
    TextView tv_username;
    @BindView(R.id.tv_all_order)
    TextView tv_all_order;
    @BindView(R.id.iv_my_setup)
    ImageView iv_setup;
    @BindView(R.id.ll_login_regist)
    LinearLayout ll_login_regist;
    @BindView(R.id.img_avatar)
    CircleImageView cv_headerImager;
    @BindViews({R.id.rb_yhq_number, R.id.rb_gold_number, R.id.rb_jf_number, R.id.rb_balance_number})
    List<RadioButton> PropertyViews;
    @BindViews({R.id.rb_collect_goods, R.id.rb_collect_shop, R.id.rb_browse_record})
    List<RadioButton> CollectViews;
    @BindView(R.id.recy_order_status)
    RecyclerView recy_status;
    @BindView(R.id.rg_coffers)
    RadioGroup rg_coffers;
    private MyCenterBean.DataBean.UserInfoBean userBean;
    private List<BaseDataListBean.DataBean> beanList = new ArrayList<>();
    private CommonAdapter<BaseDataListBean.DataBean> adapter;
    private String userId;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initData() {
        rg_service.setOnCheckedChangeListener(this);
        rg_collect.setOnCheckedChangeListener(this);
        rg_coffers.setOnCheckedChangeListener(this);
        initRecommend();
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }


    @Override
    protected void onVisible() {
        super.onVisible();
        Log.i("myFragment可见时再加载","bb");
        userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Judge();
        Guess();
    }

    /**
     * 获取个人信息
     */
    private void Judge() {
        Log.i("我的个人中心", Constant.baseUrl + "item/index.php?c=Home&m=MyCenter&user_id=" + userId);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "item/index.php?c=Home&m=MyCenter")
                .tag(this)
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<MyCenterBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("走错误", e.toString());
                        notLogin();
                    }

                    @Override
                    public void onResponse(MyCenterBean response, int id) {
                        hasLogin(response);
                    }
                });

    }

    /**
     * 猜你在找
     */
    public void Guess() {
        Log.i("我的界面猜你在找", Constant.baseUrl + "item/index.php?c=Goods&m=UserLike&pagesize=10&page=0&user_id=" + userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Goods&m=UserLike&pagesize=10&page=0")
                .addParams("user_id", userId)
                .addParams("sales", "desc")
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {


                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        beanList = response.getData();
                        adapter.add(beanList, true);
                    }
                });
    }


    /**
     * 已登录
     *
     * @param response
     */
    private void hasLogin(MyCenterBean response) {
        userBean = response.getData().getUserInfo();
        ll_login_regist.setVisibility(View.INVISIBLE);
        tv_username.setVisibility(View.VISIBLE);
        if ("".equals(userBean.getNackname())) {
            tv_username.setText("暂无昵称");
        } else {
            tv_username.setText(userBean.getNackname());
        }

        Glide.with(MyApp.getContext()).load(userBean.getHead_img()).asBitmap().error(R.mipmap.my_icon_dhi).into(cv_headerImager);
        //收藏
        List<Integer> scList = response.getData().getUserCollect();
        CollectViews.get(0).setText(scList.get(0) + "\n收藏商品");
        CollectViews.get(1).setText(scList.get(1) + "\n收藏店铺");
        CollectViews.get(2).setText(scList.get(2) + "\n浏览记录");
        //金库
        List<Double> jkList = response.getData().getMyProperty();
        PropertyViews.get(0).setText(Html.fromHtml(jkList.get(0) + "  张" + "<br>" + "优惠券"));
        PropertyViews.get(1).setText(Html.fromHtml(jkList.get(1) + "  个" + "<br>" + "金币"));
        PropertyViews.get(2).setText(Html.fromHtml(jkList.get(2) + "  分" + "<br>" + "积分"));
        PropertyViews.get(3).setText(Html.fromHtml(jkList.get(3) + "  元" + "<br>" + "余额"));

        //订单状态
        List<Integer> orderNumList = response.getData().getOrderNumber();
        initOrderRecy(orderNumList);
    }

    /**
     * 未登录
     */
    private void notLogin() {
        ll_login_regist.setVisibility(View.VISIBLE);
        tv_username.setVisibility(View.INVISIBLE);
        cv_headerImager.setImageResource(R.mipmap.my_icon_dhi);
        //收藏
        CollectViews.get(0).setText("0" + "\n收藏商品");
        CollectViews.get(1).setText("0" + "\n收藏店铺");
        CollectViews.get(2).setText("0" + "\n浏览记录");
        //金库
        PropertyViews.get(0).setText(Html.fromHtml("0  张" + "<br>" + "优惠券"));
        PropertyViews.get(1).setText(Html.fromHtml("0  个" + "<br>" + "金币"));
        PropertyViews.get(2).setText(Html.fromHtml("0  分" + "<br>" + "积分"));
        PropertyViews.get(3).setText(Html.fromHtml("0  元" + "<br>" + "余额"));
        //订单数
        List<Integer> numList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            numList.add(0);
        }
        initOrderRecy(numList);
    }

    private void initOrderRecy(List<Integer> orderNumList) {
        final String[] str = {"待付款", "待发货", "待收货", "待评价", "退款/售后"};
        final int img[] = {R.mipmap.my_icon_payment, R.mipmap.my_icon_pd, R.mipmap.my_icon_gtbr, R.mipmap.my_icon_tbe, R.mipmap.my_icon_service};
        recy_status.setLayoutManager(new GridLayoutManager(getContext(), 5));
        CommonAdapter<Integer> orderStatus = new CommonAdapter<Integer>(MyApp.getContext(), R.layout.order_status_item, orderNumList) {
            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                LinearLayout.LayoutParams parasm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout parent = holder.getView(R.id.order_item_parent);
                parent.setLayoutParams(parasm);
                parent.setGravity(Gravity.CENTER);
                int wh=DeviceUtils.dip2px(MyApp.getContext(),25);
                //图片
                Glide.with(getContext()).load(img[position]).override(wh,wh).into((ImageView) holder.getView(R.id.iv_order));
                //标题
                TextView tv = holder.getView(R.id.tv_order_title);
                if (position == 2) {
                    tv.setPadding(0, 8, 0, 0);
                }
                tv.setText(str[position]);

                //数量
                BadgeView bv = holder.getView(R.id.order_number_bv);
                bv.setBackground(getResources().getDrawable(R.drawable.circle_bg2));
                bv.setTextColor(Color.RED);
                bv.setTypeface(Typeface.DEFAULT);
                bv.setText(integer + "");
            }
        };
        recy_status.setAdapter(orderStatus);
        //查看订单

        orderStatus.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
                if (userId == null) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    return;
                }
                Intent intent = new Intent(getActivity(), LookOrderActivity.class);
                if (position == 4) {
                    position = -1;
                }
                intent.putExtra("index", position + 1);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }


    /**
     * 登录或注册
     */
    @OnClick({R.id.tv_login, R.id.tv_regist})
    public void login() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    private void initRecommend() {
        recyclerView.setLayoutManager(new GridLayoutManager(MyApp.getContext(),2));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10, 2));
        adapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(), R.layout.recommend_layout, beanList) {
            @Override
            protected void convert(ViewHolder holder, BaseDataListBean.DataBean bean, int position) {
                //图片
                ImageView iv = holder.getView(R.id.iv_recommend);
                int i = DeviceUtils.dip2px(MyApp.getContext(), 186);
                Glide.with(MyApp.getContext()).load(bean.getImg_url()).override(i, i).into(iv);
                holder.setText(R.id.tv_recommend_title, bean.getShop_name());
                //名字
                holder.setText(R.id.tv_recommend_title, bean.getGoods_name());
                //价格
                TextView tv_price = holder.getView(R.id.tv_recommend_price);
                tv_price.setText(Html.fromHtml("¥ <big>" + bean.getPrice() + "</big>"));
                //销量
                TextView tv_sales = holder.getView(R.id.tv_recomment_sales);
                tv_sales.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tv_sales.setText("销量 " + bean.getSales());
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BaseDataListBean.DataBean bean = beanList.get(position);
                Intent intent = new Intent(getActivity(), GoodsXqActivity.class);
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
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        RadioButton rb = radioGroup.findViewById(i);
        rb.setChecked(false);
        if (userId == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            return;
        }
        Intent intent = new Intent();
        switch (i) {
            case R.id.rb_collect_goods:
                intent.setClass(getActivity(), CollectActivity.class);
                intent.putExtra("index", 0);
                startActivity(intent);
                break;
            case R.id.rb_collect_shop:
                intent.setClass(getActivity(), CollectActivity.class);
                intent.putExtra("index", 1);
                startActivity(intent);
                break;
            case R.id.rb_browse_record:
                intent.setClass(getActivity(), BrowhistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.rb_address:
                intent.setClass(getActivity(), MyAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.rb_gold_number:
                intent.setClass(getActivity(), MyGoldActivity.class);
                startActivity(intent);
                break;
            case R.id.rb_balance_number:
                intent.setClass(getActivity(), MyWalletActivity.class);
                startActivity(intent);
                break;
            case R.id.rb_my_kf:
                String phoneNo = "0755-23732254";
                Intent action = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo));
                action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(action);
                break;
          case R.id.rb_jfsc:
                intent.setClass(getActivity(), IntegralOneActivity.class);
                startActivity(intent);
                break;
            default:
                MyToast.makeTextAnim(MyApp.getContext(), "暂未开放", 0, Gravity.CENTER, 0, 0).show();
                break;
        }

    }


    //查看全部订单
    @OnClick(R.id.tv_all_order)
    public void all_order() {
        if (userId == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            return;
        }
        Intent intent = new Intent(getActivity(), LookOrderActivity.class);
        intent.putExtra("index", 0);
        startActivity(intent);
    }

    //设置
    @OnClick(R.id.iv_my_setup)
    public void setup() {
        if (userId == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            return;
        }
        startActivity(new Intent(getActivity(),SettingsActivity.class));
    }


}
