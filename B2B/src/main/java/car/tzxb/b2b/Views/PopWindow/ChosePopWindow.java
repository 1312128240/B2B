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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;


/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class ChosePopWindow extends PopupWindow {


 private List<String> lists;
 private Context mContext;
    public ChosePopWindow(Context context, List<String> stringList) {
        super(context);
        this.mContext=context;
        this.lists=stringList;
        initPop(context);
    }



    private void initPop(Context context) {
        final View popView = LayoutInflater.from(context).inflate(R.layout.chose_photo_pop, null);
        RecyclerView recyclerView=popView.findViewById(R.id.recy_choose);
        TextView tv_cancle =  popView.findViewById(R.id.tv_chose_cancle);
        final View top=popView.findViewById(R.id.ll_chose_parent);
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

      //数据
        final int heigh= DeviceUtils.dip2px(context,45);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration div=new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        div.setDrawable(context.getResources().getDrawable(R.drawable.div1_item));
        recyclerView.addItemDecoration(div);
        CommonAdapter<String> adapter=new CommonAdapter<String>(context,R.layout.tv_item,lists) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,heigh);
                TextView tv=holder.getView(R.id.tv_item);
                tv.setLayoutParams(layoutParams);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.parseColor("#0096FF"));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
                tv.setText(s);
            }
        };
        recyclerView.setAdapter(adapter);
        //点击
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        String str=lists.get(position);
                       if(str!=null){
                           listener.click(str);
                       }
                       dismiss();

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

   public void showPow(View parent) {

       if (DeviceUtils.checkDeviceHasNavigationBar(mContext) == true) {
           int navigationHeight = DeviceUtils.getNavigationBarHeight(mContext);
           showAtLocation(parent, Gravity.BOTTOM, 0, navigationHeight);
       } else {
           showAtLocation(parent, Gravity.BOTTOM, 0, 0);
       }

   }

   ClickListener listener;
    public void setClickListener(ClickListener clickListener){
        this.listener=clickListener;
    }
   public interface ClickListener{
       void click(String str);
   }
}
