package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
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
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.ArrayList;
import java.util.List;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/5/25 0025.
 */

public class TjrPop extends PopupWindow{
    private Context mContext;
   // private List<BaseStringBean> lists;
    private RecyclerView recyclerView;
  //  private List<BaseDataListBean.DataBean> beanList=new ArrayList<>();
    private RadioGroup rg_tjr;
    private View parent;
    private RadioButton rb1;
    private RadioButton rb2;
    private String province[]={"云南省","北京","天津","上海","重庆","河北省","山西省","内蒙古","辽宁省","吉林省","云南省","黑龙江省","江苏省","浙江省","安徽省","福建省"
            ,"江西省","山东省","河南省","湖北省","湖南省","广东省","广西省","海南省","西藏","陕西省","甘肃省","宁夏省","新疆省","香港","澳门","台湾"};
    private String location="云南省";
    private CommonAdapter<BaseDataListBean.DataBean> adapter;
    private String mainId;

    public TjrPop(Context context,View view) {
        super(context);
        this.mContext=context;
        this.parent=view;
        initPop(context);
    }

    private void initPop(Context context) {
        final View popView = LayoutInflater.from(context).inflate(R.layout.open_shop_tjr, null);
        recyclerView = popView.findViewById(R.id.recy_tjr);
        rg_tjr = popView.findViewById(R.id.rg_tjr);
        final View top=popView.findViewById(R.id.ll_tjr_top);
        final TextView tv_address= popView.findViewById(R.id.tv_tjr_address);
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

        initRg();

        //城市选择器
        tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tjr_province tp=new tjr_province(mContext,province);
                tp.showPow(parent);
                tp.setListener(new tjr_province.OnWheelListener() {
                    @Override
                    public void onWheelPosition(int position) {

                        location = province[position];
                        tv_address.setText(location);
                        rb1.setChecked(true);
                        getShop();
                    }
                });
            }
        });
    }

    private void initRg() {
        rb1 = rg_tjr.findViewById(R.id.rb_tjr_shop);
        rb2 = rg_tjr.findViewById(R.id.rb_tjr_name);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));

        rg_tjr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                      switch (checkedId){
                          case R.id.rb_tjr_shop:
                              rb2.setVisibility(View.INVISIBLE);
                              getShop();
                              break;
                          case R.id.rb_tjr_name:
                              getName();
                              break;
                      }
            }
        });
        rb1.setChecked(true);
    }

    private void getName() {
        Log.i("业务员",Constant.baseUrl+"item/index.php?c=Home&m=GroupBindUser"+"&id="+mainId);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl+"item/index.php?c=Home&m=GroupBindUser")
                .tag(this)
                .addParams("id",mainId)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        Log.i("业务员大小",response.getData().size()+"");
                        List<BaseDataListBean.DataBean> list2=response.getData();
                        initAdapter2(list2);
                    }
                });
    }

    private void getShop() {
        Log.i("获取门店",Constant.baseUrl+"item/index.php?c=Home&m=GroupYWYList"+"&address="+location);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl+"item/index.php?c=Home&m=GroupYWYList")
                .addParams("address",location)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        List<BaseDataListBean.DataBean> list1=response.getData();
                        initAdapter1(list1);
                }
                });
    }

    public void initAdapter1(final List<BaseDataListBean.DataBean> list1){

        adapter = new CommonAdapter<BaseDataListBean.DataBean>(mContext, R.layout.tv_item,list1) {
            @Override
            protected void convert(ViewHolder holder, BaseDataListBean.DataBean bean, int position) {
                 TextView tv=holder.getView(R.id.tv_item);
                 tv.setTextColor(Color.parseColor("#303030"));
                 tv.setText(bean.getName());
                 tv.setPadding(20,10,0,10);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                mainId = list1.get(position).getID();
                rb2.setVisibility(View.VISIBLE);
                rb2.setChecked(true);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void initAdapter2(final List<BaseDataListBean.DataBean> list2) {
        CommonAdapter<BaseDataListBean.DataBean> adaper2=new CommonAdapter<BaseDataListBean.DataBean>(mContext,R.layout.tv_item,list2) {
            @Override
            protected void convert(ViewHolder holder, BaseDataListBean.DataBean bean, int position) {
                TextView tv=holder.getView(R.id.tv_item);
                tv.setTextColor(Color.parseColor("#303030"));
                tv.setText(bean.getUsername());
                tv.setPadding(20,15,0,15);
            }
        };
        recyclerView.setAdapter(adaper2);
        adaper2.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BaseDataListBean.DataBean bean=list2.get(position);
                    listener.click(bean.getUsername(),bean.getID());
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
        void click(String name,String Id);
    }
}
