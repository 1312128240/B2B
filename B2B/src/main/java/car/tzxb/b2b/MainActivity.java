package car.tzxb.b2b;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.lang.ref.WeakReference;
import butterknife.BindView;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Interface.WindowFocusChang;
import car.tzxb.b2b.Util.ActivityManager;
import car.tzxb.b2b.fragments.ClassifyFragment;
import car.tzxb.b2b.fragments.HomeFragment;
import car.tzxb.b2b.fragments.MyFragment;
import car.tzxb.b2b.fragments.ShoppingCarFragment;

public class MainActivity extends MyBaseAcitivity implements BottomNavigationBar.OnTabSelectedListener{
    @BindView(R.id.navigation_bar)
    BottomNavigationBar navigationBar;

    private static boolean isExit = false;
    public Handler mHandler = new MyHandler(this);
    private Fragment mFragment;
    private ClassifyFragment classifyFragment;
    private ShoppingCarFragment shopingFrgament;
    private MyFragment myFrgament;
    private HomeFragment homeFragment;


    private static class MyHandler extends Handler {
        private final WeakReference<Activity> mActivity;

        private MyHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }
            isExit = false;
        }
    }

    @Override
    public void initParms(Bundle parms) {

    }


    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void doBusiness(Context mContext) {
              initNavigationBar();

              initFragment();


    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        classifyFragment = new ClassifyFragment();
        shopingFrgament = new ShoppingCarFragment();
        myFrgament = new MyFragment();

        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.main_framelayout,homeFragment).commit();
        mFragment = homeFragment;
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    private void initNavigationBar() {
        navigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        navigationBar.setTabSelectedListener(this);
        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navigationBar.setActiveColor("#FA3314").setInActiveColor("#303030").setBarBackgroundColor("#FFFFFF");
        navigationBar.addItem(new BottomNavigationItem(R.mipmap.laberbar_icon_home, "首页").setInactiveIconResource(R.mipmap.laberbar_icon_home2))
                .addItem(new BottomNavigationItem(R.mipmap.laberbar_icon_df, "分类").setInactiveIconResource(R.mipmap.laberbar_icon_df2))
                .addItem(new BottomNavigationItem(R.mipmap.laberbar_icon_sc, "购物车").setInactiveIconResource(R.mipmap.laberbar_icon_sc2))
                .addItem(new BottomNavigationItem(R.mipmap.laberbar_icon_my, "我的").setInactiveIconResource(R.mipmap.laberbar_icon_my2))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成

    }

    private void switchFragment(Fragment fragment) {

        //判断当前显示的Fragment是不是切换的Fragment
        if(mFragment != fragment) {
            //判断切换的Fragment是否已经添加过
            if (!fragment.isAdded()) {
                //如果没有，则先把当前的Fragment隐藏，把切换的Fragment添加上
               getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).hide(mFragment)
                        .add(R.id.main_framelayout,fragment).commit();

            } else {
                //如果已经添加过，则先把当前的Fragment隐藏，把切换的Fragment显示出来
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).hide(mFragment).show(fragment).commit();
            }
            mFragment = fragment;
        }
    }
    @Override
    public void onTabSelected(int position) {

            switch (position){
                case 0:
                    switchFragment(homeFragment);
                    break;
                case 1:
                    switchFragment(classifyFragment);

                    break;
                case 2:

                    switchFragment(shopingFrgament);
                    break;
                case 3:
                    switchFragment(myFrgament);
                    break;

            }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void exit() {
        if (!isExit) {
            isExit = true;
            showToast("再按一次将退出应用");
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {

            ActivityManager.getInstance().exit();

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        listener.focus(hasFocus);
    }
    WindowFocusChang listener;
    public void setFocusChanged(WindowFocusChang focusChanged){
        this.listener=focusChanged;
    }
}
