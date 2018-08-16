package car.tzxb.b2b.Views.PopWindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import car.tzxb.b2b.Adapter.MyLvBaseAdapter;
import car.tzxb.b2b.Bean.DiscountsBean;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;


/**
 * Created by Administrator on 2018/7/28 0028.
 */

public class Modify_DiscountsPop extends PopupWindow {
    private Context mContext;
    private List<DiscountsBean.DataBean> lists;

    ModityDiscount listener;
    public void setModity(ModityDiscount modity){
        this.listener=modity;
    }


    public Modify_DiscountsPop(Context context, List<DiscountsBean.DataBean> beanList) {
        super(context);
        this.mContext = context;
        this.lists=beanList;
        initPop();
    }

    private void initPop() {
        final View popView = LayoutInflater.from(mContext).inflate(R.layout.modify_disconts_pop, null);
        ListView listView= popView.findViewById(R.id.recy_modify_disconts);
        final View top = popView.findViewById(R.id.ll_modify_disconts_top);
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
        ShoppinCarDiscountsAdapter adapter=new ShoppinCarDiscountsAdapter(mContext,lists);
        listView.setAdapter(adapter);
    }

    /**
     * 显示 方法
     * @param parent
     */
    public void showPow(View parent) {
        if (DeviceUtils.checkDeviceHasNavigationBar(mContext)) {
            int navigationHeight = DeviceUtils.getNavigationBarHeight(mContext);
            showAtLocation(parent, Gravity.BOTTOM, 0, navigationHeight);
        } else {
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }
    }

    //--------------------------------------------内部listview---------------------------
    public class ShoppinCarDiscountsAdapter extends MyLvBaseAdapter<DiscountsBean.DataBean> {
        private int temPosition=-1;



        public ShoppinCarDiscountsAdapter(Context context, List<DiscountsBean.DataBean> promotionBeen) {
            super(context, promotionBeen);
        }

        @Override
        public View getItemView(final int position, View convertView, final ViewGroup parent) {
            convertView=getInflater().inflate(R.layout.discounts_item,parent,false);
            TextView tv_title=convertView.findViewById(R.id.tv_discounts_title);
            CheckBox cb_select=convertView.findViewById(R.id.cb_selet_discounts);
            ListView lv=convertView.findViewById(R.id.lv_select_discounts);
            final DiscountsBean.DataBean dataBean=getItem(position);
            //标题
            tv_title.setText(dataBean.getTitle());
            //内部优惠数据
            final List<DiscountsBean.DataBean.TitlesBean> giftBeen=dataBean.getTitles();
            cb_select.setVisibility(View.VISIBLE);
           DiscountsInnerAdapter innerAdapter=new DiscountsInnerAdapter(getContext(),giftBeen);
            lv.setAdapter(innerAdapter);
            //单选
            cb_select.setId(position);
            cb_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cxId=dataBean.getId();
                    String zpId="";
                    if("2".equals(dataBean.getZp_type())){
                       zpId=giftBeen.get(0).getId();
                    }
                    listener.modity(cxId,zpId);
                    dismiss();
                }
            });
            cb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        if (temPosition != -1) {
                            //根据id找到上次点击的CheckBox,将它设置为false.
                            CheckBox tempCheckBox = parent.findViewById(temPosition);
                            if (tempCheckBox != null) {
                                tempCheckBox.setChecked(false);
                            }
                        }
                        //保存当前选中CheckBox的id值
                        temPosition = buttonView.getId();

                    } else {    //当CheckBox被选中,又被取消时,将tempPosition重新初始化.
                        temPosition = -1;
                    }
                }
            });
            if (position == temPosition) {
                //比较位置是否一样,一样就设置为选中,否则就设置为未选中.
                cb_select.setChecked(true);
            }else {
                cb_select.setChecked(false);
            }

            return convertView;
        }


        public class DiscountsInnerAdapter extends MyLvBaseAdapter<DiscountsBean.DataBean.TitlesBean> {

            public DiscountsInnerAdapter(Context context, List<DiscountsBean.DataBean.TitlesBean> giftBeen) {
                super(context, giftBeen);
            }

            @Override
            public View getItemView(int position, View convertView, final ViewGroup parent) {
                convertView=getInflater().inflate(R.layout.my_gold_sign_item,parent,false);
                LinearLayout ll_gold=convertView.findViewById(R.id.ll_gold_sign);
                TextView tv=convertView.findViewById(R.id.tv_sign_date);
                TextView tv_num=convertView.findViewById(R.id.tv_sign_gold_num);

                DiscountsBean.DataBean.TitlesBean titlesBean=getItem(position);
                ll_gold.setPadding(60,0,0,10);
                //名字
                tv.setText(titlesBean.getZp_title());
                tv.setTextColor(Color.parseColor("#BDBDBD"));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
                tv.setSingleLine(true);
                tv.setEllipsize(TextUtils.TruncateAt.END);
                //数量
                tv_num.setTextColor(Color.parseColor("#BDBDBD"));
                tv_num.setGravity(Gravity.END);
                tv_num.setText("x"+titlesBean.getZp_number());
                return convertView;
            }

        }
    }
    public interface ModityDiscount{
        void modity(String cxid,String zpid);
    }
}