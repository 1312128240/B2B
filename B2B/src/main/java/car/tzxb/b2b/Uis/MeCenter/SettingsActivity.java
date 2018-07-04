package car.tzxb.b2b.Uis.MeCenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.AlterDialogFragment;

public class SettingsActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.btn_exit_app)
    Button btn_exit;
    @BindView(R.id.tv_user_mobile)
    TextView tv_mobile;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        getWindow().setEnterTransition(new Explode().setDuration(300));
        getWindow().setExitTransition(new Explode().setDuration(300));
        return R.layout.activity_settings;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("设置");
        initUI();
    }

    private void initUI() {
        String userId= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        String mobile=SPUtil.getInstance(MyApp.getContext()).getMobile("Mobile",null);
        if(userId!=null){
            btn_exit.setVisibility(View.VISIBLE);
            tv_mobile.setText(mobile);
        }else {
            btn_exit.setVisibility(View.INVISIBLE);
            tv_mobile.setText("未登录");
        }

    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }
     @OnClick(R.id.btn_exit_app)
    public void exit(){

         final AlterDialogFragment alterDialogFragment=new AlterDialogFragment();
         Bundle bundle=new Bundle();
         bundle.putString("title","您确定要退出吗");
         bundle.putString("ok","确定");
         bundle.putString("no","取消");
         alterDialogFragment.setArguments(bundle);
         alterDialogFragment.show(getSupportFragmentManager(),"aaa");
         alterDialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
             @Override
             public void cancle() {
                 alterDialogFragment.dismiss();
             }

             @Override
             public void sure() {
                 SPUtil.getInstance(MyApp.getContext()).dele("UserId");
                 onBackPressed();
                 alterDialogFragment.dismiss();
             }
         });
     }
}
