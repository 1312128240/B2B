package car.tzxb.b2b.Adapter.GrildTitleAdpaterPackage;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.commonsdk.debug.W;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import car.myview.CircleImageView.CircleImageView;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;

/**
 * Created by Administrator on 2018/8/27 0027.
 */

public class GrildTitleAdapter extends RecyclerView.Adapter {
    private List<GrideData> list;

    public GrildTitleAdapter(List<GrideData> list) {
        this.list = list;
    }

    ClickListener listener;
    public void SetClickListener(ClickListener clickListener){
        this.listener=clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return  list.get(position).viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iv_layout, parent,false);
            GrideHolder holder = new GrideHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_item, parent,false);
            LinearHolder holder = new LinearHolder(view);
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GrideData grideData = list.get(position);
        if (grideData.viewType ==1) {
            GrideHolder holder1 = (GrideHolder) holder;

            final int i= DeviceUtils.dip2px(MyApp.getContext(),45);
            final int m= DeviceUtils.dip2px(MyApp.getContext(),20);
            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,m,0,m);
            holder1.parent.setLayoutParams(layoutParams);
            Glide.with(MyApp.getContext()).load(grideData.getImg_url()).asBitmap().override(i,i).into(holder1.iv);
            holder1.textView.setText(grideData.getTitle());
            //点击事件
            holder1.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.click(grideData.getId(),grideData.from);
                }
            });
        } else {
            LinearHolder holder1 = (LinearHolder) holder;
            holder1.tv_title.setPadding(20,15,0,15);
            holder1.tv_title.setTextColor(Color.parseColor("#303030"));
            holder1.tv_title.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((LinearHolder) holder).tv_title.setBackgroundColor(Color.parseColor("#f2f2f2"));
            holder1.tv_title.setText(grideData.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class GrideHolder extends RecyclerView.ViewHolder{
       @BindView(R.id.iv_layout_title)
       TextView textView;
        @BindView(R.id.iv_item)
        CircleImageView iv;
        @BindView(R.id.iv_layout_parent)
        View parent;
        public GrideHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    public static class LinearHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_item)
        TextView tv_title;
        public LinearHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface ClickListener{
        void click(String id,String from);
    }
}
