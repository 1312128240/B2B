package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.Bean.GoodsXqBean;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;

/**
 * Created by Administrator on 2018/7/28 0028.
 */

public class DiscountsPop extends PopupWindow {
    private Context mContext;
    private List<GoodsXqBean.DataBean.ProductBean.PromotionBean> list;

    public DiscountsPop(Context context, List<GoodsXqBean.DataBean.ProductBean.PromotionBean> lists) {
        super(context);
        this.mContext = context;
        this.list = lists;
        initPop();
    }

    private void initPop() {
        final View popView = LayoutInflater.from(mContext).inflate(R.layout.discounts_pop, null);
        RecyclerView recyclerView = popView.findViewById(R.id.recy_discounts_pop);
        final View top = popView.findViewById(R.id.ll_discounts_top);
        Button btn=popView.findViewById(R.id.btn_exit_discount);
        setContentView(popView);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setClippingEnabled(false);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x3f000000);
        setBackgroundDrawable(dw);
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setOutsideTouchable(true);
        popView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = top.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initRecy(recyclerView);
    }

    private void initRecy(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        final GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(6);
        gd.setColor(Color.parseColor("#ffb952"));

        CommonAdapter<GoodsXqBean.DataBean.ProductBean.PromotionBean> adapter=new CommonAdapter<GoodsXqBean.DataBean.ProductBean.PromotionBean>(mContext,R.layout.discounts_item,list) {
            @Override
            protected void convert(ViewHolder holder, GoodsXqBean.DataBean.ProductBean.PromotionBean bean, int position) {
               //优惠条件
                holder.setText(R.id.tv_discounts_title,bean.getTitle());
                TextView tv_type=holder.getView(R.id.tv1);
                tv_type.setVisibility(View.VISIBLE);
                String type=bean.getType();
                tv_type.setBackground(gd);
                if("mzeng".equals(type)){
                    tv_type.setText("满赠");
                }else if("mzhe".equals(type)){
                    tv_type.setText("满折");
                }else if("mjt".equals(type)||"mjy".equals(type)){
                    tv_type.setText("满减");
                }
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public void showPow(View parent) {
        if (DeviceUtils.checkDeviceHasNavigationBar(mContext)) {
            int navigationHeight = DeviceUtils.getNavigationBarHeight(mContext);
            showAtLocation(parent, Gravity.BOTTOM, 0, navigationHeight);
        } else {
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }

    }

}