package car.tzxb.b2b.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import car.tzxb.b2b.Bean.DiscountsBean;
import car.tzxb.b2b.Bean.GoodsXqBean;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;

/**
 * Created by Administrator on 2018/7/30 0030.
 */

public class ShoppinCarDiscountsAdapter extends MyLvBaseAdapter<DiscountsBean.DataBean> {
    private int temPosition=0;
    public ShoppinCarDiscountsAdapter(Context context, List<DiscountsBean.DataBean> promotionBeen) {
        super(context, promotionBeen);
    }

    @Override
    public View getItemView(final int position, View convertView, final ViewGroup parent) {
        convertView=getInflater().inflate(R.layout.discounts_item,parent,false);
        TextView tv_title=convertView.findViewById(R.id.tv_discounts_title);
        RadioButton cb_select=convertView.findViewById(R.id.cb_selet_discounts);
        ListView lv=convertView.findViewById(R.id.lv_select_discounts);
     //   final GoodsXqBean.DataBean.ProductBean.PromotionBean promotionBean=getItem(position);
        DiscountsBean.DataBean dataBean=getItem(position);
        //标题
        tv_title.setText(dataBean.getTitle());
        //内部优惠数据
        List<DiscountsBean.DataBean.TitlesBean> giftBeen=dataBean.getTitles();
        cb_select.setVisibility(View.VISIBLE);
        DiscountsInnerAdapter innerAdapter=new DiscountsInnerAdapter(getContext(),giftBeen);
        lv.setAdapter(innerAdapter);
        //单选
        cb_select.setId(position);
        cb_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    buttonView.setClickable(false);
                    if (temPosition != -1) {
                        //根据id找到上次点击的chexkboxr,将它设置为false
                        RadioButton temCheckBoz =parent.findViewById(temPosition);
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


    public class DiscountsInnerAdapter extends MyLvBaseAdapter<DiscountsBean.DataBean.TitlesBean> {

        public DiscountsInnerAdapter(Context context, List<DiscountsBean.DataBean.TitlesBean> giftBeen) {
            super(context, giftBeen);
        }

        @Override
        public View getItemView(int position, View convertView, final ViewGroup parent) {
            convertView=getInflater().inflate(R.layout.tv_item,parent,false);
            int l= DeviceUtils.dip2px(getContext(),30);
            TextView tv_content=convertView.findViewById(R.id.tv_item);
            tv_content.setPadding(l,0,0,10);
            tv_content.setTextColor(Color.parseColor("#e1e1e1"));
            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            tv_content.setEllipsize(TextUtils.TruncateAt.END);
            tv_content.setMaxLines(1);
           // final GoodsXqBean.DataBean.ProductBean.PromotionBean.GiftBean gift=getItem(position);
            DiscountsBean.DataBean.TitlesBean titlesBean=getItem(position);
            tv_content.setText(titlesBean.getZp_title());
            return convertView;
        }
    }
    DiscountsId listener;
    public void setClick(DiscountsId discountsId){
        this.listener=discountsId;
    }

    public interface DiscountsId{
        void clickDiscountsId(String id);
    }
}
