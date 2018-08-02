package car.tzxb.b2b.Uis.Order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.OrderBean;
import car.tzxb.b2b.Bean.OrderXqBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;

public class GoodsListActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.recy_order_goods_list)
    RecyclerView recyclerview;
    private OrderBean.DataBean bean;
    private List<OrderBean.DataBean.GoodsBean> goodsBeanList=new ArrayList<>();
    private String from;

    @Override
    public void initParms(Bundle parms) {
        from = getIntent().getStringExtra("from");

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_goods_list;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("商品清单");
        if("Order".equals(from)){
            initRecy1();
        }else {
            initRecy2();
        }

    }

    /**
     * 订单详情过来
     */
    private void initRecy2() {
        OrderXqBean.DataBean.OrderDetailsBean xqBean= (OrderXqBean.DataBean.OrderDetailsBean) getIntent().getSerializableExtra("bean");
        List<OrderXqBean.DataBean.OrderDetailsBean> tempList=new ArrayList<>();
        tempList.add(xqBean);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<OrderXqBean.DataBean.OrderDetailsBean> adapter=new CommonAdapter<OrderXqBean.DataBean.OrderDetailsBean>(MyApp.getContext(),R.layout.order_list_item,tempList) {
            @Override
            protected void convert(ViewHolder holder, final OrderXqBean.DataBean.OrderDetailsBean goodsBean, int position) {
                int p=DeviceUtils.dip2px(MyApp.getContext(),10);
                View view=holder.getView(R.id.parent);
                view.setPadding(p,0,p,0);
                //店名
                holder.setText(R.id.tv_order_status_shop_name,goodsBean.getShop_name());
                //隐藏多余控件
                holder.getView(R.id.tv_order_status_total).setVisibility(View.GONE);
                holder.getView(R.id.ll_order_status_infor).setVisibility(View.GONE);
                //留言
                EditText et_mesg=holder.getView(R.id.et_mesg);
                et_mesg.setClickable(false);
                holder.setText(R.id.et_mesg,goodsBean.getMessage());
                //内嵌布局
                RecyclerView recy=holder.getView(R.id.recy_order_inner);
                recy.setLayoutManager(new LinearLayoutManager(MyApp.getContext()));
                List<OrderXqBean.DataBean.OrderDetailsBean.ChildDataBean> lists=goodsBean.getChild_data();
                CommonAdapter<OrderXqBean.DataBean.OrderDetailsBean.ChildDataBean> InnerAdapter=
                        new CommonAdapter<OrderXqBean.DataBean.OrderDetailsBean.ChildDataBean>(MyApp.getContext(),R.layout.commn_item,lists) {
                    @Override
                    protected void convert(ViewHolder holder, OrderXqBean.DataBean.OrderDetailsBean.ChildDataBean dataChildBean, int position) {
                        RelativeLayout parent=holder.getView(R.id.commn_item_parent);
                        parent.setBackgroundColor(Color.parseColor("#F5F5F5"));
                        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,0,0,10);
                        parent.setLayoutParams(params);
                        //图片
                        int i= DeviceUtils.dip2px(MyApp.getContext(),65);
                        ImageView iv=holder.getView(R.id.iv_category);
                        Glide.with(MyApp.getContext()).load(dataChildBean.getImg_url()).override(i,i).into(iv);
                        //名字
                        holder.setText(R.id.tv_catagroy_name,dataChildBean.getProduct_title());
                        //价格
                        holder.setText(R.id.tv_category_pice,"¥"+dataChildBean.getReal_price());
                        //数量
                        holder.setText(R.id.tv_goods_count,"X"+dataChildBean.getQuantity());
                        //隐藏类型
                        holder.getView(R.id.tv_goods_type).setVisibility(View.INVISIBLE);
                    }
                };
                recy.setAdapter(InnerAdapter);
            }
        };
        recyclerview.setAdapter(adapter);
    }

    /**
     * 下订单时过来
     */
    private void initRecy1() {
        bean = (OrderBean.DataBean) getIntent().getSerializableExtra("bean");
        goodsBeanList = bean.getGoods();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<OrderBean.DataBean.GoodsBean> adapter=new CommonAdapter<OrderBean.DataBean.GoodsBean>(MyApp.getContext(),R.layout.order_list_item, goodsBeanList) {
            @Override
            protected void convert(ViewHolder holder, final OrderBean.DataBean.GoodsBean goodsBean, int position) {
                int p=DeviceUtils.dip2px(MyApp.getContext(),10);
                View view=holder.getView(R.id.parent);
                view.setPadding(p,0,p,0);
                     //店名
                holder.setText(R.id.tv_order_status_shop_name,goodsBean.getShop_name());
                //隐藏多余控件
                holder.getView(R.id.tv_order_status_total).setVisibility(View.GONE);
                holder.getView(R.id.ll_order_status_infor).setVisibility(View.GONE);

                //留言
                  EditText et=holder.getView(R.id.et_mesg);
                 et.addTextChangedListener(new TextWatcher() {
                     @Override
                     public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                     }

                     @Override
                     public void onTextChanged(CharSequence s, int start, int before, int count) {

                     }

                     @Override
                     public void afterTextChanged(Editable s) {
                           goodsBean.setMesg(s.toString());
                     }
                 });
                //内嵌布局
                RecyclerView recy=holder.getView(R.id.recy_order_inner);
                recy.setLayoutManager(new LinearLayoutManager(MyApp.getContext()));
                List<OrderBean.DataBean.GoodsBean.DataChildBean> lists=goodsBean.getData_child();
                CommonAdapter<OrderBean.DataBean.GoodsBean.DataChildBean> InnerAdapter= new CommonAdapter<OrderBean.DataBean.GoodsBean.DataChildBean>(MyApp.getContext(),R.layout.commn_item,lists) {
                    @Override
                    protected void convert(ViewHolder holder, OrderBean.DataBean.GoodsBean.DataChildBean dataChildBean, int position) {
                        RelativeLayout parent=holder.getView(R.id.commn_item_parent);
                        parent.setBackgroundColor(Color.parseColor("#F5F5F5"));
                        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,0,0,10);
                        parent.setLayoutParams(params);
                         //图片
                        int i= DeviceUtils.dip2px(MyApp.getContext(),65);
                        ImageView iv=holder.getView(R.id.iv_category);
                        Glide.with(MyApp.getContext()).load(dataChildBean.getImg_url()).override(i,i).into(iv);
                        //名字
                        holder.setText(R.id.tv_catagroy_name,dataChildBean.getTitle());
                        //价格
                        holder.setText(R.id.tv_category_pice,"¥"+dataChildBean.getSeal_price());
                        //数量
                        holder.setText(R.id.tv_goods_count,"X"+dataChildBean.getNumber());
                        //隐藏类型
                        holder.getView(R.id.tv_goods_type).setVisibility(View.INVISIBLE);

                    }
                };
                recy.setAdapter(InnerAdapter);
            }
        };
        recyclerview.setAdapter(adapter);

    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {

        onBackPressed();

    }

    @Override
    public void onBackPressed() {
        if("Order".equals(from)){
            StringBuilder sb=new StringBuilder();
            for (int i = 0; i <goodsBeanList.size() ; i++) {
                OrderBean.DataBean.GoodsBean bean=goodsBeanList.get(i);
                sb.append(bean.getMesg());
            }
            Intent intent=new Intent();
            intent.putExtra("mesg",sb.toString());
            setResult(RESULT_OK,intent);
        }
        super.onBackPressed();
    }
}
