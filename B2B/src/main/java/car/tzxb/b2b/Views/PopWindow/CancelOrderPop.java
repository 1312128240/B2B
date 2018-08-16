package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;

import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;


/**
 * Created by Administrator on 2018/5/5 0005.
 */

public class CancelOrderPop extends PopupWindow {

    private volatile static CancelOrderPop instance;

    public CancelOrderPop(Context context) {
        initUi(context);
    }

    // 对外提供一个该类的实例，考虑多线程问题，进行同步操作
    public static CancelOrderPop getInstance(Context context) {
        if (instance == null) {
            synchronized (CancelOrderPop.class) {
                if (instance == null) {
                    instance = new CancelOrderPop(context);
                }
            }
        }
        return instance;

    }

    private void initUi(Context context) {
        final View popView = LayoutInflater.from(context).inflate(R.layout.cancel_order_layout, null);
        setContentView(popView);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setClippingEnabled(false);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x3f000000);
        setBackgroundDrawable(dw);
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setOutsideTouchable(true);
        TextView tv_diss = (TextView) popView.findViewById(R.id.tv_dismiss_cancle_order);
        RecyclerView recyclerView = popView.findViewById(R.id.recy_cancle_order);
        final LinearLayout ll_top =  popView.findViewById(R.id.ll_cancle_order_top);
        //点击外部取消
        popView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = ll_top.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        tv_diss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initRecy(context, recyclerView);
    }

    private void initRecy(Context context, RecyclerView recyclerView) {
        String[] strings = {"我不想买了", "商家缺货", "其他渠道购买更优惠", "线下交易", "我信息填写错误，重新拍", "其它原因"};
        final List<String> strList = Arrays.asList(strings);
        DividerItemDecoration div = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        div.setDrawable(context.getDrawable(R.drawable.div1_item));
        recyclerView.addItemDecoration(div);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        CommonAdapter<String> adapter = new CommonAdapter<String>(context, R.layout.tv_item, strList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tv = holder.getView(R.id.tv_item);
                tv.setLayoutParams(layoutParams);
                tv.setText(s);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                tv.setPadding(0, 20, 0, 20);
                tv.setTextColor(Color.parseColor("#0096FF"));
            }
        };
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String s = strList.get(position);
                onClick.cancle(s);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    onClickCancleOrder onClick;

    public void setOnClickCancle(onClickCancleOrder onClickCancle) {
        this.onClick = onClickCancle;
    }

    public interface onClickCancleOrder {
        void cancle(String s);
    }

}
