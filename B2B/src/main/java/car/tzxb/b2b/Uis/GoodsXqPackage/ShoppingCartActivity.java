package car.tzxb.b2b.Uis.GoodsXqPackage;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
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
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.SpaceItemDecoration;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.Adapter.ShopcatAdapter;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.DiscountsBean;
import car.tzxb.b2b.Bean.OrderBeans.OrderHeader;
import car.tzxb.b2b.Bean.OrderBeans.OrderItem;
import car.tzxb.b2b.Bean.ShopCarBean;
import car.tzxb.b2b.MainActivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.Order.OrderActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Util.StringUtil;
import car.tzxb.b2b.Views.PopWindow.Modify_DiscountsPop;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class ShoppingCartActivity extends MyBaseAcitivity implements ShopcatAdapter.CheckInterface, ShopcatAdapter.ModifyCountInterface, ShopcatAdapter.GroupEditorListener {

    @BindView(R.id.listView)
    ExpandableListView listView;
    @BindView(R.id.all_checkBox)
    CheckBox allCheckBox;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.go_pay)
    TextView goPay;
    @BindView(R.id.order_info)
    LinearLayout orderInfo;
    @BindView(R.id.tv_del_all_goods)
    TextView delGoods;
    @BindView(R.id.ll_cart)
    LinearLayout llCart;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_actionbar_right)
    TextView tv_right;
    @BindView(R.id.empty_shopcart)
    View emptyView;
    @BindView(R.id.shopping_cart_parent)
    View parent;
    @BindView(R.id.tv_discounts_total)
    TextView tv_discounts_total;
    @BindView(R.id.recy_empty_shopping)
    RecyclerView recy_empty;
    private double mtotalPrice = 0.00;
    private double discounts_total=0.00;
    private int mtotalCount = 0;
    private boolean flag = false;                //false就是编辑，ture就是完成
    private ShopcatAdapter adapter;
    private List<OrderHeader> groups = new ArrayList<>();         //组元素的列表
    private Map<String, List<OrderItem>> childs = new HashMap<>(); //子元素的列表*/
    private String userId;
    private StringBuilder sb1,sb2,sb3,sb4,sb5;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("购物车");
        tv_right.setText("编缉");

        recy_empty.setLayoutManager(new GridLayoutManager(this, 2));
        recy_empty.addItemDecoration(new SpaceItemDecoration(10,2));
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }


    private void initLv() {
        adapter = new ShopcatAdapter(groups, childs, this);
        adapter.setCheckInterface(this);//关键步骤1：设置复选框的接口
        adapter.setModifyCountInterface(this); //关键步骤2:设置增删减的接口
        adapter.setGroupEditorListener(this);//关键步骤3:监听组列表的编辑状态
        listView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头
        listView.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            listView.expandGroup(i); //关键步骤4:初始化，将ExpandableListView以展开的方式显示
        }

    }


    private void  getData() {
        mtotalCount = 0;
        allCheckBox.setChecked(false);
        totalPrice.setText(Html.fromHtml("合计: "+"<font color='#FA3314'>"+"¥"+0.00+"</font>"));
        tv_discounts_total.setText(Html.fromHtml("优惠: "+"<font color='#FA3314'>"+"¥"+0.00+"</font>"));
        goPay.setText("去结算(" + 0 + ")");
        //获取数据
        userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/shopping_cars_moblie.php?m=shopping_list")
                .addParams("user_id", userId)
                .addParams("shop_id", "-1")
                .addParams("type", "0")
                .build()
                .execute(new GenericsCallback<ShopCarBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(ShopCarBean response, int id) {
                        List<ShopCarBean.DataBean> beanList = response.getData();
                        if (beanList.size() == 0) {
                           getGuess();
                        } else {
                            DataHelpers(beanList);
                        }

                    }
                });
    }

    //空购物车时猜你在找
    private void getGuess() {
        tv_right.setVisibility(View.GONE);
        llCart.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);

        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("猜你在找", Constant.baseUrl + "item/index.php?c=Goods&m=UserLike&pagesize=10&page=0&user_id=" + userId);
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
                        List<BaseDataListBean.DataBean> guessList = response.getData();
                        initGuessRecy(guessList);
                    }
                });
    }


    private void initGuessRecy(final List<BaseDataListBean.DataBean> guessList) {

        CommonAdapter<BaseDataListBean.DataBean> guessAdapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(), R.layout.recommend_layout, guessList) {
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
                ;           //销量
                TextView tv_sales = holder.getView(R.id.tv_recomment_sales);
                tv_sales.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tv_sales.setText("销量 " + bean.getSales());
            }
        };
        recy_empty.setAdapter(guessAdapter);
        guessAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BaseDataListBean.DataBean bean = guessList.get(position);
                Intent intent = new Intent(ShoppingCartActivity.this, GoodsXqActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("mainId", bean.getId());
                startActivity(intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    //整理数据
    private void DataHelpers(List<ShopCarBean.DataBean> beanList) {
        if(groups.size()!=0&&childs.size()!=0){
            groups.clear();
            childs.clear();
        }
        tv_right.setVisibility(View.VISIBLE);
        llCart.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

        for (int i = 0; i < beanList.size(); i++) {
            ShopCarBean.DataBean dataBean = beanList.get(i);
            //添加头部信息
            OrderHeader infor = new OrderHeader();
            infor.setId(i + "");
            infor.setSpecial_promotion(dataBean.getSpecial_promotion());
            infor.setName(dataBean.getShops_name());
            infor.setSpecial_id(dataBean.getSpecial_id());
            groups.add(infor);
            List<OrderItem> goods = new ArrayList<>();
            List<ShopCarBean.DataBean.DataChildBean> childBeanList = dataBean.getData_child();
            //添加商品信息
            for (int j = 0; j < childBeanList.size(); j++) {
                ShopCarBean.DataBean.DataChildBean childBean = childBeanList.get(j);
                OrderItem gInfor = new OrderItem();
                gInfor.setId(i + "-" + j);
                gInfor.setChild_title(childBean.getChild_title());
                gInfor.setMotion_id(childBean.getMotion_id());
                gInfor.setMotion_type(childBean.getMotion_type());
                gInfor.setShop_id(childBean.getShop_id());
                gInfor.setName(childBean.getTitle());
               gInfor.setPrice(Double.parseDouble(childBean.getSeal_price()));
                gInfor.setImageUrl(childBean.getImg_url());
                gInfor.setCount(childBean.getNumber());
                gInfor.setAid(childBean.getAid());
                gInfor.setProduct_id(childBean.getPro_id());
                gInfor.setDiscount_amount(childBean.getDiscount_amount());
                goods.add(gInfor);
            }
            childs.put(groups.get(i).getId(), goods);
        }
         initLv();
    }

   //去逛逛
    @OnClick(R.id.tv_empty_Shopping)
    public void goMain(){
        startActivity(MainActivity.class);
        finish();
    }

    //全选
    @OnClick(R.id.all_checkBox)
    public void all() {
        for (int i = 0; i < groups.size(); i++) {
            OrderHeader group = groups.get(i);
            group.setChoosed(allCheckBox.isChecked());
            List<OrderItem> child = childs.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                child.get(j).setChoosed(allCheckBox.isChecked());//这里出现过错误
            }
        }
        adapter.notifyDataSetChanged();
        calulate();
    }

    //actionbar上的编缉
    @OnClick(R.id.tv_actionbar_right)
    public void edit() {
        flag = !flag;
        for (int i = 0; i < groups.size(); i++) {
            OrderHeader group = groups.get(i);
            if (group.isActionBarEditor()) {
                group.setActionBarEditor(false);
                tv_right.setText("完成");
                orderInfo.setVisibility(View.VISIBLE);
                delGoods.setVisibility(View.GONE);
                //保存数量
                SavaAllNumber();

            } else {
                group.setActionBarEditor(true);
                tv_right.setText("编辑");
                orderInfo.setVisibility(View.INVISIBLE);
                delGoods.setVisibility(View.VISIBLE);
            }

        }
        adapter.notifyDataSetChanged();

    }

    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }

    //全部删除
    @OnClick(R.id.tv_del_all_goods)
    public void deleAll() {
        if (mtotalCount == 0) {
            MyToast.makeTextAnim(MyApp.getContext(), "请选择要删除的商品", 0, Gravity.CENTER, 0, 0).show();
            return;
        }
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setMessage("确认要删除该商品吗?");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //doDelete();
                StringBuilder sb = new StringBuilder();
                for (String in : childs.keySet()) {
                    //map.keySet()返回的是所有key的值
                    List<OrderItem> list = childs.get(in);//得到每个key多对用value的值
                    for (int i = 0; i < list.size(); i++) {
                        sb.append(list.get(i).getAid()).append(",");
                    }
                }
                OkHttpUtils
                        .get()
                        .tag(this)
                        .url(Constant.baseUrl + "orders/shopping_cars_moblie.php?m=car_del")
                        .addParams("car_id", sb.toString())
                        .addParams("user_id", userId)
                        .build()
                        .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(BaseStringBean response, int id) {
                                if ("1".equals(String.valueOf(response.getStatus()))) {
                                    doDelete();
                                }
                            }
                        });

            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.go_pay)
    public void goPay() {
        if (mtotalCount == 0) {
            MyToast.makeTextAnim(MyApp.getContext(), "请选择要支付的商品", 0, Gravity.CENTER, 0, 0).show();
            return;
        }

        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("shopId", sb1.toString());
        intent.putExtra("carId", sb2.toString());
        intent.putExtra("num", sb3.toString());
        intent.putExtra("proId", sb4.toString());
        intent.putExtra("special_id", sb5.toString()); //订单优惠id
        startActivity(intent);
    }



    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        OrderHeader group = groups.get(groupPosition);
        List<OrderItem> child = childs.get(group.getId());
        for (int i = 0; i < child.size(); i++) {
            child.get(i).setChoosed(isChecked);
        }
        if (isCheckAll()) {
            allCheckBox.setChecked(true);//全选
        } else {
            allCheckBox.setChecked(false);//反选
        }
        adapter.notifyDataSetChanged();
        calulate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true; //判断该组下面的所有子元素是否处于同一状态
        OrderHeader group = groups.get(groupPosition);
        List<OrderItem> child = childs.get(group.getId());
        for (int i = 0; i < child.size(); i++) {
            //不选全中
            if (child.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }

        if (allChildSameState) {
            group.setChoosed(isChecked);//如果子元素状态相同，那么对应的组元素也设置成这一种的同一状态
        } else {
            group.setChoosed(false);//否则一律视为未选中
        }

        if (isCheckAll()) {
            allCheckBox.setChecked(true);//全选
        } else {
            allCheckBox.setChecked(false);//反选
        }

        adapter.notifyDataSetChanged();
        calulate();

    }

    /**
     * @return 判断组元素是否全选
     */
    private boolean isCheckAll() {
        for (OrderHeader group : groups) {
            if (!group.isChoosed()) {
                return false;
            }
        }
        return true;
    }

    //减
    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        OrderItem good = (OrderItem) adapter.getChild(groupPosition, childPosition);
        int count = good.getCount();
        count++;
        good.setCount(count);
        ((TextView) showCountView).setText(String.valueOf(count));
        adapter.notifyDataSetChanged();
        calulate();
    }

    //减
    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        OrderItem good = (OrderItem) adapter.getChild(groupPosition, childPosition);
        int count = good.getCount();
        if (count == 1) {
            return;
        }
        count--;
        good.setCount(count);
        ((TextView) showCountView).setText("" + count);
        adapter.notifyDataSetChanged();
        calulate();
    }


    @Override
    public void doUpdate(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        OrderItem good = (OrderItem) adapter.getChild(groupPosition, childPosition);
        int count = good.getCount();
        ((TextView) showCountView).setText(String.valueOf(count));
        adapter.notifyDataSetChanged();
        calulate();
    }

    @Override
    public void childDelete(final int groupPosition, final int childPosition, final String Aid) {
        new AlertDialog.Builder(this)
                .setMessage("确定要删除该商品吗")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OkHttpUtils
                                .get()
                                .tag(this)
                                .url(Constant.baseUrl + "orders/shopping_cars_moblie.php?m=car_del")
                                .addParams("car_id", Aid)
                                .addParams("user_id", userId)
                                .build()
                                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {

                                    }

                                    @Override
                                    public void onResponse(BaseStringBean response, int id) {
                                        if ("1".equals(String.valueOf(response.getStatus()))) {
                                            OrderHeader group = groups.get(groupPosition);
                                            List<OrderItem> child = childs.get(group.getId());
                                            child.remove(childPosition);
                                            if (child.size() == 0) {
                                                groups.remove(groupPosition);
                                            }
                                            adapter.notifyDataSetChanged();
                                            calulate();

                                        }
                                    }
                                });

                    }
                })
                .create()
                .show();
        ;
    }

    @Override
    public void modify_discounts(String shopId, String aid) {
        ModifyDiscounts(shopId, aid);
    }

    @Override
    public void groupEditor(int groupPosition, boolean isEdit) {
        if (!isEdit) {
            OrderHeader group = groups.get(groupPosition);
            List<OrderItem> child = childs.get(group.getId());

            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            StringBuilder sb4 = new StringBuilder();

            for (int i = 0; i < child.size(); i++) {
                OrderItem item = child.get(i);
                sb1.append(item.getShop_id()).append(",");
                sb2.append(item.getAid()).append(",");
                sb3.append(item.getCount()).append(",");
                sb4.append(item.getProduct_id()).append(",");
            }
            SaveNumber(sb1, sb2, sb3, sb4);
        }

    }

    //保存数量
    private void SaveNumber(StringBuilder sb1, StringBuilder sb2, StringBuilder sb3, StringBuilder sb4) {
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "orders/shopping_cars_moblie.php?m=update_number")
                .tag(this)
                .addParams("user_id", userId)
                .addParams("shop_id", sb1.toString())
                .addParams("car_id", sb2.toString())
                .addParams("number", sb3.toString())
                .addParams("pro_id", sb4.toString())
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        Log.i("修改数量返回", response.getMsg() + "____" + response.getStatus());
                    }
                });
    }


    private void SavaAllNumber() {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        for (int i = 0; i < groups.size(); i++) {
            OrderHeader group = groups.get(i);
            List<OrderItem> child = childs.get(group.getId());
            for (int j = 0; j <child.size() ; j++) {

                OrderItem item = child.get(i);
                sb1.append(item.getShop_id()).append(",");
                sb2.append(item.getAid()).append(",");
                sb3.append(item.getCount()).append(",");
                sb4.append(item.getProduct_id()).append(",");

                SaveNumber(sb1, sb2, sb3, sb4);
            }
        }

    }
    /**
     * 修改查询促销信息
     */
    private void ModifyDiscounts(String shopId, final String aid) {
        String url = Constant.baseUrl + "orders/shopping_cars_moblie.php?m=update_type_list" + "&shop_id=" + shopId + "&car_id=" + aid + "&user_id=" + userId;
        Log.i("购物车查询促销", url);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/shopping_cars_moblie.php?m=update_type_list")
                .addParams("shop_id", shopId)
                .addParams("car_id", aid)
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<DiscountsBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(DiscountsBean response, int id) {
                        if ("1".equals(response.getStatus())) {
                            List<DiscountsBean.DataBean> beanList = response.getData();
                            final Modify_DiscountsPop mdp = new Modify_DiscountsPop(MyApp.getContext(), beanList);
                            mdp.showPow(parent);
                            mdp.setModity(new Modify_DiscountsPop.ModityDiscount() {
                                @Override
                                public void modity(String cxid, String zpid) {
                                    Log.i("修改促销", Constant.baseUrl + "orders/shopping_cars_moblie.php?m=update_promotion" + "&user_id=" + userId + "&motion_id=" + cxid + "&motion_zpid" + zpid + "&car_id=" + aid);
                                    OkHttpUtils
                                            .get()
                                            .url(Constant.baseUrl + "orders/shopping_cars_moblie.php?m=update_promotion")
                                            .addParams("user_id", userId)
                                            .addParams("car_id", aid)
                                            .addParams("motion_id", cxid)
                                            .addParams("motion_zpid", zpid)
                                            .build()
                                            .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                                                @Override
                                                public void onError(Call call, Exception e, int id) {

                                                }

                                                @Override
                                                public void onResponse(BaseStringBean response, int id) {
                                                    if ("1".equals(response.getStatus() + "")) {
                                                        mdp.dismiss();
                                                        getData();
                                                    }
                                                }
                                            });
                                }
                            });
                        }
                    }
                });
    }


    //计算总价
    private void calulate() {
        mtotalPrice = 0.00;
        mtotalCount = 0;
        discounts_total=0.00;

        sb1 = new StringBuilder();
        sb2 = new StringBuilder();
        sb3 = new StringBuilder();
        sb4 = new StringBuilder();
        sb5=new StringBuilder();
        for (int i = 0; i < groups.size(); i++) {
            OrderHeader group = groups.get(i);
            List<OrderItem> child = childs.get(group.getId());
            sb5.append(group.getSpecial_id()).append(",");
            for (int j = 0; j < child.size(); j++) {
                OrderItem good = child.get(j);
                if (good.isChoosed()) {
                    //总数量
                    mtotalCount++;
                    //总价钱
                    mtotalPrice += good.getPrice() * good.getCount();
                    //总优惠
                    discounts_total+=good.getDiscount_amount();
                    //拼接shopId
                    sb1.append(good.getShop_id()).append(",");
                    //拼接carId
                    sb2.append(good.getAid()).append(",");
                    //数量
                    sb3.append(good.getCount()).append(",");
                    //产品id
                    sb4.append(good.getProduct_id()).append(",");
                }
            }
        }
        String entPrice= StringUtil.doubleConvert(mtotalPrice);
        totalPrice.setText(Html.fromHtml("合计: "+"<font color='#FA3314'>"+"¥"+entPrice+"</font>"));
        tv_discounts_total.setText(Html.fromHtml("优惠: "+"<font color='#FA3314'>"+"¥"+discounts_total+"</font>"));
        goPay.setText("去结算(" + mtotalCount + ")");
        if (mtotalCount == 0) {
            setCartNum();
        }

    }

    //重置购物车数量
    private void setCartNum() {
        int count = 0;
        for (int i = 0; i < groups.size(); i++) {
            OrderHeader group = groups.get(i);
            group.setChoosed(allCheckBox.isChecked());
            List<OrderItem> Childs = childs.get(group.getId());
            for (OrderItem childs : Childs) {
                count++;
            }
        }
        //购物车已经清空
        if (count == 0) {
           getGuess();
        }
    }


    /**
     * 删除操作
     * 1.不要边遍历边删除,容易出现数组越界的情况
     * 2.把将要删除的对象放进相应的容器中，待遍历完，用removeAll的方式进行删除
     */
    private void doDelete() {
        List<OrderHeader> toBeDeleteGroups = new ArrayList<>(); //待删除的组元素
        for (int i = 0; i < groups.size(); i++) {
            OrderHeader group = groups.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            List<OrderItem> toBeDeleteChilds = new ArrayList<OrderItem>();//待删除的子元素
            List<OrderItem> child = childs.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                if (child.get(j).isChoosed()) {
                    toBeDeleteChilds.add(child.get(j));
                }
            }
            child.removeAll(toBeDeleteChilds);
        }
        groups.removeAll(toBeDeleteGroups);
        //重新设置购物车
        setCartNum();
        adapter.notifyDataSetChanged();
    }


}
