package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;


/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class AddShoppingCarPop extends PopupWindow {

    public AddShoppingCarPop(Context context) {
        super(context);
        initPop(context);
    }

    private void initPop(Context context) {
        View view= LayoutInflater.from(context).inflate(R.layout.add_shoppingcar_layout,null);

        final View top=view.findViewById(R.id.add_shoppingcar_top);
        setContentView(view);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setClippingEnabled(false);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x3f000000);
        setBackgroundDrawable(dw);
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setOutsideTouchable(true);

        view.setOnTouchListener(new View.OnTouchListener() {
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
    public void show(View parent){

        if(DeviceUtils.checkDeviceHasNavigationBar(MyApp.getContext())){
            int navgationHeight=DeviceUtils.getNavigationBarHeight(MyApp.getContext());
            showAtLocation(parent, Gravity.BOTTOM,0,navgationHeight);
        }else {
            showAtLocation(parent,Gravity.BOTTOM,0,0);
        }

    }
}
