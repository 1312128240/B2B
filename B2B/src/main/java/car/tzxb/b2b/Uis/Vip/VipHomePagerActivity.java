package car.tzxb.b2b.Uis.Vip;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;

public class VipHomePagerActivity extends MyBaseAcitivity {

     @BindView(R.id.vip_home_pager_actionbar)
    View actionbar;
     @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_actionbar_back)
    TextView tv_back;
    @BindView(R.id.tv_actionbar_right)
    TextView tv_right;
    @BindView(R.id.tv_regist_vip_date)
    TextView tv_date;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_vip_home_pager;
    }

    @Override
    public void doBusiness(Context mContext) {
       initView();
    }

    private void initView() {
        tv_back.setText("");
        Drawable drawable=getDrawable(R.mipmap.tab_icon_return);
        tv_back.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        actionbar.setBackgroundColor(Color.parseColor("#232429"));
        tv_title.setText("会员中心");
        tv_title.setTextColor(Color.WHITE);
        tv_right.setText("帮助");
        tv_right.setTextColor(Color.WHITE);
        tv_right.setCompoundDrawablePadding(3);
        Drawable d2=getDrawable(R.mipmap.vip_icon_help);
        tv_right.setCompoundDrawablesWithIntrinsicBounds(null,null,d2,null);

    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
}
