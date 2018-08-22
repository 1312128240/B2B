package car.tzxb.b2b.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
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
import car.tzxb.b2b.Bean.OrderBeans.GoodsOrderInfo;
import car.tzxb.b2b.Bean.OrderBeans.OrderGoodsItem;
import car.tzxb.b2b.Bean.OrderBeans.OrderPayInfo;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.Order.LookOrderActivity;

/**
 * Created by Administrator on 2018/8/21 0021.
 */

public class BrowhistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> data;
    private int ITEM_HEADER = 1, ITEM_CONTENT = 2;
    private boolean isShow;
    public BrowhistoryAdapter(Context context, List<Object> data) {
        this.context = context;
        this.data = data;
    }

    OnItemClick listener;
    public void setOnItemClick(OnItemClick click){
        this.listener=click;
    }
    //增加
    public void add(List<Object> datas, boolean isClean) {
        if (isClean) {
            data.clear();
        }
        data.addAll(datas);

        notifyDataSetChanged();
    }

    public void isShow(boolean b){
         this.isShow=b;
          notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==ITEM_HEADER){
            view=LayoutInflater.from(context).inflate(R.layout.tv_item,parent,false);
            return new MyViewHolderHeader(view);
        }else if(viewType==ITEM_CONTENT){
            view=LayoutInflater.from(context).inflate(R.layout.commn_item,parent,false);
            return new MyViewHolderContent(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolderHeader) {
            GoodsOrderInfo datas = (GoodsOrderInfo) data.get(position);
            ((MyViewHolderHeader) holder).tv_date.setPadding(20,15,0,15);
            ((MyViewHolderHeader) holder).tv_date.setText(datas.getTime());
        }else if(holder instanceof MyViewHolderContent){
             final OrderGoodsItem datas= (OrderGoodsItem) data.get(position);
             Glide.with(context).load(datas.getImg_url()).into(((MyViewHolderContent) holder).iv_goods);
             ((MyViewHolderContent) holder).tv_type.setVisibility(View.GONE);
             ((MyViewHolderContent) holder).tv_name.setText(datas.getProduct_title());
             ((MyViewHolderContent) holder).tv_price.setText(Html.fromHtml("¥"+"<big>"+datas.getReal_price()));
             ((MyViewHolderContent) holder).iv_go_xq.setVisibility(View.VISIBLE);
             ((MyViewHolderContent) holder).iv_go_xq.setImageResource(R.mipmap.commodity_icon_atc2);

             if(isShow){
                 ((MyViewHolderContent) holder).tv_dele.setVisibility(View.VISIBLE);
             }else {
                 ((MyViewHolderContent) holder).tv_dele.setVisibility(View.GONE);
             }

             ((MyViewHolderContent) holder).parent.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      listener.goxq(datas.getId());
                  }
              });

             ((MyViewHolderContent) holder).tv_dele.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     listener.dele(datas.getId());
                 }
             });
        }

    }



    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof GoodsOrderInfo) {
            return ITEM_HEADER;
        } else if (data.get(position) instanceof OrderGoodsItem) {
            return ITEM_CONTENT;
        }

        return ITEM_CONTENT;
    }

    /**
     * item第一部分
     */
    class MyViewHolderHeader extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item)
        TextView tv_date;
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
        @BindView(R.id.tv_goods_type) TextView tv_type;
        @BindView(R.id.iv_gwc_icon) ImageView iv_go_xq;
        @BindView(R.id.commn_item_parent) RelativeLayout parent;
        @BindView(R.id.tv_del_browhistory) TextView tv_dele;
        public MyViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        return count;
    }

    public interface  OnItemClick{
        void goxq(String id);

        void dele(String id);
    }
}
