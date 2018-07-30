package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CustomToast.MyToast;
import car.myview.RadioGroupEx;
import car.tzxb.b2b.Adapter.DiscountsAdapter;
import car.tzxb.b2b.Bean.GoodsXqBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;


/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class AddShoppingCarPop extends PopupWindow implements View.OnClickListener{
    private List<GoodsXqBean.DataBean.ProductBean> lists;
    private Context mContext;
    private TextView tv_show_num;
    private int num=1;
    private String type;
    private String pro_id;
    private String shopId;
    private int index;
    private String discountsId;
    private GoodsXqBean.DataBean.ProductBean productBean;
    private View view;
    private List<GoodsXqBean.DataBean.ProductBean.PromotionBean> promotionBeanList=new ArrayList<>();
    private DiscountsAdapter adapter;


    public AddShoppingCarPop(Context context, List<GoodsXqBean.DataBean.ProductBean> productBeanList, int i) {
        this.mContext=context;
        this.lists=productBeanList;
        this.index=i;
        initPop(context);
    }

    private void initPop(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.add_shoppingcar_layout,null);
        final View top= view.findViewById(R.id.add_shoppingcar_top);
        ImageView iv_subtract=view.findViewById(R.id.iv_subtract);
        ImageView iv_plus=view.findViewById(R.id.iv_plus);
        Button btn_add=view.findViewById(R.id.btn_add_shoppingcar);
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
        iv_plus.setOnClickListener(this);
        iv_subtract.setOnClickListener(this);
        btn_add.setOnClickListener(this);

        initDiscounts();
        initRg();
    }

  private void initDiscounts() {
        ListView lv=view.findViewById(R.id.lv_shopingcar_select_discounts);
       adapter = new DiscountsAdapter(mContext,promotionBeanList);
        lv.setAdapter(adapter);
        adapter.setClick(new DiscountsAdapter.DiscountsId() {
            @Override
            public void clickDiscountsId(String id) {
                discountsId=id;
                Log.i("传过来id",discountsId+"");
            }
        });
    }


    /**
     *  商品规格信息， 可转行的RadioGroup,
     */
    private void initRg() {
        RadioGroupEx rg_gg = view.findViewById(R.id.rg_add_shoppingcar_gg);
        final TextView tv_price=view.findViewById(R.id.tv_add_shoppingcar_price);
        final ImageView xv=view.findViewById(R.id.iv_add_shoppingcar);
        final TextView tv_stock=view.findViewById(R.id.tv_stock);
        tv_show_num = view.findViewById(R.id.tv_show_number);
        int width=DeviceUtils.dip2px(mContext,80);
        int height=DeviceUtils.dip2px(mContext,25);
        RadioGroup.LayoutParams params=new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,height);
        params.setMargins(0,0,20,20);
        for (int i = 0; i <lists.size() ; i++) {
            GoodsXqBean.DataBean.ProductBean bean=lists.get(i);
            RadioButton rb=new RadioButton(mContext);
            rb.setGravity(Gravity.CENTER);
            rb.setTextColor(Color.BLACK);
            rb.setMinHeight(height);
            rb.setMinWidth(width);
            rb.setLayoutParams(params);
            rb.setButtonDrawable(null);
            rb.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            rb.setId(i);
            String s=bean.getColor_name()+bean.getNetwork_name();
            if(!"".equals(s)){
                rb.setText(s);
            }else {
                rb.setText("默认");
            }

            rb.setTextColor(mContext.getResources().getColorStateList(R.color.tv_color2));
            rb.setBackground(mContext.getResources().getDrawable(R.drawable.gg_select));
            rg_gg.addView(rb);
        }

        rg_gg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                productBean = lists.get(checkedId);
                //图片
                Glide.with(mContext).load(productBean.getImg_url()).asBitmap().into(xv);
                //价格
                tv_price.setText(Html.fromHtml("¥"+"<big>"+ productBean.getSeal_price()+"</big>"));
                //库存
                tv_stock.setText("库存: "+ productBean.getStock_distributor_all());
                //切换选择的商品id
                pro_id= productBean.getProduct_id();
                type= productBean.getIs_point_product();
                shopId= productBean.getShop_id();
                //最低数量
                num = productBean.getMinimum_order_quantity();
                tv_show_num.setText(num+"");
                //商品优惠
                promotionBeanList=lists.get(checkedId).getPromotion();
                adapter.addAll(promotionBeanList,true);
            }
        });

        RadioButton checkButton= (RadioButton) rg_gg.getChildAt(index);
        checkButton.setChecked(true);
    }

    /**
     * 显示方法
     * @param parent
     */
    public void show(View parent){

        if(DeviceUtils.checkDeviceHasNavigationBar(MyApp.getContext())){
            int navgationHeight=DeviceUtils.getNavigationBarHeight(MyApp.getContext());
            showAtLocation(parent, Gravity.BOTTOM,0,navgationHeight);
        }else {
            showAtLocation(parent,Gravity.BOTTOM,0,0);
        }

    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.iv_subtract:

                 if(num==1||num==productBean.getMinimum_order_quantity()){
                     return;
                 }
                 num--;
                 tv_show_num.setText(num+"");
                 break;
             case R.id.iv_plus:
                 num++;
                 tv_show_num.setText(num+"");
                 break;
             case R.id.btn_add_shoppingcar:
                  if(pro_id==null){
                      MyToast.makeTextAnim(MyApp.getContext(),"请选择商品规格",0,Gravity.CENTER,0,0).show();
                      return;
                  }
                  listener.Click(num,pro_id,shopId,type,discountsId);
                  dismiss();
                  break;
         }
    }

    AddShoppingCarListener listener;
    public void setAddShoppingCar(AddShoppingCarListener addShoppingCar){
        this.listener=addShoppingCar;
    }

    public interface AddShoppingCarListener{

        void Click(int number,String pro_id,String shop_id,String type,String discoutnsId);
    }
}
