package car.tzxb.b2b.Uis.MeCenter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;

public class WithMeActivity extends MyBaseAcitivity {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_version_name)
    TextView tv_vn;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_with_me;
    }

    @Override
    public void doBusiness(Context mContext) {
         tv_title.setText("关于我们");
         tv_vn.setText("版本 "+getString(R.string.version));
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }
}
