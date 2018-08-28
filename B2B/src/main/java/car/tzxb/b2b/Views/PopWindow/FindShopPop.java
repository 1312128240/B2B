package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import java.util.List;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.TagTextViewItem;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.R;

/**
 * Created by Administrator on 2018/8/21 0021.
 */

public class FindShopPop extends PopupWindow {
    private Context mContext;
    private  List<BaseDataBean.DataBean.CategoryBean> lists;
    private RecyclerView recyclerView;

    public FindShopPop(Context context, List<BaseDataBean.DataBean.CategoryBean> cateList) {
        super(context);
        this.mContext = context;
        this.lists=cateList;
        initView();
    }

    ItemClickListener listener;
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.listener=itemClickListener;
    }

    private void initView() {

        LinearLayout linearLayout=new LinearLayout(mContext);
        recyclerView = new RecyclerView(mContext);
        recyclerView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        recyclerView.setPadding(10,20,10,20);
        linearLayout.addView(recyclerView);
        setContentView(linearLayout);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setClippingEnabled(false);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x3f000000);
        setBackgroundDrawable(dw);
        setAnimationStyle(R.style.mypopwindow_anim_style2);
        setOutsideTouchable(true);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = recyclerView.getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        initRecy();
    }

    private void initRecy() {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        final CommonAdapter<BaseDataBean.DataBean.CategoryBean> adapter=new CommonAdapter<BaseDataBean.DataBean.CategoryBean>(mContext,R.layout.tv_item,lists) {
            @Override
            protected void convert(ViewHolder holder, BaseDataBean.DataBean.CategoryBean categoryBean, int position) {
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TagTextViewItem tv=holder.getView(R.id.tv_item);
                layoutParams.setMargins(10,0,10,10);
                tv.setLayoutParams(layoutParams);
                tv.setGravity(Gravity.CENTER);
                tv.setText(categoryBean.getCategory_name());
                tv.setCheckTextColor(Color.parseColor("#FEBB66"));
                tv.setUncheckTextColor(Color.parseColor("#303030"));
                tv.setPadding(0,15,0,15);
                tv.setCheckBackgroundDrawable(mContext.getDrawable(R.drawable.bg13));
                tv.setUncheckbackgroundDrawable(mContext.getDrawable(R.drawable.bg3));
                tv.setChecked(categoryBean.isCheck());
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                for (int i = 0; i <lists.size(); i++) {
                    if (i == position) {
                        lists.get(i).setCheck(true);

                    } else {
                        lists.get(i).setCheck(false);
                    }
                }
                adapter.notifyDataSetChanged();
                dismiss();
                String cateId=lists.get(position).getId();
                listener.click(cateId);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    public interface ItemClickListener{
        void click(String cateId);
    }
}
