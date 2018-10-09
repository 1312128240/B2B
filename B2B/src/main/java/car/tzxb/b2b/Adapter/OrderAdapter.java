package car.tzxb.b2b.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import car.tzxb.b2b.Bean.OrderBeans.OrderHeader;
import car.tzxb.b2b.Bean.OrderBeans.OrderItem;
import car.tzxb.b2b.Bean.OrderBeans.OrderFooter;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;

/**
 * Created by Administrator on 2016/11/9.
 */

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> data;
    private int ITEM_HEADER = 1, ITEM_CONTENT = 2, ITEM_FOOTER = 3;
    public TextClickListener listener;

    public void setTextClickListener(TextClickListener textClickListener){
        this.listener=textClickListener;
    }

    public OrderAdapter(Context context, List<Object> data) {
        this.context = context;
        this.data = data;
    }

    //增加
    public void add(List<Object> datas, boolean isClean) {
        if (isClean) {
            data.clear();
        }
        data.addAll(datas);

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.order_header_item, parent, false);
            return new OrderAdapter.MyViewHolderHeader(view);
        } else if (viewType == ITEM_CONTENT) {
            view = LayoutInflater.from(context).inflate(R.layout.commn_item, parent, false);
            return new OrderAdapter.MyViewHolderContent(view);
        } else if (viewType == ITEM_FOOTER) {
            view = LayoutInflater.from(context).inflate(R.layout.order_footer_item, parent, false);
            return new OrderAdapter.MyViewHolderFooter(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyViewHolderHeader) {
            OrderHeader datas = (OrderHeader) data.get(position);
            //店名
            ((OrderAdapter.MyViewHolderHeader) holder).tvAllOrderItemShopName.setText(datas.getShopName());
            //订单状态
            ((MyViewHolderHeader) holder).tv_order_status_describe.setText(datas.getStatus());
        } else if (holder instanceof MyViewHolderContent) {
            //灰色背景
            ((MyViewHolderContent) holder).parent.setBackgroundColor(Color.parseColor("#f5f5f5"));
            int h= DeviceUtils.dip2px(MyApp.getContext(),100);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h);
            params.setMargins(0,0,0,5);
            ((MyViewHolderContent) holder).parent.setLayoutParams(params);

            final OrderItem datas = (OrderItem) data.get(position);
            //图片
            Glide.with(context).load(datas.getImg_url()).into(((MyViewHolderContent) holder).iv_goods);
            //名字
            ((MyViewHolderContent) holder).tv_name.setText(datas.getProduct_title());
            //价格
            ((MyViewHolderContent) holder).tv_price.setText("¥" + datas.getReal_price());
            //数量
            ((MyViewHolderContent) holder).tv_count.setText("x" + datas.getQuantity());
            //隐藏控件
            ((MyViewHolderContent) holder).tv_type.setVisibility(View.GONE);
            //item点击事件
             ((MyViewHolderContent) holder).parent.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     listener.itemClick(datas.getAid());
                 }
             });


        } else if (holder instanceof MyViewHolderFooter) {
            final OrderFooter datas = (OrderFooter) data.get(position);
            ((MyViewHolderFooter) holder).tv_number.setText("共" + datas.getNumber() + "种,");
            ((MyViewHolderFooter) holder).tv_total_price.setText("合计: ¥" + datas.getAmount_pay_able());
            if ("等待付款".equals(datas.getStatus())) {    //待付款
                ((MyViewHolderFooter) holder).tv1.setVisibility(View.VISIBLE);
                ((MyViewHolderFooter) holder).tv2.setVisibility(View.VISIBLE);
                ((MyViewHolderFooter) holder).tv1.setText("付款");
                ((MyViewHolderFooter) holder).tv2.setText("取消订单");
                ((MyViewHolderFooter) holder).tv3.setVisibility(View.GONE);
            } else if ("等待发货".equals(datas.getStatus())) { //待发货
                ((MyViewHolderFooter) holder).tv1.setVisibility(View.VISIBLE);
                ((MyViewHolderFooter) holder).tv1.setText("提醒发货");
                ((MyViewHolderFooter) holder).tv2.setVisibility(View.GONE);
                ((MyViewHolderFooter) holder).tv3.setVisibility(View.GONE);

            } else if ("商家已发货".equals(datas.getStatus())) { //待收货
                ((MyViewHolderFooter) holder).tv1.setVisibility(View.VISIBLE);
                ((MyViewHolderFooter) holder).tv2.setVisibility(View.VISIBLE);
                 ((MyViewHolderFooter) holder).tv3.setVisibility(View.GONE);
                ((MyViewHolderFooter) holder).tv1.setText("确认收货");
                ((MyViewHolderFooter) holder).tv2.setText("物流详情");
            } else if ("交易成功".equals(datas.getStatus())) { //待评价
                ((MyViewHolderFooter) holder).tv1.setVisibility(View.VISIBLE);
                ((MyViewHolderFooter) holder).tv2.setVisibility(View.VISIBLE);
                ((MyViewHolderFooter) holder).tv3.setVisibility(View.VISIBLE);
                ((MyViewHolderFooter) holder).tv1.setText("晒单评价");
                ((MyViewHolderFooter) holder).tv2.setText("物流详情");
                ((MyViewHolderFooter) holder).tv3.setText("删除订单");
            } else if ("已取消".equals(datas.getStatus())) {
                 ((MyViewHolderFooter) holder).tv1.setVisibility(View.VISIBLE);
                 ((MyViewHolderFooter) holder).tv2.setVisibility(View.GONE);
                 ((MyViewHolderFooter) holder).tv3.setVisibility(View.GONE);
                 ((MyViewHolderFooter) holder).tv1.setText("删除订单");
            }

            ((MyViewHolderFooter) holder).tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.click1(datas.getStatus(),datas.getIndex(),datas.getOrder_seqno(),datas.getAmount_pay_able(),datas.getAid());
                }
            });

            ((MyViewHolderFooter) holder).tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.click2(datas.getStatus(),datas.getAid());
                }
            });

            ((MyViewHolderFooter) holder).tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.click3(datas.getStatus(),datas.getIndex(),datas.getAid());
                }
            });
            ((MyViewHolderFooter) holder).tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.click4(datas.getProId(),datas.getCount(),datas.getShop_id());
                }
            });


        }

    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof OrderHeader) {
            return ITEM_HEADER;
        } else if (data.get(position) instanceof OrderItem) {
            return ITEM_CONTENT;
        } else if (data.get(position) instanceof OrderFooter) {
            return ITEM_FOOTER;
        }
        return ITEM_CONTENT;
    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
     /*   if (headerView != null) {
            count++;
        }*/
        return count;
    }


    /**
     * item第一部分
     */
    class MyViewHolderHeader extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_order_status_shop_name)
        TextView tvAllOrderItemShopName;
        @BindView(R.id.tv_order_status_describe)
        TextView tv_order_status_describe;

        public MyViewHolderHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 商品内容
     */
    class MyViewHolderContent extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_category) ImageView iv_goods;
        @BindView(R.id.tv_catagroy_name) TextView tv_name;
        @BindView(R.id.tv_category_pice) TextView tv_price;
        @BindView(R.id.tv_goods_count) TextView tv_count;
        @BindView(R.id.tv_goods_type) TextView tv_type;
        @BindView(R.id.commn_item_parent) RelativeLayout parent;
        public MyViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * item第三部分内容
     */
    class MyViewHolderFooter extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_order_status_number) TextView tv_number;
        @BindView(R.id.tv_order_status_total) TextView tv_total_price;
        @BindView(R.id.tv_view1) TextView tv1;
        @BindView(R.id.tv_view2) TextView tv2;
        @BindView(R.id.tv_view3) TextView tv3;
        @BindView(R.id.tv_view4) TextView tv4;
        public MyViewHolderFooter(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


  /*  public View getHeaderView() {
        return headerView;
    }*/

    public interface TextClickListener{
        void click1(String status,int position,String Order_seqno,double pay,String aid);

        void click2(String status,String aid);

        void click3(String status,int index,String adid);

        void click4(String proId,String number,String shopId);

        void itemClick(String aid);


    }
}
