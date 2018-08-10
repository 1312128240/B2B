package car.tzxb.b2b.Uis.Order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CustomToast.MyToast;
import car.myview.SpringView.DefaultFooter;
import car.myview.SpringView.DefaultHeader;
import car.myview.SpringView.SpringView;
import car.myview.Tab.NavigationTabStrip;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.OrderStatusBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.AlterDialogFragment;
import car.tzxb.b2b.Views.DialogFragments.LoadingDialog;
import car.tzxb.b2b.Views.PopWindow.CancelOrderPop;
import car.tzxb.b2b.config.Constant;
import car.tzxb.b2b.wxapi.WXPayEntryActivity;
import okhttp3.Call;


public class OrderStatusActivity extends MyBaseAcitivity implements NavigationTabStrip.OnTabStripSelectedIndexListener, SpringView.OnFreshListener {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.nts_center)
    NavigationTabStrip nts;
    @BindView(R.id.recy_test)
    RecyclerView recyclerView;
    @BindView(R.id.parent)
    View parent;
    @BindView(R.id.sv)
    SpringView springView;
    private int index;
    private int pager;
    private CommonAdapter<OrderStatusBean.DataBean.OrderListBean> adapter;
    private List<OrderStatusBean.DataBean.OrderListBean> beanList = new ArrayList<>();
    private String type;
    private LoadingDialog loadingDialog;


    @Override
    public void initParms(Bundle parms) {
        index = getIntent().getIntExtra("index", -1);
    }

    @Override
    public int bindLayout() {

        return R.layout.activity_order_status;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("我的订单");
        nts.setTabIndex(index);
        nts.setOnTabStripSelectedIndexListener(this);
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(MyApp.getContext()));
        springView.setFooter(new DefaultFooter(MyApp.getContext()));
        initRecy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pager=0;
        type=getType(index);
        Refresh();
    }

    private void Refresh() {
        showLoading();
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("我的订单", Constant.baseUrl + "orders/order_list_mobile.php?m=order_lists" + "&user_id=" + userId + "&list=" + type + "&page=" + pager + "&pagesize=10");
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/order_list_mobile.php?m=order_lists")
                .addParams("user_id", userId)
                .addParams("list", type)
                .addParams("page", String.valueOf(pager))
                .addParams("pagesize", "10")
                .build()
                .execute(new GenericsCallback<OrderStatusBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(OrderStatusBean response, int id) {
                        loadingDialog.dismiss();
                        beanList = response.getData().getOrder_list();
                        adapter.add(beanList, true);
                        if (beanList.size() > 0) {
                            pager++;
                        }
                    }
                });

    }


    private void initRecy() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CommonAdapter<OrderStatusBean.DataBean.OrderListBean>(MyApp.getContext(), R.layout.order_list_item, beanList) {
            @Override
            protected void convert(ViewHolder holder, final OrderStatusBean.DataBean.OrderListBean bean, final int position) {
                //店名
                holder.setText(R.id.tv_order_status_shop_name, bean.getShop_name());
                //共几件和总价
                holder.setText(R.id.tv_order_status_number, "共" + bean.getNumbers() + "件,");
                TextView tv_total = holder.getView(R.id.tv_order_status_total);
                tv_total.setText(Html.fromHtml("合计:¥" + "<big>" + bean.getAmount_pay_able() + "</big>"));
                //隐藏留言
                holder.getView(R.id.et_mesg).setVisibility(View.GONE);
                //内部recycler
                RecyclerView recy = holder.getView(R.id.recy_order_inner);
                recy.setLayoutManager(new LinearLayoutManager(MyApp.getContext()));
                recy.setNestedScrollingEnabled(false);
                final List<OrderStatusBean.DataBean.OrderListBean.ChildDataBean> childDataBeanList = bean.getChild_data();
                CommonAdapter<OrderStatusBean.DataBean.OrderListBean.ChildDataBean> Xadapter =
                        new CommonAdapter<OrderStatusBean.DataBean.OrderListBean.ChildDataBean>(MyApp.getContext(), R.layout.commn_item, childDataBeanList) {
                            @Override
                            protected void convert(ViewHolder holder, OrderStatusBean.DataBean.OrderListBean.ChildDataBean childDataBean, int position) {
                                //隐藏不必要控件
                                holder.getView(R.id.tv_goods_type).setVisibility(View.GONE);
                                //添加灰色背景
                                View parent = holder.getView(R.id.commn_item_parent);
                                parent.setBackgroundColor(Color.parseColor("#F5F5F5"));
                                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.setMargins(0, 0, 0, 2);
                                parent.setLayoutParams(params);
                                //图片
                                int i = DeviceUtils.dip2px(MyApp.getContext(), 65);
                                ImageView iv = holder.getView(R.id.iv_category);
                                Glide.with(MyApp.getContext()).load(childDataBean.getImg_url()).override(i, i).into(iv);
                                //商品名字
                                holder.setText(R.id.tv_catagroy_name, childDataBean.getProduct_title());
                                //价格
                                holder.setText(R.id.tv_category_pice, "¥" + childDataBean.getReal_price());
                                //数量
                                holder.setText(R.id.tv_goods_count, "x" + childDataBean.getQuantity());

                            }
                        };
                recy.setAdapter(Xadapter);
                Xadapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        String orderId = childDataBeanList.get(position).getOrder_id();
                        Intent i = new Intent(OrderStatusActivity.this, OrderXqActivity.class);
                        i.putExtra("orderid", orderId);
                        startActivity(i);
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                });
                //订单标识
                TextView tv1 = holder.getView(R.id.tv_view1);
                TextView tv2 = holder.getView(R.id.tv_view2);
                TextView tv3 = holder.getView(R.id.tv_view3);
                TextView  tv_describe=holder.getView(R.id.tv_order_status_describe);
                if ("等待付款".equals(bean.getStatus())) {    //待付款
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setVisibility(View.GONE);
                    tv1.setText("付款");
                    tv2.setText("取消订单");
                    tv_describe.setText("等待付款");
                } else if ("等待发货".equals(bean.getStatus())) { //待发货
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.GONE);
                    tv3.setVisibility(View.GONE);
                    tv1.setText("提醒发货");
                    tv_describe.setText("等待发货");
                } else if ("商家已发货".equals(bean.getStatus())) { //待收货
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setVisibility(View.GONE);
                    tv1.setText("确认收货");
                    tv2.setText("物流详情");
                    tv_describe.setText("商家已发货");
                } else if ("交易成功".equals(bean.getStatus())) { //待评价
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setVisibility(View.VISIBLE);
                    tv1.setText("晒单评价");
                    tv2.setText("物流详情");
                    tv3.setText("删除订单");
                    tv_describe.setText("交易成功");
                } else if ("已取消".equals(bean.getStatus())) {
                    tv1.setVisibility(View.GONE);
                    tv2.setVisibility(View.GONE);
                    tv3.setVisibility(View.VISIBLE);
                    tv3.setText("已取消");
                    tv_describe.setText("交易关闭");
                }

                tv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        if ("等待付款".equals(bean.getStatus())) {
                            //去付款
                            intent.setClass(OrderStatusActivity.this, WXPayEntryActivity.class);
                            intent.putExtra("total",bean.getAmount_pay_able());
                            intent.putExtra("order_seqnos",bean.getOrder_seqno());
                            startActivity(intent);
                        } else if ("等待发货".equals(bean.getStatus())) {
                            //提醒发货
                            Reminder(bean.getAid());
                        } else if ("商家已发货".equals(bean.getStatus())) {
                            //确认收货
                            final AlterDialogFragment alterDialogFragment=new AlterDialogFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("title","确定完成订单吗");
                            bundle.putString("ok","确定");
                            bundle.putString("no","再想想");
                            alterDialogFragment.setArguments(bundle);
                            alterDialogFragment.show(getSupportFragmentManager(),"del");
                            alterDialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
                                @Override
                                public void cancle() {
                                    alterDialogFragment.dismiss();
                                }

                                @Override
                                public void sure() {
                                    alterDialogFragment.dismiss();
                                    Confirm(bean.getAid());
                                }
                            });

                        } else if ("交易成功".equals(bean.getStatus())) {
                            //晒单评价

                        }

                    }
                });
                tv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if ("等待付款".equals(bean.getStatus())) {
                            //取消订单
                            final CancelOrderPop cop = new CancelOrderPop(MyApp.getContext());
                            cop.show(parent);
                            cop.setOnClickCancle(new CancelOrderPop.onClickCancleOrder() {
                                @Override
                                public void cancle(String s) {
                                    cancleOrder(bean.getAid(), s);
                                    cop.dismiss();
                                }
                            });
                        }  else if ("商家已发货".equals(bean.getStatus()) ||"交易成功".equals(bean.getStatus())) {
                            //物流详情
                            Intent intent=new Intent(OrderStatusActivity.this,LogisticsActivity.class);
                            intent.putExtra("orderId",bean.getAid());
                            startActivity(intent);
                        }
                    }
                });
                tv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if("交易成功".equals(bean.getStatus())){
                            final AlterDialogFragment alterDialogFragment=new AlterDialogFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("title","确认删除订单");
                            bundle.putString("ok","确定");
                            bundle.putString("no","再想想");
                            alterDialogFragment.setArguments(bundle);
                            alterDialogFragment.show(getSupportFragmentManager(),"del");
                            alterDialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
                                @Override
                                public void cancle() {
                                    alterDialogFragment.dismiss();
                                }

                                @Override
                                public void sure() {
                                    alterDialogFragment.dismiss();
                                    delOrder(bean.getAid());
                                }
                            });
                        }
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);

    }

    /**
     * 确认收货
     */
    private void Confirm(String orderId) {
        //http://172.20.10.142/mobile_api/orders/order_list_mobile.php?m=order_confirm&user_id=446&order_id=93
        String userId=SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"orders/order_list_mobile.php?m=order_confirm")
                .addParams("user_id",userId)
                .addParams("order_id",orderId)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                            if(response.getStatus()==1){
                                nts.setTabIndex(4);
                                type="stay_evaluate";
                                Refresh();
                            }else {
                                MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0,Gravity.CENTER,0,0).show();
                            }
                    }
                });
    }


    /**
     * 删除订单
     * @param aid
     */
    private void delOrder(String aid) {
        String userId=SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"orders/order_list_mobile.php?m=order_del")
                .addParams("user_id",userId)
                .addParams("order_id",aid)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        if(response.getStatus()==1){
                            onBackPressed();
                        }else {
                            MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0,Gravity.CENTER,0,0).show();
                        }
                    }
                });
    }

    /**
     * 提醒发货
     * @param aid
     */

    private void Reminder(String aid) {
             String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
             OkHttpUtils
                     .get()
                     .tag(this)
                     .url(Constant.baseUrl+"orders/order_list_mobile.php?m=order_remind")
                     .addParams("user_id",userId)
                     .addParams("order_id",aid)
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
     * 取消订单
     * @param orderId
     * @param s
     */
    private void cancleOrder(String orderId, String s) {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("取消的", Constant.baseUrl + "orders/order_list_mobile.php?m=order_cancel" + "&user_id=" + userId + "&order_id=" + orderId + "&return_reason=s");
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "orders/order_list_mobile.php?m=order_cancel")
                .tag(this)
                .addParams("user_id", userId)
                .addParams("order_id", orderId)
                .addParams("return_reason", s)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                       // MyToast.makeTextAnim(MyApp.getContext(), response.getMsg(), 0, Gravity.CENTER, 0, 0).show();
                        if("1".equals(String.valueOf(response.getStatus()))){
                             pager=0;
                             Refresh();
                        }else {
                            MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0,Gravity.CENTER,0,0).show();
                        }
                    }
                });
    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }


    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }

    public void showLoading() {
        loadingDialog = new LoadingDialog();
        loadingDialog.setCancelable(false);
        loadingDialog.show(getSupportFragmentManager(), "loading");
    }


    /**
     * 点击tab切换
     * @param title
     * @param index
     */
    @Override
    public void onStartTabSelected(String title, int index) {

    }

    @Override
    public void onEndTabSelected(String title, int i) {
        // 查询状态->全部订单：all；待付款：stay_payment；待发货：stay_shipment；待收货：stay_take；待评价： stay_evaluate
        index=i;
        type=getType(index);
        pager=0;
        Refresh();
    }

    //方法名
    public String getType(int i){
        String m=null;
        switch (i) {
            case 0:
                m = "all";
                break;
            case 1:
                m = "stay_payment";
                break;
            case 2:
                m = "stay_shipment";
                break;
            case 3:
                m = "stay_take";
                break;
            case 4:
                m = "stay_evaluate";
                break;
        }
        return   m;
    }


    /*
     *下拉刷新
     */
    private void pullDown() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("我的订单", Constant.baseUrl + "orders/order_list_mobile.php?m=order_lists" + "&user_id=" + userId + "&list=" + type + "&page=" + pager + "&pagesize=10");
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/order_list_mobile.php?m=order_lists")
                .addParams("user_id", userId)
                .addParams("list", type)
                .addParams("page", String.valueOf(pager))
                .addParams("pagesize", "10")
                .build()
                .execute(new GenericsCallback<OrderStatusBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        springView.onFinishFreshAndLoad();
                    }

                    @Override
                    public void onResponse(OrderStatusBean response, int id) {
                        springView.onFinishFreshAndLoad();
                        beanList = response.getData().getOrder_list();
                        adapter.add(beanList, true);
                        if (beanList.size() > 0) {
                            pager++;
                        }
                    }
                });
    }

    /**
     * 上拉加载更多
     */
    private void LoadMore() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("我的订单加载更多", Constant.baseUrl + "orders/order_list_mobile.php?m=order_lists" + "&user_id=" + userId + "&list=" + type + "&page=" + pager + "&pagesize=10");
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/order_list_mobile.php?m=order_lists")
                .addParams("user_id", userId)
                .addParams("list", type)
                .addParams("page", String.valueOf(pager))
                .addParams("pagesize", "10")
                .build()
                .execute(new GenericsCallback<OrderStatusBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        springView.onFinishFreshAndLoad();
                    }

                    @Override
                    public void onResponse(OrderStatusBean response, int id) {
                        springView.onFinishFreshAndLoad();
                        List<OrderStatusBean.DataBean.OrderListBean> tempList = response.getData().getOrder_list();
                        if (tempList.size() > 0) {
                            beanList.addAll(tempList);
                            adapter.add(beanList, true);
                            ++pager;
                        } else {
                            MyToast.makeTextAnim(MyApp.getContext(), "已经全部为您加载完毕", 0, Gravity.CENTER, 0, 0).show();
                        }
                    }
                });

    }


    @Override
    public void onRefresh() {
        pager = 0;
        pullDown();
    }


    @Override
    public void onLoadmore() {
        LoadMore();
    }
}
