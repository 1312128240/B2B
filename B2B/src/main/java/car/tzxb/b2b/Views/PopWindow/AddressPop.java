package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.Bean.MyAddressBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.MeCenter.MyAddressActivity;
import car.tzxb.b2b.Util.DeviceUtils;


/**
 * Created by Administrator on 2018/9/26 0026.
 */

public class AddressPop extends PopupWindow implements View.OnClickListener{

    public Context mContext;
    public ClickAddress listener;
    public AddressPop(Context context, List<MyAddressBean.DataBean.AddressBean> beanList) {
        this.mContext=context;
        initPop(context,beanList);
    }


    public void setClickAddress(ClickAddress clickAddress){
        this.listener=clickAddress;
    }

    private void initPop(Context context, final List<MyAddressBean.DataBean.AddressBean> beanList) {
        View view= LayoutInflater.from(context).inflate(R.layout.address_pop,null);
        setContentView(view);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setClippingEnabled(false);
        ColorDrawable dw = new ColorDrawable(0x70000000);
        setBackgroundDrawable(dw);
        setAnimationStyle(R.style.mypopwindow_anim_style);

        TextView tv1=view.findViewById(R.id.tv_chose_address);
        TextView tv2=view.findViewById(R.id.tv_add_address);
        RecyclerView recy=view.findViewById(R.id.recy_address);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        //地址
        recy.setLayoutManager(new LinearLayoutManager(mContext));
        recy.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        CommonAdapter<MyAddressBean.DataBean.AddressBean> adapter=new CommonAdapter<MyAddressBean.DataBean.AddressBean>(MyApp.getContext(),R.layout.my_gold_sign_item,beanList) {
            @Override
            protected void convert(ViewHolder holder,MyAddressBean.DataBean.AddressBean dataBean, int position) {
                  //姓名
                TextView tv=holder.getView(R.id.tv_offline_payment);
                tv.setVisibility(View.VISIBLE);
                tv.setBackgroundColor(Color.WHITE);
                String mobile=dataBean.getMobile();
                if(mobile.length()>10){
                    String maskNumber = mobile.substring(0,3)+"****"+mobile.substring(7,mobile.length());
                    tv.setText(dataBean.getUser_name()+"            "+maskNumber);
                }
                //详细地址
                holder.setText(R.id.tv_sign_date,dataBean.getAddress());
                //图片
                Drawable d=mContext.getDrawable(R.mipmap.store_icon_right);
                TextView tv_right=holder.getView(R.id.tv_sign_gold_num);
                tv_right.setSingleLine(true);
                tv_right.setEllipsize(TextUtils.TruncateAt.END);
                tv_right.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,d,null);

            }
        };
        recy.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    MyAddressBean.DataBean.AddressBean bean=beanList.get(position);
                    listener.click(bean);
                    dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    /**
     * 弹出方法
     * @param
     */
    public void show(View parent){
        int NavigationBarHeight= DeviceUtils.getNavigationBarHeight(mContext);
        boolean b=DeviceUtils.checkDeviceHasNavigationBar(mContext);
        if(b){
            showAtLocation(parent, Gravity.BOTTOM , 0,NavigationBarHeight);
        }else {
            showAtLocation(parent, Gravity.BOTTOM , 0,0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_chose_address:
                dismiss();
                break;
            case R.id.tv_add_address:
                dismiss();
                Intent intent=new Intent(mContext, MyAddressActivity.class);
                mContext.startActivity(intent);
                break;
        }
    }
    public interface  ClickAddress{
        void click(MyAddressBean.DataBean.AddressBean bean);
    }
}
