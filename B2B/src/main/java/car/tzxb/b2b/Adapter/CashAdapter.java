package car.tzxb.b2b.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import java.util.List;

import car.tzxb.b2b.R;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class CashAdapter extends MyLvBaseAdapter<String> {
    private int temPosition = 0;     //记录已经点击的chexkbox的位置
    private Drawable[] drawab;
    private boolean b;
    public CashAdapter(Context context, List<String> strings,Drawable[] drawables,boolean isShow) {
        super(context, strings);
        this.drawab=drawables;
        this.b=isShow;
    }

    @Override
    public View getItemView(final int position, View convertView, final ViewGroup parent) {
        convertView =getInflater().inflate(R.layout.my_gold_sign_item, parent, false);
        TextView tv = convertView.findViewById(R.id.tv_sign_date);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        CheckBox cb = convertView.findViewById(R.id.cb_zf);
        TextView tv_num=convertView.findViewById(R.id.tv_sign_gold_num);
        TextView tv_not_enough=convertView.findViewById(R.id.tv_balance_not_enough);
        TextView tv_zf_type=convertView.findViewById(R.id.tv_offline_payment);
        cb.setVisibility(View.VISIBLE);
        tv_num.setVisibility(View.INVISIBLE);
        if(position==0){
            tv_zf_type.setVisibility(View.VISIBLE);
            tv_zf_type.setText("在线支付方式");
        }else if(position==4){
            tv_zf_type.setVisibility(View.VISIBLE);
            tv_zf_type.setText("其它支付方式");
        }
        if(b&&position==0){
            tv_not_enough.setVisibility(View.VISIBLE);
        }
        final String name=getItem(position);
        //支付名字
        tv.setText(name);
        tv.setCompoundDrawablesWithIntrinsicBounds(drawab[position],null,null,null);
        //点击
        cb.setId(position);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition.onClick(position,name);
            }
        });
        //单选
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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

        if (position == temPosition) {
            cb.setChecked(true);

        } else {
            cb.setChecked(false);
        }
        return convertView;
    }
    ClickPosition clickPosition;
    public void setClickPosition(ClickPosition cp){
        this.clickPosition=cp;
    }
    public interface ClickPosition{
        void onClick(int index,String name);
    }

}
