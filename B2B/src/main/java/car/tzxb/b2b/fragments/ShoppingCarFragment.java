package car.tzxb.b2b.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CustomToast.MyToast;
import car.myview.SpringView.SpringView;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.ShopCarBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.MainActivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.Presenter.ShoppingCarPresenterIml;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.Order.OrderActivity;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.PopWindow.Modify_DiscountsPop;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class ShoppingCarFragment extends MyBaseFragment implements MvpViewInterface, CheckBox.OnCheckedChangeListener {

    MvpContact.Presenter presenter = new ShoppingCarPresenterIml(this);

    @BindView(R.id.tv_actionbar_back)
    TextView tv_back;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_actionbar_right)
    TextView tv_right;
    @BindView(R.id.empty_shopping_car)
    View empty;
    @BindView(R.id.recy_shopping_car)
    RecyclerView recyclerView;
    @BindView(R.id.ll_shoppingcar_content)
    LinearLayout content;
    @BindView(R.id.cb_shoppingcar_all)
    CheckBox cb_all;
    @BindView(R.id.tv_shoppingcar_total_number)
    TextView tv_total_num;
    @BindView(R.id.tv_shoppingcar_total_price)
    TextView tv_total_price;
    @BindView(R.id.shoppincar_parent)
    View parent;
    @BindView(R.id.tv_shopingcar_discounts_total)
    TextView tv_discounts_total;

    private List<ShopCarBean.DataBean> beanList = new ArrayList<>();
    private CommonAdapter<ShopCarBean.DataBean> adapter;
    private boolean Status = false;
    private boolean isShow = true;
    private double total;
    private double discountsTotals;
    private int total_num;
    private MainActivity mainActivity;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_shopping_car;
    }

    @Override
    public void initData() {
        tv_back.setVisibility(View.INVISIBLE);
        tv_title.setText("购物车");
        tv_right.setText("编缉");

        getData();
    }

    @Override
    protected BasePresenter bindPresenter() {
        return presenter;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
            Log.i("到可见时更新数据", Constant.baseUrl + "orders/shopping_cars_moblie.php?m=shopping_list" + "&user_id=" + userId);
            cb_all.setChecked(false);
            getData();
        }
    }

    private void getData() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        //显示，更新数据
        String url = Constant.baseUrl + "orders/shopping_cars_moblie.php?m=shopping_list";
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        Log.i("创建购物fragment", Constant.baseUrl + "orders/shopping_cars_moblie.php?m=shopping_list" + "&user_id=" + userId);
        presenter.PresenterGetData(url, params);
    }



    private void initRecy() {
        content.setVisibility(View.VISIBLE);
        tv_total_num.setText("结算(0)");
        tv_total_price.setText(Html.fromHtml("合计: " + "<font color='#ff0000'><big>" + "¥" + total + "</big>"));
        cb_all.setOnCheckedChangeListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CommonAdapter<ShopCarBean.DataBean>(getContext(), R.layout.shopping_car_layout, beanList) {
            @Override
            protected void convert(final ViewHolder vh, final ShopCarBean.DataBean dataBean, final int i) {
                vh.setText(R.id.tv_shopping_car_shopname, dataBean.getShops_name());
                //内部recyclerview
                RecyclerView InnerRecy = vh.getView(R.id.recy_shopping_car_item);
                final List<ShopCarBean.DataBean.DataChildBean> InnerList = dataBean.getData_child();
                InnerRecy.setLayoutManager(new LinearLayoutManager(MyApp.getContext()));
                InnerRecy.setItemAnimator(new DefaultItemAnimator());
                final CommonAdapter<ShopCarBean.DataBean.DataChildBean> childBeanCommonAdapter = new CommonAdapter<ShopCarBean.DataBean.DataChildBean>(MyApp.getContext(), R.layout.shopping_car_item, InnerList) {
                    @Override
                    protected void convert(final ViewHolder holder, final ShopCarBean.DataBean.DataChildBean dataChildBean, final int position) {
                        //图片
                        ImageView iv = holder.getView(R.id.iv_shoppingcar);
                        Glide.with(MyApp.getContext()).load(dataChildBean.getImg_url()).into(iv);
                        //名字
                        holder.setText(R.id.tv_commodity_name, dataChildBean.getTitle());
                        //价格
                        TextView tv_price = holder.getView(R.id.tv_commodity_price);
                        tv_price.setText(Html.fromHtml("¥ " + "<big>" + dataChildBean.getSeal_price() + "</big>"));
                        //规格
                        holder.setText(R.id.tv_shoppingcar_size, "规格: " + dataChildBean.getName());
                        //商品数量
                        TextView tv_number = holder.getView(R.id.tv_shoppingcar_num);
                        tv_number.setText("X" + dataChildBean.getNumber());
                        //优惠信息
                        LinearLayout ll_discounts = holder.getView(R.id.ll_shopingcar_discounts);
                        RecyclerView recy = holder.getView(R.id.recy_discounts);
                        holder.setText(R.id.tv_shoppingcar_discounts_hint,dataChildBean.getMotion_type());
                        TextView tv_modify_disconts = holder.getView(R.id.tv_modify_disconts);
                        holder.setText(R.id.tv_shoppingcar_discounts_hint_content, dataChildBean.getChild_title());
                        List<ShopCarBean.DataBean.DataChildBean.GiftBean> giftBeanList = dataChildBean.getGift();
                        if (giftBeanList.size() != 0) {
                            ll_discounts.setVisibility(View.VISIBLE);
                            //  tv_modify_disconts.setVisibility(View.VISIBLE);
                            initDiscouns(giftBeanList, recy);
                        } else {
                            ll_discounts.setVisibility(View.GONE);
                            // tv_modify_disconts.setVisibility(View.INVISIBLE);
                        }
                        tv_modify_disconts.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Modify_DiscountsPop mdp = new Modify_DiscountsPop(MyApp.getContext());
                                mdp.showPow(parent);
                            }
                        });
                        //切换布局
                        View view = holder.getView(R.id.more_or_less);
                        if (isShow) {
                            tv_number.setVisibility(View.VISIBLE);
                            view.setVisibility(View.GONE);
                        } else {
                            tv_number.setVisibility(View.GONE);
                            view.setVisibility(View.VISIBLE);
                        }

                        //增
                        final TextView tv_show = holder.getView(R.id.tv_show_number);
                        tv_show.setText(dataChildBean.getNumber() + "");
                        ImageView iv_plus = holder.getView(R.id.iv_plus);

                        iv_plus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int number = dataChildBean.getNumber();
                                number++;
                                tv_show.setText(number + "");
                                dataChildBean.setNumber(number);
                            }
                        });
                        //减
                        ImageView iv_subtract = holder.getView(R.id.iv_subtract);
                        iv_subtract.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int number = dataChildBean.getNumber();
                                if (number == 1) {
                                    return;
                                }
                                number--;
                                tv_show.setText(number + "");
                                dataChildBean.setNumber(number);
                            }
                        });
                        //删除
                        TextView tv_delete = holder.getView(R.id.tv_shoppingcar_delete);
                        tv_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
                                if (mainActivity.isFastClick()) {
                                    Log.i("删除购物车", Constant.baseUrl + "orders/shopping_cars_moblie.php?m=car_del" + "&car_id=" + dataChildBean.getAid() + "&user_id=" + userId);
                                    OkHttpUtils
                                            .get()
                                            .tag(this)
                                            .url(Constant.baseUrl + "orders/shopping_cars_moblie.php?m=car_del")
                                            .addParams("car_id", dataChildBean.getAid())
                                            .addParams("user_id", userId)
                                            .build()
                                            .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                                                @Override
                                                public void onError(Call call, Exception e, int id) {

                                                }

                                                @Override
                                                public void onResponse(BaseStringBean response, int id) {

                                                    if ("1".equals(String.valueOf(response.getStatus()))) {
                                                        del(InnerList, position);
                                                        if (InnerList.size() == 0) {
                                                            adapter.del(beanList, i);
                                                        }
                                                        if (beanList.size() == 0) {
                                                            cb_all.setChecked(false);
                                                            content.setVisibility(View.GONE);
                                                            empty.setVisibility(View.VISIBLE);
                                                        }

                                                        if (dataBean.isCheck()) {
                                                            total -= dataChildBean.getTotal();
                                                            total_num -= 1;
                                                            tv_total_num.setText("结算(" + total_num + ")");
                                                            tv_total_price.setText(Html.fromHtml("合计: " + "<font color='#ff0000'><big>" + "¥" + total + "</big>"));
                                                        }

                                                    }
                                                }
                                            });
                                }
                            }
                        });

                    }

                };
                InnerRecy.setAdapter(childBeanCommonAdapter);
                //外层复选框
                final CheckBox cb = vh.getView(R.id.cb_shoppingcar_outside);
                cb.setChecked(dataBean.isCheck());
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        List<ShopCarBean.DataBean> tempList = new ArrayList<>();
                        double tempPrice = 0.00;
                        double discounts=0.00;
                        //改变状态
                        for (int i = 0; i < InnerList.size(); i++) {
                            ShopCarBean.DataBean.DataChildBean childBean = InnerList.get(i);
                            tempPrice += childBean.getTotal();
                            discounts+=childBean.getDiscount_amount();
                        }
                        //计算价钱，数量
                        if (b) {
                            total_num += InnerList.size();
                            total += tempPrice;
                           discountsTotals+=discounts;
                        } else {
                            total_num -= InnerList.size();
                            total -= tempPrice;
                            discountsTotals-=discounts;
                        }
                        dataBean.setCheck(b);

                        //此时查询外层框是否全部选中,如果全选中就将底部复选框选中,

                        for (int j = 0; j < beanList.size(); j++) {
                            ShopCarBean.DataBean tempBean = beanList.get(j);
                            if (tempBean.isCheck()) {
                                tempList.add(tempBean);
                            }
                        }
                        if (tempList.size() == beanList.size()) {
                            cb_all.setChecked(true);
                        } else if (tempList.size() == 0) {
                            cb_all.setChecked(false);
                        }
                        double finaPrice= total-discountsTotals;
                        tv_discounts_total.setText("优惠: - ¥"+discountsTotals);
                        tv_total_num.setText("结算(" + total_num + ")");
                        tv_total_price.setText(Html.fromHtml("合计: " + "<font color='#ff0000'><big>" + "¥" + finaPrice + "</big>"));
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }

    /**
     * 优惠信息
     *
     * @param giftBeanList
     */
    private void initDiscouns(List<ShopCarBean.DataBean.DataChildBean.GiftBean> giftBeanList, RecyclerView recy) {
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
        CommonAdapter<ShopCarBean.DataBean.DataChildBean.GiftBean> adapter = new CommonAdapter<ShopCarBean.DataBean.DataChildBean.GiftBean>(MyApp.getContext(), R.layout.my_gold_sign_item, giftBeanList) {
            @Override
            protected void convert(ViewHolder holder, ShopCarBean.DataBean.DataChildBean.GiftBean giftBean, int position) {
                TextView tv=holder.getView(R.id.tv_sign_date);
                tv.setText(giftBean.getZp_title());
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                tv.setMaxLines(1);
                tv.setEllipsize(TextUtils.TruncateAt.END);
                //数量
                TextView tv_num=holder.getView(R.id.tv_sign_gold_num);
                tv_num.setTextColor(Color.parseColor("#000000"));
                tv_num.setText("x"+giftBean.getZp_number());
            }
        };
        recy.setAdapter(adapter);
    }

    @OnClick(R.id.tv_actionbar_right)
    public void edit() {
        Status = !Status;
        if (Status) {
            tv_right.setText("完成");
            isShow = false;
            adapter.notifyDataSetChanged();
        } else {
            tv_right.setText("编辑");
            isShow = true;
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void showData(Object o) {
        ShopCarBean bean = (ShopCarBean) o;
        beanList = bean.getData();
        if (beanList.size() != 0) {

            initRecy();

        } else {

            empty.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showErro() {

        empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        //改变选中状态
        double tempPrice = 0.00;
        double discountsPrice=0.00;
        List<ShopCarBean.DataBean.DataChildBean> tempList = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            ShopCarBean.DataBean dataBean = beanList.get(i);
            List<ShopCarBean.DataBean.DataChildBean> childBeanList = dataBean.getData_child();
            for (int j = 0; j < childBeanList.size(); j++) {
                ShopCarBean.DataBean.DataChildBean childBean = childBeanList.get(j);
                childBean.setChecked(b);
                //总价钱
                tempPrice += childBean.getTotal();
                //总数量
                tempList.add(childBean);
                //总优惠
                discountsPrice+=childBean.getDiscount_amount();
            }

            dataBean.setCheck(b);
            adapter.notifyDataSetChanged();
        }


        if (b) {
            tv_total_num.setText("结算(" + tempList.size() + ")");
            double finaPrice=tempPrice-discountsPrice;
            tv_total_price.setText(Html.fromHtml("合计: " + "<font color='#ff0000'><big>" + "¥" + finaPrice + "</big>"));
            tv_discounts_total.setText("优惠: - ¥"+discountsPrice);

        } else {
            tv_total_price.setText(Html.fromHtml("合计: " + "<font color='#ff0000'><big>" + "¥" + 0.00 + "</big>"));
            tv_total_num.setText("结算(0)");
            tv_discounts_total.setText("优惠: - ¥0.0");
        }
    }

    @OnClick(R.id.tv_shoppingcar_total_number)
    public void goOrder() {
        if (isFastClick()) {

            List<ShopCarBean.DataBean> isCheckList = new ArrayList<>();
            for (int i = 0; i < beanList.size(); i++) {
                ShopCarBean.DataBean dataBean = beanList.get(i);
                if (dataBean.isCheck()) {
                    isCheckList.add(dataBean);
                }
            }
            if (isCheckList.size() == 0) {
                MyToast.makeTextAnim(MyApp.getContext(), "您还没有选择商品", 0, Gravity.CENTER, 0, 0).show();
                return;
            }

            GoOrder(isCheckList);

        }
    }

    private void GoOrder(List<ShopCarBean.DataBean> isCheckList) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        ShopCarBean.DataBean dataBean = null;
        ShopCarBean.DataBean.DataChildBean childBean = null;
        for (int i = 0; i < isCheckList.size(); i++) {
            dataBean = isCheckList.get(i);
            //门店id
            sb1.append(dataBean.getShop_id()).append(",");
            List<ShopCarBean.DataBean.DataChildBean> childBeanList = isCheckList.get(i).getData_child();
            for (int j = 0; j < childBeanList.size(); j++) {
                childBean = childBeanList.get(j);
                //购物车id
                sb2.append(childBean.getAid()).append(",");
                //数量
                sb3.append(childBean.getNumber()).append(",");
                //产品id
                sb4.append(childBean.getPro_id()).append(",");
            }
        }
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        intent.putExtra("shopId", sb1.toString());
        intent.putExtra("carId", sb2.toString());
        intent.putExtra("num", sb3.toString());
        intent.putExtra("proId", sb4.toString());
        startActivity(intent);
    }
}
