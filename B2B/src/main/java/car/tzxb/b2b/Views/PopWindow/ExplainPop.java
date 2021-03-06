package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;


/**
 * Created by Administrator on 2018/6/21 0021.
 */

public class ExplainPop extends PopupWindow {

    private static volatile ExplainPop mInstance;
    private Context mContext;

    public ExplainPop(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public static ExplainPop getmInstance(Context context) {
        if (mInstance == null) {
            synchronized (ExplainPop.class) {
                if (mInstance == null) {
                    mInstance = new ExplainPop(context);
                }
            }
        }
        return mInstance;
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.goods_xq_explain, null);
        setContentView(view);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setClippingEnabled(false);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x3f000000);
        setBackgroundDrawable(dw);
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setOutsideTouchable(true);
        final View top = view.findViewById(R.id.ll_goods_xq_explain_top);
        TextView btn = view.findViewById(R.id.btn_colse_goodsxq_explain);
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

  /*  public void show(View parent) {
        Log.i("有底部导航栏", DeviceUtils.checkDeviceHasNavigationBar(MyApp.getContext()) + "");
        if (DeviceUtils.checkDeviceHasNavigationBar(MyApp.getContext())) {
            int navigationHeight = DeviceUtils.getNavigationBarHeight(mContext);
            showAtLocation(parent, Gravity.BOTTOM, 0, navigationHeight);
        } else {
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }
    }*/
}
