package car.tzxb.b2b.Uis.Order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.wrapper.LoadMoreWrapper;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.Adapter.OrderAdapter;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.OrderBeans.OrderHeader;
import car.tzxb.b2b.Bean.OrderBeans.OrderItem;
import car.tzxb.b2b.Bean.OrderBeans.OrderFooter;
import car.tzxb.b2b.Bean.OrderStatusBean;
import car.tzxb.b2b.MainActivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.ShoppingCartActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.AlterDialogFragment;
import car.tzxb.b2b.Views.DialogFragments.LoadingDialog;
import car.tzxb.b2b.Views.PopWindow.CancelOrderPop;
import car.tzxb.b2b.config.Constant;
import car.tzxb.b2b.wxapi.WXPayEntryActivity;
import okhttp3.Call;

public class LookOrderActivity extends MyBaseAcitivity {

    @BindView(R.id.recy_order)
    RecyclerView recyclerView;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tab_order)
    TabLayout tablayout;
    @BindView(R.id.order_parent)
    LinearLayout parent;
    @BindView(R.id.tv_empty_order)
    TextView tv_emtpy;
    private List<Object> resultList=new ArrayList<>();
    private OrderAdapter adapter;
    private List<OrderStatusBean.DataBean.OrderListBean> beanList;
    private int position,pager;
    private String m;
    private LoadingDialog loadingDialog;
    private View loadview;
    private LoadMoreWrapper<Object> loadMoreWrapper;
    @Override
    public void initParms(Bundle parms) {
        position = getIntent().getIntExtra("index",-1);
        createMethod(position);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_look_order;
    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("我的订单");
        initTab();
        initRecy();
    }

    private void initTab() {
       String [] str={"全部","待付款","待发货","待收货","待评价"};
        for (int i = 0; i <str.length ; i++) {
            tablayout.addTab(tablayout.newTab().setText(str[i]));
        }
        tablayout.getTabAt(position).select();
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //刷新数据
                createMethod(tab.getPosition());
                pager=0;
                Refresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initRecy() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderAdapter(MyApp.getContext(),resultList);
        recyclerView.setAdapter(adapter);
        loadMoreWrapper = new LoadMoreWrapper<>(adapter);
        recyclerView.setAdapter(loadMoreWrapper);
        //点击接口回调
        adapter.setTextClickListener(new OrderAdapter.TextClickListener() {
            @Override
            public void click1(String status, int index, String Order_seqno, double pay,String aid) {
                if ("等待付款".equals(status)) {
                    //去付款
                    Intent intent = new Intent(LookOrderActivity.this, WXPayEntryActivity.class);
                    intent.putExtra("total", String.valueOf(pay));
                    intent.putExtra("order_seqnos", Order_seqno);
                    startActivity(intent);
                } else if ("等待发货".equals(status)) {
                    //提醒发货
                    Reminder(aid);
                } else if ("商家已发货".equals(status)) {
                    //确认收货
                    notarize(aid);
                }else if("已取消".equals(status)){
                    deleItem(aid);
                }
            }

            @Override
            public void click2(String status, final String aid) {
                if ("等待付款".equals(status)) {
                    //取消订单
                    final CancelOrderPop cop = new CancelOrderPop(MyApp.getContext());
                    DeviceUtils.showPopWindow(parent,cop);
                    cop.setOnClickCancle(new CancelOrderPop.onClickCancleOrder() {
                        @Override
                        public void cancle(String s) {
                            cancleOrder(aid, s);
                            cop.dismiss();
                        }
                    });
                }  else if ("商家已发货".equals(status) ||"交易成功".equals(status)) {
                    //物流详情
                    Intent intent=new Intent(LookOrderActivity.this,LogisticsActivity.class);
                    intent.putExtra("orderId",aid);
                    startActivity(intent);
                }
            }

            @Override
            public void click3(String status,final int index,final String aid) {
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
                        deleItem(aid);
                    }
                });
            }

            @Override
            public void click4(String proId, String number, String shopId) {
                AgainOrder(proId,number,shopId);
            }

            @Override
            public void itemClick(String aid) {
                Intent i = new Intent(LookOrderActivity.this, OrderXqActivity.class);
                i.putExtra("orderid", aid);
                startActivity(i);
            }
        });
        //加载更多监听
        loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LoadMore();
            }
        });
    }



    /**
     * 取消订单
     * @param aid
     * @param s
     */
    private void cancleOrder(String aid, String s) {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("取消的", Constant.baseUrl + "orders/order_list_mobile.php?m=order_cancel" + "&user_id=" + userId + "&order_id=" + aid + "&return_reason=s");
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "orders/order_list_mobile.php?m=order_cancel")
                .tag(this)
                .addParams("user_id", userId)
                .addParams("order_id", aid)
                .addParams("return_reason", s)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        if("1".equals(String.valueOf(response.getStatus()))){
                            pager=0;
                            Refresh();
                        }else {
                            MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0,Gravity.CENTER,0,0).show();
                        }
                    }
                });
    }

    /**
     * 删除订单
     */
    private void deleItem(String aid) {
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

                             Refresh();
                        }else {
                            MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0,Gravity.CENTER,0,0).show();
                        }
                    }
                });
    }

    //确认完成订单
    private void notarize(final String aid) {
        final AlterDialogFragment alterDialogFragment = new AlterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "确定完成订单吗");
        bundle.putString("ok", "确定");
        bundle.putString("no", "再想想");
        alterDialogFragment.setArguments(bundle);
        alterDialogFragment.show(getSupportFragmentManager(), "del");
        alterDialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                alterDialogFragment.dismiss();
            }

            @Override
            public void sure() {
                alterDialogFragment.dismiss();
                String userId=SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
                OkHttpUtils
                        .get()
                        .tag(this)
                        .url(Constant.baseUrl+"orders/order_list_mobile.php?m=order_confirm")
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
                                    tablayout.getTabAt(4).select();
                                }else {
                                    MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0,Gravity.CENTER,0,0).show();
                                }
                            }
                        });
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
                        MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0, Gravity.CENTER,0,0).show();
                    }
                });
    }

    /**
     * 再来一单
     */
    private void AgainOrder(String proId, String number, String shopId) {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("再来一单",Constant.baseUrl+"orders/order_list_mobile.php?m=order_shopping_add"+"&pro_id="+proId+"&number="+number
                 +"&shop_id="+shopId+"&user_id="+userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"orders/order_list_mobile.php?m=order_shopping_add")
                .addParams("pro_id",proId)
                .addParams("number",number)
                .addParams("shop_id",shopId)
                .addParams("user_id",userId)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                          if("1".equals(String.valueOf(response.getStatus()))){
                              Intent intent=new Intent(LookOrderActivity.this, ShoppingCartActivity.class);
                              startActivity(intent);
                              finish();
                          }
                    }
                });
    }


    private void Refresh() {
        showLoading();
        pager=0;
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("我的订单", Constant.baseUrl + "orders/order_list_mobile.php?m=order_lists" + "&user_id=" + userId + "&list=" + m+"&page="+pager+"&pagesize=10");
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/order_list_mobile.php?m=order_lists")
                .addParams("user_id", userId)
                .addParams("page", String.valueOf(pager))
                .addParams("pagesize", "10")
                .addParams("list", m)
                .build()
                .execute(new GenericsCallback<OrderStatusBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                         closeLoading();
                    }

                    @Override
                    public void onResponse(OrderStatusBean response, int id) {
                        closeLoading();
                        beanList = response.getData().getOrder_list();
                        if(beanList.size()!=0){
                            DataHelper(beanList,true);
                            tv_emtpy.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            pager++;
                        }else {
                            tv_emtpy.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                });
    }

      //整理数据
    private void DataHelper(List<OrderStatusBean.DataBean.OrderListBean> beanList,boolean Refresh) {
        List<Object> dataList=new ArrayList<>();
        for (int i = 0; i < beanList.size() ; i++) {
            OrderStatusBean.DataBean.OrderListBean xBean= beanList.get(i);
            //头部信息
            OrderHeader headerBean=new OrderHeader();
            headerBean.setShopName(xBean.getShop_name());
            headerBean.setStatus(xBean.getStatus());
            dataList.add(headerBean);
            //商品信息
            List<OrderStatusBean.DataBean.OrderListBean.ChildDataBean> xxBean=xBean.getChild_data();
            OrderItem orderGoodsItem=null;

            StringBuilder sb_proId=new StringBuilder();
            StringBuilder sb_number=new StringBuilder();
            for (int j = 0; j <xxBean.size() ; j++) {
                OrderStatusBean.DataBean.OrderListBean.ChildDataBean childBean=xxBean.get(j);
                orderGoodsItem=new OrderItem();
                orderGoodsItem.setAid(childBean.getAid());
                orderGoodsItem.setImg_url(childBean.getImg_url());
                orderGoodsItem.setProduct_title(childBean.getProduct_title());
                orderGoodsItem.setReal_price(childBean.getReal_price());
                orderGoodsItem.setQuantity(childBean.getQuantity());
                dataList.add(orderGoodsItem);
                //拼接商品id,和数量
                sb_proId.append(childBean.getProduct_id()).append(",");
                sb_number.append(childBean.getQuantity()).append(",");
            }

            //尾部信息
            OrderFooter footerBean=new OrderFooter();
            footerBean.setAmount_pay_able(xBean.getAmount_pay_able());
            footerBean.setNumber(xBean.getNumbers());
            footerBean.setStatus(xBean.getStatus());
            footerBean.setShop_id(xBean.getShop_id());
            footerBean.setProId(sb_proId.toString());
            footerBean.setCount(sb_number.toString());
            footerBean.setOrder_seqno(xBean.getOrder_seqno());
            footerBean.setAid(xBean.getAid());
            footerBean.setIndex(i);
            dataList.add(footerBean);
        }

        adapter.add(dataList,true);
        if(Refresh){
            if(beanList.size()<10){  //不用加载更多
                loadview =null;
            }else {
                loadview = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.default_footer,recyclerView,false);
            }
        }else {
            loadview = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.default_footer,recyclerView,false);
        }
        loadMoreWrapper.setLoadMoreView(loadview);
        loadMoreWrapper.notifyDataSetChanged();
    }


    /**
     * 上拉加载更多
     */
    private void LoadMore() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("我的订单加载更多", Constant.baseUrl + "orders/order_list_mobile.php?m=order_lists" + "&user_id=" + userId + "&list=" + m + "&page=" + pager + "&pagesize=10");
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/order_list_mobile.php?m=order_lists")
                .addParams("user_id", userId)
                .addParams("list", m)
                .addParams("page", String.valueOf(pager))
                .addParams("pagesize", "10")
                .build()
                .execute(new GenericsCallback<OrderStatusBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(OrderStatusBean response, int id) {
                        List<OrderStatusBean.DataBean.OrderListBean> tempList=response.getData().getOrder_list();
                        if(tempList.size()>0){
                            beanList.addAll(tempList);
                            DataHelper(beanList,false);
                            pager++;
                        }else {
                            loadview =null;
                            loadMoreWrapper.setLoadMoreView(loadview);
                            MyToast.makeTextAnim(MyApp.getContext(), "已经全部为您加载完毕", 0, Gravity.CENTER, 0, 0).show();
                        }
                       loadMoreWrapper.notifyDataSetChanged();
                    }
                });

    }



    //生成方法名
    public String createMethod(int i){
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
        return m;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }
    //显示加载加载框
    public void showLoading() {
        loadingDialog = new LoadingDialog();
        loadingDialog.setCancelable(false);
        loadingDialog.show(getSupportFragmentManager(), "loading");
    }
    //关闭加载框

    public void closeLoading(){
        if(loadingDialog!=null){
            loadingDialog.dismiss();
        }
    }

}
