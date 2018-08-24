package car.tzxb.b2b.Uis.MeCenter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CircleImageView.CircleImageView;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.MyCenterBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.HomePager.Vip.VipHomePagerActivity;
import car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage.AccountSecurityHomePageActivity;

import car.tzxb.b2b.Util.DataCleanManager;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.AlterDialogFragment;
import car.tzxb.b2b.Views.DialogFragments.LoadingDialog;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class SettingsActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.btn_exit_app)
    Button btn_exit;
    @BindView(R.id.tv_user_mobile)
    TextView tv_mobile;
    @BindView(R.id.img_avatar)
    CircleImageView civ;
    @BindView(R.id.tv_cache)
    TextView tv_cache;
    public static AppCompatActivity sInstance = null;


    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {

        return R.layout.activity_settings;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("设置");
        sInstance = this;
        try {
            String dataSize = DataCleanManager.getTotalCacheSize(MyApp.getContext());
            tv_cache.setText(dataSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }

    /**
     * 清除缓存
     */
    @OnClick(R.id.tv_cache)
    public void cache(){
        DataCleanManager.clearAllCache(MyApp.getContext());
        final LoadingDialog dialog=new LoadingDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(),"aaa");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                try {
                    String  dataSize = DataCleanManager.getTotalCacheSize(MyApp.getContext());
                    tv_cache.setText(dataSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },1000);
    }
    /*
      退出
     */
    @OnClick(R.id.btn_exit_app)
    public void exit() {

        final AlterDialogFragment alterDialogFragment = new AlterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "您确定要退出吗");
        bundle.putString("ok", "确定");
        bundle.putString("no", "取消");
        alterDialogFragment.setArguments(bundle);
        alterDialogFragment.show(getSupportFragmentManager(), "aaa");
        alterDialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                alterDialogFragment.dismiss();
            }

            @Override
            public void sure() {
                SPUtil.getInstance(MyApp.getContext()).dele("UserId");
                MobclickAgent.onProfileSignOff();           //友盟统计退出
                onBackPressed();
                alterDialogFragment.dismiss();
            }
        });
    }

    /**
     * 地址
     */
    @OnClick(R.id.ll_setting_address)
    public void address() {
        startActivity(MyAddressActivity.class);
    }

    /**
     * 会员中心
     */
    @OnClick(R.id.tv_vip)
    public void vip(){
        startActivity(VipHomePagerActivity.class);
    }
    /**
     * 关于
     */
    @OnClick(R.id.ll_version)
    public void version(){
        startActivity(WithMeActivity.class);
    }
    /**
     * 反馈
     */
    @OnClick(R.id.tv_feedback)
    public void feedback(){
        startActivity(FeedbackActivity.class);
    }
    /**
     * 个人资料
     */
    @OnClick(R.id.ll_my_infor)
    public void infor(){
        startActivity(PersonalDataActivity.class);
    }

    /**
     * 账户安全
     */
    @OnClick(R.id.tv_account_security)
    public void account(){
        startActivity(AccountSecurityHomePageActivity.class);
    }
    private void Refresh() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("我的个人粢料", Constant.baseUrl+"item/index.php?c=Home&m=MyCenter&user_id="+userId);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl+"item/index.php?c=Home&m=MyCenter")
                .tag(this)
                .addParams("user_id",userId)
                .build()
                .execute(new GenericsCallback<MyCenterBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(MyCenterBean response, int id) {
                        MyCenterBean.DataBean.UserInfoBean userBean=response.getData().getUserInfo();
                        tv_mobile.setText(userBean.getUsername());
                        String imgUrl=userBean.getHead_img();
                        if(imgUrl!=null){
                            Glide.with(MyApp.getContext()).load(userBean.getHead_img()).asBitmap().error(R.mipmap.my_icon_dhi).into(civ);
                        }

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sInstance = null;
    }

}
