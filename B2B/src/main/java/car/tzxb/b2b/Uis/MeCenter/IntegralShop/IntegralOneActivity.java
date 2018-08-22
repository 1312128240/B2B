package car.tzxb.b2b.Uis.MeCenter.IntegralShop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;

public class IntegralOneActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
     @BindView(R.id.rg_ingegral)
    RadioGroup rg;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_integral_one;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("积分商城");
        initRg();
    }

    private void initRg() {
        String str[]={"无双剑姬","无双剑姬","无双剑姬","无双剑姬","无双剑姬","无双剑姬","无双剑姬"};
        RadioButton rb=null;
        final GradientDrawable gd=new GradientDrawable();
        gd.setCornerRadius(30);
        gd.setColor(Color.RED);
        int i1= DeviceUtils.dip2px(MyApp.getContext(),6);
        int i2=DeviceUtils.dip2px(MyApp.getContext(),12);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(i1,0,i1,0);
        for (int i = 0; i <str.length; i++) {
            rb=new RadioButton(this);
            rb.setText(str[i]);
            rb.setId(i);
            rb.setLayoutParams(params);
            rb.setButtonDrawable(null);
            rb.setPadding(i2,0,i2,0);
            rg.addView(rb);
            rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton rb, boolean isChecked) {
                     if(isChecked){
                         rb.setBackground(gd);
                         rb.setTextColor(Color.WHITE);
                     }else {
                         rb.setBackground(null);
                         rb.setTextColor(Color.BLACK);
                     }
                }
            });
        }

        RadioButton rb1=rg.findViewById(0);
        rb1.setChecked(true);

    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }
}
