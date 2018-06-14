package car.tzxb.b2b.Uis.MeCenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;

public class CollectActivity extends MyBaseAcitivity implements RadioGroup.OnCheckedChangeListener {
  @BindView(R.id.rg_collect_swich)
    RadioGroup rg_swich;
    @BindView(R.id.rb_goods)
    RadioButton rb_goods;
    @BindView(R.id.rb_shop)
    RadioButton rb_shop;
    private int index;

    @Override
    public void initParms(Bundle parms) {
        index = getIntent().getIntExtra("index",-1);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_collect;
    }

    @Override
    public void doBusiness(Context mContext) {
        initUi();

    }

    private void initUi() {
        if(index==1){
            rb_goods.setChecked(true);
        }else {
            rb_shop.setChecked(true);
        }
    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

    }
}
