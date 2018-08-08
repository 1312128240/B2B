package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lljjcoder.citypickerview.widget.wheel.OnWheelChangedListener;
import com.lljjcoder.citypickerview.widget.wheel.WheelView;
import com.lljjcoder.citypickerview.widget.wheel.adapters.ArrayWheelAdapter;

import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;


/**
 * Created by Administrator on 2018/3/30 0030.
 */

public class tjr_province extends PopupWindow  implements OnWheelChangedListener,View.OnClickListener {
    private Context mContex;
    private String [] str;
    private int index;

    public tjr_province(Context context, String[] str) {
        super(context);
        this.mContex =context;
        this.str = str;
        initUi();
    }

    private void initUi() {
      final   View  view = LayoutInflater.from(mContex).inflate(R.layout.province_wheel,null);
        setContentView(view);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0x7f000000);
        setBackgroundDrawable(dw);
        setFocusable(true);
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setClippingEnabled(false);
        WheelView wh= view.findViewById(R.id.whv_md);
        TextView tv_sure=view.findViewById(R.id.tv_md_popuw_sure);
        TextView tv_cancle=view.findViewById(R.id.tv_md_popuw_cancle);
        final RelativeLayout top= view.findViewById(R.id.rl_md_popo_parent);
        //外部可点击
        setOutsideTouchable(true);
       view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height= top.getTop();
                int y= (int) event.getY();
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

        wh.setViewAdapter(new ArrayWheelAdapter<>(mContex,str));
        wh.setCyclic(false);
        wh.setVisibleItems(5);
        wh.setCurrentItem(0);
        wh.addChangingListener(this);

        tv_cancle.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        index = newValue;
    }
     OnWheelListener  onWheelListener;
    public void setListener(OnWheelListener wheelListener){
        this.onWheelListener=wheelListener;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_md_popuw_sure:
                onWheelListener.onWheelPosition(index);
                dismiss();
                break;
            case R.id.tv_md_popuw_cancle:
                dismiss();
                break;
        }
    }

    public void showPow(View parent) {
        int navigationBarHeight= DeviceUtils.getNavigationBarHeight(mContex);
        boolean b= DeviceUtils.checkDeviceHasNavigationBar(mContex);
        if (b == true) {
            showAtLocation(parent, Gravity.BOTTOM, 0, navigationBarHeight);
        } else {
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }

    }


    public interface OnWheelListener{
      void onWheelPosition(int position);
    }
}
