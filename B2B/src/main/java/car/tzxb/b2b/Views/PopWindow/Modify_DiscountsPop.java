package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.Adapter.ShoppinCarDiscountsAdapter;
import car.tzxb.b2b.Bean.DiscountsBean;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;

/**
 * Created by Administrator on 2018/7/28 0028.
 */

public class Modify_DiscountsPop extends PopupWindow {
    private Context mContext;
  //  private RecyclerView recyclerView;
    private List<DiscountsBean.DataBean> lists;
    private int temPosition=0;
    public Modify_DiscountsPop(Context context, List<DiscountsBean.DataBean> beanList) {
        super(context);
        this.mContext = context;
        this.lists=beanList;
        initPop();
    }

    private void initPop() {
        final View popView = LayoutInflater.from(mContext).inflate(R.layout.modify_disconts_pop, null);
        ListView listView= popView.findViewById(R.id.recy_modify_disconts);
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
        ShoppinCarDiscountsAdapter adapter=new ShoppinCarDiscountsAdapter(mContext,lists);
        listView.setAdapter(adapter);

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