package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import java.util.ArrayList;
import java.util.List;

import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/5/25 0025.
 */

public class TjrPop extends PopupWindow {
     private Context mContext;
     private List<BaseStringBean> lists;
    private RecyclerView recyclerView;
    private CommonAdapter<BaseStringBean> adapter;


    public TjrPop(Context context,List<BaseStringBean> list) {
        super(context);
        this.mContext=context;
        this.lists=list;
        initPop(context);
    }

    private void initPop(Context context) {
        final View popView = LayoutInflater.from(context).inflate(R.layout.open_shop_tjr, null);
        recyclerView = popView.findViewById(R.id.recy_tjr);
        final View top=popView.findViewById(R.id.ll_tjr_top);
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
        initRecy();


    }



    public void showPow(View parent) {

        if (DeviceUtils.checkDeviceHasNavigationBar(mContext) == true) {
            int navigationHeight = DeviceUtils.getNavigationBarHeight(mContext);
            showAtLocation(parent, Gravity.BOTTOM, 0, navigationHeight);
        } else {
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }
    }

    public void initRecy(){
        final int heigh= DeviceUtils.dip2px(mContext,40);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration div=new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
        div.setDrawable(mContext.getResources().getDrawable(R.drawable.div1_item));
        recyclerView.addItemDecoration(div);


        adapter = new CommonAdapter<BaseStringBean>(mContext, R.layout.tv_item,lists) {
            @Override
            protected void convert(ViewHolder holder, BaseStringBean bean, int position) {
                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,heigh);
                TextView tv=holder.getView(R.id.tv_item);
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setPadding(20,0,0,0);
                tv.setTextColor(Color.BLACK);
                tv.setLayoutParams(layoutParams);
                tv.setText(bean.getShop_name());
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String pro=lists.get(position).getShop_name();
                getData(pro);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void getData(String pro) {
       Log.i("业务员",Constant.baseUrl+"item/index.php?m=shop"+"&address="+pro+"&user_type=sj");
        final List<BaseStringBean> strBeanList=new ArrayList<>();
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"item/index.php?m=shop")
                .addParams("address",pro)
                .addParams("user_type","sj")
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        List<BaseDataListBean.DataBean> beanList=response.getData();
                        if(beanList.size()==0){
                            MyToast.makeTextAnim(mContext,"该省暂无数据",0,Gravity.CENTER,0,0).show();
                        }else {
                            for (int i = 0; i <beanList.size() ; i++) {
                                BaseDataListBean.DataBean dataBean=beanList.get(i);
                                BaseStringBean bean=new BaseStringBean(dataBean.getShop_name(),dataBean.getID());
                                strBeanList.add(bean);
                            }

                        }
                        adapter.add(strBeanList,true);




                    }
                });
        //获取业务员
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                     String id=strBeanList.get(position).getID();
                     getYwy(id);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void getYwy(String id) {
      final List<BaseStringBean> stringBeanList=new ArrayList<>();
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"item/index.php?m=BindUser")
                .addParams("user_id",id)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                          List<BaseDataListBean.DataBean> beanlist=response.getData();
                        if(beanlist.size()==0){
                            MyToast.makeTextAnim(mContext,"暂无数据",0,Gravity.CENTER,0,0).show();
                        }else {
                            for (int i = 0; i <beanlist.size() ; i++) {
                                BaseDataListBean.DataBean bean=beanlist.get(i);

                                BaseStringBean strignBean=new BaseStringBean(bean.getUsername(),bean.getID());
                                stringBeanList.add(strignBean);
                            }

                        }
                        adapter.add(stringBeanList,true);

                    }
                });
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BaseStringBean bean=stringBeanList.get(position);
                if(bean!=null){
                    listener.click(bean);
                }
                dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
    ClickListener listener;
    public void setClickListener(ClickListener clickListener){
        this.listener=clickListener;
    }

    public interface ClickListener{
        void click(BaseStringBean bean);
    }
}
