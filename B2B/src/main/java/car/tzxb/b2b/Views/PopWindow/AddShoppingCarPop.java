package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.text.Html;
import android.text.TextUtils;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import java.util.ArrayList;
import java.util.List;

import car.myview.CustomToast.MyToast;
import car.myview.FlexRadioGroupPackage.FlexRadioGroup;
import car.tzxb.b2b.Adapter.MyLvBaseAdapter;
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
    private String pro_id;
    private String shopId;
    private int index;
    private String discountsId;
    private int kc;
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
    }


    /**
     *  商品规格信息， 可转行的RadioGroup,
     */
    private void initRg() {
        FlexRadioGroup rg_gg = view.findViewById(R.id.rg_add_shoppingcar_gg);
        final TextView tv_price=view.findViewById(R.id.tv_add_shoppingcar_price);
        final ImageView xv=view.findViewById(R.id.iv_add_shoppingcar);
        final TextView tv_stock=view.findViewById(R.id.tv_stock);
        tv_show_num = view.findViewById(R.id.tv_show_number);
        int width=DeviceUtils.dip2px(mContext,80);
        int height=DeviceUtils.dip2px(mContext,25);
        FlexboxLayout.LayoutParams layoutParams=new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
        layoutParams.setMargins(15,0,0,15);
        for (int i = 0; i <lists.size() ; i++) {
            GoodsXqBean.DataBean.ProductBean bean=lists.get(i);
            RadioButton rb=new RadioButton(mContext);
            rb.setGravity(Gravity.CENTER);
            rb.setMinHeight(height);
            rb.setMinWidth(width);
            rb.setLayoutParams(layoutParams);
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
        rg_gg.setOnCheckedChangeListener(new FlexRadioGroup.OnCheckedChangeListener() {



            @Override
            public void onCheckedChanged(@IdRes int checkedId) {
                productBean = lists.get(checkedId);
                //图片
                Glide.with(mContext).load(productBean.getPic()).asBitmap().into(xv);
                //价格
                tv_price.setText(Html.fromHtml("¥"+"<big>"+ productBean.getSeal_price()+"</big>"));
                //库存
                kc = Integer.valueOf(productBean.getStock_headquarters());
                tv_stock.setText("库存: "+ kc);
                //切换选择的商品id
                pro_id= productBean.getProduct_id();
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

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.iv_subtract:
                 if(num==1||num==productBean.getMinimum_order_quantity()){
                     return;
                 }
                 num--;
                 tv_show_num.setText(num+"");
                 adapter.notifyDataSetChanged();
                 break;
             case R.id.iv_plus:
                 num++;
                 tv_show_num.setText(num+"");
                 adapter.notifyDataSetChanged();
                 break;
             case R.id.btn_add_shoppingcar:
                  if(productBean.getMinimum_order_quantity()>kc){
                      MyToast.makeTextAnim(MyApp.getContext(),"当前库存不足(余:"+kc+")",0,Gravity.CENTER,0,0).show();
                      return;
                  }

                  listener.Click(num,pro_id,shopId,discountsId);
                  dismiss();
                  break;
         }
    }

    AddShoppingCarListener listener;
    public void setAddShoppingCar(AddShoppingCarListener addShoppingCar){
        this.listener=addShoppingCar;
    }

    public interface AddShoppingCarListener{

        void Click(int number,String pro_id,String shop_id,String discoutnsId);
    }
    //------------------------------------------------------内部listview

    public class DiscountsAdapter extends MyLvBaseAdapter<GoodsXqBean.DataBean.ProductBean.PromotionBean> {
        private int temPosition=-1;



        public DiscountsAdapter(Context context, List<GoodsXqBean.DataBean.ProductBean.PromotionBean> promotionBeen) {
            super(context, promotionBeen);
        }

        @Override
        public View getItemView(final int position, View convertView, final ViewGroup parent) {
            convertView=getInflater().inflate(R.layout.discounts_item,parent,false);
            TextView tv_title=convertView.findViewById(R.id.tv_discounts_title);
            CheckBox cb_select=convertView.findViewById(R.id.cb_selet_discounts);
            ListView lv=convertView.findViewById(R.id.lv_select_discounts);
            final GoodsXqBean.DataBean.ProductBean.PromotionBean promotionBean=getItem(position);
           //标题
            tv_title.setText(promotionBean.getTitle());
            //内部优惠数据
            final List<GoodsXqBean.DataBean.ProductBean.PromotionBean.GiftBean> giftBeen=promotionBean.getGift();
            cb_select.setVisibility(View.VISIBLE);
            DiscountsInnerAdapter innerAdapter=new DiscountsInnerAdapter(getContext(),giftBeen);
            lv.setAdapter(innerAdapter);
            //单选
            double totalPrice=num*productBean.getSeal_price();
            if("mjt".equals(promotionBean.getType())){
                 if(num<promotionBean.getFull_amount()){
                     cb_select.setBackgroundColor(Color.parseColor("#BDBDBD"));
                     cb_select.setClickable(false);
                     tv_title.setTextColor(Color.parseColor("#BDBDBD"));
                 }
            }else {
                if(totalPrice<promotionBean.getFull_amount()){
                    cb_select.setBackgroundColor(Color.parseColor("#BDBDBD"));
                    cb_select.setClickable(false);
                    tv_title.setTextColor(Color.parseColor("#BDBDBD"));
                }
            }
            cb_select.setId(position);
            cb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    discountsId=promotionBean.getId();
                    if (isChecked) {
                        buttonView.setClickable(false);
                        if (temPosition != -1) {
                            //根据id找到上次点击的chexkboxr,将它设置为false
                            CheckBox temCheckBoz =parent.findViewById(temPosition);
                            if (temCheckBoz != null) {
                                temCheckBoz.setChecked(false);
                                temCheckBoz.setClickable(true);
                            }
                        }
                        //保存当前选中的chexkbox的值
                        temPosition = buttonView.getId();
                    } else {
                        temPosition = -1;
                    }

                }
            });


            return convertView;
        }


        public class DiscountsInnerAdapter extends MyLvBaseAdapter<GoodsXqBean.DataBean.ProductBean.PromotionBean.GiftBean> {

            public DiscountsInnerAdapter(Context context, List<GoodsXqBean.DataBean.ProductBean.PromotionBean.GiftBean> giftBeen) {
                super(context, giftBeen);
            }

            @Override
            public View getItemView(int position, View convertView, final ViewGroup parent) {
                convertView=getInflater().inflate(R.layout.tv_item,parent,false);
                int l= DeviceUtils.dip2px(getContext(),30);
                TextView tv_content=convertView.findViewById(R.id.tv_item);
                tv_content.setPadding(l,0,0,10);
                tv_content.setTextColor(Color.parseColor("#BDBDBD"));
                tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                tv_content.setEllipsize(TextUtils.TruncateAt.END);
                tv_content.setMaxLines(1);
                final GoodsXqBean.DataBean.ProductBean.PromotionBean.GiftBean gift=getItem(position);
                tv_content.setText(gift.getZp_title());
                return convertView;
            }
        }
    }
}
