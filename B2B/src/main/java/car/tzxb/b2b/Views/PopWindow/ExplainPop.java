package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mylibrary.HttpClient.OkHttpUtils;

import car.tzxb.b2b.R;


/**
 * Created by Administrator on 2018/6/21 0021.
 */

public class ExplainPop extends PopupWindow {
     private static volatile ExplainPop mInstance;
    public ExplainPop(Context context) {
        super(context);
        initView(context);
    }

    public static ExplainPop initClient(Context context) {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ExplainPop(context);
                }
            }
        }
        return mInstance;
    }

    private void initView(Context context) {
        View view= LayoutInflater.from(context).inflate(R.layout.goods_xq_explain,null);
        setContentView(view);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setClippingEnabled(false);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x3f000000);
        setBackgroundDrawable(dw);
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setOutsideTouchable(true);
        final View top=view.findViewById(R.id.ll_goods_xq_explain_top);
        TextView btn=view.findViewById(R.id.btn_colse_goodsxq_explain);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height =top.getTop();
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

}
