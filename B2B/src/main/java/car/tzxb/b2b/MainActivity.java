package car.tzxb.b2b;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.transition.Transition;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.BindView;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.fragments.ClassifyFragment;
import car.tzxb.b2b.fragments.HomeFragment;
import car.tzxb.b2b.fragments.JoinFragment;
import car.tzxb.b2b.fragments.MyFragment;
import car.tzxb.b2b.fragments.ShoppingCarFragment;

public class MainActivity extends MyBaseAcitivity implements BottomNavigationBar.OnTabSelectedListener{
    @BindView(R.id.navigation_bar)
    BottomNavigationBar navigationBar;

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
                .addItem(new BottomNavigationItem(R.mipmap.laberbar_icon_joinin, "加盟").setInactiveIconResource(R.mipmap.laberbar_icon_joinin2))
                .addItem(new BottomNavigationItem(R.mipmap.laberbar_icon_sc, "购物车").setInactiveIconResource(R.mipmap.laberbar_icon_sc2))
                .addItem(new BottomNavigationItem(R.mipmap.laberbar_icon_my, "我的").setInactiveIconResource(R.mipmap.laberbar_icon_my2))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.main_framelayout,new HomeFragment()).commit();

    }


    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        switch (position){
            case 0:
                transaction.replace(R.id.main_framelayout,new HomeFragment()).commit();
                break;
            case 1:
                transaction.replace(R.id.main_framelayout,new ClassifyFragment()).commit();
                break;
            case 2:
                transaction.replace(R.id.main_framelayout,new JoinFragment()).commit();
                break;
            case 3:
                transaction.replace(R.id.main_framelayout,new ShoppingCarFragment()).commit();
                break;
            case 4:
                transaction.replace(R.id.main_framelayout,new MyFragment()).commit();
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

}
