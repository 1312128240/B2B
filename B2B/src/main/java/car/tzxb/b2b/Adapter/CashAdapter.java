package car.tzxb.b2b.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
    private Drawable[] img={getContext().getDrawable(R.mipmap.pyt_icon_zfb),getContext().getDrawable(R.mipmap.pyt_icon_wx),
            getContext().getDrawable(R.mipmap.pyt_icon_yl),getContext().getDrawable(R.mipmap.pyt_icon_xj)};
    public CashAdapter(Context context, List<String> strings) {
        super(context, strings);
    }

    @Override
    public View getItemView(final int position, View convertView, final ViewGroup parent) {
        convertView = getInflater().from(getContext()).inflate(R.layout.my_gold_sign_item, parent, false);
        TextView tv = convertView.findViewById(R.id.tv_sign_date);
        CheckBox cb = convertView.findViewById(R.id.cb_zf);
        cb.setVisibility(View.VISIBLE);
        convertView.findViewById(R.id.tv_sign_succeed).setVisibility(View.INVISIBLE);
        convertView.findViewById(R.id.tv_sign_gold_num).setVisibility(View.GONE);

        tv.setText(getItem(position));
        tv.setCompoundDrawablesWithIntrinsicBounds(img[position],null,null,null);
        //点击
        cb.setId(position);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition.onClick(position);
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
                        CheckBox temCheckBoz = (CheckBox) parent.findViewById(temPosition);
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
        void onClick(int index);
    }

}
