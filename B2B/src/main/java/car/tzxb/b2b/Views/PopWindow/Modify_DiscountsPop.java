package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.List;

import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.Bean.GoodsXqBean;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;

/**
 * Created by Administrator on 2018/7/28 0028.
 */

public class Modify_DiscountsPop extends PopupWindow {
    private Context mContext;
  // private List<GoodsXqBean.DataBean.ProductBean.PromotionBean> list;

    public Modify_DiscountsPop(Context context) {
        super(context);
        this.mContext = context;
       // this.list = lists;
        initPop();
    }

    private void initPop() {
        final View popView = LayoutInflater.from(mContext).inflate(R.layout.modify_disconts_pop, null);
        RecyclerView recyclerView = popView.findViewById(R.id.recy_modify_disconts);
        final View top = popView.findViewById(R.id.ll_modify_disconts_top);
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